package map;

/**
 * to be used to generate a map with a given or a new random matrix of wall
 * 
 * @author Zhuofan Chen
 * @version 0.1
 */
public class MapMatrix {
	private boolean[][] destructibleWall;
	private boolean[][] indestructibleWall;
	
	public MapMatrix(int ySize, int xSize){
		destructibleWall = new boolean[ySize][xSize];
		indestructibleWall = new boolean[ySize][xSize];
	}
	
	public void randomFill() {
		
	}
}
