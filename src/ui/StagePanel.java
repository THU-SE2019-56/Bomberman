package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.Game;
import game.GameConstants;
import game.TimerListener;
import map.Map;

/**
 * Choose stage. Include thumbnails, arrows, stories.
 * 
 * @author Wang
 * @version 1.0
 */
public class StagePanel extends JPanel implements GameConstants {
	private MainFrame mainFrame;
	private ThumbnailLabel thumbnailLabel;

	private ImageIcon stageBackgroundIcon;
	private JLabel stageBackgroundLabel;

	private ImageIcon thumbnailBackgroundIcon;
	private JLabel thumbnailBackgroundLabel;

	private ImageIcon storyIcon;
	private JLabel storyLabel;

	private int[][] wallMatrix = new int[CELL_NUM_X][CELL_NUM_Y];
	private int[] monsterX;
	private int[] monsterY;
	private int[] monsterID;
	private int theme;

	private int gameMode;
	private int stageNumber = 0;
	private int player1CharacterID;
	private int player2CharacterID;

	private ImageIcon buttonBackOffIcon;
	private ImageIcon buttonBackOnIcon;
	private JLabel buttonBackLabel;

	private ImageIcon buttonConfirmOffIcon;
	private ImageIcon buttonConfirmOnIcon;
	private JLabel buttonConfirmLabel;

	private ImageIcon buttonForwardOffIcon;
	private ImageIcon buttonForwardOnIcon;
	private JLabel buttonForwardLabel;

	private ImageIcon buttonBackwardOffIcon;
	private ImageIcon buttonBackwardOnIcon;
	private JLabel buttonBackwardLabel;

	private final static int tinyCellWidth = SCALED_THUMBNAIL_WIDTH / CELL_NUM_X;
	private final static int tinyCellHeight = SCALED_THUMBNAIL_HEIGHT / CELL_NUM_Y;
	private BufferedImage mapImage[] = new BufferedImage[4];
	private BufferedImage wallImage[][] = new BufferedImage[4][8];
	private int totalStageNum;

	public StagePanel(MainFrame mainFrame, int gameMode, int p1cID, int p2cID) {
		this.mainFrame = mainFrame;
		this.gameMode = gameMode;
		this.player1CharacterID = p1cID;
		this.player2CharacterID = p2cID;
		File savePath = new File("data");
		File fileList[] = savePath.listFiles();
		totalStageNum = fileList.length;
		
		
		this.setLayout(null);
		loadStage(stageNumber);
		this.addThumbnail(stageNumber);
		this.addButton();
		this.addStory(stageNumber);
		this.addBackground();
	}

	public void addBackground() {
		thumbnailBackgroundIcon = new ImageIcon("image/background/thumbnailBackground.png");
		thumbnailBackgroundIcon
				.setImage(thumbnailBackgroundIcon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, 1));
		thumbnailBackgroundLabel = new JLabel(thumbnailBackgroundIcon);
		thumbnailBackgroundLabel.setBounds(WINDOW_WIDTH / 2 - THUMBNAIL_WIDTH / 2, 130, THUMBNAIL_WIDTH,
				THUMBNAIL_HEIGHT);
		this.add(thumbnailBackgroundLabel);

		stageBackgroundIcon = new ImageIcon("image/background/chooseStagePanel.png");
		stageBackgroundIcon.setImage(stageBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		stageBackgroundLabel = new JLabel(stageBackgroundIcon);
		stageBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(stageBackgroundLabel);
	}

