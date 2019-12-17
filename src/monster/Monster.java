package monster;

import map.Map;
import player.Player;
import game.GameConstants;


/**
 * The Monster Base class.
 * Monster will random walk on the whole map, until distance between the player smaller than ALERT_DISTANCE.
 * Then the monster will follow the player, until get killed or be rid of, or collide with the player (
 * in this case the player's HP will reduce by HP_LOSS_BY_MONSTER).
 *
 * @author  Hang Chen
 * @version 1.0
 */
public class Monster implements GameConstants {
    boolean alive;
	boolean alert;		// whether the monster is in alert state
	double velocity;
	int oldDirection;
	int direction;
	int x;
	int y;
	Brain brain;
	Path path;
	public int id;

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

    /**
     * Set monster's properties
     */
    void init() {
		this.alive = true;
		this.alert = false;
		this.velocity = MONSTER_SPEED_LOW +
			(MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW) * Math.random();
		this.direction = -1;
		this.oldDirection = 0;
		brain = new Brain(1);
		path = new Path();
		id = 0;
	}

    /**
     * Generate the next direction
     */
	int nextDirection(Player p, Map m) {
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

    /**
     * Update monster's alert state
     */
	void updateAlert(Player p) {
		int mi = Math.round((float) x/CELL_WIDTH);
		int mj = Math.round((float) y/CELL_HEIGHT);
		int pi = Math.round((float) p.getX()/CELL_WIDTH);
		int pj = Math.round((float) p.getY()/CELL_HEIGHT);
		int dis = Math.abs(mi-pi) + Math.abs(mj-pj);
		this.alert = (dis <= ALERT_DISTANCE);
	}

	void setDirection(int d) {
		if (this.direction==DIRECTION_LEFT || this.direction==DIRECTION_RIGHT)
    		this.oldDirection = this.direction;
    	this.direction = d;
	}

    /**
     * Get the image's index
     */
	public int getImageIndex() {
		int d = this.direction==DIRECTION_LEFT || this.direction==DIRECTION_RIGHT ?
				this.direction : this.oldDirection;
		switch (d) {
			case DIRECTION_LEFT: return 0;
			case DIRECTION_RIGHT: return 1;
		}
		return 0;
	}

	public void setVelocity(double v) {
		this.velocity = v;
	}

	public double getVelocity() {
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

    /**
     * Move a step and update path
     */
	void moveStep(Player p, Map m) {
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
	}

    /**
     * Take some actions in a game loop
     */
	public void monsterMove(Player p, Map m) {
		moveStep(p, m);		// move a step
		if (p.getActiveItem() != null) {	// killed by bullet
			if(isCollided(p.getActiveItem().getX(), p.getActiveItem().getY(), CELL_WIDTH, CELL_HEIGHT)) {
				eliminate();
				p.getActiveItem().setState(false);
				p.setIsUsingBulletFlag(0);
				p.setActiveItem(null);
			}
		}
		else if (isCollided(p.getX(), p.getY(), CELL_WIDTH, CELL_HEIGHT)) { // collide with player
			eliminate();
			p.getHurt(HP_LOSS_BY_MONSTER);
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
	boolean isBlownOff(Map m) {
	    int mi = Math.floorDiv(x, CELL_WIDTH);
	    int mj = Math.floorDiv(y, CELL_HEIGHT);
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
	boolean isCollided(int x, int y, int w, int h) {
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
