package map;

/**
 * Basic unit of map, with information of current status of certain position on
 * the map, including Barrier information, Bomb information and explosion effect
 * information
 * 
 * @author Zhuofan Chen
 * @version 0.1
 */

public class Cell {
	// Define this cell with barrier/bomb or not
	private boolean withBarrier;
	private boolean withBomb;
	// Define explosion effects exerted on current this
	private byte explosionEffect;

	/**
	 * A cell is available means a player or monster can move to it or a bomb can be
	 * set on it
	 */
	public boolean isAvailable() {
		return (!withBarrier) && (!withBomb);
	}

	/**
	 * Activate a explosion effect on this cell
	 */
	public void explosionActivate() {
		explosionEffect = 20;
	}

	/**
	 * Explosion effect decays when refreshed
	 */
	public void explosionDecay() {
		if (explosionEffect > 0)
			explosionEffect--;
	}

	/**
	 * @return this cell is now at explosion
	 */
	public boolean isAtExplosion() {
		return (explosionEffect > 0);
	}

	
}
