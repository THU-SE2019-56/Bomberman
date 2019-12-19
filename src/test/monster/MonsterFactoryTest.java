package test.monster; 

import game.GameConstants;
import map.Map;
import monster.Monster;
import monster.MonsterFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* MonsterFactory Tester. 
* 
* @author Hang Chen
* @since <pre>12月 19, 2019</pre> 
* @version 1.0 
*/ 
public class MonsterFactoryTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getMonster(int id, Map m) 
* 
*/ 
@Test
public void testGetMonsterForIdM() throws Exception {
    for (int k=1; k<5; ++k) {
        // generate random map
        int[][] mat = new int[GameConstants.CELL_NUM_X][GameConstants.CELL_NUM_Y];
        for (int i = 0; i < GameConstants.CELL_NUM_X; ++i) {
            for (int j = 0; j < GameConstants.CELL_NUM_Y; ++j) {
                mat[i][j] = (int) (3 * Math.random()) - 1;
            }
        }
        Map map = new Map(mat);
        Monster m = MonsterFactory.getMonster(k, map);
        Assert.assertNotNull(m);
        Assert.assertEquals(m.id, k);
        Assert.assertTrue(map.isAvailable(m.getX() / GameConstants.CELL_WIDTH,
                m.getY() / GameConstants.CELL_HEIGHT));
    }
}

/** 
* 
* Method: getMonster(int id, int x, int y) 
* 
*/ 
@Test
public void testGetMonsterForIdXY() throws Exception {
    for (int k=1; k<5; ++k) {
        Monster m = MonsterFactory.getMonster(k, 10, 10);
        Assert.assertNotNull(m);
        Assert.assertEquals(m.id, k);
    }
} 

/** 
* 
* Method: getRandomMonster(Map m) 
* 
*/ 
@Test
public void testGetRandomMonsterM() throws Exception {
    for (int k=1; k<5; ++k) {
        // generate random map
        int[][] mat = new int[GameConstants.CELL_NUM_X][GameConstants.CELL_NUM_Y];
        for (int i = 0; i < GameConstants.CELL_NUM_X; ++i) {
            for (int j = 0; j < GameConstants.CELL_NUM_Y; ++j) {
                mat[i][j] = (int) (3 * Math.random()) - 1;
            }
        }
        Map map = new Map(mat);
        Monster m = MonsterFactory.getRandomMonster(map);
        Assert.assertNotNull(m);
        Assert.assertTrue(map.isAvailable(m.getX() / GameConstants.CELL_WIDTH,
                m.getY() / GameConstants.CELL_HEIGHT));
    }
} 

/** 
* 
* Method: getRandomMonster(int x, int y) 
* 
*/ 
@Test
public void testGetRandomMonsterForXY() throws Exception {
    for (int k=1; k<5; ++k) {
        Monster m = MonsterFactory.getRandomMonster(1, 5);
        Assert.assertNotNull(m);
    }
} 


} 
