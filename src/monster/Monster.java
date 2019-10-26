package monster;

import map.Map;
import player.Player;
import game.GameConstants;

public class Monster implements GameConstants{
	private int velocity;
	private boolean is_alive;
	private int direction;
	private int x;
	private int y;
	private int cnt;        // update direction only if cnt==0

	public Monster() {
		
	}

	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
		this.cnt = 50;
		this.velocity = 20;
	}

    /**
     * Get direction of the monster.
     *
     * @return Return an integer value.
     */
    public int getDirection() {
//        return this.direction;
        return 0;
    }


    /**
     * Update monster's properties
     */
    public void refresh() {
        // update parameters
        switch (this.direction) {
            case DIRECTION_UP:
                this.y -= this.velocity;
                if (this.y < 0) this.y = 0;
                break;
            case DIRECTION_DOWN:
                this.y += this.velocity;
//                if (this.y > 0) this.y = 0;
                break;
            case DIRECTION_LEFT:
                this.x -= this.velocity;
                if (this.x < 0) this.x = 0;
                break;
            case DIRECTION_RIGHT:
                this.x += this.velocity;
//                if (this.y < 0) this.y = 0;
                break;
        }

        if (cnt==0) {
            this.direction = (int)(4*Math.random());
        }
        else {
            this.cnt -= 1;
        }
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(direction);
//        System.out.println(cnt);
}


	// TODO Complete all the set, get methods
	public void setVelocity(int v) {
		this.velocity = v;
	}

	public int getVelocity() {
		return this.velocity;
	}
	
	/**
	 * Set the x of the monster.
	 * 
	 * @param X
	 */
	public void setX(int X) {
		this.x = X;
	}

	/**
	 * Set the y of the monster.
	 * 
	 * @param Y
	 */
	public void setY(int Y) {
		this.y = Y;
	}

	/**
	 * Get the x of the monster.
	 * 
	 * @return Return an integer value.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the y of the monster.
	 * 
	 * @return Return an integer value.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Set the location of the monster.
	 * 
	 * @param X
	 * @param Y
	 */
	public void setLocation(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	/**
	 * Use mapinfo to decide the monster's location after moving.
	 */
	public void monsterMove(Player player, Map mi) {
		// TODO Move the monster, consider the player, walls, bombs. Use setX(),setY()

	}

	/**
	 * Remove itself from the map when killed.
	 */
	public void eliminate() {

	}
}
