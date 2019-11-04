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
	private static final int CNT_MAX = 10;



	public Monster() {
		this(0, 0);
	}

	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
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
				if (this.x==SCREEN_WIDTH - MONSTER_WIDTH) this.direction = DIRECTION_LEFT;
				if (this.y==0) this.direction = DIRECTION_DOWN;
				if (this.y==SCREEN_HEIGHT - MONSTER_HEIGHT) this.direction = DIRECTION_UP;
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
				this.y = Math.min(this.y + this.velocity, SCREEN_HEIGHT - MONSTER_HEIGHT);
				break;
			case DIRECTION_LEFT:
				this.x = Math.max(this.x - this.velocity, 0);
				break;
			case DIRECTION_RIGHT:
				this.x = Math.min(this.x + this.velocity, SCREEN_WIDTH - MONSTER_WIDTH);
				break;
		}
		if (isCollided(player)) eliminate();
	}

	/**
	 * Check whether the monster is collided with the player
	 */
	private boolean isCollided(Player player) {
		int x1 = Math.max(player.getX(), this.x);
		int x2 = Math.min(player.getX() + PLAYER_WIDTH, this.x + MONSTER_WIDTH);
		int y1 = Math.max(player.getY(), this.y);
		int y2 = Math.min(player.getY() + PLAYER_HEIGHT, this.y + MONSTER_HEIGHT);
		return (x2 > x1) && (y2 > y1);
	}

	/**
	 * Remove itself from the map after killed.
	 */
	public void eliminate() {
		this.isAlive = false;
	}
}
