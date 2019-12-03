package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.GameConstants;
import map.Map;
import monster.Monster;

public class EditorPanel extends JPanel implements GameConstants {

	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;
	Map map;

	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage itemImage[] = new BufferedImage[ITEM_NUM];
	BufferedImage bombImage[] = new BufferedImage[2];

	public EditorPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		try {
			loadImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map = new Map();

	}

	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintMap(g);

	}

	public void paintMap(Graphics g) {
		int xSize = map.getSizeX();
		int ySize = map.getSizeY();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(mapImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(mapImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (map.isWithDestructibleWall(i, j))
					g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (map.isWithIndestructibleWall(i, j))
					g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (map.isWithBomb(i, j))
					g.drawImage(bombImage[BOMB], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				if (map.isWithItem(i, j))
					g.drawImage(itemImage[map.getItemID(i, j)], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							ITEM_WIDTH, ITEM_HEIGHT, this);
				if (map.isAtExplosion(i, j))
					g.drawImage(bombImage[EXPLODE], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), BOMB_WIDTH,
							BOMB_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void loadImage() throws Exception {
		// TODO Load all the images here
		itemImage[VELOCITY_UP] = ImageIO.read(new File("image/item/velocity.png"));
		itemImage[BOMB_UP] = ImageIO.read(new File("image/item/bomb.png"));
		itemImage[HP_UP] = ImageIO.read(new File("image/item/HP_UP.png"));
		itemImage[POWER_UP] = ImageIO.read(new File("image/item/power.jpg"));

		mapImage[GROUND_1] = ImageIO.read(new File("image/maps/ground1.png"));
		mapImage[GROUND_2] = ImageIO.read(new File("image/maps/ground2.png"));
		mapImage[DESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_destructible.png"));
		mapImage[INDESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_indestructibel.png"));

		bombImage[BOMB] = ImageIO.read(new File("image/bomb/bomb.png"));
		bombImage[EXPLODE] = ImageIO.read(new File("image/bomb/explode.png"));
	}

}
