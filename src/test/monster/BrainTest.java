package test.monster;

import game.GameConstants;
import map.Map;
import monster.Brain;
import monster.Monster;
import monster.Path;
import monster.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Brain Tester.
 *
 * @author <Authors name>
 * @since <pre>11月 29, 2019</pre>
 * @version 1.0
 */
public class BrainTest {
    Brain brain;
    Map map;

    @Before
    public void before() throws Exception {
        brain = new Brain(0);
        int[][] mat = {
                {0, -1, 1, 0, 1},
                {0, 0, 0, 0, 0},
                {1, -1, 0, -1, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0},
        };
        map = new Map(mat);
    }

    @After
    public void after() throws Exception {
        brain = null;
        map = null;
    }

    /**
     *
     * Method: isMovable(Map m, int i, int j)
     *
     */
    @Test
    public void testIsMovable() throws Exception {
//        Assert.assertTrue(brain.isMovable(map,0,0));
//        Assert.assertFalse(brain.isMovable(map,4,0));
    }

    /**
     *
     * Method: reset()
     *
     */
    @Test
    public void testReset() throws Exception {
        brain.reset();
        for (int i=0; i<map.getSizeX(); ++i)
            for (int j=0; j<map.getSizeY(); ++j) {
                Assert.assertFalse(brain.viz[i][j]);
                Assert.assertEquals(brain.fa[i][j], -1);
            }
    }

    /**
     *
     * Method: bfs(Map m, int si, int sj, int ti, int tj)
     *
     */
    @Test
    public void testBfs() throws Exception {
        brain.bfs(map, 0, 0, 2, 2);
        Assert.assertEquals(brain.fa[2][2], 3);
        Assert.assertEquals(brain.fa[1][1], 1);
    }

    /**
     *
     * Method: randomSelectVizPos()
     *
     */
    @Test
    public void testRandomSelectVizPos() throws Exception {
        brain.bfs(map,0,0,-1,-1);
        for (int i=0; i<10; ++i) {
            Point p = brain.randomSelectVizPos();
            Assert.assertTrue(brain.isMovable(map, p.x, p.y));
        }
    }

    /**
     *
     * Method: genPath(Path p, int i, int j, int si, int sj)
     *
     */
    @Test
    public void testGenPath() throws Exception {
        Path p = new Path();
        brain.bfs(map,0,0,2,2);
        brain.genPath(p, 2, 2, 0, 0);
        System.out.println(p);
        Assert.assertEquals(p.toString(), "(0, 1) -> (1, 1) -> (2, 1) -> (2, 2)");
    }

    /**
     *
     * Method: genRandomPath(Path p, int si, int sj)
     *
     */
    @Test
    public void testGenRandomPath() throws Exception {
        Path p = new Path();
        brain.bfs(map, 0,0,-1,-1);
        brain.genRandomPath(p, 0, 0);
        for (Point pt: p.data) {
            Assert.assertTrue(brain.isMovable(map, pt.x, pt.y));
        }
    }

    /**
     *
     * Method: findPath(Map m, Path p, int si, int sj, int ti, int tj)
     *
     */
    @Test
    public void testFindPath() throws Exception {
        Path p = new Path();
        brain.findPath(map, p, 0, 0, 2, 2);
        Assert.assertEquals(p.toString(), "(0, 1) -> (1, 1) -> (2, 1) -> (2, 2)");
    }

    /**
     *
     * Method: randomPath(Map m, Path p, int si, int sj)
     *
     */
    @Test
    public void testRandomPath() throws Exception {
        Path p = new Path();
        brain.randomPath(map, p, 0, 0);
        for (Point pt: p.data) {
            Assert.assertTrue(brain.isMovable(map, pt.x, pt.y));
        }
    }
} 
