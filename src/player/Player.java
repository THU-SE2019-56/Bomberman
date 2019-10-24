package player;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import game.GameConstants;
//import map.Map;
//import monster.Monster;

/**
 * The player can be controlled by the keyboard. However its motion is not
 * related to the maps now. The character runs on a blank JPanel without the
 * limitation of the map info.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */

public class Player implements AWTEventListener, GameConstants {
	private int velocity;
	private int bombNumber;
	private int bombPower;
	private boolean isImmune;
	private boolean isAlive;
	private int direction;
	private int imageDirection;// This variable is for deciding the image of DIRECTION_STOP 
	private int x;
	private int y;

	public Player() {
		this.direction=DIRECTION_STOP;
		this.imageDirection=DIRECTION_DOWN;
		this.x=0;
		this.y=0;
		this.velocity=20;
	}

	// TODO Complete all the set, get methods

	/**
	 * Use setVelocity() to set the velocity of the player.
	 * 
	 * @param v Velocity of the player.
	 */
	public void setVelocity(int v) {
		this.velocity = v;
	}

	/**
	 * Get the velocity of the player.
	 * 
	 * @return Return an integer value.
	 */
	public int getVelocity() {
		return this.velocity;
	}

	/**
	 * Use setDirection() to set the direction of the player.
	 * 
	 * @param d Direction of the player.
	 */
	public void setDirection(int d) {
		if(d!=DIRECTION_STOP) {
			this.direction = d;
			this.imageDirection=d;
		}
		else {
			this.direction=d;
		}
	}

	/**
	 * Use this method to reset the direction of the player.
	 * 
	 * @param d Direction of the player.
	 */

	/**
	 * Get the direction of the players.
	 * 
	 * @return Return a double value.
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Get the image direction of the players.
	 * 
	 * @return Return a double value.
	 */
	public int getImageDirection() {
		return this.imageDirection;
	}

	/**
	 * Set the x of the player.
	 * 
	 * @param X
	 */
	public void setX(int X) {
		this.x = X;
	}

	/**
	 * Set the y of the player.
	 * 
	 * @param Y
	 */
	public void setY(int Y) {
		this.y = Y;
	}

	/**
	 * Get the x of the player.
	 * 
	 * @return Return an integer value.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the y of the player.
	 * 
	 * @return Return an integer value.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Set the location of the player.
	 * 
	 * @param X
	 * @param Y
	 */
	public void setLocation(int X, int Y) {
		this.x = X;
		this.y = Y;
	}

	/**
	 * <p>
	 * Judge the direction of the player and change the images and the coordinates.
	 * <p>
	 * This method aims to eliminate the key delay of the computer itself.
	 */
	public void playerMove() {
		switch (this.getDirection()) {
		case DIRECTION_UP:
			this.setY(this.getY() - this.getVelocity());
			break;
		case DIRECTION_DOWN:
			this.setY(this.getY() + this.getVelocity());
			break;
		case DIRECTION_LEFT:
			this.setX(this.getX() - this.getVelocity());
			break;
		case DIRECTION_RIGHT:
			this.setX(this.getX() + this.getVelocity());
			break;
		case DIRECTION_STOP:
			break;
		}
	}

	/**
	 * <p>
	 * Respond to the keyboard events.
	 * <p>
	 * I believe this methods is more convenient and effective than "KeyListener",
	 * since it is easier to find the focus.
	 * 
	 */
	@Override
	public void eventDispatched(AWTEvent event) {
		/*
		 * When the keys are pressed, set the direction of the player.
		 */
		if (event.getID() == KeyEvent.KEY_PRESSED) {
			KeyEvent e = (KeyEvent) event;
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				this.setDirection(DIRECTION_UP);

			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.setDirection(DIRECTION_DOWN);

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.setDirection(DIRECTION_LEFT);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.setDirection(DIRECTION_RIGHT);
			}
		}

		/*
		 * When the keys are released, set the direction of the player to DIRECTION_STOP.
		 */
		if (event.getID() == KeyEvent.KEY_RELEASED) {
			this.setDirection(DIRECTION_STOP);
		}

	}

	/**
	 * To run player version 0.2, these methods are temporarily commented out.
	 */

//	/**
//	 * Use mapinfo to decide the player's location after moving. Can not receive any
//	 * command before it moves completely into a new integral cell.
//	 */
//	public void setNewLocation(Player player, Map mi) {
//		 TODO Move the player, consider walls, bombs. Use setLocation()
//
//	}
//
//	/**
//	 * Update mapinfo BOMB_INFO
//	 */
//	public void plantBomb(Player player, Map mi) {
//
//	}
//
//	/**
//	 * Use mapinfo and monsters to decide whether the player is alive, whether the
//	 * player acquire any item, etc.
//	 */
//	public void decideState(Player player, Map mi) {
//
//	}

}
