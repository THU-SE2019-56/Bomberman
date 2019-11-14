package bomb;

import map.Map;
import map.Cell;
import game.GameConstants;

/**
 * Responsible for all the bomb methods, including exploding when time is up, chain exploding
 *
 * @author Wang
 * @version 0.1
 */
public class Bomb implements GameConstants {

    private int bombPow;
    private int x;
    private int y;
    private Map currMap;
    private int timeRemain = BOMB_TIME;
    private final static int[] dxs = {-1, 1, 0, 0};
    private final static int[] dys = {0, 0, -1, 1};

    /**
     * @param x       the x position of bomb
     * @param y       the y position of bomb
     * @param bombPow the distance the bomb can spread
     * @param map     where bomb on
     */
    public Bomb(int x, int y, int bombPow, Map map) {
        this.x = x;
        this.y = y;
        this.bombPow = bombPow;
        this.currMap = map;
    }

    /**
     * Refresh the remain time
     */
    public void refresh() {
        timeRemain--;
        if (timeRemain < 0) {
            explode();
            currMap.getCell(x, y).removeBomb();
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
        Cell currCell;
        currMap.getCell(x, y).explosionActivate();
        for (int i = 0; i < 4; i++) {
            currX = x;
            currY = y;
            for (int j = 1; j <= bombPow; j++) {
                currX += dxs[i];
                currY += dys[i];
                if (!currMap.isInMap(currX, currY)) {
                    break;
                }
                currCell = currMap.getCell(currX, currY);
                if (currCell.isWithWall()) {
                    currCell.explosionActivate(); // In explosionActivate will judge whether wall distroible
                    break;
                }
                currCell.explosionActivate();
            }
        }
    }
}
