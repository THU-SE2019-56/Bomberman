package map;

import game.GameConstants;
import java.lang.Math;
import java.util.*;

/**
 * to be used to generate a map with a given or a new random matrix of wall
 * 
 * @author Zhuofan Chen
 * @version 1.0
 */
public class MapMatrix implements GameConstants {
	// Size of matrix
	private int xSize;
	private int ySize;
	// Define wall matrix
	private byte[][] wall;
	// Chance for wall to be generated
	private float destructibleWallDensity = 0.5f;
	private float indestructibleWallDensity = 0.3f;
	// Number of current indestructible wall
	private int indestructibleWallNum = 0;
	private boolean[][] visited;
	private UFSet set;

	/**
	 * Union find set for connectivity check
	 * 
	 * @author Zhuofan Chen
	 */
	class UFSet {
		private int set[];
		private int size = xSize * ySize;
		private int setCount = size;

		UFSet() {
			set = new int[size];
			for (int i = 0; i < size; i++)
				set[i] = -1;
		}

		int findRoot(int n) {
			while (set[n] >= 0) {
				n = set[n];
			}
			return n;
		}

		int findRoot(int y, int x) {
			return findRoot(y * xSize + x);
		}

		boolean connect(int p, int q) {
			p = findRoot(p);
			q = findRoot(q);
			if (p == q)
				return false;
			set[p] += set[q];
			set[q] = p;
			setCount--;
			return true;
		}

		boolean connect(int y1, int x1, int y2, int x2) {
			int p = y1 * xSize + x1;
			int q = y2 * xSize + x2;
			return connect(p, q);
		}

		boolean finishConnection() {
			return setCount == 1;
		}

		void addSet() {
			setCount++;
		}

		void removeSet() {
			setCount--;
		}
	}

	/**
	 * Construction method for given size
	 */
	public MapMatrix(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		wall = new byte[ySize][xSize];
		set = new UFSet();
		visited = new boolean[ySize][xSize];
		randomFill();
	}

	/**
	 * Randomly fill the map with wall, guaranteeing connectivity
	 */
	public void randomFill() {
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				if ((float) Math.random() < indestructibleWallDensity) {
					wall[i][j] = INDESTRUCTIBLE;
					set.removeSet();
					// indestructibleWallNum++;
				}
			}
		checkConnectivity();
		//testOutWall();
		clearBlock();
		//testOutWall();
		// Reset and random fill again when failed
		if(!set.finishConnection()) {
			wall = new byte[ySize][xSize];
			set = new UFSet();
			visited = new boolean[ySize][xSize];
			randomFill();
		}
	}

	/**
	 * remove walls that block the connectivity
	 */
	public void clearBlock() {
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				if (wall[i][j] == INDESTRUCTIBLE && isBlockConnectivity(i, j)) {
					wall[i][j] = NONE;
					set.addSet();
					connect(i, j);
				}
			}
	}

	/**
	 * @return if the wall on certain position block the connectivity
	 */
	public boolean isBlockConnectivity(int yPos, int xPos) {
		if (wall[yPos][xPos] == NONE)
			return false;
		int root[] = { -1, -1, -1, -1 };
		if (yPos < ySize - 1 && wall[yPos + 1][xPos] == NONE) {
			root[0] = set.findRoot(yPos + 1, xPos);
		}
		if (yPos > 0 && wall[yPos - 1][xPos] == NONE) {
			root[1] = set.findRoot(yPos - 1, xPos);
		}
		if (xPos < xSize - 1 && wall[yPos][xPos + 1] == NONE) {
			root[2] = set.findRoot(yPos, xPos + 1);
		}
		if (xPos > 0 && wall[yPos][xPos - 1] == NONE) {
			root[3] = set.findRoot(yPos, xPos - 1);
		}
		int positiveRoot = -1;
		for (int i = 0; i < 4; i++) {
			if (root[i] >= 0) {
				if (positiveRoot >= 0 && root[i] != positiveRoot)
					return true;
				positiveRoot = root[i];
			}
		}
		return false;
	}

	/**
	 * Check the connectivity of the map after indestructible walls have been
	 * randomly set
	 */
	public void checkConnectivity() {
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				connect(i, j);
				if (set.finishConnection())
					return;
			}
	}

	/**
	 * a recursive algorithm for connectivity check using UFSet
	 */
	public void connect(int yPos, int xPos) {
		if (visited[yPos][xPos] == true || wall[yPos][xPos] != NONE || set.finishConnection())
			return;
		visited[yPos][xPos] = true;
		if (yPos < ySize - 1 && wall[yPos + 1][xPos] == NONE) {
			set.connect(yPos, xPos, yPos + 1, xPos);
			connect(yPos + 1, xPos);
		}
		if (yPos > 0 && wall[yPos - 1][xPos] == NONE) {
			set.connect(yPos, xPos, yPos - 1, xPos);
			connect(yPos - 1, xPos);
		}
		if (xPos < xSize - 1 && wall[yPos][xPos + 1] == NONE) {
			set.connect(yPos, xPos, yPos, xPos + 1);
			connect(yPos, xPos + 1);
		}
		if (xPos > 0 && wall[yPos][xPos - 1] == NONE) {
			set.connect(yPos, xPos, yPos, xPos - 1);
			connect(yPos, xPos - 1);
		}
	}

	/**
	 * test out for connectivity check
	 */
	public void testOut() {
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				System.out.print(set.findRoot(i, j));
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		System.out.println(set.setCount);
	}

	/**
	 * test out to show the walls during clearBlock
	 */
	public void testOutWall() {
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				if (isBlockConnectivity(i, j))
					System.out.print("{}");
				else if (wall[i][j] == NONE)
					System.out.print("  ");
				else
					System.out.print("[]");
			}
			System.out.print("\n");
		}
		System.out.println(set.setCount);
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public boolean isWithDestructibleWall(int yPos, int xPos) {
		return wall[yPos][xPos] == DESTRUCTIBLE;
	}

	public boolean isWithIndestructibleWall(int yPos, int xPos) {
		return wall[yPos][xPos] == INDESTRUCTIBLE;
	}
}
