package bomb;

import map.Map;
import player.Player;
import game.GameConstants;

/**
 * Responsible for the bomb methods, including exploding when time is up, chain
 * exploding.<br>
 * Have a close connection to Map.
 *
 * @author Yunjia Yang
 * @version 0.9
 */
public class Bomb implements GameConstants {

	private int bombPow;
	private int x;
	private int y;
	private Map currMap;
	private Player owner;
	private int timeRemain = BOMB_TIME;
	private final static int[] dxs = { -1, 1, 0, 0 };
	private final static int[] dys = { 0, 0, -1, 1 };

	/**
	 * @param x       the x position of bomb
	 * @param y       the y position of bomb
	 * @param bombPow the distance the bomb can spread
	 * @param map     where bomb on
	 */
	public Bomb(int x, int y, int bombPow, Map map, Player owner) {
		this.x = x;
		this.y = y;
		this.bombPow = bombPow;
		this.currMap = map;
		this.owner = owner;
	}

	/**
	 * Refresh the remain time
	 */
	public void refresh() {
		timeRemain--;
		if (timeRemain < 0) {
			explode();
			owner.reduceBombPlantedNumber();
			currMap.removeBomb(x, y);
		}
	}

	/**
	 * Used to set the remaining time of the bomb, when other bomb involve it
	 *
	 * @param time remaining time to be set
	 */

	public void setBombTime(int time) {
		timeRemain = time <= 0 ? 0 : time;
	}

	/**
	 * mark the involved area as active
	 */

	private void explode() {
		int currX, currY;
		currMap.explosionActivate(x, y); // explosion at bomb position
		for (int i = 0; i < 4; i++) {
			currX = x;
			currY = y;
			for (int j = 1; j <= bombPow; j++) {
				currX += dxs[i];
				currY += dys[i];
				if (!currMap.isInMap(currX, currY)) {
					break;
				}
				if (currMap.isWithWall(currX, currY)) {
					currMap.explosionActivate(currX, currY); // In explosionActivate will judge whether wall distroible
					break;
				}
				currMap.explosionActivate(currX, currY);
			}
		}
	}
}
