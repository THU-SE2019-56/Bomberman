package game;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import player.Player;
import map.Map;
import monster.Monster;

/**
 * v0.1 <br>
 * Display the map. <br>
 * Draw all the players, enemies, walls, roads, etc. In fact, this class should
 * mainly just focuses on "drawing". Logical methods may be implemented in other
 * classes.
 * <p>
 * v0.2 <br>
 * Draw the player and achieve simple visual animation effects.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */
public class Display extends JPanel implements ActionListener, GameConstants {
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Map map;
	private Player player;
	private Monster[] monsters = new Monster[MONSTER_NUMBER];

	BufferedImage characterImage[] = new BufferedImage[4];

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
		try {
			loadImage();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		timer = new Timer(REFRESH, this);
		timer.start();

		// initializeMap(); May delete.

		map = new Map();
		player = new Player();
		for (int i = 0; i < MONSTER_NUMBER; i++) {
			monsters[i] = new Monster(1, 1);
		}

		this.setFocusable(true);
		this.getToolkit().addAWTEventListener(player, AWTEvent.KEY_EVENT_MASK);// Initialize the AWTEventListener.
	}

	/**
	 * Create JFrame and JPanel. Temp method.
	 */
	public static void createPanel() {
		JFrame f = new JFrame();
		Display jp = new Display();
		f.setTitle("Bomberman");
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
		paintPlayer(g);
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		g.drawImage(characterImage[player.getImageDirection()], player.getX(), player.getY(), 100, 100, this);
	}

	public void paintMap(Graphics g) {

	}

	public void paintMonsters(Graphics g) {

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
		player.playerMove();// Change the location of the player
		repaint();
	}

	/**
	 * This method should be invoked in the constructor to initialize. <br>
	 * Consider all components including player, walls, monsters, etc. <br>
	 * Perhaps using paintComponent(Graphics g) will be a more appropriate method？
	 * ---Comment from Chengsong Xiong <br>
	 * What I mean is, for example, setting the player's initial place or velocity
	 * here, not painting. ---Comment from Wang <br>
	 * Another thought, we should do all these in the construction methods. Maybe
	 * this method should be deleted. ---Comment from Wang
	 */
	public void initializeMap() {
		// TODO
	}

	public void loadImage() throws Exception {
		// TODO Load all the images here

		characterImage[DIRECTION_UP] = ImageIO.read(new File("image/character/characterBack.png"));
		characterImage[DIRECTION_RIGHT] = ImageIO.read(new File("image/character/characterRight.png"));
		characterImage[DIRECTION_DOWN] = ImageIO.read(new File("image/character/characterFront.png"));
		characterImage[DIRECTION_LEFT] = ImageIO.read(new File("image/character/characterLeft.png"));
	}
}
