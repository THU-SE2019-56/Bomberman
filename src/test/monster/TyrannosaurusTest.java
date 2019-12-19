package test.monster; 

import game.GameConstants;
import monster.Tyrannosaurus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Tyrannosaurus Tester. 
* 
* @author Hang Chen
* @since <pre>12月 19, 2019</pre> 
* @version 1.0 
*/ 
public class TyrannosaurusTest {
Tyrannosaurus tyr;

@Before
public void before() throws Exception {
    tyr = new Tyrannosaurus(10,10);
}

@After
public void after() throws Exception {
    tyr = null;
} 

/** 
* 
* Method: init() 
* 
*/ 
@Test
public void testInit() throws Exception { 
    tyr.init();
    Assert.assertTrue(tyr.alive);
    Assert.assertFalse(tyr.alert);
    Assert.assertEquals(tyr.velocity, GameConstants.MONSTER_SPEED_FAST, 1e-5);
    Assert.assertEquals(tyr.direction, -1);
    Assert.assertEquals(tyr.oldDirection, 0);
    Assert.assertNotNull(tyr.brain);
    Assert.assertEquals(tyr.brain.eer, 2);
    Assert.assertNotNull(tyr.path);
    Assert.assertEquals(tyr.id, 1);
} 


} 
