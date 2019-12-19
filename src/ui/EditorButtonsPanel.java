package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import game.GameConstants;
import map.MapEditor;

public class EditorButtonsPanel extends JPanel implements GameConstants {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MapEditor mapEditor;

	BufferedImage editorImage[] = new BufferedImage[9];

	private IconButton buttonIndestructibleWall;
	private IconButton buttonDestructibleWall;
	private IconButton buttonRemoveWall;

	private IconButton buttonAddTyrannosaurus;
	private IconButton buttonAddTriceratops;
	private IconButton buttonAddFrog;
	private IconButton buttonAddParrot;
	private IconButton buttonRemoveMonster;

	private ImageIcon buttonUndoOffIcon;
	private ImageIcon buttonUndoOnIcon;
	private JLabel buttonUndoLabel;

	private ImageIcon buttonRedoOffIcon;
	private ImageIcon buttonRedoOnIcon;
	private JLabel buttonRedoLabel;

	private ImageIcon buttonClearOffIcon;
	private ImageIcon buttonClearOnIcon;
	private JLabel buttonClearLabel;

	private ImageIcon buttonRandomOffIcon;
	private ImageIcon buttonRandomOnIcon;
	private JLabel buttonRandomLabel;

	private ImageIcon buttonSaveOffIcon;
	private ImageIcon buttonSaveOnIcon;
	private JLabel buttonSaveLabel;

	private ImageIcon buttonBackOffIcon;
	private ImageIcon buttonBackOnIcon;
	private JLabel buttonBackLabel;

