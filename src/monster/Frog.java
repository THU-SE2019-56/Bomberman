package monster;

import map.Map;
import player.Player;


/**
 * The Frog monster class, which have the following features:
 *   - clumsy: Frog cannot dodge bomb, its `estimate explosion range` is 0.
 *   - resurrection: Frog have some resurrection chance.
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class Frog extends Monster {
    int numLives;      // number of lives
    int resurrectionCounter;    // resurrection time

    public Frog(Map m) {
        super(m);
    }

    public Frog(int x, int y) {
        super(x, y);
    }

    @Override
    void init() {
        this.alive = true;
        this.numLives = 2 + (int)(2*Math.random());
        this.alert = false;
        this.velocity = MONSTER_SPEED_LOW +
                (MONSTER_SPEED_HIGH - MONSTER_SPEED_LOW) * Math.random();
        this.direction = -1;
        this.oldDirection = 0;
        brain = new Brain(0);
        path = new Path();
        id = 3;
    }

    @Override
    public void monsterMove(Player p, Map m) {
        if (isAlive()) super.monsterMove(p, m);
        else if (this.numLives>=0) {
            this.resurrectionCounter--;
            if (this.resurrectionCounter <=0 ) {
                this.alive = true;
            }
        }
    }

    @Override
    public void eliminate() {
        this.alive = false;
        this.numLives--;
        this.resurrectionCounter = 100;     // take 3 seconds to resurrection
    }
}
