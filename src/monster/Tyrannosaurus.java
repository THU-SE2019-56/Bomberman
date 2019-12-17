package monster;

import map.Map;


/**
 * The Tyrannosaurus monster class, which have the following features:
 *   - agile: The tyrannosaurus have the highest speed, i.e. 2*MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW
 *   - cautious: The tyrannosaurus is more cautious, its `estimate explosion range` is 2 rather than 1
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class Tyrannosaurus extends Monster {
    public Tyrannosaurus(Map m) {
        super(m);
    }

    public Tyrannosaurus(int x, int y) {
        super(x, y);
    }

    @Override
    void init() {
        this.alive = true;
        this.alert = false;
        this.velocity = 2*MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW;
        this.direction = -1;
        this.oldDirection = 0;
        brain = new Brain(2);
        path = new Path();
        id = 1;
    }
}
