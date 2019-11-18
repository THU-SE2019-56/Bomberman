package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import items.Item;
import player.Player;
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
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */
public class MapPanel extends JPanel implements GameConstants {

	private static final long serialVersionUID = 1L;
	private Map map;

	private Monster[] monsters = new Monster[MONSTER_NUMBER];
	private Player[] player = new Player[PLAYER_NUMBER];
	private Item item;
	public boolean gameOver = false;
	private int playerNum = 0;// Number of the players
	private int pauseFlag;
	private int gameMode;

	BufferedImage player1Image[] = new BufferedImage[4];
	BufferedImage player2Image[] = new BufferedImage[4];
	BufferedImage itemImage[] = new BufferedImage[3];
	BufferedImage monsterImage[] = new BufferedImage[4];
	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage gameImage[] = new BufferedImage[3];
	BufferedImage bombImage[] = new BufferedImage[2];

	/**
	 * Initialize the Display class.
	 */
	public MapPanel(int mode) {
		try {
			loadImage();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		this.setSize(CELL_NUM_X*CELL_WIDTH,CELL_NUM_Y*CELL_HEIGHT);

		// Initialize map,player,monsters
		setMap(new Map(new MapMatrix(CELL_NUM_X, CELL_NUM_Y)));

		for (int i = 0; i < MONSTER_NUMBER; i++) {
			getMonsters()[i] = new Monster(getMap());
		}

		if (mode == PVE_MODE) {
			setPlayerNum(1);
		}
		if (mode == PVP_MODE) {
			setPlayerNum(2);
			for (Monster m : getMonsters()) {
				m.eliminate();
			}
		}
		this.gameMode = mode;
		this.pauseFlag = 0;

		for (int i = 0; i < getPlayerNum(); i++) {
			getPlayer()[i] = new Player(getMap(), i);
		}
		item = new Item(2, 2);

		for (int i = 0; i < getPlayerNum(); i++) {
			this.getToolkit().addAWTEventListener(getPlayer()[i], AWTEvent.KEY_EVENT_MASK);// Initialize the
																						// AWTEventListener.
		}

		this.setFocusable(true);
	}
	
	public int getMode(){
		return this.gameMode;
	}

	/**
	 * Get the pauseFlag
	 */
	public int getPauseFlag() {
		return this.pauseFlag;
	}

	/**
	 * Set the pauseFlag
	 */
	public void setPauseFlag(int pauseFlag) {
		this.pauseFlag = pauseFlag;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Monster[] getMonsters() {
		return monsters;
	}

	public void setMonsters(Monster[] monsters) {
		this.monsters = monsters;
	}

	/**
	 * End the game and show "game over"
	 */
	public void endGame(Graphics g,String endMessage) {
		
		//Draw "game over" Image
		g.drawImage(gameImage[GAMEOVER], CELL_NUM_X*CELL_WIDTH/4, CELL_NUM_Y*CELL_HEIGHT/4, CELL_NUM_X*CELL_WIDTH/2, CELL_NUM_Y*CELL_HEIGHT/4, this);
		g.setFont(new Font("Times New Roman Italic", Font.BOLD, 60));
		
		//Show message
		g.setColor(Color.WHITE);
		g.drawString(endMessage, CELL_NUM_X*CELL_WIDTH/4, CELL_NUM_Y*CELL_HEIGHT*2/3 );
		this.gameOver = true;
	}


	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintMap(g);
		paintPlayer(g);
		paintMonsters(g);
		for (int i = 0; i < getPlayerNum(); i++) {
			if (getPlayer()[i].acquireItem(item) == false) {
				paintItem(g);
			}
		}
		
		if (this.gameMode == PVE_MODE) {
			
			//Monsters win
			if (player[PLAYER_ID_P1].getHP() <= 0) endGame(g,"Monsters Win!");
			
			//Player wins
			else {
				boolean monstersAlive = false;
				for (Monster m : getMonsters()) {
					monstersAlive |= m.isAlive();
				}
				if (monstersAlive==false&&player[PLAYER_ID_P1].getHP() > 0) {
					endGame(g,"Player Wins!");
				}
			}
		}
		else if (this.gameMode == PVP_MODE) {
			if (player[PLAYER_ID_P1].getHP() <= 0&&player[PLAYER_ID_P2].getHP()>0 ) endGame(g,"Player 1 Wins!");
			else if (player[PLAYER_ID_P2].getHP() <= 0&&player[PLAYER_ID_P1].getHP()>0 ) endGame(g,"Player 2 Wins!");
			else if (player[PLAYER_ID_P1].getHP() <= 0&&player[PLAYER_ID_P2].getHP() <= 0 ) endGame(g,"Game Tie!");
		}	
		

	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		for (int i = 0; i < getPlayerNum(); i++) {
			if (getPlayer()[i].getPlayerID() == PLAYER_ID_P1) {
				g.drawImage(player1Image[getPlayer()[i].getImageDirection()], getPlayer()[i].getX(), getPlayer()[i].getY(),
						PLAYER_WIDTH, PLAYER_HEIGHT, this);
			}
			if (getPlayer()[i].getPlayerID() == PLAYER_ID_P2) {
				g.drawImage(player2Image[getPlayer()[i].getImageDirection()], getPlayer()[i].getX(), getPlayer()[i].getY(),
						PLAYER_WIDTH, PLAYER_HEIGHT, this);
			}
			g.setColor(Color.BLUE);

		}
	}

	public void paintItem(Graphics g) {
		g.drawImage(itemImage[item.getItemID()], item.getX(), item.getY(), ITEM_WIDTH, ITEM_HEIGHT, this);
	}

	public void paintMap(Graphics g) {
		int xSize = getMap().getSizeX();
		int ySize = getMap().getSizeY();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(mapImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(mapImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (getMap().isWithDestructibleWall(i, j))
					g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (getMap().isWithIndestructibleWall(i, j))
					g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (getMap().isWithBomb(i, j))
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				if (getMap().isAtExplosion(i, j))
					g.drawImage(bombImage[EXPLODE], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void paintMonsters(Graphics g) {
		for (Monster m : getMonsters()) {
			if (m.isAlive()) {
				g.drawImage(monsterImage[m.getImageDirection()], m.getX(), m.getY(), MONSTER_WIDTH, MONSTER_HEIGHT,
						this);
			}
		}
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
		bombImage[EXPLODE] = ImageIO.read(new File("image/bomb/explode.jpeg"));

	}
}