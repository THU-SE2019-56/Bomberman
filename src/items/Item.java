package items;

import javax.swing.JPanel;

import player.Player;

/**
 * The super class for all the items
 * 
 * @author Wang
 * @version 0.1
 */
abstract public class Item{
	public Item() {
		
	}

	abstract public void getItem(Player player);
}
