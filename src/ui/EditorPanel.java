package ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.GameConstants;
import map.MapEditor;

/**
 * A UI for editing stage, with function of map editing and spawn position set
 * 
 * @author Zhuofan Chen
 * @version 0.1
 */
public class EditorPanel extends JPanel implements GameConstants {
	private static final long serialVersionUID = 1L;
	private MapEditor mapEditor;

	BufferedImage groundImage[] = new BufferedImage[2];
	BufferedImage wallImage[][] = new BufferedImage[4][8];

	/**
	 * Construction method
	 * 
	 * @param mainFrame the game mainframe
	 */
	public EditorPanel(MainFrame mainFrame, MapEditor mapEditor) {
		this.mapEditor = mapEditor;
		try {
			loadImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.addMouseListener(new MapListener());
		repaint();
	}

	/**
	 * All painting methods are invoked in "paintComponent(Graphics g)".
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintMap(g);
	}

	/**
	 * paint map from current mapmatrix
	 * 
	 * @param g
	 */
	public void paintMap(Graphics g) {
		int xSize = mapEditor.getMapMatrix().getXSize();
		int ySize = mapEditor.getMapMatrix().getYSize();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(groundImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(groundImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (mapEditor.getMapMatrix().isWithDestructibleWall(j, i))
					g.drawImage(wallImage[mapEditor.getTheme()][(i + j) % 7], (int) (i * CELL_WIDTH),
							(int) (j * CELL_HEIGHT), CELL_WIDTH, CELL_HEIGHT, this);
				if (mapEditor.getMapMatrix().isWithIndestructibleWall(j, i))
					g.drawImage(wallImage[mapEditor.getTheme()][7], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void loadImage() throws Exception {
		groundImage[GROUND_1] = ImageIO.read(new File("image/maps/grass1.png"));
		groundImage[GROUND_2] = ImageIO.read(new File("image/maps/grass2.png"));
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 8; j++) {
				wallImage[i][j] = ImageIO.read(new File("image/maps/wall" + (1 + i) + "-" + (1 + j) + ".png"));
			}

	}

	class MapListener implements MouseListener {

		public int getXPos() {
			Point p = getMousePosition();
			System.out.println(p);
			if (p == null)
				return -1;
			return (p.x / CELL_WIDTH);
		}

		public int getYPos() {
			Point p = getMousePosition();
			if (p == null)
				return -1;
			return (p.y / CELL_HEIGHT);
		}

		@Override
		public void mouseClicked(MouseEvent e) {	
			int xPos = getXPos();
			int yPos = getYPos();
			
			if (!mapEditor.isInMap(yPos, xPos))
				return;
			mapEditor.saveStatus();
			mapEditor.editCell(yPos, xPos);
			System.out.println(mapEditor.getEditingMode() + " " + yPos + " " + xPos);
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	class Mob {
		private int spawnX;
		private int spawnY;
		private int type;
		private int ID;

		public Mob(int type, int ID, int x, int y) {
			this.type = type;
			this.ID = ID;
			spawnX = x;
			spawnY = y;
		}

		public int getX() {
			return spawnX;
		}

		public int getY() {
			return spawnY;
		}

		public int getType() {
			return type;
		}

		public int getID() {
			return ID;
		}
	}
}
