package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

import game.GameConstants;
import game.Game;
import player.PlayerController;
import monster.Monster;

/**
 * Display necessary elements for the game, including player(s), monsters,
 * walls, items, etc.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class MapPanel extends JPanel implements GameConstants {

	private static final long serialVersionUID = 1L;

	private Game game;
	private MainFrame mainFrame;
	private int theme;
	
	BufferedImage player1Image[] = new BufferedImage[4];
	BufferedImage player2Image[] = new BufferedImage[4];
	BufferedImage player3Image[] = new BufferedImage[4];
	BufferedImage player4Image[] = new BufferedImage[4];
	BufferedImage itemImage[] = new BufferedImage[ITEM_NUM];
	BufferedImage monsterImage[][] = new BufferedImage[5][3];
	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage wallImage[][] = new BufferedImage[4][8];
	BufferedImage bombImage[] = new BufferedImage[2];
	BufferedImage bulletImage[] = new BufferedImage[4];

	/**
	 * Initialize the Display class.
	 * 
	 * @throws Exception
	 */
	public MapPanel(Game game, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.game = game;
		try {
			loadImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.theme = game.getTheme();
		this.setSize(MAP_WIDTH, MAP_HEIGHT);
		this.setFocusable(true);

		for (int i = 0; i < game.getPlayerNum(); i++) {
			this.getToolkit().addAWTEventListener(new PlayerController(game.getPlayer()[i]), AWTEvent.KEY_EVENT_MASK);// Initialize
																														// the
			// AWTEventListener.
		}
	}

	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintMap(g);
		paintMonsters(g);
		paintActiveItem(g);
		paintPlayer(g);
		for (int i = 0; i < game.getPlayerNum(); i++) {
			game.getPlayer()[i].acquireItemByMap(game.getMap());
		}
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		for (int i = 0; i < game.getPlayerNum(); i++) {

			if (game.getPlayer()[i].proectedByItem()) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.yellow);
				g2d.drawOval(game.getPlayer()[i].getX() - CELL_WIDTH / 4, game.getPlayer()[i].getY() - CELL_HEIGHT / 4,
						6 * CELL_WIDTH / 4, 6 * CELL_HEIGHT / 4);
			}

			switch (game.getPlayer()[i].getPlayerCharacterID()) {
			case 0:
				g.drawImage(player1Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY() - (PLAYER_HEIGHT - CELL_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT, this);
				break;
			case 1:
				g.drawImage(player2Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY() - (PLAYER_HEIGHT - CELL_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT, this);
				break;
			case 2:
				g.drawImage(player3Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY() - (PLAYER_HEIGHT - CELL_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT, this);
				break;
			case 3:
				g.drawImage(player4Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY() - (PLAYER_HEIGHT - CELL_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT, this);
				break;
			}

		}
	}

	/**
	 * Paint active item
	 */
	public void paintActiveItem(Graphics g) {
		for (int i = 0; i < game.getPlayerNum(); i++) {
			
			int bullet_width = 0;
			int bullet_height = 0;
			
			if (game.getPlayer()[i].getIsUsingBulletFlag()==1) {
				game.getPlayer()[i].getActiveItem().move(game.getMap());		
				if (game.getPlayer()[i].getActiveItem() != null) {
					switch (game.getPlayer()[i].getActiveItem().getDirection()) {				
					case DIRECTION_UP:
						bullet_width = BULLET_WIDTH;
						bullet_height = BULLET_HEIGHT;
						g.drawImage(bulletImage[game.getPlayer()[i].getActiveItem().getDirection()], game.getPlayer()[i].getActiveItem().getX() + 10, game.getPlayer()[i].getActiveItem().getY(),
								bullet_width, bullet_height,this);
						break;
					case DIRECTION_DOWN:
						bullet_width = BULLET_WIDTH;
						bullet_height = BULLET_HEIGHT;
						g.drawImage(bulletImage[game.getPlayer()[i].getActiveItem().getDirection()], game.getPlayer()[i].getActiveItem().getX() + 10, game.getPlayer()[i].getActiveItem().getY(),
								bullet_width, bullet_height,this);
						break;
					case DIRECTION_LEFT:
						bullet_width = BULLET_HEIGHT;
						bullet_height = BULLET_WIDTH;
						g.drawImage(bulletImage[game.getPlayer()[i].getActiveItem().getDirection()], game.getPlayer()[i].getActiveItem().getX(), game.getPlayer()[i].getActiveItem().getY() + 10,
								bullet_width, bullet_height,this);
						break;
					case DIRECTION_RIGHT:
						bullet_width = BULLET_HEIGHT;
						bullet_height = BULLET_WIDTH;
						g.drawImage(bulletImage[game.getPlayer()[i].getActiveItem().getDirection()], game.getPlayer()[i].getActiveItem().getX(), game.getPlayer()[i].getActiveItem().getY() + 10,
								bullet_width, bullet_height,this);
						break;				
					}
				}
				
			}
			
		}
		
	}

	public void paintMap(Graphics g) {
		int xSize = game.getMap().getSizeX();
		int ySize = game.getMap().getSizeY();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if (theme == 0 || theme == 3) {
					if ((i + j) % 2 == 0)
						g.drawImage(mapImage[GRASS_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
								CELL_HEIGHT, this);
					else
						g.drawImage(mapImage[GRASS_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
								CELL_HEIGHT, this);
					}
				else {
					if ((i + j) % 2 == 0)
						g.drawImage(mapImage[SAND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
								CELL_HEIGHT, this);
					else
						g.drawImage(mapImage[SAND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
								CELL_HEIGHT, this);
				}
				if (game.getMap().isWithWall(i, j))
					g.drawImage(wallImage[theme][game.getMap().getWallID(i, j)], (int) (i * CELL_WIDTH),
							(int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				if (game.getMap().isWithBomb(i, j))
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				if (game.getMap().isWithItem(i, j))
					g.drawImage(itemImage[game.getMap().getItemID(i, j)], (int) (i * CELL_WIDTH),
							(int) (j * CELL_HEIGHT), ITEM_WIDTH, ITEM_HEIGHT, this);
				if (game.getMap().isAtExplosion(i, j))
					g.drawImage(bombImage[EXPLODE], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void paintMonsters(Graphics g) {
		for (Monster m : game.getMonsters()) {
			if (m != null) {
				if (m.isVisible()) {
					g.drawImage(monsterImage[m.id][m.getImageIndex()], m.getX(), m.getY(),
							MONSTER_WIDTH, MONSTER_HEIGHT, this);
				}
			}
		}
	}

	/**
	 * End the game and show "game over"
	 */
	public void generateGameover(String endMessage) {

		GameOverPanel gameOverPanel = new GameOverPanel(mainFrame, endMessage);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mainFrame.getContentPane() instanceof JPanel) {
			JPanel mainPanel = (JPanel) mainFrame.getContentPane();
			mainPanel.removeAll();
		} else {
			JLabel mainPanel = (JLabel) mainFrame.getContentPane();
			mainPanel.removeAll();
		}

		mainFrame.add(gameOverPanel);
		mainFrame.validate();// repaint

		mainFrame.setLayout(null);

		gameOverPanel.setLocation(0, 0);
		gameOverPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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

		player3Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p3UP.png"));
		player3Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p3RIGHT.png"));
		player3Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p3DOWN.png"));
		player3Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p3LEFT.png"));

		player4Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p4UP.png"));
		player4Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p4RIGHT.png"));
		player4Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p4DOWN.png"));
		player4Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p4LEFT.png"));

		itemImage[VELOCITY_UP] = ImageIO.read(new File("image/item/velocity.png"));
		itemImage[BOMB_UP] = ImageIO.read(new File("image/item/bomb.png"));
		itemImage[HP_UP] = ImageIO.read(new File("image/item/HP_UP.png"));
		itemImage[POWER_UP] = ImageIO.read(new File("image/item/power.png"));
		itemImage[IMMUNE] = ImageIO.read(new File("image/item/immune.png"));
		itemImage[BULLET] = ImageIO.read(new File("image/item/bullet.png"));

		bulletImage[DIRECTION_UP] = ImageIO.read(new File("image/item/bullet_up.png"));
		bulletImage[DIRECTION_DOWN] = ImageIO.read(new File("image/item/bullet_down.png"));
		bulletImage[DIRECTION_RIGHT] = ImageIO.read(new File("image/item/bullet_right.png"));
		bulletImage[DIRECTION_LEFT] = ImageIO.read(new File("image/item/bullet_left.png"));

		monsterImage[0][0] = ImageIO.read(new File("image/monster/m0LEFT.png"));
		monsterImage[0][1] = ImageIO.read(new File("image/monster/m0RIGHT.png"));
		monsterImage[0][2] = ImageIO.read(new File("image/monster/m0RIGHT.png"));
		monsterImage[1][0] = ImageIO.read(new File("image/monster/m1LEFT.png"));
		monsterImage[1][1] = ImageIO.read(new File("image/monster/m1RIGHT.png"));
		monsterImage[1][2] = ImageIO.read(new File("image/monster/m1DIE.png"));
		monsterImage[2][0] = ImageIO.read(new File("image/monster/m2LEFT.png"));
		monsterImage[2][1] = ImageIO.read(new File("image/monster/m2RIGHT.png"));
		monsterImage[2][2] = ImageIO.read(new File("image/monster/m2DIE.png"));
		monsterImage[3][0] = ImageIO.read(new File("image/monster/m3LEFT.png"));
		monsterImage[3][1] = ImageIO.read(new File("image/monster/m3RIGHT.png"));
		monsterImage[3][2] = ImageIO.read(new File("image/monster/m3DIE.png"));
		monsterImage[4][0] = ImageIO.read(new File("image/monster/m4LEFT.png"));
		monsterImage[4][1] = ImageIO.read(new File("image/monster/m4RIGHT.png"));
		monsterImage[4][2] = ImageIO.read(new File("image/monster/m4DIE.png"));

		mapImage[GRASS_1] = ImageIO.read(new File("image/maps/grass1.png"));
		mapImage[GRASS_2] = ImageIO.read(new File("image/maps/grass2.png"));
		mapImage[SAND_1] = ImageIO.read(new File("image/maps/sand1.png"));
		mapImage[SAND_2] = ImageIO.read(new File("image/maps/sand2.png"));

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				wallImage[i][j] = ImageIO.read(new File("image/maps/wall" + (1 + i) + "-" + (1 + j) + ".png"));
			}
		}

		bombImage[BOMB] = ImageIO.read(new File("image/bomb/bomb.png"));
		bombImage[EXPLODE] = ImageIO.read(new File("image/bomb/explode.png"));

	}
}