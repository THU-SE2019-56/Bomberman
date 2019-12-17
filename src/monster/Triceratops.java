package monster;

import map.Map;


/**
 * The Triceratops monster class.
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
        this.velocity = MONSTER_SPEED_LOW +
                (MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW) * Math.random();
        this.direction = -1;
        this.oldDirection = 0;
        brain = new Brain(1);
        path = new Path();
        id = 2;
    }
}
