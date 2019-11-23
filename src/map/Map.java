package map;

import java.lang.Math;

import game.GameConstants;
import bomb.Bomb;
import items.Item;
import player.Player;

/**
 * Core class of the game. It is composed of Cell, and store a matrix of size
 * CELL_NUM_Y*CELL_NUM_X.<br>
 * Provides methods with params x, y to get access to the state of a certain
 * cell.
 *
 * @author Zhuofan Chen
 * @version 1.0
 */
public class Map implements GameConstants {
	/*
	 * Define map matrix and its size. In matrix _map, the first index refers to the
	 * y axis position and the second index refers to the x axis position.
	 */
	private Cell[][] _map;
	private int ySize = CELL_NUM_Y;
	private int xSize = CELL_NUM_X;

	/**
	 * Default construction method
	 */
	public Map() {
		_map = new Cell[ySize][xSize]; // initialize _map
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				_map[i][j] = new Cell();
			}
	}

	/**
	 * Construction method for given y size and x size
	 */
	public Map(int ySize, int xSize) {
		this.ySize = ySize;
		this.xSize = xSize;
		_map = new Cell[ySize][xSize]; // initialize _map;
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				_map[i][j] = new Cell();
			}
	}

	/**
	 * Construction method for given MapMatrix
	 */
	public Map(MapMatrix mmat) {
		xSize = mmat.getXSize();
		ySize = mmat.getYSize();
		_map = new Cell[ySize][xSize];
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				_map[i][j] = new Cell();
				if (mmat.isWithDestructibleWall(i, j))
					_map[i][j].setWall(true);
				if (mmat.isWithIndestructibleWall(i, j))
					_map[i][j].setWall(false);
			}
	}
	
	/**
	 * Construction method for given int[][]
	 */
	public Map(int[][] wallMatrix) {
		ySize = wallMatrix.length;
		xSize = wallMatrix[0].length;
		_map = new Cell[ySize][xSize];
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				_map[i][j] = new Cell();
				if (wallMatrix[i][j]==DESTRUCTIBLE)
					_map[i][j].setWall(true);
				if (wallMatrix[i][j]==INDESTRUCTIBLE)
					_map[i][j].setWall(false);
			}
	}

	/**
	 * Call all member methods needing refreshing
	 */
	public void refresh() {
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++)
				_map[i][j].refresh();
	}

	/**
	 * @return the cell at given position for any operation on certain cell !!this
	 *         method is NOT RECOMMENDED!!
	 * @deprecated
	 */
	public Cell getCell(int xPos, int yPos) {
		return _map[yPos][xPos];
	}

	/**
	 * Judge whether a certain position is out of map
	 */
	public boolean isInMap(int xPos, int yPos) {
		return yPos >= 0 && yPos < ySize && xPos >= 0 && xPos < xSize;
	}

	/**
	 * @return if the cell of certain yPos and xPos is available
	 */
	public boolean isAvailable(int xPos, int yPos) {
		return isInMap(xPos, yPos) && _map[yPos][xPos].isAvailable();
	}

	/**
	 * Get the xSize of the map
	 */
	public int getSizeX() {
		return this.xSize;
	}

	/**
	 * Get the ySize of the map
	 */
	public int getSizeY() {
		return this.ySize;
	}

	/**
	 * @param xPos      x position to set bomb
	 * @param yPos      y position to set bomb
	 * @param bombPower power of bomb
	 * @param owner     the player who set the bomb
	 * @return if the bomb is successfully set
	 */
	public boolean setBomb(int xPos, int yPos, int bombPower, Player owner) {
		if (isInMap(xPos, yPos) && _map[yPos][xPos].setBomb(new Bomb(xPos, yPos, bombPower, this, owner))) {
			owner.addBombPlantedNumber();
			return true;
		}
		return false;
	}

	/**
	 * @return if an bomb is successfully removed from given position
	 */
	public boolean removeBomb(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].removeBomb());
	}

	/**
	 * @return if a bomb is on given position
	 */
	public boolean isWithBomb(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isWithBomb());
	}

	/**
	 * @param xPos, yPos position to set item
	 * @return if the item is successfully set
	 */
	public boolean setItem(int xPos, int yPos) {
		if (isInMap(xPos, yPos) && _map[yPos][xPos].setItem(new Item(xPos, yPos))) {
			return true;
		}
		return false;
	}

	/**
	 * Create an item at given position according to given chance in GameConstants
	 * 
	 * @return if the item is successfully created
	 */
	public boolean createItem(int xPos, int yPos) {
		if ((float) Math.random() < ITEM_CHANCE) {
			return (setItem(xPos, yPos));
		}
		return false;
	}

	/**
	 * @return if an item is successfully removed from given position
	 */
	public boolean removeItem(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].removeItem());
	}

	/**
	 * @return if an item is on given position
	 */
	public boolean isWithItem(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isWithItem());
	}

	/**
	 * Activate a explosion effect on given position
	 */
	public void explosionActivate(int xPos, int yPos) {
		if (isInMap(xPos, yPos)) {
			if (_map[yPos][xPos].isWithDestructibleWall())
				createItem(xPos, yPos);
			_map[yPos][xPos].explosionActivate();
		}
	}

	/**
	 * @return given position is now at explosion
	 */
	public boolean isAtExplosion(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isAtExplosion());
	}

	/**
	 * set a wall at given position
	 * 
	 * @param destructible whether the wall to be set is destructible
	 */
	public void setWall(int xPos, int yPos, boolean destructible) {
		if (isInMap(xPos, yPos))
			_map[yPos][xPos].setWall(destructible);
	}

	/**
	 * remove wall from given position
	 */
	public void removeWall(int xPos, int yPos) {
		if (isInMap(xPos, yPos))
			_map[yPos][xPos].removeWall();
	}

	/**
	 * @return if a wall is on given position
	 */
	public boolean isWithWall(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isWithWall());
	}

	/**
	 * @return if a destructible wall is on given position
	 */
	public boolean isWithDestructibleWall(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isWithDestructibleWall());
	}

	/**
	 * @return if an indestructible wall is on given position
	 */
	public boolean isWithIndestructibleWall(int xPos, int yPos) {
		return (isInMap(xPos, yPos) && _map[yPos][xPos].isWithIndestructibleWall());
	}

	/**
	 * @param power assumed bomb power
	 * @return true if given position is threatened by bomb, assuming all bomb with
	 *         a certain power
	 */
	public boolean isInExplosionRange(int xPos, int yPos, int power) {
		if (!isInMap(xPos, yPos))
			return false;
		if (isWithBomb(xPos, yPos))
			return true;
		for (int i = xPos - 1; i >= xPos - power; i--) {
			if (!isInMap(i, yPos) || isWithWall(i, yPos))
				break;
			if (isWithBomb(i, yPos))
				return true;
		}
		for (int i = xPos + 1; i <= xPos + power; i++) {
			if (!isInMap(i, yPos) || isWithWall(i, yPos))
				break;
			if (isWithBomb(i, yPos))
				return true;
		}
		for (int i = yPos - 1; i >= yPos - power; i--) {
			if (!isInMap(xPos, i) || isWithWall(xPos, i))
				break;
			if (isWithBomb(xPos, i))
				return true;
		}
		for (int i = yPos + 1; i <= yPos + power; i++) {
			if (!isInMap(xPos, i) || isWithWall(xPos, i))
				break;
			if (isWithBomb(xPos, i))
				return true;
		}
		return false;
	}

}