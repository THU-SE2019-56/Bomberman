package test.monster; 

import game.GameConstants;
import map.Map;
import monster.Parrot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import player.Player;

/** 
* Parrot Tester. 
* 
* @author Hang Chen
* @since <pre>12月 19, 2019</pre> 
* @version 1.0 
*/ 
public class ParrotTest { 
Parrot par;

@Before
public void before() throws Exception { 
    par = new Parrot(10,1);
}

@After
public void after() throws Exception { 
    par = null;
}

/** 
* 
* Method: init() 
* 
*/ 
@Test
public void testInit() throws Exception { 
    par.init();
    Assert.assertTrue(par.alive);
    Assert.assertFalse(par.alert);
    Assert.assertTrue(
            par.velocity >= GameConstants.MONSTER_SPEED_LOW &&
                    par.velocity < GameConstants.MONSTER_SPEED_HIGH
    );
    Assert.assertEquals(par.direction, -1);
    Assert.assertEquals(par.oldDirection, 0);
    Assert.assertNotNull(par.brain);
    Assert.assertEquals(par.brain.eer, 1);
    Assert.assertNotNull(par.path);
    Assert.assertEquals(par.id, 4);
}

/** 
* 
* Method: updateAlert(Player p) 
* 
*/ 
@Test
public void testUpdateAlert() throws Exception {
    Assert.assertFalse(par.alert);
    par.updateAlert(new Player(new Map(), 1, 1));
    Assert.assertTrue(par.alert);
}


} 
