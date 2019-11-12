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
import map.Cell;
import map.Map;
import map.MapMatrix;
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
 * @author Chengsong, Wang
 * @version 0.2
 */
public class Display extends JPanel implements ActionListener, GameConstants {
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Map map;
	private int playerNum = 2;//Number of the players
	private Monster[] monsters = new Monster[MONSTER_NUMBER];
	private Item item;
	private boolean gameOver = false;

	Player player[] = new Player[2];
	BufferedImage player1Image[] = new BufferedImage[4];
	BufferedImage player2Image[] = new BufferedImage[4];
	
	BufferedImage itemImage[] = new BufferedImage[3];
	BufferedImage monsterImage[] = new BufferedImage[4];
	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage gameImage[] = new BufferedImage[3];
	BufferedImage bombImage[] = new BufferedImage[2];

	/**
	 *
	 * The method “public static void main(String args[]) 17 is achieved here to
	 * test the effects of the Player class. Remove it when you don't need it.
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

		//map = new Map();
		//generateTestMap();
		map = new Map(new MapMatrix(CELL_NUM_X,CELL_NUM_Y));
		
		for (int i=0;i<playerNum;i++) {
			player[i] = new Player(map,i);
		}
		item = new Item(2, 2);
		for (int i = 0; i < MONSTER_NUMBER; i++) {
			monsters[i] = new Monster(map);
		}

		this.setFocusable(true);
		for (int i=0;i<playerNum;i++) {
			this.getToolkit().addAWTEventListener(player[i], AWTEvent.KEY_EVENT_MASK);// Initialize the AWTEventListener.
		}
	}

	/**
	 * to detect if the player and items are collided
	 */
	public boolean itemCollisionDetection() {

		boolean isGet = false;
		
		for (int i=0;i<playerNum;i++) {
			int playerX = player[i].getX();
			int playerY = player[i].getY();
			int itemX = item.getX();
			int itemY = item.getY();

			Rectangle playerRectangle = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
			Rectangle itemRectangle = new Rectangle(itemX, itemY, ITEM_WIDTH, ITEM_HEIGHT);

			if (playerRectangle.intersects(itemRectangle)) {

				item.getItem(player[i]);
				item.setIsAcquired(true);
				isGet = true;
			} 
			else {
				isGet = false;
			}
		}
		
	return item.getIsAcquired();
		
	}

	/**
	 * Create JFrame and JPanel. Temp method.
	 */
	public static void createPanel() {
		JFrame f = new JFrame();
		Display jp = new Display();
		f.setTitle("Bomberman");
		f.getContentPane().setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		// f.setLocationRelativeTo(null); // set location to screen center
		// --this method will lead to the failure of the game, and I don't know why.....
		// Perhaps owning to the macOS system
		f.setVisible(true);
		jp.setVisible(true);
		f.add(jp);
	}

	/**
	 * End the game and show "game over"
	 */
	public void endGame(Graphics g) {
		g.drawImage(gameImage[GAMEOVER], 230, 230, 500, 300, this);
		this.gameOver = true;
	}

	/**
	 * PLEASE NOTE: This is only a temporary method for testing the method
	 * "plantBomb()" of the player, without using the bomb class.
	 */
	public void updateBomb(Graphics g) {
		for (int i = 0; i < map.getSizeX(); i++) {
			for (int j = 0; j < map.getSizeY(); j++) {
				if (map.getCell(i, j).isWithBomb())
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH, BOMB_HEIGHT, this);
			}
		}
	}

	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paintMapTest(g);
		paintMap(g);
		updateBomb(g);// PLEASE NOTE: This is only a temporary method for testing the method
		// "plantBomb()" of the player,without using the bomb class.

		paintPlayer(g);
		paintMonsters(g);

		if (!itemCollisionDetection()) {
			paintItem(g);
		}
		//if (player.getHP() <= 0) {
			// endGame(g);
		//}
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		
		for (int i=0;i<playerNum;i++) {
			if (player[i].getPlayerID()==PLAYER_ID_P1) {
			g.drawImage(player1Image[player[i].getImageDirection()], player[i].getX(), player[i].getY(), PLAYER_WIDTH,
					PLAYER_HEIGHT, this);
			}
			if (player[i].getPlayerID()==PLAYER_ID_P2) {
			g.drawImage(player2Image[player[i].getImageDirection()], player[i].getX(), player[i].getY(), PLAYER_WIDTH,
					PLAYER_HEIGHT, this);
			}
			
			g.setColor(Color.BLUE);
			
			
			/*
			 * Draw HP bar of the player
			 */
			g.drawRect(player[i].getX(), player[i].getY() - 15, CELL_WIDTH, 10);
			g.setColor(Color.RED);
			g.fillRect(player[i].getX(), player[i].getY() - 15, player[i].getHP(), 10);
		}
	}

	public void paintItem(Graphics g) {
		g.drawImage(itemImage[item.getItemID()], item.getX(), item.getY(), ITEM_WIDTH, ITEM_HEIGHT, this);
	}

	/**
	 * Paint a simple map for testing.
	 */
