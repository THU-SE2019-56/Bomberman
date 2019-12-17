package monster;

import map.Map;
import player.Player;


/**
 * The Triceratops monster class, which have the following features:
 *   - slow: triceratops's speed is much low usually, i.e. MONSTER_SPEED_LOW
 *   - dash: triceratops's speed will be greatly improved when it find the player.
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class Triceratops extends Monster {
    public Triceratops(Map m) {
        super(m);
    }

    public Triceratops(int x, int y) {
        super(x, y);
    }

    @Override
    void init() {
        this.alive = true;
        this.alert = false;
        this.velocity = MONSTER_SPEED_LOW;
        this.direction = -1;
        this.oldDirection = 0;
        brain = new Brain(1);
        path = new Path();
        id = 2;
    }

    @Override
    void updateAlert(Player p) {
        super.updateAlert(p);
        if (this.alert)
            this.velocity = MONSTER_SPEED_FAST;
        else
            this.velocity = MONSTER_SPEED_LOW;
    }
}
