package monster;

import game.GameConstants;
import map.Map;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {		// for debug
        return String.format("(%d, %d)", x, y);
    }
}


/**
 * This class is used to represent a path.
 * The path consists of each points in the path.
 *
 * @author  Hang Chen
 * @version 0.2
 */
class Path implements GameConstants {
    private ArrayList<Point> data;

    Path() {
        data = new ArrayList<>();
    }

    int getNextI() {	// grid coordinates
        return data.get(0).x;
    }

    int getNextJ() {
        return data.get(0).y;
    }

    int getNextX() {	// pixel coordinates
        return getNextI() * CELL_WIDTH;
    }

    int getNextY() {
        return getNextJ() * CELL_HEIGHT;
    }

    void addPoint(int i, int j) {
        data.add(new Point(i, j));
    }

    void removeFirst() {
        data.remove(0);
    }

    void clear() {
        data.clear();
    }

    int size() {
        return data.size();
    }

    public String toString() {		// for debug
        String s = "";
        for (int k=0; k<size()-1; ++k)
            s += data.get(k) + " -> ";
        s += data.get(size()-1);
        return s;
    }
}


/**
 * This class is used to encapsulate some path finding algorithm.
 * Now BFS and Random Path is supported.
 *
 * @author  Hang Chen
 * @version 0.2
 */
class Brain implements GameConstants {
    private final static int[] dxs = {-1, 1, 0, 0};
    private final static int[] dys = {0, 0, -1, 1};
    private boolean[][] viz;
    private int[][] fa;
    private int eer;	// estimate explosion range

    Brain(int eer) {
        this.eer = eer;
        viz = new boolean[CELL_NUM_X][CELL_NUM_X];
        fa = new int[CELL_NUM_X][CELL_NUM_Y];
        reset();
    }

    boolean isMovable(Map m, int i, int j) {	// filter out explosion or near bomb area
        if (!m.isInMap(i, j)) return false;
        if (m.isAtExplosion(i, j)) return false;
        if (m.isInExplosionRange(i, j, eer)) return false;
        return m.isAvailable(i, j);
    }

    private void reset() {
        for (int i=0; i<CELL_NUM_X; ++i)
            for (int j=0; j<CELL_NUM_Y; ++j) {
                viz[i][j] = false;
                fa[i][j] = -1;
            }
    }

    private boolean bfs(Map m, int si, int sj, int ti, int tj) {
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(si, sj));

        while (q.size()>0) {
            Point p = q.pop();
            if (p.x==ti && p.y==tj) return true;

            for (int k=0; k<4; ++k) {
                int ii = p.x + dxs[k];
                int jj = p.y + dys[k];
                if (isMovable(m, ii, jj) && !viz[ii][jj]) {
                    q.addLast(new Point(ii, jj));
                    fa[ii][jj] = k;
                    viz[ii][jj] = true;
                }
            }
        }
        return false;
    }

    /**
     * Random select a visible position in the map. Must be called after bfs.
     *
     * @return A random visible position
     */
    private Point randomSelectVizPos() {
        int cnt = 0, p = 0;

        // count all visible position
        for (int i=0; i<CELL_NUM_X; ++i)
            for (int j=0; j<CELL_NUM_Y; ++j)
                if (viz[i][j]) cnt++;

        // random select a position
        int stp = (int)(cnt*Math.random()) + 1;
        for (int i=0; i<CELL_NUM_X; ++i) {
            for (int j=0; j<CELL_NUM_Y; ++j) {
                if (viz[i][j]) p++;
                if (p==stp) return new Point(i, j);
            }
        }
        return null;
    }


    private void genPath(Path p, int i, int j, int si, int sj) {
        int d = fa[i][j];
        if (d >= 0 && (i!=si || j!=sj)) {
            int fi = i - dxs[fa[i][j]];
            int fj = j - dys[fa[i][j]];
            genPath(p, fi, fj, si, sj);
            p.addPoint(i, j);
        }
    }


    private void genRandomPath(Path p, int si, int sj) {
        Point rp = randomSelectVizPos();
        if (rp!=null) genPath(p, rp.x, rp.y, si, sj);
    }

    void findPath(Map m, Path p, int si, int sj, int ti, int tj) {
        p.clear();
        reset();
        if (bfs(m, si, sj, ti, tj)) genPath(p, ti, tj, si, sj);
        else genRandomPath(p, si, sj);		// if cannot find a path, take a random one instead
    }

    void randomPath(Map m, Path p, int si, int sj) {
        if (p.size()==0) {		// generate new path only if the current one used up
            reset();
            bfs(m, si, sj, -1, -1);
            genRandomPath(p, si, sj);
        }
    }
}