//	public void paintMapTest(Graphics g) {
//		for (byte i = 0; i < map.getSizeX(); i++)
//			for (byte j = 0; j < map.getSizeY(); j++) {
//				if ((j == 4 || j == 9 || j == 11) && (i == 3 || i == 6 || i == 9 || i == 10 || i == 11)) {
//					Cell mapCell = map.getCell(i, j);
//					mapCell.setWall(true);// write in display()
//				}
//			}
//		paintMap(g);
//	}

	public void generateTestMap() {
		for (byte i = 0; i < map.getSizeX(); i++)
			for (byte j = 0; j < map.getSizeY(); j++) {
				if ((j == 4 || j == 9 || j == 11) && (i == 3 || i == 6 || i == 9 || i == 10 || i == 11)) {
					Cell mapCell = map.getCell(i, j);
					mapCell.setWall(true);// write in display()
				}
			}
	}

	public void paintMap(Graphics g) {
		int xSize = map.getSizeX();
		int ySize = map.getSizeY();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(mapImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				else
					g.drawImage(mapImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				if (map.isWithDestructibleWall(i, j))
					g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				if (map.isWithIndestructibleWall(i, j))
					g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void paintMonsters(Graphics g) {
		for (Monster m : monsters) {
			if (m.isAlive()) {
				g.drawImage(monsterImage[m.getImageDirection()], m.getX(), m.getY(), MONSTER_WIDTH, MONSTER_HEIGHT,
						this);
			}
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
		for (int i=0;i<playerNum;i++) {
			player[i].playerMove();// Change the location of the player
			for (Monster m : monsters) { // Change the location of monsters
				if (m.isAlive()) {
					m.monsterMove(player[i], map);
				}
			}
		}

		if (this.gameOver == false) {
			repaint();
		}

		repaint();
	}

	/**
	 * This method should be invoked in the constructor to initialize. <br>
	 * Consider all components including player, walls, monsters, etc. <br>
	 * Perhaps using paintComponent(Graphics g) will be a more appropriate
	 * method? ---Comment from Chengsong Xiong <br>
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

		player1Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p1UP.png"));
		player1Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p1RIGHT.png"));
		player1Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p1DOWN.png"));
		player1Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p1LEFT.png"));
		
		player2Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p2UP.png"));
		player2Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p2RIGHT.png"));
		player2Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p2DOWN.png"));
		player2Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p2LEFT.png"));	
		
		itemImage[VELOCITY_UP] = ImageIO.read(new File("image/Item/velocity.png"));
		monsterImage[DIRECTION_UP] = ImageIO.read(new File("image/monster/up.png"));
		monsterImage[DIRECTION_DOWN] = ImageIO.read(new File("image/monster/down.png"));
		monsterImage[DIRECTION_RIGHT] = ImageIO.read(new File("image/monster/right.png"));
		monsterImage[DIRECTION_LEFT] = ImageIO.read(new File("image/monster/left.png"));
		mapImage[GROUND_1] = ImageIO.read(new File("image/maps/ground1.png"));
		mapImage[GROUND_2] = ImageIO.read(new File("image/maps/ground2.png"));
		mapImage[DESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_destructible.png"));
		mapImage[INDESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_indestructibel.png"));
		gameImage[GAMEOVER] = ImageIO.read(new File("image/game/gameover.jpg"));
		bombImage[BOMB] = ImageIO.read(new File("image/bomb/bomb.png"));
	}
}