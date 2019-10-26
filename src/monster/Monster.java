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

	public Monster() {
		
	}
	public Monster(int X, int Y) {
		this.x = X;
		this.y = Y;
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
