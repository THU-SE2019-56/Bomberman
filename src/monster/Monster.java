package monster;

import map.Map;
import player.Player;
import game.GameConstants;

import java.util.*;


class Point {
	int x, y;
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Path implements GameConstants {
	private ArrayList<Point> data;

	Path() {
		data = new ArrayList<>();
	}

	int getNextX() {
		return data.get(0).x * CELL_WIDTH;
	}

	int getNextY() {
		return data.get(0).y * CELL_HEIGHT;
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
}


class Brain implements GameConstants {
	final static int[] dxs = {-1, 1, 0, 0};
	final static int[] dys = {0, 0, -1, 1};
	boolean viz[][];
	int fa[][];

	Brain() {
		viz = new boolean[CELL_NUM_X][CELL_NUM_X];
		fa = new int[CELL_NUM_X][CELL_NUM_Y];
		reset();
	}

	private void reset() {
		for (int i=0; i<CELL_NUM_X; ++i)
			for (int j=0; j<CELL_NUM_Y; ++j) {
				viz[i][j] = false;
				fa[i][j] = -1;
			}
	}

	private void bfs(Map m, int si, int sj, int ti, int tj) {
		Deque<Point> q = new ArrayDeque<>();
		q.add(new Point(si, sj));

		while (q.size()>0) {
			Point p = q.pop();
			if (p.x==ti && p.y==tj) return;

			for (int k=0; k<4; ++k) {
				int ii = p.x + dxs[k];
				int jj = p.y + dys[k];
				if (m.isAvailable(jj, ii) && !viz[ii][jj]) {
					q.addLast(new Point(ii, jj));
					fa[ii][jj] = k;
					viz[ii][jj] = true;
				}
			}
		}
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

	void findPath(Map m, Path p, int si, int sj, int ti, int tj) {
		p.clear();
		reset();
		bfs(m, si, sj, ti, tj);
		genPath(p, ti, tj, si, sj);
	}
}


/**
 * The Monster class.
 * Monster will walk around on the map, until killed by a bomb (currently using the player instead).
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class Monster implements GameConstants {
	private boolean isAlive;
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
			if (m.isAvailable(j, i)) {
				this.x = i * CELL_WIDTH;
				this.y = j * CELL_HEIGHT;
				init();
				break;
			}
		}
	}

	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
		init();
	}

	private void init() {
		this.isAlive = true;
		this.velocity = 3 + (int)(3*Math.random());
		this.direction = -1;
		this.oldDirection = 0;
		brain = new Brain();
		path = new Path();
	}

    /**
     * Update monster's properties
     */
    public void refresh() {}

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
				return DIRECTION_STOP;
			}
			if (dx > 0 && dy == 0) return DIRECTION_RIGHT;
			if (dx < 0 && dy == 0) return DIRECTION_LEFT;
			if (dx == 0 && dy > 0) return DIRECTION_DOWN;
			if (dx == 0 && dy < 0) return DIRECTION_UP;
		}
		return DIRECTION_STOP;
	}

	public boolean isAlive() {
    	return this.isAlive;
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
				move(m, 0, -this.velocity);
				break;
			case DIRECTION_DOWN:
				move(m, 0, this.velocity);
				break;
			case DIRECTION_LEFT:
				move(m, -this.velocity, 0);
				break;
			case DIRECTION_RIGHT:
				move(m, this.velocity, 0);
				break;
			case DIRECTION_STOP:
				brain.findPath(m, path, Math.round((float) x/CELL_WIDTH), Math.round((float) y/CELL_HEIGHT),
						Math.round((float) p.getX()/CELL_WIDTH), Math.round((float) p.getY()/CELL_HEIGHT));
				break;
		}
		if (isCollided(p.getX(), p.getY(), PLAYER_WIDTH, PLAYER_HEIGHT))
			eliminate();
		setDirection(nextDirection(p, m));
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

	/**
	 * Move the monster by the given shift
	 */
	private void move(Map m, int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Remove itself from the map after killed.
	 */
	public void eliminate() {
		this.isAlive = false;
	}
}
