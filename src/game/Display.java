package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import items.Item;

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
	private Item item;

	BufferedImage characterImage[] = new BufferedImage[4];
	BufferedImage itemImage[] = new BufferedImage[3];

	/**
	 * 
	 * The method “public static void main(String args[]) 1�7 is achieved here to test
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

//		map = new Map();
		player = new Player();
		item = new Item();
		for (int i = 0; i < MONSTER_NUMBER; i++) {
			monsters[i] = new Monster(1, 1);
		}

		this.setFocusable(true);
		this.getToolkit().addAWTEventListener(player, AWTEvent.KEY_EVENT_MASK);// Initialize the AWTEventListener.
	}
	/**
	 * to detect if the player and items are collided
	 * @return
	 */
	public boolean itemCollisionDetection() {
		int playerX = player.getX();
		int playerY = player.getY();
		int itemX = item.getX();
		int itemY = item.getY();
		int itemSize = 60;
		
		Rectangle playerRectangle = new Rectangle(playerX, playerY, 100, 100);
		Rectangle itemRectangle = new Rectangle(itemX, itemY, 60, 60);
		
		if(playerRectangle.intersects(itemRectangle)) {
			item.setIsAcquired(true);
			item.getItem(player);
			return item.getIsAcquired();
		}
		else return item.getIsAcquired();
		

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
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintPlayer(g);
		paintMonsters(g);
		if(!itemCollisionDetection()) {
			paintItem(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		g.drawImage(characterImage[player.getImageDirection()], player.getX(), player.getY(), 100, 100, this);
	}
	
	public void paintItem(Graphics g) {
		g.drawImage(itemImage[item.getItemID()], item.getX(), item.getY(), 60, 60, this);
	}

	public void paintMap(Graphics g) {

	}

	public void paintMonsters(Graphics g) {
		for (Monster m: monsters) {
			m.refresh();
			g.drawImage(characterImage[m.getDirection()],
					m.getX(), m.getY(), 100, 100, this);
		}
	}

	/**
	 * Repaint the whole component. All the logical methods necessary should have
	 * been implemented in other classes.
	 */
	@Override
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
	 * Perhaps using paintComponent(Graphics g) will be a more appropriate method＄1�7
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
		itemImage[VELOCITY_UP] = ImageIO.read(new File("image/Item/velocity.png"));

	}
}
