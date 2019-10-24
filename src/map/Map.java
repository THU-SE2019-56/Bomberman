package map;

/**
 * <p>
 * Store the necessary information. Should be updated by package bomb, items, player. 
 * Used by player to decide its location after moving.
 * 
 * @author Wang
 * @version 0.1
 */
public class Map {
	static int[][] BOMB_INFO;// Store the location of bombs planted by a player
	static int[][] BACKGROUND_INFO;// Walls, unbreakable walls
	static int[][] ITEM_INFO;// Items' location
	static int[][] EXPLOSION_INFO;// Range of bombs
}
