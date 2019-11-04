package monster;

import map.Map;
import player.Player;
import game.GameConstants;


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
	private int direction;
	private int x;
	private int y;
	private int cnt;        // update direction only if cnt >= CNT_MAX
<<<<<<< HEAD
//	private int sizeX;
//	private int sizeY;
	private static final int CNT_MAX = 10;
//	private static final int WIDTH = 1000;
//	private static final int HEIGHT = 1000;
=======
	private int sizeX;
	private int sizeY;
	private static final int CNT_MAX = 10;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4


	public Monster() {
		this(0, 0);
	}

	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
<<<<<<< HEAD
//		this.sizeX = 100;
//		this.sizeY = 100;
=======
		this.sizeX = 100;
		this.sizeY = 100;
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
		this.cnt = 0;
		this.isAlive = true;
		this.velocity = 10 + (int)(10*Math.random());
		this.direction = 1 + (int)(2*Math.random());
	}

    /**
     * Update monster's properties
     */
    public void refresh() {
    	if (this.isAlive) {
			// random select a direction
			if (this.cnt > CNT_MAX) {
				this.direction = (int)(4*Math.random());
				if (this.x==0) this.direction = DIRECTION_RIGHT;
<<<<<<< HEAD
				if (this.x==SCREEN_WIDTH - MONSTER_WIDTH) this.direction = DIRECTION_LEFT;
				if (this.y==0) this.direction = DIRECTION_DOWN;
				if (this.y==SCREEN_HEIGHT - MONSTER_HEIGHT) this.direction = DIRECTION_UP;
=======
				if (this.x==WIDTH - this.sizeX) this.direction = DIRECTION_LEFT;
				if (this.y==0) this.direction = DIRECTION_DOWN;
				if (this.y==HEIGHT - this.sizeY) this.direction = DIRECTION_UP;
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
				this.cnt = 0;
			}
			else {
				this.cnt += 1;
			}
		}
	}

	public boolean isAlive() {
    	return this.isAlive;
	}

<<<<<<< HEAD
=======
	public int getSizeX() {
		return this.sizeX;
	}

	public int getSizeY() {
		return this.sizeY;
	}

>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
	public int getDirection() {
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

	public void monsterMove(Player player, Map mi) {
		// move a step
		switch (this.direction) {
			case DIRECTION_UP:
				this.y = Math.max(this.y - this.velocity, 0);
				break;
			case DIRECTION_DOWN:
<<<<<<< HEAD
				this.y = Math.min(this.y + this.velocity, SCREEN_HEIGHT - MONSTER_HEIGHT);
=======
				this.y = Math.min(this.y + this.velocity, HEIGHT - this.sizeY);
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
				break;
			case DIRECTION_LEFT:
				this.x = Math.max(this.x - this.velocity, 0);
				break;
			case DIRECTION_RIGHT:
<<<<<<< HEAD
				this.x = Math.min(this.x + this.velocity, SCREEN_WIDTH - MONSTER_WIDTH);
=======
				this.x = Math.min(this.x + this.velocity, WIDTH - this.sizeX);
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
				break;
		}
		if (isCollided(player)) eliminate();
	}

	/**
	 * Check whether the monster is collided with the player
	 */
	private boolean isCollided(Player player) {
		int x1 = Math.max(player.getX(), this.x);
<<<<<<< HEAD
		int x2 = Math.min(player.getX() + 100, this.x + MONSTER_WIDTH);
		int y1 = Math.max(player.getY(), this.y);
		int y2 = Math.min(player.getY() + 100, this.y + MONSTER_HEIGHT);
=======
		int x2 = Math.min(player.getX() + 100, this.x + this.sizeX);
		int y1 = Math.max(player.getY(), this.y);
		int y2 = Math.min(player.getY() + 100, this.y + this.sizeY);
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
		return (x2 > x1) && (y2 > y1);
	}

	/**
<<<<<<< HEAD
	 * Remove itself from the map after killed.
=======
	 * Remove itself from the map when killed.
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
	 */
	public void eliminate() {
		this.isAlive = false;
	}
}
