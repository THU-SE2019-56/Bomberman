package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class Display extends JPanel implements ActionListener, GameConstants, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Map map;
	
	private Monster[] monsters = new Monster[MONSTER_NUMBER];
	private Item item;
	private boolean gameOver = false;
	private int playerNum = 2;//Number of the players
	private int pauseFlag = 0;
	
	Player player[] = new Player[2];
	BufferedImage player1Image[] = new BufferedImage[4];
	BufferedImage player2Image[] = new BufferedImage[4];
	
	
	BufferedImage itemImage[] = new BufferedImage[3];
	BufferedImage monsterImage[] = new BufferedImage[4];
	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage gameImage[] = new BufferedImage[3];
	BufferedImage bombImage[] = new BufferedImage[2];

	JTextField playerLifeText[] = new JTextField[2];
	JButton pauseButton = new JButton("Pause");

	/**
	 *
	 * The method “public static void main(String args[]) 17 is achieved here to
	 * test the effects of the Player class. Remove it when you don't need it.
	 */
	public static void main(String args[]) {
		Display dp = new Display();
		dp.createPanel();
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
	 * Create JFrame and JPanel. Temp method.
	 */
	public void createPanel() {
		JFrame f = new JFrame();
		Display jp = new Display();

		jp.setLayout(null);
		jp.addStatusPanel();
		
		f.setTitle("Bomberman");
		f.getContentPane().setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);



		f.setVisible(true);
		jp.setVisible(true);
		f.add(jp);
		
		// set location to screen center
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		f.setLocation((ge.getMaximumWindowBounds().width-SCREEN_WIDTH)/2,
				(ge.getMaximumWindowBounds().height-SCREEN_HEIGHT)/2);
	}
	
	/**
	 * Add a status panel on the right side of the main panel.
	 */
	private void addStatusPanel() {
	
		for (int i = 0;i<this.playerNum;i++) {	
			if (i==PLAYER_ID_P1) {
				this.playerLifeText[i] = new JTextField("");
				this.playerLifeText[i].setBounds(CELL_SIZE * CELL_NUM_X+4*CELL_SIZE, 3*CELL_SIZE, 4*CELL_SIZE, CELL_SIZE/2);
			}
			if (i==PLAYER_ID_P2) {
				this.playerLifeText[i] = new JTextField("");
				this.playerLifeText[i].setBounds(CELL_SIZE * CELL_NUM_X+4*CELL_SIZE, 6*CELL_SIZE, 4*CELL_SIZE, CELL_SIZE/2);
			}
			this.playerLifeText[i].setEditable(false);
			this.playerLifeText[i].setForeground(Color.BLUE);
			this.playerLifeText[i].setBorder(null);
			Font textFont = new Font("Times New Roman Italic", Font.BOLD, 14);
			this.playerLifeText[i].setFont(textFont);
			this.playerLifeText[i].setVisible(true);
			this.playerLifeText[i].setBackground(null);

			this.add(this.playerLifeText[i]);
		}
		
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 16);
		this.pauseButton.setBounds(CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,8*CELL_SIZE,2*CELL_SIZE,CELL_SIZE);
		this.pauseButton.setVisible(true);
		this.pauseButton.setFont(buttonFont);
		this.pauseButton.addMouseListener(this);
		this.add(pauseButton);
		
	}
	
	/**
	 * Refresh the status panel.
	 */
	public void refreshStatusPanel(Graphics g) {
		for (int i = 0;i < this.playerNum;i++) {
			int playerHP = this.player[i].getHP();
			if (i==PLAYER_ID_P1) {
				this.playerLifeText[i].setText(String.valueOf(playerHP));
				g.setColor(Color.BLUE);
				g.drawRect(CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,3*CELL_SIZE, 100, CELL_SIZE/2);
				g.setColor(Color.ORANGE);
				g.fillRect(CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,3*CELL_SIZE, player[i].getHP(), CELL_SIZE/2);
				
				g.drawImage(player1Image[DIRECTION_DOWN],CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,CELL_SIZE,CELL_SIZE+CELL_SIZE/2,CELL_SIZE+CELL_SIZE/2,this);
			}
				
				
			if (i==PLAYER_ID_P2) {
				this.playerLifeText[i].setText(String.valueOf(playerHP));
				g.setColor(Color.BLUE);
				g.drawRect(CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,6*CELL_SIZE, 100, CELL_SIZE/2);
				g.setColor(Color.ORANGE);
				g.fillRect(CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,6*CELL_SIZE, player[i].getHP(), CELL_SIZE/2);
				
				g.drawImage(player2Image[DIRECTION_DOWN],CELL_SIZE * CELL_NUM_X+1*CELL_SIZE,4*CELL_SIZE,CELL_SIZE+CELL_SIZE/2,CELL_SIZE+CELL_SIZE/2,this);
			}
			
		
		}
	}

	/**
	 * End the game and show "game over"
	 */
	public void endGame(Graphics g) {
		g.drawImage(gameImage[GAMEOVER], 230, 230, 500, 300, this);
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

		this.refreshStatusPanel(g);
		
		for (int i=0;i<playerNum;i++) {
			if (player[i].acquireItem(item)==false) {
				paintItem(g);
			}
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
		
		}
	}

	public void paintItem(Graphics g) {
		g.drawImage(itemImage[item.getItemID()], item.getX(), item.getY(), ITEM_WIDTH, ITEM_HEIGHT, this);
	}


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
				if (map.isWithBomb(i, j))
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH, BOMB_HEIGHT, this);
				if (map.isAtExplosion(i,j))
					g.drawImage(bombImage[EXPLODE], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH, BOMB_HEIGHT, this);
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
		if (this.pauseFlag==0) {
			for (int i=0;i<playerNum;i++) {
				player[i].refresh(map);//refresh player
			}
			
			for (Monster m : monsters) { // Change the location of monsters
				if (m.isAlive()) {
					int p = (int)(playerNum*Math.random());	// select a bad luck player randomly
					m.monsterMove(player[p], map);
				}
			}
	
			map.refresh(); // Refresh the map, for bomb
	
			if (this.gameOver == false) {
				repaint();
			}
		}

	}

	/**
	 * This method should be invoked in the constructor to initialize. <br>
	 * Consider all components including player, walls, monsters, etc. <br>
	 * Perhaps using paintComponent(Graphics g) will be a more appropriate
	 * method ---Comment from Chengsong Xiong <br>
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
		bombImage[EXPLODE] = ImageIO.read(new File("image/bomb/explode.jpeg"));

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==this.pauseButton) {
			if (this.pauseFlag==0) {
				this.pauseFlag=1;
				this.pauseButton.setText("Start");
			}
			else {
				this.pauseFlag=0;
				this.pauseButton.setText("Pause");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}