package test.monster;

import game.GameConstants;
import monster.Path;
import monster.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.nio.channels.Pipe;
import java.util.ArrayList;

/** 
* Path Tester. 
* 
* @author Hang Chen
* @since <pre>11月 29, 2019</pre> 
* @version 1.0 
*/ 
public class PathTest {
    Path path;

@Before
public void before() throws Exception {
    path = new Path();
    path.data.add(new Point(0,1));
    path.data.add(new Point(1,1));
    path.data.add(new Point(1,2));
}

@After
public void after() throws Exception {
    path = null;
} 

/** 
* 
* Method: getNextI() 
* 
*/ 
@Test
public void testGetNextI() throws Exception {
    Assert.assertEquals(path.getNextI(), 0);
}

/** 
* 
* Method: getNextJ() 
* 
*/ 
@Test
public void testGetNextJ() throws Exception {
    Assert.assertEquals(path.getNextJ(), 1);
} 

/** 
* 
* Method: getNextX() 
* 
*/ 
@Test
public void testGetNextX() throws Exception {
    Assert.assertEquals(path.getNextX(), 0);
} 

/** 
* 
* Method: getNextY() 
* 
*/ 
@Test
public void testGetNextY() throws Exception {
    Assert.assertEquals(path.getNextY(), GameConstants.CELL_HEIGHT);
} 

/** 
* 
* Method: addPoint(int i, int j) 
* 
*/ 
@Test
public void testAddPoint() throws Exception { 
    path.addPoint(2,4);
    Assert.assertEquals(path.data.get(3).x, 2);
    Assert.assertEquals(path.data.get(3).y, 4);
}

/** 
* 
* Method: removeFirst() 
* 
*/ 
@Test
public void testRemoveFirst() throws Exception {
    path.removeFirst();
    Assert.assertEquals(path.data.get(0).x, 1);
    Assert.assertEquals(path.data.get(0).y, 1);
} 

/** 
* 
* Method: clear() 
* 
*/ 
@Test
public void testClear() throws Exception { 
    path.clear();
    Assert.assertEquals(path.data.size(), 0);
}

/** 
* 
* Method: size() 
* 
*/ 
@Test
public void testSize() throws Exception {
    Assert.assertEquals(path.size(), path.data.size());
}

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
    Assert.assertEquals(path.toString(), "(0, 1) -> (1, 1) -> (1, 2)");
}


} 
