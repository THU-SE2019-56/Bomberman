package monster;

import map.Map;
import player.Player;

public class Monster{
	private int velocity;
	private boolean is_alive;
	private int direction;
	private int x;
	private int y;

	// TODO Complete all the set, get methods
	public void setVelocity(int v) {
		this.velocity = v;
	}

	public int getVelocity() {
		return this.velocity;
	}

	/**
	 * Use mapinfo to decide the monster's location after moving.
	 */
	public void setNewLocation(Player player, Map mi) {
		// TODO Move the player, consider walls, bombs. Use setLocation()

	}
	
	/**
	 * Remove itself from the map when killed.
	 */
	public void eliminate() {
		
	}
}
