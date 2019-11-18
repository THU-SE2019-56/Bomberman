package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

import game.GameConstants;
import game.Game;
import monster.Monster;

/**
 * Display necessary elements here, including player(s), monsters, walls, items,
 * etc.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class MapPanel extends JPanel implements GameConstants {
	private Game game;

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
	public MapPanel(Game game) {
		this.game = game;

		try {
			loadImage();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		this.setSize(MAP_WIDTH, MAP_HEIGHT);
		this.setFocusable(true);

		for (int i = 0; i < game.getPlayerNum(); i++) {
			this.getToolkit().addAWTEventListener(game.getPlayer()[i], AWTEvent.KEY_EVENT_MASK);// Initialize the
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
		paintPlayer(g);
		paintMonsters(g);
		for (int i = 0; i < game.getPlayerNum(); i++) {
			if (game.getPlayer()[i].acquireItem(game.getItem()) == false) {
				paintItem(g);
			}
		}

		if (game.getGameMode() == PVE_MODE) {
			if (game.getPlayer()[PLAYER_ID_P1].getHP() <= 0) {
				paintEndGame(g, "Monsters Win!");
			} else {
				boolean monstersAlive = false;
				for (Monster m : game.getMonsters()) {
					monstersAlive |= m.isAlive();
				}
				if (monstersAlive == false && game.getPlayer()[PLAYER_ID_P1].getHP() > 0) {
					paintEndGame(g, "Player Wins!");
				}
			}
		} else if (game.getGameMode() == PVP_MODE) {
			if (game.getPlayer()[PLAYER_ID_P1].getHP() <= 0 && game.getPlayer()[PLAYER_ID_P2].getHP() > 0)
				paintEndGame(g, "Player 1 Wins!");
			else if (game.getPlayer()[PLAYER_ID_P2].getHP() <= 0 && game.getPlayer()[PLAYER_ID_P1].getHP() > 0)
				paintEndGame(g, "Player 2 Wins!");
			else if (game.getPlayer()[PLAYER_ID_P1].getHP() <= 0 && game.getPlayer()[PLAYER_ID_P2].getHP() <= 0)
				paintEndGame(g, "Game Tie!");
		}
	}

	/**
	 * Paint the image of the player.
	 */
	public void paintPlayer(Graphics g) {
		for (int i = 0; i < game.getPlayerNum(); i++) {
			if (game.getPlayer()[i].getPlayerID() == PLAYER_ID_P1) {
				g.drawImage(player1Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY(), PLAYER_WIDTH, PLAYER_HEIGHT, this);
			}
			if (game.getPlayer()[i].getPlayerID() == PLAYER_ID_P2) {
				g.drawImage(player2Image[game.getPlayer()[i].getImageDirection()], game.getPlayer()[i].getX(),
						game.getPlayer()[i].getY(), PLAYER_WIDTH, PLAYER_HEIGHT, this);
			}
		}
	}

	public void paintItem(Graphics g) {
		g.drawImage(itemImage[game.getItem().getItemID()], game.getItem().getX(), game.getItem().getY(), ITEM_WIDTH,
				ITEM_HEIGHT, this);
	}

	public void paintMap(Graphics g) {
		int xSize = game.getMap().getSizeX();
		int ySize = game.getMap().getSizeY();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(mapImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(mapImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (game.getMap().isWithDestructibleWall(i, j))
					g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (game.getMap().isWithIndestructibleWall(i, j))
					g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (game.getMap().isWithBomb(i, j))
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				if (game.getMap().isAtExplosion(i, j))
					g.drawImage(bombImage[EXPLODE], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void paintMonsters(Graphics g) {
		for (Monster m : game.getMonsters()) {
			if (m.isAlive()) {
				g.drawImage(monsterImage[m.getImageDirection()], m.getX(), m.getY(), MONSTER_WIDTH, MONSTER_HEIGHT,
						this);
			}
		}
	}

	/**
	 * End the game and show "game over"
	 */
	public void paintEndGame(Graphics g, String endMessage) {
		// Draw "game over" Image
		g.drawImage(gameImage[GAMEOVER], CELL_NUM_X * CELL_WIDTH / 4, CELL_NUM_Y * CELL_HEIGHT / 4,
				CELL_NUM_X * CELL_WIDTH / 2, CELL_NUM_Y * CELL_HEIGHT / 4, this);
		
		// Show message
		g.setFont(new Font("Times New Roman Italic", Font.BOLD, 60));
		g.setColor(Color.WHITE);
		g.drawString(endMessage, CELL_NUM_X * CELL_WIDTH / 4, CELL_NUM_Y * CELL_HEIGHT * 2 / 3);
		game.setGameOver(true);
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