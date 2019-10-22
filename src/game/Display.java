package game;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import player.Player;

/**
 * v0.1 <br>
 * Display the map. <br>
 * Draw all the players, enemies, walls, roads, etc. In fact, this class should
 * mainly just focuses on "drawing". Logical methods may be implemented in other
 * classes.
 * <p>
 * v0.2 <br>
 * Draw the player and achieve simple visual motion effects.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */
public class Display extends JPanel implements ActionListener, GameConstants {
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Player player;

	private ImageIcon characterDownImage;
	private ImageIcon characterUpImage;
	private ImageIcon characterRightImage;
	private ImageIcon characterLeftImage;

	/**
	 * 
	 * The method “public static void main(String args[])” is achieved here to test
	 * the effects of the Player class. Remove it when you don't need it.
	 */
	public static void main(String args[]) {
		createPanel();
	}

	/**
	 * Initialize the Display class.
	 */
	public Display() {
		timer = new Timer(REFRESH, this);
		timer.start();

		initializeMap();
		loadImage();

		player = new Player();

		this.setFocusable(true);
		this.getToolkit().addAWTEventListener(player, AWTEvent.KEY_EVENT_MASK);// Initialize the AWTEventListener.
	}

	/**
	 * Create JFrame and JPanel. Temp method.
	 */
	public static void createPanel() {
		JFrame f = new JFrame();
		Display jp = new Display();
		f.setTitle("POP Tag");
		f.setBounds(0, 0, 1000, 1000);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		jp.setVisible(true);
		f.add(jp);
	}

	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		player.playerMove();// Change the location of the player
		paintPlayer(g);
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		switch (player.getImageDirection()) {
		case DIRECTION_UP:
			g.drawImage(characterUpImage.getImage(), player.getX(), player.getY(), 100, 100, this);
			break;
		case DIRECTION_DOWN:
			g.drawImage(characterDownImage.getImage(), player.getX(), player.getY(), 100, 100, this);
			break;
		case DIRECTION_RIGHT:
			g.drawImage(characterRightImage.getImage(), player.getX(), player.getY(), 100, 100, this);
			break;
		case DIRECTION_LEFT:
			g.drawImage(characterLeftImage.getImage(), player.getX(), player.getY(), 100, 100, this);
			break;
		}
	}

	@Override
	/**
	 * Repaint the whole component. All the logical methods necessary should have
	 * been implemented in other classes.
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
	 * This method should be invoked in the constructor to initialize. <br>
	 * Consider all components including player, walls, monsters, etc. <br>
	 * Perhaps using paintComponent(Graphics g) will be a more appropriate method？
	 * ---Comment from Chengsong Xiong <br>
	 * What I mean is, for example, setting the player's initial place or velocity
	 * here, not painting. ---Comment from Wang
	 */
	public void initializeMap() {
		// TODO
	}

	public void loadImage() {
		// TODO Load all the images here
		characterDownImage = new ImageIcon("image/characterFront.png");
		characterUpImage = new ImageIcon("image/characterBack.png");
		characterRightImage = new ImageIcon("image/characterRight.png");
		characterLeftImage = new ImageIcon("image/characterLeft.png");
	}
}
