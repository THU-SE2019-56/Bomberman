package monster;

import game.GameConstants;

import java.util.ArrayList;

/**
 * This class is used to represent a path.
 * The path consists of each points in the path.
 *
 * @author  Hang Chen
 * @version 0.2
 */
public class Path implements GameConstants {
    public ArrayList<Point> data;

    public Path() {
        data = new ArrayList<>();
    }

    public int getNextI() {	// grid coordinates
        return data.get(0).x;
    }

    public int getNextJ() {
        return data.get(0).y;
    }

    public int getNextX() {	// pixel coordinates
        return getNextI() * CELL_WIDTH;
    }

    public int getNextY() {
        return getNextJ() * CELL_HEIGHT;
    }

    public void addPoint(int i, int j) {
        data.add(new Point(i, j));
    }

    public void removeFirst() {
        data.remove(0);
    }

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }

    public String toString() {		// for debug
        StringBuilder s = new StringBuilder();
        for (int k=0; k<size()-1; ++k)
            s.append(data.get(k)).append(" -> ");
        s.append(data.get(size() - 1));
        return s.toString();
    }
}
