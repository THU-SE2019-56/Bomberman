package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import player.Player;

/**
 * <p>
 * Provide the map.
 * <p>
 * Draw all the players, enemies, walls, roads, etc. In fact, this class should
 * mainly just focuses on "drawing". Logical methods may be implemented in other
 * classes.
 * 
 * @author Wang
 * @version 0.1
 * 
 */
public class Display extends JPanel implements ActionListener {
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Player player;

	public Display() {
		timer = new Timer(REFRESH, this);
		timer.start();

		initializeMap();

		this.setSize(750, 670);
		this.addKeyListener(player);
	}

	@Override
	/**
	 * Repaint the whole component. All the methods necessary should have been
	 * implemented in other classes.
	 */
	public void actionPerformed(ActionEvent e) {
		/*
		 * TODO Every 30 milliseconds, do (at least) the following things: Move the
		 * player to new location, player.Player.setNewLocation(). Move the monsters to
		 * new location, monster.Monster.setNewLocation(). Decide whether the player is
		 * alive. If new bombs are planted? Have bombs exploded? Eliminate those walls
		 * and monsters that have been boomed.
		 */
		repaint();
	}

	/**
	 * <p>
	 * This method should be invoked in the constructor to initialize.
	 * <p>
	 * Paint the whold component including player, walls, monsters, etc.
	 */
	public void initializeMap() {
		// TODO
	}
}
