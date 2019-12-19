package test.monster; 

import game.GameConstants;
import map.Map;
import monster.Frog;
import monster.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import player.Player;

/** 
* Frog Tester. 
* 
* @author Hang Chen
* @since <pre>12月 19, 2019</pre> 
* @version 1.0 
*/ 
public class FrogTest {
Frog frog;

@Before
public void before() throws Exception {
    frog = new Frog(10,10);
} 

@After
public void after() throws Exception {
    frog = null;
} 

/** 
* 
* Method: init() 
* 
*/ 
@Test
public void testInit() throws Exception { 
    frog.init();
    Assert.assertTrue(frog.alive);
    Assert.assertTrue(
        frog.numLives >= 2 &&
                frog.numLives < 4
    );
    Assert.assertFalse(frog.alert);
    Assert.assertTrue(
            frog.velocity >= GameConstants.MONSTER_SPEED_LOW &&
                    frog.velocity < GameConstants.MONSTER_SPEED_HIGH
    );
    Assert.assertEquals(frog.direction, -1);
    Assert.assertEquals(frog.oldDirection, 0);
    Assert.assertNotNull(frog.brain);
    Assert.assertEquals(frog.brain.eer, 0);
    Assert.assertNotNull(frog.path);
    Assert.assertEquals(frog.id, 3);
} 

/** 
* 
* Method: monsterMove(Player p, Map m) 
* 
*/ 
@Test
public void testMonsterMove() throws Exception {
    Map map = new Map();
    Player player = new Player(map, 1, 1);

    frog.alive = false;
    frog.numLives = 1;
    frog.resurrectionCounter = 2;
    frog.monsterMove(player, map);
    Assert.assertEquals(frog.resurrectionCounter, 1);
    Assert.assertFalse(frog.alive);

    frog.monsterMove(player, map);
    Assert.assertEquals(frog.resurrectionCounter, 0);
    Assert.assertTrue(frog.alive);
}

/** 
* 
* Method: eliminate() 
* 
*/ 
@Test
public void testEliminate() throws Exception { 
    frog.numLives = 1;
    frog.path.addPoint(1,1);
    frog.eliminate();
    Assert.assertEquals(frog.path.size(), 0);
    Assert.assertFalse(frog.alive);
    Assert.assertEquals(frog.resurrectionCounter, 100);
    Assert.assertEquals(frog.numLives, 0);
    Assert.assertEquals(frog.dyingCounter, 30);
} 

/** 
* 
* Method: isAlive() 
* 
*/ 
@Test
public void testIsAlive() throws Exception { 
    frog.alive = true;
    frog.numLives = 0;
    Assert.assertTrue(frog.isAlive());
    frog.alive = false;
    Assert.assertFalse(frog.isAlive());
} 


} 