	public void addButton() {
		// back
		buttonBackOffIcon = new ImageIcon("image/buttons/back_off.png");
		buttonBackOffIcon
				.setImage(buttonBackOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonBackOnIcon
				.setImage(buttonBackOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackLabel = new JLabel(buttonBackOffIcon);
		buttonBackLabel.setBounds(-10, 700, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonBackLabel);

		// confirm
		buttonConfirmOffIcon = new ImageIcon("image/buttons/confirm_off.png");
		buttonConfirmOffIcon.setImage(
				buttonConfirmOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonConfirmOnIcon = new ImageIcon("image/buttons/confirm_on.png");
		buttonConfirmOnIcon.setImage(
				buttonConfirmOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonConfirmLabel = new JLabel(buttonConfirmOffIcon);
		buttonConfirmLabel.setBounds(900, 700, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonConfirmLabel);

		// forward
		buttonForwardOffIcon = new ImageIcon("image/buttons/forward_off.png");
		buttonForwardOffIcon.setImage(buttonForwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardOnIcon = new ImageIcon("image/buttons/forward_on.png");
		buttonForwardOnIcon.setImage(buttonForwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardLabel = new JLabel(buttonForwardOffIcon);
		buttonForwardLabel.setBounds(800, 230, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonForwardLabel);

		// backward
		buttonBackwardOffIcon = new ImageIcon("image/buttons/backward_off.png");
		buttonBackwardOffIcon
				.setImage(buttonBackwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardOnIcon = new ImageIcon("image/buttons/backward_on.png");
		buttonBackwardOnIcon.setImage(buttonBackwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardLabel = new JLabel(buttonBackwardOffIcon);
		buttonBackwardLabel.setBounds(160, 230, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonBackwardLabel);

		buttonBackLabel.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonConfirmLabel.addMouseListener(new ButtonListener(mainFrame, "confirm"));
		buttonForwardLabel.addMouseListener(new ButtonListener(mainFrame, "forward"));
		buttonBackwardLabel.addMouseListener(new ButtonListener(mainFrame, "backward"));
	}

	public void addStory(int stageNumber) {
		storyIcon = new ImageIcon("image/story/story" + stageNumber + ".png");
		storyIcon.setImage(storyIcon.getImage().getScaledInstance(STORY_WIDTH, STORY_HEIGHT, 1));
		storyLabel = new JLabel(storyIcon);
		storyLabel.setBounds(WINDOW_WIDTH / 2 - STORY_WIDTH / 2, 560, STORY_WIDTH, STORY_HEIGHT);
		this.add(storyLabel);
	}

	public void addThumbnail(int stageNumber) {
		thumbnailLabel = new ThumbnailLabel();
		thumbnailLabel.setBounds(WINDOW_WIDTH / 2 - SCALED_THUMBNAIL_WIDTH / 2, 130 + 10, SCALED_THUMBNAIL_WIDTH,
				SCALED_THUMBNAIL_HEIGHT);
		this.add(thumbnailLabel);
	}

	public void loadStage(int stageNumber) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(new File("data/stage" + stageNumber + ".txt")));
			String line;
			int row = 0;
			while ((line = in.readLine()) != null) {
				if (row < CELL_NUM_X) {
					// read wallMatrix
					String[] temp = line.split("\t");
					for (int j = 0; j < temp.length; j++) {
						wallMatrix[row][j] = Integer.parseInt(temp[j]);
					}
					row++;
				} else if (row == CELL_NUM_X) {
					// read monsterX
					String[] temp = line.split("\t");
					monsterX=new int[temp.length];
					for (int j = 0; j < temp.length; j++) {
						monsterX[j] = Integer.parseInt(temp[j]);
					}
					row++;
				} else if (row == CELL_NUM_X + 1) {
					// read monsterY
					String[] temp = line.split("\t");
					monsterY=new int[temp.length];
					for (int j = 0; j < temp.length; j++) {
						monsterY[j] = Integer.parseInt(temp[j]);
					}
					row++;
				} else if (row == CELL_NUM_X + 2) {
					// read monsterID
					String[] temp = line.split("\t");
					monsterID=new int[temp.length];
					for (int j = 0; j < temp.length; j++) {
						monsterID[j] = Integer.parseInt(temp[j]);
					}
					row++;
				} else{
					// read theme
					theme = Integer.parseInt(line);
					row++;
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

			switch (this.name) {
			case "back":
				ChoosePlayerPanel choosePlayerPanel = new ChoosePlayerPanel(mainFrame, gameMode);
				if (mainFrame.getContentPane() instanceof JPanel) {
					JPanel mainPanel = (JPanel) mainFrame.getContentPane();
					mainPanel.removeAll();
				} else {
					JLabel mainPanel = (JLabel) mainFrame.getContentPane();
					mainPanel.removeAll();
				}

				mainFrame.add(choosePlayerPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				choosePlayerPanel.setLocation(0, 0);
				choosePlayerPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "confirm":
				//loadStage(stageNumber);
				Game game = new Game(wallMatrix, monsterX,monsterY,monsterID, gameMode, theme, stageNumber,
						player1CharacterID, player2CharacterID);

				MapPanel mapPanel = new MapPanel(game, mainFrame);
				StatusPanel statusPanel = new StatusPanel(game, mainFrame);

				if (mainFrame.getContentPane() instanceof JPanel) {
					JPanel mainPanel = (JPanel) mainFrame.getContentPane();
					mainPanel.removeAll();
				} else {
					JLabel mainPanel = (JLabel) mainFrame.getContentPane();
					mainPanel.removeAll();
				}

				mainFrame.setContentPane(new JLabel(new ImageIcon("image/background/mapBackground.png")));
				mainFrame.validate();

				mainFrame.add(mapPanel);
				mainFrame.validate();// repaint

				mainFrame.add(statusPanel);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				mapPanel.setLocation(325, 33);
				mapPanel.setSize(MAP_WIDTH, MAP_HEIGHT);

				statusPanel.setLocation(38, 38);
				statusPanel.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);

				TimerListener timerListenerPve = new TimerListener(game, mapPanel, statusPanel);
				Timer timerPve = new Timer(REFRESH, timerListenerPve);
				timerPve.start();
				break;
			case "forward":
				stageNumber = stageNumber + 1;
				if (stageNumber >= totalStageNum) {
					stageNumber = stageNumber - totalStageNum;
				}

				StagePanel.this.removeAll();
				loadStage(stageNumber);
				
				thumbnailLabel = new ThumbnailLabel();
				thumbnailLabel.setBounds(WINDOW_WIDTH / 2 - SCALED_THUMBNAIL_WIDTH / 2, 130 + 10,
						SCALED_THUMBNAIL_WIDTH, SCALED_THUMBNAIL_HEIGHT);
				StagePanel.this.add(thumbnailLabel);
				thumbnailLabel.setVisible(true);
				StagePanel.this.addButton();
				StagePanel.this.addStory(stageNumber);
				StagePanel.this.addBackground();
				StagePanel.this.revalidate();
				StagePanel.this.repaint();
				break;
			case "backward":
				stageNumber = stageNumber - 1;
				if (stageNumber < 0) {
					stageNumber = stageNumber + totalStageNum;
				}

				StagePanel.this.removeAll();
				loadStage(stageNumber);
				
				thumbnailLabel = new ThumbnailLabel();
				thumbnailLabel.setBounds(WINDOW_WIDTH / 2 - SCALED_THUMBNAIL_WIDTH / 2, 130 + 10,
						SCALED_THUMBNAIL_WIDTH, SCALED_THUMBNAIL_HEIGHT);
				StagePanel.this.add(thumbnailLabel);
				thumbnailLabel.setVisible(true);
				StagePanel.this.addButton();
				StagePanel.this.addStory(stageNumber);
				StagePanel.this.addBackground();
				StagePanel.this.revalidate();
				StagePanel.this.repaint();
				break;

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "back":
				buttonBackLabel.setIcon(buttonBackOnIcon);
				break;
			case "confirm":
				buttonConfirmLabel.setIcon(buttonConfirmOnIcon);
				break;
			case "forward":
				buttonForwardLabel.setIcon(buttonForwardOnIcon);
				break;
			case "backward":
				buttonBackwardLabel.setIcon(buttonBackwardOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "back":
				buttonBackLabel.setIcon(buttonBackOffIcon);
				break;
			case "confirm":
				buttonConfirmLabel.setIcon(buttonConfirmOffIcon);
				break;
			case "forward":
				buttonForwardLabel.setIcon(buttonForwardOffIcon);
				break;
			case "backward":
				buttonBackwardLabel.setIcon(buttonBackwardOffIcon);
				break;
			}
		}
	}

	/**
	 * Display thumbnail of the mapPanel, only include ground & walls. Change its
	 * type from panel to label
	 *
	 * @author Hang Chen
	 * @version 1.0
	 */

	class ThumbnailLabel extends JLabel implements GameConstants {
		private Map map;

		public ThumbnailLabel() {
			try {
				loadMapImage();
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			map = new Map(wallMatrix);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int xSize = map.getSizeX();
			int ySize = map.getSizeY();
			for (int i = 0; i < xSize; i++)
				for (int j = 0; j < ySize; j++) {
					if (theme == 0 || theme == 3) {
						if ((i + j) % 2 == 0)
							g.drawImage(mapImage[GRASS_1], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
									tinyCellHeight, this);
						else
							g.drawImage(mapImage[GRASS_2], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
									tinyCellHeight, this);
						}
					else {
						if ((i + j) % 2 == 0)
							g.drawImage(mapImage[SAND_1], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
									tinyCellHeight, this);
						else
							g.drawImage(mapImage[SAND_2], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
									tinyCellHeight, this);
					}
					if (map.isWithWall(i, j))
						g.drawImage(wallImage[theme][map.getWallID(i, j)], (int) (i * tinyCellWidth),
								(int) (j * tinyCellHeight), tinyCellWidth, tinyCellHeight, this);
				}
		}

		private void loadMapImage() throws Exception {
			mapImage[GRASS_1] = ImageIO.read(new File("image/maps/grass1.png"));
			mapImage[GRASS_2] = ImageIO.read(new File("image/maps/grass2.png"));
			mapImage[SAND_1] = ImageIO.read(new File("image/maps/sand1.png"));
			mapImage[SAND_2] = ImageIO.read(new File("image/maps/sand2.png"));

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 8; j++) {
					wallImage[i][j] = ImageIO.read(new File("image/maps/wall" + (1 + i) + "-" + (1 + j) + ".png"));
				}
			}

		}
	}
}
