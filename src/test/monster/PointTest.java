package test.monster; 

import monster.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Point Tester. 
* 
* @author Hang Chen
* @since <pre>11月 29, 2019</pre> 
* @version 1.0 
*/ 
public class PointTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception {
    Assert.assertEquals(new Point(5,9).toString(), "(5, 9)");
} 


} 
