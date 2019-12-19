package test.monster;

import game.GameConstants;
import map.Map;
import monster.Monster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import player.Player;

import static game.GameConstants.*;


/**
 * Monster Tester.
 *
 * @author Hang Chen
 * @since <pre>11&#x6708; 29, 2019</pre>
 * @version 1.0
 */
public class MonsterTest {
    Monster monster;
    Map map;
    Player player;

    @Before
    public void before() throws Exception {
        monster = new Monster(0, 0);
        int[][] mat = {
                {0, -1, 1, 0, 1},
                {0, 0, 0, 0, 0},
                {1, -1, 0, -1, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0},
        };
        map = new Map(mat);
        player = new Player(map, 0, PLAYER_ID_P1);
    }

    @After
    public void after() throws Exception {
        monster = null;
        map = null;
        player = null;
    }

    /**
     *
     * Method: init()
     *
     */
    @Test
    public void testInit() throws Exception {
        monster.init();
        Assert.assertTrue(monster.alive);
        Assert.assertFalse(monster.alert);
        Assert.assertTrue(monster.velocity>0);
        Assert.assertEquals(monster.direction, -1);
        Assert.assertEquals(0, monster.oldDirection);
        Assert.assertNotNull(monster.brain);
        Assert.assertNotNull(monster.path);
    }

    /**
     *
     * Method: nextDirection(Player p, Map m)
     *
     */
    @Test
    public void testNextDirection() throws Exception {
        monster.path.addPoint(0,1);
        Assert.assertEquals(monster.nextDirection(player, map), GameConstants.DIRECTION_DOWN);
    }

    /**
    *
    * Method: updateAlert(Player p)
    *
    */
    @Test
    public void testUpdateAlert() throws Exception {
        // put player on (1,1)
        player.setLocation(GameConstants.CELL_WIDTH, GameConstants.CELL_HEIGHT);
        monster.updateAlert(player);
        Assert.assertTrue(monster.alert);

        // put player on (5,5)
        player.setLocation(5*GameConstants.CELL_WIDTH, 5*GameConstants.CELL_HEIGHT);
        monster.updateAlert(player);
        Assert.assertFalse(monster.alert);
    }

    /**
    *
    * Method: setDirection(int d)
    *
    */
    @Test
    public void testSetDirection() throws Exception {
        monster.setDirection(GameConstants.DIRECTION_DOWN);
        Assert.assertEquals(monster.direction, GameConstants.DIRECTION_DOWN);

        monster.setDirection(GameConstants.DIRECTION_UP);
        Assert.assertEquals(monster.direction, GameConstants.DIRECTION_UP);

        monster.setDirection(GameConstants.DIRECTION_LEFT);
        Assert.assertEquals(monster.direction, GameConstants.DIRECTION_LEFT);

        monster.setDirection(GameConstants.DIRECTION_RIGHT);
        Assert.assertEquals(monster.direction, GameConstants.DIRECTION_RIGHT);
        Assert.assertEquals(monster.oldDirection, GameConstants.DIRECTION_LEFT);

        monster.setDirection(GameConstants.DIRECTION_STOP);
        Assert.assertEquals(monster.direction, GameConstants.DIRECTION_STOP);
        Assert.assertEquals(monster.oldDirection, GameConstants.DIRECTION_RIGHT);
    }

    /**
    *
    * Method: getImageDirection()
    *
    */
    @Test
    public void testGetImageIndex() throws Exception {
        monster.setDirection(GameConstants.DIRECTION_LEFT);
        Assert.assertEquals(monster.getImageIndex(), 0);
        monster.setDirection(GameConstants.DIRECTION_STOP);
        Assert.assertEquals(monster.getImageIndex(), 0);
    }

    /**
    *
    * Method: setVelocity(int v)
    *
    */
    @Test
    public void testSetVelocity() throws Exception {
        monster.setVelocity(10);
        Assert.assertEquals(monster.velocity, 10, 1e-5);
    }

    /**
    *
    * Method: getVelocity()
    *
    */
    @Test
    public void testGetVelocity() throws Exception {
        monster.velocity = 20;
        Assert.assertEquals(monster.getVelocity(), 20, 1e-5);
    }

