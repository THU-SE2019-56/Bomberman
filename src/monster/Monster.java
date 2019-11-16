package monster;

import java.text.BreakIterator;
import java.util.*;
import map.Map;
import player.Player;
import game.GameConstants;


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

	Brain() {
		this(1);
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
		int stp = (int)(cnt*Math.random());
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


/**
 * The Monster class.
 * Monster will random walk on the whole map, until distance between the player smaller than ALERT_DISTANCE.
 * Then the monster will follow the player, until get killed or be rid of, or collide with the player (
 * in this case the player's HP will reduce by HP_LOSS_BY_MONSTER).
 *
 * @author  Hang Chen
 * @version 0.2
 */
public class Monster implements GameConstants {
	private boolean alive;
	private boolean alert;		// whether the monster is in alert state
	private int velocity;
	private int oldDirection;
	private int direction;
	private int x;
	private int y;
	private Brain brain;
	private Path path;

	/**
	 * Create the monster at random position
	 */
	public Monster(Map m) {
		while (true) {
			int i = (int)(CELL_NUM_X*Math.random());
			int j = (int)(CELL_NUM_Y*Math.random());
			if (m.isAvailable(i, j)) {
				this.x = i * CELL_WIDTH;
				this.y = j * CELL_HEIGHT;
				init();
				// brain.findPath(m, path, i, j, 0, 0);
				// System.out.println(path);
				break;
			}
		}
	}

	/**
	 * Create a monster at the given position (pixel coordinates)
	 */
	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
		init();
	}

	private void init() {
		this.alive = true;
		this.alert = false;
		this.velocity = 3 + (int)(3*Math.random());
		this.direction = -1;
		this.oldDirection = 0;
		brain = new Brain();
		path = new Path();
	}

	private int nextDirection(Player p, Map m) {
		if (path.size() > 0) {
			// compute direction
			int dx = path.getNextX() - this.x;
			int dy = path.getNextY() - this.y;
			if (Math.abs(dx) < this.velocity) dx = 0;
			if (Math.abs(dy) < this.velocity) dy = 0;

			if (dx == 0 && dy == 0) {
				this.x = path.getNextX();
				this.y = path.getNextY();
				path.removeFirst();
				// replanning if the next position in the path is invalid or in explosion
				if (path.size()>0 && !brain.isMovable(m, path.getNextI(), path.getNextJ()))
					path.clear();
				return DIRECTION_STOP;
			}
			if (dx > 0 && dy == 0) return DIRECTION_RIGHT;
			if (dx < 0 && dy == 0) return DIRECTION_LEFT;
			if (dx == 0 && dy > 0) return DIRECTION_DOWN;
			if (dx == 0 && dy < 0) return DIRECTION_UP;
		}
		return DIRECTION_STOP;
	}

	void updateAlert(Player p) {
		int mi = Math.round((float) x/CELL_WIDTH);
		int mj = Math.round((float) y/CELL_HEIGHT);
		int pi = Math.round((float) p.getX()/CELL_WIDTH);
		int pj = Math.round((float) p.getY()/CELL_HEIGHT);
		int dis = Math.abs(mi-pi) + Math.abs(mj-pj);
		this.alert = (dis <= ALERT_DISTANCE);
//		System.out.println(this.alert);
	}

	public void setDirection(int d) {
    	this.oldDirection = this.direction;
    	this.direction = d;
	}

	public int getImageDirection() {
    	if (this.direction < 0)
    		return Math.max(this.oldDirection, 0);
		return this.direction;
	}

	public void setVelocity(int v) {
		this.velocity = v;
	}

	public int getVelocity() {
		return this.velocity;
	}

	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setLocation(int X, int Y) {
		this.x = X;
		this.y = Y;
	}

	public void monsterMove(Player p, Map m) {
		// move a step
		switch (this.direction) {
			case DIRECTION_UP:
				this.y -= this.velocity;
				break;
			case DIRECTION_DOWN:
				this.y += this.velocity;
				break;
			case DIRECTION_LEFT:
				this.x -= this.velocity;
				break;
			case DIRECTION_RIGHT:
				this.x += this.velocity;
				break;
			case DIRECTION_STOP:
				int mi = Math.round((float) x/CELL_WIDTH);
				int mj = Math.round((float) y/CELL_HEIGHT);
				if (this.alert) {
					int pi = Math.round((float) p.getX()/CELL_WIDTH);
					int pj = Math.round((float) p.getY()/CELL_HEIGHT);
					brain.findPath(m, path, mi, mj, pi, pj);
				}
				else brain.randomPath(m, path, mi, mj);
				break;
		}
		if (isCollided(p.getX(), p.getY(), PLAYER_WIDTH, PLAYER_HEIGHT)) { // collide with player
			eliminate();
			p.setHP(p.getHP()-HP_LOSS_BY_MONSTER);
		}
		else if (isBlownOff(m)) {	// killed by bomb
			eliminate();
		}
		else {	// still alive
			setDirection(nextDirection(p, m));
			updateAlert(p);
		}
	}

	/**
	 * Check whether the monster is collided with a blown region
	 */
	private boolean isBlownOff(Map m) {
		int mi = Math.round((float) x/CELL_WIDTH);
		int mj = Math.round((float) y/CELL_HEIGHT);
		for (int i=mi; i<Math.min(mi+2, CELL_NUM_X); ++i)
			for (int j=mj; j<Math.min(mj+2, CELL_NUM_Y); ++j)
				if (m.isAtExplosion(i, j) &&
					isCollided(i*CELL_WIDTH, j*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT))
					return true;
		return false;
	}

	/**
	 * Check whether the monster is collided with a rectangle [x,y,w,h]
	 */
	private boolean isCollided(int x, int y, int w, int h) {
		int x1 = Math.max(x, this.x);
		int x2 = Math.min(x + w, this.x + MONSTER_WIDTH);
		int y1 = Math.max(y, this.y);
		int y2 = Math.min(y + h, this.y + MONSTER_HEIGHT);
		return (x2 > x1) && (y2 > y1);
	}


	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * Remove itself from the map after killed.
	 */
	public void eliminate() {
		this.alive = false;
	}
}
