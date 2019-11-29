package monster;

import game.GameConstants;
import map.Map;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


/**
 * This class is used to encapsulate some path finding algorithm.
 * Now BFS and Random Path is supported.
 *
 * @author  Hang Chen
 * @version 0.2
 */
public class Brain implements GameConstants {
    public final static int[] dxs = {-1, 1, 0, 0};
    public final static int[] dys = {0, 0, -1, 1};
    public boolean[][] viz;
    public int[][] fa;
    public int eer;	// estimate explosion range

    public Brain(int eer) {
        this.eer = eer;
        viz = new boolean[CELL_NUM_X][CELL_NUM_X];
        fa = new int[CELL_NUM_X][CELL_NUM_Y];
        reset();
    }

    public Brain() {
        this(1);
    }

    public boolean isMovable(Map m, int i, int j) {	// filter out explosion or near bomb area
        if (!m.isInMap(i, j)) return false;
        if (m.isAtExplosion(i, j)) return false;
        if (m.isInExplosionRange(i, j, eer)) return false;
        return m.isAvailable(i, j);
    }

    public void reset() {
        for (int i=0; i<CELL_NUM_X; ++i)
            for (int j=0; j<CELL_NUM_Y; ++j) {
                viz[i][j] = false;
                fa[i][j] = -1;
            }
    }

    public boolean bfs(Map m, int si, int sj, int ti, int tj) {
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
    public Point randomSelectVizPos() {
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


    public void genPath(Path p, int i, int j, int si, int sj) {
        int d = fa[i][j];
        if (d >= 0 && (i!=si || j!=sj)) {
            int fi = i - dxs[fa[i][j]];
            int fj = j - dys[fa[i][j]];
            genPath(p, fi, fj, si, sj);
            p.addPoint(i, j);
        }
    }


    public void genRandomPath(Path p, int si, int sj) {
        Point rp = randomSelectVizPos();
        if (rp!=null) genPath(p, rp.x, rp.y, si, sj);
    }

    public void findPath(Map m, Path p, int si, int sj, int ti, int tj) {
        p.clear();
        reset();
        if (bfs(m, si, sj, ti, tj)) genPath(p, ti, tj, si, sj);
        else genRandomPath(p, si, sj);		// if cannot find a path, take a random one instead
    }

    public void randomPath(Map m, Path p, int si, int sj) {
        if (p.size()==0) {		// generate new path only if the current one used up
            reset();
            bfs(m, si, sj, -1, -1);
            genRandomPath(p, si, sj);
        }
    }
}