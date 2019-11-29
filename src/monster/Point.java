package monster;

public class Point {
    public int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {		// for debug
        return String.format("(%d, %d)", x, y);
    }
}
