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
	// Define this cell with wall/bomb or not
	private boolean withBomb = false;
	private boolean withWall = false;
	// If a wall on this cell is destructible, true when no wall is on this cell
	private boolean wallIsDestructible = true;
	// Define explosion effects exerted on current this
	private int explosionEffect = 0;

	/**
	 * Call all member methods needing refreshing
	 */
	public void refresh() {
		explosionDecay();
	}

	/**
	 * A cell is available means a player or monster can move to it or a bomb can be
	 * set on it
	 */
	public boolean isAvailable() {
		return (!withWall) && (!withBomb);
	}

	/**
	 * set a bomb on this cell
	 * 
	 * @return if the bomb is successfully set
	 */
	public boolean setBomb() {
		if (!isAvailable())
			return false;
		withBomb = true;
		return true;
	}

	/**
	 * remove a bomb from this cell
	 * 
	 * @return false when this cell was originally without bomb
	 */
	public boolean removeBomb() {
		if (!withBomb)
			return false;
		withBomb = false;
		return true;
	}

	/**
	 * @return if a bomb is on this cell
	 */
	public boolean isWithBomb() {
		return withBomb;
	}

	/**
	 * Activate a explosion effect on this cell
	 */
	public void explosionActivate() {
		if (wallIsDestructible) {
			explosionEffect = 20;
			withWall = false;
		}
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

	/**
	 * set a wall at this cell
	 * 
	 * @param destructible: whether the wall to be set is destructible
	 */
	public void setWall(boolean destructible) {
		withWall = true;
		wallIsDestructible = destructible;
	}

	/**
	 * remove wall from this cell
	 */
	public void removeWall() {
		withWall = false;
		wallIsDestructible = true;
	}
	
	/**
	 * @return if a wall is on this cell
	 */
	public boolean isWithWall() {
		return withWall;
	}

	/**
	 * @return if a destructible wall is on this cell
	 */
	public boolean isWithDestructibleWall() {
		return (withWall && wallIsDestructible);
	}

	/**
	 * @return if an indestructible wall is on this cell
	 */
	public boolean isWithIndestructibleWall() {
		return (withWall && !wallIsDestructible);
	}

}
