package items;

import player.Player;

/**
 * Increase the speed
 * 
 * @author Wang
 * @version 0.1
 */
public class VelocityUp extends Item {

	public VelocityUp(int xPos, int yPos, int ID) {
		super(xPos,yPos);
	}

	public void getItem(Player player) {
		// Eliminate itself(may all be written in the Item superclass)
		int v = player.getVelocity();
		player.setVelocity(v + 20);
	}
}
