package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.GameConstants;
import map.MapMatrix;

/**
 * A UI for editing stage, with function of map editing and spawn position set
 * 
 * @author Zhuofan Chen
 * @version 0.1
 */
public class EditorPanel extends JPanel implements GameConstants {

	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;
	MapMatrix mmat;
	Stack<MapMatrix> undoStack;
	Stack<MapMatrix> redoStack;
	int editingMode = NONE;
	int theme = 0;

	BufferedImage groundImage[] = new BufferedImage[2];
	BufferedImage wallImage[][] = new BufferedImage[4][8];
	BufferedImage editorImage[] = new BufferedImage[2];

	private JButton buttonBack;
	private JButton buttonSave;
	private JButton buttonUndo;
	private JButton buttonRedo;
	private JButton buttonClear;
	private JButton buttonRandom;

	private IconButton buttonIndestructibleWall;
	private IconButton buttonDestructibleWall;
	private IconButton buttonRemoveWall;
	private IconButton buttonRemoveMob;

	Controls control;

	/**
	 * Construction method
	 * 
	 * @param mainFrame the game mainframe
	 */
	public EditorPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		try {
			loadImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		control = new Controls();

		mmat = new MapMatrix();
		undoStack = new Stack<MapMatrix>();
		redoStack = new Stack<MapMatrix>();

		this.addButton();
		this.addMouseListener(new MapListener());
	}

	/**
	 * @return if given position is in current editing mmat
	 */
	public boolean isInMap(int yPos, int xPos) {
		return xPos >= 0 && xPos < mmat.getXSize() && yPos >= 0 && yPos < mmat.getYSize();
	}

	public void switchMode(int mode) {
		if (editingMode == mode)
			editingMode = NONE;
		else
			editingMode = mode;
	}

	/**
	 * Edit given position according to given mode
	 * 
	 * @param yPos
	 * @param xPos
	 */
	public void editCell(int yPos, int xPos) {
		if (!isInMap(yPos, xPos))
			return;
		switch (editingMode) {
		case REMOVE_WALL:
			mmat.removeWall(yPos, xPos);
			break;
		case SET_DESTRUCTIBLE_WALL:
			mmat.setWall(yPos, xPos, true);
			break;
		case SET_INDESTRUCTIBLE_WALL:
			mmat.setWall(yPos, xPos, false);
			break;
		case REMOVE_MOB:
			break;

		default:
			break;
		}
	}

	/**
	 * Save current mapmatrix into undoStack
	 */
	public void saveStatus() {
		undoStack.push(new MapMatrix(mmat));
		redoStack.clear();
	}

	/**
	 * Track back to last mapmatrix in undoStack
	 */
	public void undo() {
		if (undoStack.empty()) {
//			System.out.println("undo stack empty");
			return;
		}
		redoStack.push(new MapMatrix(mmat));
		mmat = undoStack.pop();
	}

