<<<<<<< HEAD
package map;

/**
 * <p>
 * Store the necessary information. Should be updated by package bomb, items,
 * player. Used by player to decide its location after moving.
 * 
 * @author Zhuofan Chen, Wang
 * @version 0.1
 */
public class Map {
	// Define map matrix and its size
	// In matrix _map, the first index refers to the y axis position and the second
	// index refers to the x axis position
	private Cell[][] _map;
	private byte ySize = 16;
	private byte xSize = 16;

	/**
	 * Default construction method
	 */
	Map() {
		_map = new Cell[ySize][xSize];
	}

	/**
	 * Construction method for given y size and x size
	 */
	Map(byte ySize, byte xSize) {
		this.ySize = ySize;
		this.xSize = xSize;
		_map = new Cell[ySize][xSize];
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
	public Cell map(byte yPos, byte xPos) {
		return _map[yPos][xPos];
	}
}
=======
package map;

/**
 * <p>
 * Store the necessary information. Should be updated by package bomb, items,
 * player. Used by player to decide its location after moving.
 * 
 * @author Zhuofan Chen, Wang
 * @version 0.1
 */
public class Map {
	// Define map matrix and its size
	// In matrix _map, the first index refers to the y axis position and the second
	// index refers to the x axis position
	private Cell[][] _map;
	private byte ySize = 16;
	private byte xSize = 16;

	/**
	 * Default construction method
	 */
	public Map() {
		
		_map = new Cell[ySize][xSize];
		//initialize _map
		for (byte i=0;i<ySize; i++) {
			for (byte j=0;j<xSize; j++) {
				_map[i][j]=new Cell();
			}
		}
	}

	/**
	 * Construction method for given y size and x size
	 */
	public Map(byte ySize, byte xSize) {
		this.ySize = ySize;
		this.xSize = xSize;
		_map = new Cell[ySize][xSize];
		
		//initialize _map;
		for (byte i=0;i<ySize; i++) {
			for (byte j=0;j<xSize; j++) {
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
	public Cell map(byte yPos, byte xPos) {
		
		
		return _map[yPos][xPos];
	}
	
	/**
	 * Get the xSize of the map 
	 */
	public byte getSizeX() {
		return this.xSize;
	}
	
	/**
	 * Get the ySize of the map
	 */
	public byte getSizeY() {
		return this.ySize;
	}
	
}
>>>>>>> 21e322865eadfd5ca6f1ad369ab2474bffe5add4