	public EditorButtonsPanel(MainFrame mainFrame, MapEditor mapEditor) {
		try {
			loadImage();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		this.mapEditor = mapEditor;
		this.setBackground(new Color(153, 191, 68));

		this.setLayout(null);

		this.addButton();

		buttonUndoLabel.addMouseListener(new ButtonListener(mainFrame, "undo"));
		buttonRedoLabel.addMouseListener(new ButtonListener(mainFrame, "redo"));
		buttonClearLabel.addMouseListener(new ButtonListener(mainFrame, "clear"));
		buttonRandomLabel.addMouseListener(new ButtonListener(mainFrame, "random"));
		buttonSaveLabel.addMouseListener(new ButtonListener(mainFrame, "save"));
		buttonBackLabel.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonIndestructibleWall.addMouseListener(new ButtonListener(mainFrame, "indestructible"));
		buttonDestructibleWall.addMouseListener(new ButtonListener(mainFrame, "destructible"));
		buttonRemoveWall.addMouseListener(new ButtonListener(mainFrame, "removewall"));

	}

	/**
	 * 
	 * Initialize text field
	 */
	public void initializeTextField(JTextField jtf, int x, int y, int width, int height) {
		jtf.setBounds(x, y, width, height);
		jtf.setFont(new Font("YouYuan", Font.BOLD, 20));
		jtf.setBackground(null);
		jtf.setEditable(false);
		jtf.setBorder(null);
	}

	public void addButton() {
		buttonIndestructibleWall = new IconButton(editorImage[SET_INDESTRUCTIBLE_WALL], SET_INDESTRUCTIBLE_WALL, 0,
				ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonDestructibleWall = new IconButton(editorImage[SET_DESTRUCTIBLE_WALL], SET_DESTRUCTIBLE_WALL,
				ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonRemoveWall = new IconButton(editorImage[REMOVE_WALL], REMOVE_WALL, 2 * ICON_BUTTON_WIDTH,
				ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonAddTyrannosaurus = new IconButton(editorImage[ADD_TYRANNOSAURUS], ADD_TYRANNOSAURUS, 0,
				3 * ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonAddTriceratops = new IconButton(editorImage[ADD_TRICERATOPS], ADD_TRICERATOPS, ICON_BUTTON_WIDTH,
				3 * ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonAddFrog = new IconButton(editorImage[ADD_FROG], ADD_FROG, 2 * ICON_BUTTON_WIDTH, 3 * ICON_BUTTON_HEIGHT,
				ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonAddParrot = new IconButton(editorImage[ADD_PARROT], ADD_PARROT, 3 * ICON_BUTTON_WIDTH,
				3 * ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		buttonRemoveMonster = new IconButton(editorImage[REMOVE_MONSTER], REMOVE_MONSTER, 0,
				4 * ICON_BUTTON_HEIGHT, ICON_BUTTON_WIDTH, ICON_BUTTON_HEIGHT);
		this.add(buttonIndestructibleWall);
		this.add(buttonDestructibleWall);
		this.add(buttonRemoveWall);
		this.add(buttonAddTyrannosaurus);
		this.add(buttonAddTriceratops);
		this.add(buttonAddFrog);
		this.add(buttonAddParrot);
		this.add(buttonRemoveMonster);

		buttonUndoOffIcon = new ImageIcon("image/buttons/undo_off.png");
		buttonUndoOffIcon
				.setImage(buttonUndoOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonUndoOnIcon = new ImageIcon("image/buttons/undo_on.png");
		buttonUndoOnIcon
				.setImage(buttonUndoOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonUndoLabel = new JLabel(buttonUndoOffIcon);
		buttonUndoLabel.setBounds(10, 400, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonUndoLabel);

		buttonRedoOffIcon = new ImageIcon("image/buttons/redo_off.png");
		buttonRedoOffIcon
				.setImage(buttonRedoOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonRedoOnIcon = new ImageIcon("image/buttons/redo_on.png");
		buttonRedoOnIcon
				.setImage(buttonRedoOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonRedoLabel = new JLabel(buttonRedoOffIcon);
		buttonRedoLabel.setBounds(130, 400, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonRedoLabel);

		buttonClearOffIcon = new ImageIcon("image/buttons/clear_off.png");
		buttonClearOffIcon
				.setImage(buttonClearOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonClearOnIcon = new ImageIcon("image/buttons/clear_on.png");
		buttonClearOnIcon
				.setImage(buttonClearOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonClearLabel = new JLabel(buttonClearOffIcon);
		buttonClearLabel.setBounds(10, 500, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonClearLabel);

		buttonRandomOffIcon = new ImageIcon("image/buttons/random_off.png");
		buttonRandomOffIcon
				.setImage(buttonRandomOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonRandomOnIcon = new ImageIcon("image/buttons/random_on.png");
		buttonRandomOnIcon
				.setImage(buttonRandomOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonRandomLabel = new JLabel(buttonRandomOffIcon);
		buttonRandomLabel.setBounds(130, 500, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonRandomLabel);

		buttonSaveOffIcon = new ImageIcon("image/buttons/save_off.png");
		buttonSaveOffIcon
				.setImage(buttonSaveOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonSaveOnIcon = new ImageIcon("image/buttons/save_on.png");
		buttonSaveOnIcon
				.setImage(buttonSaveOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonSaveLabel = new JLabel(buttonSaveOffIcon);
		buttonSaveLabel.setBounds(10, 600, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonSaveLabel);

		buttonBackOffIcon = new ImageIcon("image/buttons/back_off.png");
		buttonBackOffIcon
				.setImage(buttonBackOffIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonBackOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonBackOnIcon
				.setImage(buttonBackOnIcon.getImage().getScaledInstance(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, 1));

		buttonBackLabel = new JLabel(buttonBackOffIcon);
		buttonBackLabel.setBounds(130, 600, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		this.add(buttonBackLabel);
	}

	public void loadImage() throws Exception {
		editorImage[REMOVE_WALL] = ImageIO.read(new File("image/editor/remove_wall.png"));
		editorImage[SET_DESTRUCTIBLE_WALL] = ImageIO.read(new File("image/editor/destructible_wall.png"));
		editorImage[SET_INDESTRUCTIBLE_WALL] = ImageIO.read(new File("image/editor/indestructible_wall.png"));
		editorImage[REMOVE_MONSTER] = ImageIO.read(new File("image/editor/remove_monster.png"));
		editorImage[ADD_TYRANNOSAURUS] = ImageIO.read(new File("image/monster/m1LEFT.png"));
		editorImage[ADD_TRICERATOPS] = ImageIO.read(new File("image/monster/m2LEFT.png"));
		editorImage[ADD_FROG] = ImageIO.read(new File("image/monster/m3LEFT.png"));
		editorImage[ADD_PARROT] = ImageIO.read(new File("image/monster/m4LEFT.png"));
	}

	/**
	 * Respond to button events
	 */
	class ButtonListener implements MouseListener {

		MainFrame mainFrame;
		String name;

		public ButtonListener(MainFrame mainFrame, String name) {
			this.mainFrame = mainFrame;
			this.name = name;
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (this.name) {
			case "undo":
				mapEditor.undo();
				break;
			case "redo":
				mapEditor.redo();
				break;
			case "clear":
				mapEditor.saveStatus();
				mapEditor.getMapMatrix().clearAll();
				break;
			case "random":
				mapEditor.saveStatus();
				mapEditor.getMapMatrix().reFill();
				break;
			case "save":
				mapEditor.saveToTxt();
				break;
			case "back":
				MenuPanel newMenuPanel = new MenuPanel(mainFrame);

				if (mainFrame.getContentPane() instanceof JPanel) {
					JPanel mainPanel = (JPanel) mainFrame.getContentPane();
					mainPanel.removeAll();
				} else {
					JLabel mainPanel = (JLabel) mainFrame.getContentPane();
					mainPanel.removeAll();
				}

				mainFrame.add(newMenuPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				newMenuPanel.setLocation(0, 0);
				newMenuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "indestructible":
				mapEditor.switchMode(SET_INDESTRUCTIBLE_WALL);
				break;
			case "destructible":
				mapEditor.switchMode(SET_DESTRUCTIBLE_WALL);
				break;
			case "removewall":
				mapEditor.switchMode(REMOVE_WALL);
				break;
			}
			mainFrame.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "undo":
				buttonUndoLabel.setIcon(buttonUndoOnIcon);
				break;
			case "redo":
				buttonRedoLabel.setIcon(buttonRedoOnIcon);
				break;
			case "clear":
				buttonClearLabel.setIcon(buttonClearOnIcon);
				break;
			case "random":
				buttonRandomLabel.setIcon(buttonRandomOnIcon);
				break;
			case "save":
				buttonSaveLabel.setIcon(buttonSaveOnIcon);
				break;
			case "back":
				buttonBackLabel.setIcon(buttonBackOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "undo":
				buttonUndoLabel.setIcon(buttonUndoOffIcon);
				break;
			case "redo":
				buttonRedoLabel.setIcon(buttonRedoOffIcon);
				break;
			case "clear":
				buttonClearLabel.setIcon(buttonClearOffIcon);
				break;
			case "random":
				buttonRandomLabel.setIcon(buttonRandomOffIcon);
				break;
			case "save":
				buttonSaveLabel.setIcon(buttonSaveOffIcon);
				break;
			case "back":
				buttonBackLabel.setIcon(buttonBackOffIcon);
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
			this.initializeButton(this, xPos, yPos, xSize, ySize);
		}

		public void initializeButton(JButton button, int x, int y, int width, int height) {

			button.setBounds(x, y, width, height);

			Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
			// This is the default border of WIN10 system.For macOS, use this border to make
			// sure the buttons are correctly initialized.

			Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);

			button.setForeground(Color.BLACK);
			button.setBorder(originBorder);
			button.setBackground(Color.WHITE);
			button.setFont(buttonFont);
			button.setOpaque(true);

		}

		@Override
		public void paintComponent(Graphics g) {
			if (mode == mapEditor.getEditingMode())
				setBackground(Color.GRAY);
			else
				setBackground(Color.WHITE);
			super.paintComponent(g);
		}

	}
}
