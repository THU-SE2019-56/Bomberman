package monster;

import map.Map;
import player.Player;


/**
 * The Parrot monster class, which have the following features:
 *   - bird's eye view: the parrot can find the player at any position, so it is always
 *          in alert state.
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class Parrot extends Monster {
    public Parrot(Map m) {
        super(m);
    }

    public Parrot(int x, int y) {
        super(x, y);
    }

    @Override
    void init() {
        this.alive = true;
        this.alert = false;
        this.velocity = MONSTER_SPEED_LOW +
                (MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW) * Math.random();
        this.direction = -1;
        this.oldDirection = 0;
        brain = new Brain(1);
        path = new Path();
        id = 4;
    }

    @Override
    void updateAlert(Player p) {
        this.alert = true;
    }
}
