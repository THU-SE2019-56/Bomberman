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
	
	private final static int tinyCellWidth =SCALED_THUMBNAIL_WIDTH/ CELL_NUM_X;
	private final static int tinyCellHeight =SCALED_THUMBNAIL_HEIGHT/ CELL_NUM_Y;
	private Map map;
	private BufferedImage mapImage[] = new BufferedImage[4];
	private BufferedImage wallImage[][] = new BufferedImage[4][8];

	public StagePanel(MainFrame mainFrame, int gameMode, int p1cID, int p2cID) {
		this.mainFrame = mainFrame;
		this.gameMode = gameMode;
		this.player1CharacterID = p1cID;
		this.player2CharacterID = p2cID;

		this.setLayout(null);

		this.addThumbnail(stageNumber);
		this.addButton();
		this.addStory(stageNumber);
		this.addBackground();
	}

	public void addBackground() {
		thumbnailBackgroundIcon = new ImageIcon("image/background/thumbnailBackground.png");
		thumbnailBackgroundIcon.setImage(thumbnailBackgroundIcon.getImage().getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, 1));
		thumbnailBackgroundLabel = new JLabel(thumbnailBackgroundIcon);
		thumbnailBackgroundLabel.setBounds(WINDOW_WIDTH/2-THUMBNAIL_WIDTH/2, 130, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
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
		buttonBackOffIcon.setImage(buttonBackOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonBackOnIcon.setImage(buttonBackOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackLabel = new JLabel(buttonBackOffIcon);
		buttonBackLabel.setBounds(0, 650, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonBackLabel);

		// confirm
		buttonConfirmOffIcon = new ImageIcon("image/buttons/confirm_off.png");
		buttonConfirmOffIcon
				.setImage(buttonConfirmOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonConfirmOnIcon = new ImageIcon("image/buttons/confirm_on.png");
		buttonConfirmOnIcon.setImage(buttonConfirmOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonConfirmLabel = new JLabel(buttonConfirmOffIcon);
		buttonConfirmLabel.setBounds(760, 650,SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonConfirmLabel);

		// forward
		buttonForwardOffIcon = new ImageIcon("image/buttons/forward_off.png");
		buttonForwardOffIcon.setImage(buttonForwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardOnIcon = new ImageIcon("image/buttons/forward_on.png");
		buttonForwardOnIcon.setImage(buttonForwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardLabel = new JLabel(buttonForwardOffIcon);
		buttonForwardLabel.setBounds(780, 230, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonForwardLabel);

		// backward
		buttonBackwardOffIcon = new ImageIcon("image/buttons/backward_off.png");
		buttonBackwardOffIcon
				.setImage(buttonBackwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardOnIcon = new ImageIcon("image/buttons/backward_on.png");
		buttonBackwardOnIcon.setImage(buttonBackwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardLabel = new JLabel(buttonBackwardOffIcon);
		buttonBackwardLabel.setBounds(60, 230, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonBackwardLabel);

		buttonBackLabel.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonConfirmLabel.addMouseListener(new ButtonListener(mainFrame, "confirm"));
		buttonForwardLabel.addMouseListener(new ButtonListener(mainFrame, "forward"));
		buttonBackwardLabel.addMouseListener(new ButtonListener(mainFrame, "backward"));
	}
	
	public void addStory(int stageNumber) {
		storyIcon = new ImageIcon("image/background/story"+stageNumber+".png");
		storyIcon.setImage(storyIcon.getImage().getScaledInstance(STORY_WIDTH, STORY_HEIGHT, 1));
		storyLabel = new JLabel(storyIcon);
		storyLabel.setBounds(WINDOW_WIDTH/2-STORY_WIDTH/2, 560, STORY_WIDTH, STORY_HEIGHT);
		this.add(storyLabel);
	}
	
	public void addThumbnail(int stageNumber) {
		thumbnailLabel = new ThumbnailLabel(stageNumber);
		thumbnailLabel.setBounds(WINDOW_WIDTH/2-SCALED_THUMBNAIL_WIDTH/2, 130+10, SCALED_THUMBNAIL_WIDTH,SCALED_THUMBNAIL_HEIGHT);
		this.add(thumbnailLabel);
	}

	public static int[][] loadStage(int stageNumber) {
		BufferedReader in;
		int[][] wallMatrix = new int[CELL_NUM_X][CELL_NUM_Y];
		try {
			in = new BufferedReader(new FileReader(new File("data/stage" + stageNumber + ".txt")));
			String line;
			int row = 0;
			while ((line = in.readLine()) != null) {
				String[] temp = line.split("\t");
				for (int j = 0; j < temp.length; j++) {
					wallMatrix[row][j] = Integer.parseInt(temp[j]);
				}
				row++;
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wallMatrix;
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
			JPanel mainPanel = (JPanel) mainFrame.getContentPane();
			
			switch (this.name) {
			case "back":
				ChoosePlayerPanel choosePlayerPanel = new ChoosePlayerPanel(mainFrame, gameMode);
				mainPanel.removeAll();

				mainFrame.add(choosePlayerPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				choosePlayerPanel.setLocation(0, 0);
				choosePlayerPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "confirm":
				wallMatrix = loadStage(stageNumber);
				Game game = new Game(wallMatrix, 0, 0, new int[5], new int[5], gameMode, stageNumber, player1CharacterID,
						player2CharacterID);
				
				MapBackgroundPanel mapBackgroundPanel=new MapBackgroundPanel(mainFrame);
				MapPanel mapPanel = new MapPanel(game,mainFrame);
				StatusPanel statusPanel = new StatusPanel(game, mainFrame);
				
				mainPanel.removeAll();
				
				mainFrame.add(mapBackgroundPanel);
				mainFrame.validate();// repaint
				
				mainFrame.add(mapPanel);
				mainFrame.validate();// repaint

				mainFrame.add(statusPanel);
				mainFrame.validate();// repaint
				
				mainFrame.setLayout(null);

				mapPanel.setLocation(325, 33);
				mapPanel.setSize(MAP_WIDTH, MAP_HEIGHT);

				statusPanel.setLocation(38, 38);
				statusPanel.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);
				
				
				mapBackgroundPanel.setLocation(0, 0);
				mapBackgroundPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

				TimerListener timerListenerPve = new TimerListener(game, mapPanel, statusPanel);
				Timer timerPve = new Timer(REFRESH, timerListenerPve);
				timerPve.start();
				break;
			case "forward":
				stageNumber=stageNumber+1;
				if (stageNumber>3){
					stageNumber=stageNumber-4;
				}
				
				StagePanel.this.removeAll();
							
				thumbnailLabel = new ThumbnailLabel(stageNumber);
				thumbnailLabel.setBounds(WINDOW_WIDTH/2-SCALED_THUMBNAIL_WIDTH/2, 130+10, SCALED_THUMBNAIL_WIDTH,SCALED_THUMBNAIL_HEIGHT);
				StagePanel.this.add(thumbnailLabel);
				thumbnailLabel.setVisible(true);
				StagePanel.this.addButton();
				StagePanel.this.addStory(stageNumber);
				StagePanel.this.addBackground();
				StagePanel.this.revalidate();
				StagePanel.this.repaint();
				break;
			case "backward":
				stageNumber=stageNumber-1;
				if (stageNumber<0){
					stageNumber=stageNumber+4;
				}
				
				StagePanel.this.removeAll();

				thumbnailLabel = new ThumbnailLabel(stageNumber);
				thumbnailLabel.setBounds(WINDOW_WIDTH/2-SCALED_THUMBNAIL_WIDTH/2, 130+10, SCALED_THUMBNAIL_WIDTH,SCALED_THUMBNAIL_HEIGHT);
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
		
		public ThumbnailLabel(int stageNumber) {
			try {
				loadMapImage();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			wallMatrix = loadStage(stageNumber);
			map = new Map(wallMatrix);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int xSize = map.getSizeX();
			int ySize = map.getSizeY();
			for (int i = 0; i < xSize; i++)
				for (int j = 0; j < ySize; j++) {
					if ((i + j) % 2 == 0)
						g.drawImage(mapImage[GRASS_1], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight),
								tinyCellWidth, tinyCellHeight, this);
					else
						g.drawImage(mapImage[GRASS_2], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight),
								tinyCellWidth, tinyCellHeight, this);
					if (map.isWithWall(i, j))
						g.drawImage(wallImage[stageNumber][map.getWallID(i, j)], (int) (i * tinyCellWidth),
								(int) (j * tinyCellHeight), tinyCellWidth, tinyCellHeight, this);
				}
		}
		
		private void loadMapImage() throws Exception {
			mapImage[GRASS_1] = ImageIO.read(new File("image/maps/grass1.png"));
			mapImage[GRASS_2] = ImageIO.read(new File("image/maps/grass2.png"));
			mapImage[SAND_1] = ImageIO.read(new File("image/maps/sand1.png"));
			mapImage[SAND_2] = ImageIO.read(new File("image/maps/sand2.png"));

			wallImage[0][0] = ImageIO.read(new File("image/maps/wall1-1.png"));
			wallImage[0][1] = ImageIO.read(new File("image/maps/wall1-2.png"));
			wallImage[0][2] = ImageIO.read(new File("image/maps/wall1-3.png"));
			wallImage[0][3] = ImageIO.read(new File("image/maps/wall1-4.png"));
			wallImage[0][4] = ImageIO.read(new File("image/maps/wall1-5.png"));
			wallImage[0][5] = ImageIO.read(new File("image/maps/wall1-6.png"));
			wallImage[0][6] = ImageIO.read(new File("image/maps/wall1-7.png"));
			wallImage[0][7] = ImageIO.read(new File("image/maps/wall1-8.png"));

			wallImage[1][0] = ImageIO.read(new File("image/maps/wall2-1.png"));
			wallImage[1][1] = ImageIO.read(new File("image/maps/wall2-2.png"));
			wallImage[1][2] = ImageIO.read(new File("image/maps/wall2-3.png"));
			wallImage[1][3] = ImageIO.read(new File("image/maps/wall2-4.png"));
			wallImage[1][4] = ImageIO.read(new File("image/maps/wall2-5.png"));
			wallImage[1][5] = ImageIO.read(new File("image/maps/wall2-6.png"));
			wallImage[1][6] = ImageIO.read(new File("image/maps/wall2-7.png"));
			wallImage[1][7] = ImageIO.read(new File("image/maps/wall2-8.png"));

			wallImage[2][0] = ImageIO.read(new File("image/maps/wall3-1.png"));
			wallImage[2][1] = ImageIO.read(new File("image/maps/wall3-2.png"));
			wallImage[2][2] = ImageIO.read(new File("image/maps/wall3-3.png"));
			wallImage[2][3] = ImageIO.read(new File("image/maps/wall3-4.png"));
			wallImage[2][4] = ImageIO.read(new File("image/maps/wall3-5.png"));
			wallImage[2][5] = ImageIO.read(new File("image/maps/wall3-6.png"));
			wallImage[2][6] = ImageIO.read(new File("image/maps/wall3-7.png"));
			wallImage[2][7] = ImageIO.read(new File("image/maps/wall3-8.png"));

			wallImage[3][0] = ImageIO.read(new File("image/maps/wall4-1.png"));
			wallImage[3][1] = ImageIO.read(new File("image/maps/wall4-2.png"));
			wallImage[3][2] = ImageIO.read(new File("image/maps/wall4-3.png"));
			wallImage[3][3] = ImageIO.read(new File("image/maps/wall4-4.png"));
			wallImage[3][4] = ImageIO.read(new File("image/maps/wall4-5.png"));
			wallImage[3][5] = ImageIO.read(new File("image/maps/wall4-6.png"));
			wallImage[3][6] = ImageIO.read(new File("image/maps/wall4-7.png"));
			wallImage[3][7] = ImageIO.read(new File("image/maps/wall4-8.png"));

		}
	}
}
