package monster;

import map.Map;


/**
 * The Monster Generator class.
 * This class is used to generate a monster of the given type, or random type.
 *
 * @author  Hang Chen
 * @version 0.1
 */
public class MonsterGenerator {
    public static Monster getMonster(int id, Map m) {
        switch (id) {
            case 0: return new Monster(m);
            case 1: return new Tyrannosaurus(m);
            case 2: return new Triceratops(m);
            case 3: return new Frog(m);
            case 4: return new Parrot(m);
        }
        return null;
    }

    public static Monster getMonster(int id, int x, int y) {
        switch (id) {
            case 0: return new Monster(x, y);
            case 1: return new Tyrannosaurus(x, y);
            case 2: return new Triceratops(x, y);
            case 3: return new Frog(x, y);
            case 4: return new Parrot(x, y);
        }
        return null;
    }

    public static Monster getRandomMonster(Map m) {
        int id = (int)(4*Math.random()) + 1;
        return getMonster(id, m);
    }

    public static Monster getRandomMonster(int x, int y) {
        int id = (int)(4*Math.random()) + 1;
        return getMonster(id, x, y);
    }
}
