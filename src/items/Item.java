package items;

import player.Player;
import game.GameConstants;

/**
 * The super class for all the items.
 * 
 * @author Haozhe Zhan, Junyue Zheng
 * @version 0.2
 */
public class Item implements GameConstants {

	private int x;
	private int y;
	private int itemID = (int) (ITEM_NUM * (float)Math.random()); // saved in GameConstants to judge which item is created and used
	private boolean isAcquired = false;

	public Item(int xPos, int yPos) {
		x = xPos * 45 ;
		y = yPos * 45 ;
	}
	
	/**
	 * select item to use
	 */
	public void getItem(Player player) {
		if (!this.isAcquired) {
			switch (this.getItemID()) {
			case VELOCITY_UP:
				player.addVelocityByItems();// Correctly set the location of the player to avoid bugs
				break;
			case BOMB_UP:
				if (player.getBombMaxNumber() < PLAYER_MAX_BOMB) 
					player.setBombMaxNumber(player.getBombMaxNumber() + 1);
				break;
			case POWER_UP:
				if (player.getBombPower()<BOMB_MAX_POWER) 
					player.SetBombPower(player.getBombPower()+1);
				break;
			case HP_UP:
				if (player.getHP()<=player.getMaxHP()-HP_ADDED) {
					player.setHP(player.getHP() + HP_ADDED);
				}
				else if (player.getHP()> player.getMaxHP()-HP_ADDED) {
					player.setHP(player.getMaxHP());
				}
				break;
			case IMMUNE:
				player.setProtectedByItem(true);
				break;
			case BULLET:
				player.setActiveItemID(BULLET);
				break;
			case LANDMINE:
				player.setActiveItemID(LANDMINE);
				break;
			}
		}
	}

	public void setItemID(int i) {
		this.itemID = i;
	}

	public int getItemID() {
		return this.itemID;
	}

	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setIsAcquired(boolean f) {
		this.isAcquired = f;
	}

	public boolean getIsAcquired() {
		return this.isAcquired;
	}

}