    /**
    *
    * Method: setX(int X)
    *
    */
    @Test
    public void testSetX() throws Exception {
        monster.setX(100);
        Assert.assertEquals(monster.x, 100);
    }

    /**
    *
    * Method: setY(int Y)
    *
    */
    @Test
    public void testSetY() throws Exception {
        monster.setY(200);
        Assert.assertEquals(monster.y, 200);
    }

    /**
    *
    * Method: getX()
    *
    */
    @Test
    public void testGetX() throws Exception {
        monster.x = 300;
        Assert.assertEquals(monster.getX(), 300);
    }

    /**
    *
    * Method: getY()
    *
    */
    @Test
    public void testGetY() throws Exception {
        monster.y = 400;
        Assert.assertEquals(monster.getY(), 400);
    }

    /**
    *
    * Method: setLocation(int X, int Y)
    *
    */
    @Test
    public void testSetLocation() throws Exception {
        monster.setLocation(10,20);
        Assert.assertEquals(monster.x, 10);
        Assert.assertEquals(monster.y, 20);
    }

    /**
    *
    * Method: monsterMove(Player p, Map m)
    *
    */
    @Test
    public void testMonsterMove() throws Exception {
        monster.velocity = 10;
        monster.alive = true;
        monster.setLocation(4*GameConstants.CELL_WIDTH, 4*GameConstants.CELL_HEIGHT);
        player.setLocation(4*GameConstants.CELL_WIDTH, 5*GameConstants.CELL_HEIGHT);

        monster.direction = GameConstants.DIRECTION_RIGHT;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH+10);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_HEIGHT);

        monster.direction = GameConstants.DIRECTION_LEFT;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_HEIGHT);

        monster.direction = GameConstants.DIRECTION_UP;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_HEIGHT-10);

        monster.direction = GameConstants.DIRECTION_DOWN;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_HEIGHT);

        monster.direction = GameConstants.DIRECTION_STOP;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_WIDTH);

        monster.alive = false;
        monster.dyingCounter = 30;
        monster.monsterMove(player, map);
        Assert.assertEquals(monster.x, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.y, 4*GameConstants.CELL_WIDTH);
        Assert.assertEquals(monster.dyingCounter, 29);
    }

    /**
     *
     * Method: isBlownOff(Map m)
     *
     */
    @Test
    public void testIsBlownOff() throws Exception {
        Assert.assertFalse(monster.isBlownOff(map));
        map.explosionActivate(0,0);
        Assert.assertTrue(monster.isBlownOff(map));
    }

    /**
     *
     * Method: isCollided(int x, int y, int w, int h)
     *
     */
    @Test
    public void testIsCollided() throws Exception {
        Assert.assertTrue(monster.isCollided(GameConstants.CELL_WIDTH-1, 0,
                GameConstants.CELL_WIDTH, GameConstants.CELL_HEIGHT));
        Assert.assertFalse(monster.isCollided(GameConstants.CELL_WIDTH, 0,
                GameConstants.CELL_WIDTH, GameConstants.CELL_HEIGHT));
    }

    /**
    *
    * Method: isAlive()
    *
    */
    @Test
    public void testIsAlive() throws Exception {
        monster.alive = false;
        Assert.assertFalse(monster.isAlive());
    }

    @Test
    public void testIsVisible() throws Exception {
        monster.alive = true;
        monster.dyingCounter = 0;
        Assert.assertTrue(monster.isVisible());
        monster.alive = false;
        Assert.assertFalse(monster.isVisible());
    }

    @Test
    public void testIsDying() throws Exception {
        monster.dyingCounter = 10;
        Assert.assertTrue(monster.isDying());
        monster.dyingCounter = 0;
        Assert.assertFalse(monster.isDying());
    }

    /**
    *
    * Method: eliminate()
    *
    */
    @Test
    public void testEliminate() throws Exception {
        monster.eliminate();
        Assert.assertFalse(monster.alive);
        Assert.assertEquals(monster.dyingCounter, 30);
    }
}
