package player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import map.Map;
import monster.Monster;

/**
 * It is actually a JPanel(picture) which can be controlled by the keyboard.
 * When playing, we move this JPanel on the top of the map.
 * 
 * @author Wang
 * @version 0.1
 */
public class Player implements KeyListener {
	private int velocity;
	private int bomb_num;
	private int bomb_pow;
	private boolean is_immune;
	private boolean is_alive;
	private int direction;
	private int x;
	private int y;

	ImageIcon icon = new ImageIcon("images/player.png");

	// TODO Complete all the set, get methods
	public void setVelocity(int v) {
		this.velocity = v;
	}

	public int getVelocity() {
		return this.velocity;
	}

	/**
	 * Use mapinfo to decide the player's location after moving. Can not receive any
	 * command before it moves completely into a new integral cell.
	 */
	public void setNewLocation(Player player, Map mi) {
		// TODO Move the player, consider walls, bombs. Use setLocation()

	}

	/**
	 * Update mapinfo BOMB_INFO
	 */
	public void plantBomb(Player player, Map mi) {

	}

	/**
	 * Use mapinfo and monsters to decide whether the player is alive, whether the
	 * player acquire any item, etc.
	 */
	public void decideState(Player player, Map mi, Monster[] monsters) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Set the direction, or plant a bomb according to the keyboard
		int keyCode = arg0.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			// setDirection(LEFT)
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		// setDirection(STOP)
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
