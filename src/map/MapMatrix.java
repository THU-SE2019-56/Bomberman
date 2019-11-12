package map;

import game.GameConstants;
import java.lang.Math;

/**
 * to be used to generate a map with a given or a new random matrix of wall
 * 
 * @author Zhuofan Chen
 * @version 0.1
 */
public class MapMatrix implements GameConstants {
	private int xSize;
	private int ySize;
	private byte[][] wall;
	private float destructibleWallDensity = 0.5f;
	private float indestructibleWallDensity = 0.5f;
	private int indestructibleWallNum = 0;

	class UFSet {
		int set[];
		int size = xSize * ySize;
		int setCount = size;

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

		boolean connect(int p, int q) {
			p = findRoot(p);
			q = findRoot(q);
			if (p == q)
				return false;
			set[p] += set[q];
			setCount--;
			return true;
		}

		boolean connect(int x1, int y1, int x2, int y2) {
			int p = y1 * xSize + x1;
			int q = y2 * xSize + x2;
			return connect(p, q);
		}
	}

	public MapMatrix(int xSize, int ySize) {
		wall = new byte[ySize][xSize];
		randomFill();
	}

	public void randomFill() {
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				if ((float) Math.random() < indestructibleWallDensity)
					wall[i][j] = INDESTRUCTIBLE;
				indestructibleWallNum++;
			}
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
