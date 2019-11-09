package map;

import game.GameConstants;

/**
 * <p>
 * Store the necessary information. Should be updated by package bomb, items,
 * player. Used by player to decide its location after moving.
 *
 * @author Zhuofan Chen, Wang
 * @version 0.1
 */
public class Map implements GameConstants {
    // Define map matrix and its size
    // In matrix _map, the first index refers to the y axis position and the second
    // index refers to the x axis position
    private Cell[][] _map;
    private int ySize = CELL_NUM_Y;
    private int xSize = CELL_NUM_Y;

	/**
	 * Default construction method
	 */
	public Map() {

		_map = new Cell[ySize][xSize];
		//initialize _map
		for (int i=0;i<ySize; i++) {
			for (int j=0;j<xSize; j++) {
				_map[i][j]=new Cell();
			}
		}
	}

	/**
	 * Construction method for given y size and x size
	 */
	public Map(int ySize, int xSize) {
		this.ySize = ySize;
		this.xSize = xSize;
		_map = new Cell[ySize][xSize];

		//initialize _map;
		for (int i=0;i<ySize; i++) {
			for (int j=0;j<xSize; j++) {
				_map[i][j]=new Cell();
			}
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
	 * @return the cell at given position for any operation on certain cell
	 */
	public Cell map(int yPos, int xPos) {


		return _map[yPos][xPos];
	}

	public boolean isAvailable(int yPos, int xPos) {
		return yPos>=0 && yPos<ySize && xPos>=0 && xPos<xSize &&
				_map[yPos][xPos].isAvailable();
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

}