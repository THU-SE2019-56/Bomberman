package ui;

import java.awt.Graphics;
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

	BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage itemImage[] = new BufferedImage[ITEM_NUM];
	BufferedImage bombImage[] = new BufferedImage[2];

	private JButton buttonBack;
	private JButton buttonSave;
	private JButton buttonUndo;
	private JButton buttonRedo;
	private JButton buttonClear;
	private JButton buttonRandom;

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
					g.drawImage(mapImage[GROUND_1], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				else
					g.drawImage(mapImage[GROUND_2], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT), CELL_WIDTH,
							CELL_HEIGHT, this);
				if (mmat.isWithDestructibleWall(j, i))
					g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				if (mmat.isWithIndestructibleWall(j, i))
					g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * CELL_WIDTH), (int) (j * CELL_HEIGHT),
							CELL_WIDTH, CELL_HEIGHT, this);
				// TODO waiting for adding bomb and item
			}
	}

	public void loadImage() throws Exception {
		mapImage[GROUND_1] = ImageIO.read(new File("image/maps/grass1.png"));
		mapImage[GROUND_2] = ImageIO.read(new File("image/maps/grass2.png"));
		mapImage[DESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_destructible.png"));
		mapImage[INDESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_indestructibel.png"));
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

		this.setLayout(null);

		this.add(buttonBack);
		this.add(buttonSave);
		this.add(buttonUndo);
		this.add(buttonRedo);
		this.add(buttonClear);
		this.add(buttonRandom);

		buttonBack.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonSave.addMouseListener(new ButtonListener(mainFrame, "save"));
		buttonUndo.addMouseListener(new ButtonListener(mainFrame, "undo"));
		buttonRedo.addMouseListener(new ButtonListener(mainFrame, "redo"));
		buttonClear.addMouseListener(new ButtonListener(mainFrame, "clear"));
		buttonRandom.addMouseListener(new ButtonListener(mainFrame, "random"));
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
}