	/**
	 * 
	 */
	public void redo() {
		if (redoStack.empty()) {
//			System.out.println("redo stack empty");
			return;
		}
		undoStack.push(new MapMatrix(mmat));
		mmat = redoStack.pop();
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
		int xSize = mmat.getXSize();
		int ySize = mmat.getYSize();
		for (int i = 0; i < xSize; i++)
			for (int j = 0; j < ySize; j++) {
				if ((i + j) % 2 == 0)
					g.drawImage(groundImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(groundImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (mmat.isWithDestructibleWall(j, i))
					g.drawImage(wallImage[theme][(i + j) % 7], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (mmat.isWithIndestructibleWall(j, i))
					g.drawImage(wallImage[theme][7], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void loadImage() throws Exception {
		groundImage[GROUND_1] = ImageIO.read(new File("image/maps/grass1.png"));
		groundImage[GROUND_2] = ImageIO.read(new File("image/maps/grass2.png"));

		editorImage[0] = ImageIO.read(new File("image/editor/remove_wall.png"));
		editorImage[1] = ImageIO.read(new File("image/editor/remove_mob.png"));

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 8; j++) {
				String fileName = new String("image/maps/wall");
				fileName += (1 + i);
				fileName += '-';
				fileName += (1 + j);
				fileName += (".png");
				wallImage[i][j] = ImageIO.read(new File(fileName));
			}
	}

	public void addButton() {
		buttonBack = new JButton("Back");
		control.initializeButton(buttonBack, MAP_WIDTH + 120, MAP_HEIGHT - 65, 90, 50);
		buttonSave = new JButton("Save");
		control.initializeButton(buttonSave, MAP_WIDTH + 15, MAP_HEIGHT - 65, 90, 50);

		buttonUndo = new JButton("Undo");
		control.initializeButton(buttonUndo, MAP_WIDTH + 15, 15, 90, 50);
		buttonRedo = new JButton("Redo");
		control.initializeButton(buttonRedo, MAP_WIDTH + 120, 15, 90, 50);
		buttonClear = new JButton("Clear");
		control.initializeButton(buttonClear, MAP_WIDTH + 15, 80, 90, 50);
		buttonRandom = new JButton("Random");
		control.initializeButton(buttonRandom, MAP_WIDTH + 120, 80, 90, 50);

		buttonIndestructibleWall = new IconButton(wallImage[0][7], SET_INDESTRUCTIBLE_WALL, MAP_WIDTH + 60, 200,
				CELL_WIDTH, CELL_HEIGHT);
		buttonDestructibleWall = new IconButton(wallImage[0][0], SET_DESTRUCTIBLE_WALL, MAP_WIDTH + 105, 200,
				CELL_WIDTH, CELL_HEIGHT);
		buttonRemoveWall = new IconButton(editorImage[0], REMOVE_WALL, MAP_WIDTH + 15, 200, CELL_WIDTH, CELL_HEIGHT);

		this.setLayout(null);

		this.add(buttonBack);
		this.add(buttonSave);
		this.add(buttonUndo);
		this.add(buttonRedo);
		this.add(buttonClear);
		this.add(buttonRandom);

		this.add(buttonIndestructibleWall);
		this.add(buttonDestructibleWall);
		this.add(buttonRemoveWall);

		buttonBack.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonSave.addMouseListener(new ButtonListener(mainFrame, "save"));
		buttonUndo.addMouseListener(new ButtonListener(mainFrame, "undo"));
		buttonRedo.addMouseListener(new ButtonListener(mainFrame, "redo"));
		buttonClear.addMouseListener(new ButtonListener(mainFrame, "clear"));
		buttonRandom.addMouseListener(new ButtonListener(mainFrame, "random"));

		buttonIndestructibleWall.addMouseListener(new ButtonListener(mainFrame, "indestructible"));
		buttonDestructibleWall.addMouseListener(new ButtonListener(mainFrame, "destructible"));
		buttonRemoveWall.addMouseListener(new ButtonListener(mainFrame, "removewall"));
	}

	class ButtonListener implements MouseListener {
		MainFrame mainFrame;
		String name;

		public ButtonListener(MainFrame mainFrame, String name) {
			this.mainFrame = mainFrame;
			this.name = name;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (this.name) {
			case "back":
				MenuPanel newMenuPanel = new MenuPanel(mainFrame);

				JPanel mainPanel = (JPanel) mainFrame.getContentPane();
				mainPanel.removeAll();

				mainFrame.add(newMenuPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				newMenuPanel.setLocation(0, 0);
				newMenuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "save":
				break;
			case "undo":
				undo();
				break;
			case "redo":
				redo();
				break;
			case "clear":
				saveStatus();
				mmat.clearAll();
				break;
			case "random":
				saveStatus();
				mmat.reFill();
				break;
			case "indestructible":
				switchMode(SET_INDESTRUCTIBLE_WALL);
				break;
			case "destructible":
				switchMode(SET_DESTRUCTIBLE_WALL);
				break;
			case "removewall":
				switchMode(REMOVE_WALL);
				break;
			}
			repaint();
//			System.out.print("undoStack:");
//			System.out.print(undoStack.size());
//			System.out.print("\n");
//			System.out.print("redoStack:");
//			System.out.print(redoStack.size());
//			System.out.print("\n");

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "back":
				control.highLightButton(buttonBack);
				break;
			case "save":
				control.highLightButton(buttonSave);
				break;
			case "undo":
				control.highLightButton(buttonUndo);
				break;
			case "redo":
				control.highLightButton(buttonRedo);
				break;
			case "clear":
				control.highLightButton(buttonClear);
				break;
			case "random":
				control.highLightButton(buttonRandom);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "back":
				control.resetButton(buttonBack);
				break;
			case "save":
				control.resetButton(buttonSave);
				break;
			case "undo":
				control.resetButton(buttonUndo);
				break;
			case "redo":
				control.resetButton(buttonRedo);
				break;
			case "clear":
				control.resetButton(buttonClear);
				break;
			case "random":
				control.resetButton(buttonRandom);
				break;
			}
		}

	}

	class IconButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int mode;

		IconButton(BufferedImage icon, int mode, int xPos, int yPos, int xSize, int ySize) {
			super(new ImageIcon(icon.getScaledInstance(xSize, ySize, 1)));
			this.mode = mode;
			control.initializeButton(this, xPos, yPos, xSize, ySize);
		}

		@Override
		public void paintComponent(Graphics g) {
			if (mode == editingMode)
				setBackground(Color.GRAY);
			else
				setBackground(Color.WHITE);
			super.paintComponent(g);
		}

	}

	class MapListener implements MouseListener {

		public int getXPos() {
			Point p = getMousePosition();
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
			if (!isInMap(yPos, xPos))
				return;
			saveStatus();
			editCell(yPos, xPos);
			System.out.println(editingMode + " " + yPos + " " + xPos);
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
