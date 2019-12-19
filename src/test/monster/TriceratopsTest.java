package test.monster; 

import game.Game;
import game.GameConstants;
import map.Map;
import monster.Triceratops;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import player.Player;

/** 
* Triceratops Tester. 
* 
* @author Hang Chen
* @since <pre>12月 19, 2019</pre> 
* @version 1.0 
*/ 
public class TriceratopsTest { 
Triceratops trp;

@Before
public void before() throws Exception {
    trp = new Triceratops(10,10);
}

@After
public void after() throws Exception { 
    trp = null;
}

/** 
* 
* Method: init() 
* 
*/ 
@Test
public void testInit() throws Exception { 
    trp.init();
    Assert.assertTrue(trp.alive);
    Assert.assertFalse(trp.alert);
    Assert.assertEquals(trp.velocity, GameConstants.MONSTER_SPEED_LOW, 1e-5);
    Assert.assertEquals(trp.direction, -1);
    Assert.assertEquals(trp.oldDirection, 0);
    Assert.assertNotNull(trp.brain);
    Assert.assertEquals(trp.brain.eer, 1);
    Assert.assertNotNull(trp.path);
    Assert.assertEquals(trp.id, 2);
}

/** 
* 
* Method: updateAlert(Player p) 
* 
*/ 
@Test
public void testUpdateAlert() throws Exception { 
    trp.setLocation(10*GameConstants.CELL_WIDTH,10*GameConstants.CELL_HEIGHT);
    Player p = new Player(new Map(), 1, 1);

    p.setLocation(12*GameConstants.CELL_WIDTH, 12*GameConstants.CELL_HEIGHT);
    trp.updateAlert(p);
    Assert.assertEquals(trp.velocity, GameConstants.MONSTER_SPEED_FAST, 1e-5);

    p.setLocation(20* GameConstants.CELL_WIDTH, 20*GameConstants.CELL_HEIGHT);
    trp.updateAlert(p);
    Assert.assertEquals(trp.velocity, GameConstants.MONSTER_SPEED_LOW, 1e-5);

} 


} 
