package items;

import player.Player;

public class BombUp extends Item {
	
	public BombUp(int xPos,int yPos) {
		super(xPos,yPos);
	}

	public void getItem(Player player) {
		// Eliminate itself(may all be written in the Item superclass)
	//	int b = player.getBombNumber();
	//	player.setBombNumber(b + 1);
	}

}
