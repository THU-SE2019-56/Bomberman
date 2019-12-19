package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import game.Game;
import game.GameConstants;
import game.TimerListener;

/**
 * Create a panel on the right side of mapManel. Show the status of player(s)
 * and provide buttons for specific usage. Related to mainFrame in order to
 * realize the "back" function.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class StatusPanel extends JPanel implements GameConstants {
	private BufferedImage bombImage[] = new BufferedImage[2];
	private BufferedImage itemImage[] = new BufferedImage[ITEM_NUM];
	private BufferedImage player1Image[] = new BufferedImage[4];
	private BufferedImage player2Image[] = new BufferedImage[4];
	private BufferedImage player3Image[] = new BufferedImage[4];
	private BufferedImage player4Image[] = new BufferedImage[4];

	private JTextField bombNum[] = new JTextField[2];
	private JTextField bombPow[] = new JTextField[2];
	private JTextField itemName[] = new JTextField[2];
	private JTextField playerLifeText[] = new JTextField[2];
    private JTextField velocityNum[] = new JTextField[2];

	private Game game;

	private ImageIcon buttonPauseOffIcon;
	private ImageIcon buttonPauseOnIcon;
	private ImageIcon buttonStartOffIcon;
	private ImageIcon buttonStartOnIcon;
	private JLabel buttonPauseLabel;

	private ImageIcon buttonRestartOffIcon;
	private ImageIcon buttonRestartOnIcon;
	private JLabel buttonRestartLabel;

	private ImageIcon buttonBackOffIcon;
	private ImageIcon buttonBackOnIcon;
	private JLabel buttonBackLabel;

	public StatusPanel(Game game, MainFrame mainFrame) {
		this.game = game;
		this.setBackground(new Color(153, 191, 68));

		try {
			loadImage();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		this.setLayout(null);

		this.addButton();

		for (int i = 0; i < game.getPlayerNum(); i++) {
			int refY = 50 + 150 * i;
			this.playerLifeText[i] = new JTextField("");

			this.initializeTextField(this.playerLifeText[i], 160, 105 + refY, 40, 20);

			this.bombNum[i] = new JTextField("");
			this.initializeTextField(this.bombNum[i], 160, refY, 60, 20);
			this.bombPow[i] = new JTextField("");
			this.initializeTextField(this.bombPow[i], 160, 35 + refY, 60, 20);
			this.itemName[i] = new JTextField("");
			this.initializeTextField(this.itemName[i], 60, 73 + refY, 70, 20);
			
	        this.velocityNum[i] = new JTextField("");  
	        this.initializeTextField(this.velocityNum[i], 160, 70+ refY, 60, 20);

			this.add(this.playerLifeText[i]);
			this.add(this.bombNum[i]);
			this.add(this.bombPow[i]);
			this.add(this.itemName[i]);
			this.add(this.velocityNum[i]);
		}

		buttonPauseLabel.addMouseListener(new ButtonListener(mainFrame, "pause"));
		buttonRestartLabel.addMouseListener(new ButtonListener(mainFrame, "restart"));
		buttonBackLabel.addMouseListener(new ButtonListener(mainFrame, "back"));
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
		buttonPauseOffIcon = new ImageIcon("image/buttons/pause_off.png");
		buttonPauseOffIcon.setImage(
				buttonPauseOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonPauseOnIcon = new ImageIcon("image/buttons/pause_on.png");
		buttonPauseOnIcon
				.setImage(buttonPauseOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonStartOffIcon = new ImageIcon("image/buttons/start_off.png");
		buttonStartOffIcon.setImage(
				buttonStartOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonStartOnIcon = new ImageIcon("image/buttons/start_on.png");
		buttonStartOnIcon
				.setImage(buttonStartOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonPauseLabel = new JLabel(buttonPauseOffIcon);
		buttonPauseLabel.setBounds(40, 400, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonPauseLabel);

		buttonRestartOffIcon = new ImageIcon("image/buttons/restart_off.png");
		buttonRestartOffIcon.setImage(
				buttonRestartOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonRestartOnIcon = new ImageIcon("image/buttons/restart_on.png");
		buttonRestartOnIcon.setImage(
				buttonRestartOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonRestartLabel = new JLabel(buttonRestartOffIcon);
		buttonRestartLabel.setBounds(40, 500, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonRestartLabel);

		buttonBackOffIcon = new ImageIcon("image/buttons/back_off.png");
		buttonBackOffIcon
				.setImage(buttonBackOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonBackOnIcon
				.setImage(buttonBackOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackLabel = new JLabel(buttonBackOffIcon);
		buttonBackLabel.setBounds(40, 600, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonBackLabel);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		for (int i = 0; i < game.getPlayerNum(); i++) {
			int refX = 50;
			int refY = 50 + 150 * i;

			// Player
			switch (game.getPlayer()[i].getPlayerCharacterID()) {
			case 0:
				g.drawImage(player1Image[DIRECTION_DOWN], refX, refY, 70, 70, this);
				break;
			case 1:
				g.drawImage(player2Image[DIRECTION_DOWN], refX, refY, 70, 70, this);
				break;
			case 2:
				g.drawImage(player3Image[DIRECTION_DOWN], refX, refY, 70, 70, this);
				break;
			case 3:
				g.drawImage(player4Image[DIRECTION_DOWN], refX, refY, 70, 70, this);
				break;
			}

			// HP bar
			int playerHP = game.getPlayer()[i].getHP();
			int playerMaxHP = game.getPlayer()[i].getMaxHP();
			this.playerLifeText[i].setText(String.valueOf(playerHP));
			g.setColor(Color.BLUE);
			g.drawRect(refX, 110 + refY, playerMaxHP, 10);
			g.setColor(Color.getHSBColor((float) playerHP / 300, 1, 1));
			g.fillRect(refX, 110 + refY, playerHP, 10);

			// bomb
			int bombNum = game.getPlayer()[i].getBombMaxNumber() - game.getPlayer()[i].getBombPlantedNumber();
			int bombPow = game.getPlayer()[i].getBombPower();
		    int shoeNum = game.getPlayer()[i].getVelocityLevel();
			this.bombNum[i].setText(" × " + String.valueOf(bombNum));
			this.bombPow[i].setText(" × " + String.valueOf(bombPow));
			this.velocityNum[i].setText(" × " + String.valueOf(shoeNum));
			g.drawImage(bombImage[0], refX + 80, refY, 30, 30, this);
			g.drawImage(bombImage[1], refX + 80, 35 + refY, 30, 30, this);
		     g.drawImage(itemImage[VELOCITY_UP], refX + 80,	70 + refY, 30, 30, this);

			// bullet
			int itemID = game.getPlayer()[i].getActiveItemID();
			switch (itemID) {
			case BULLET:
				g.drawImage(itemImage[BULLET], refX -30, 70 + refY, 30,30, this);
				this.itemName[i].setText("Bullet");
				break;
			case NO_ACTIVE_ITEM:
				this.itemName[i].setText("");
				break;
			}

			// Protected
			if (game.getPlayer()[i].proectedByItem()) {
				g.setColor(Color.black);
				g.drawOval(refX - 10, refY - 20, 75, 75);
			}
		}
	}

	public void loadImage() throws Exception {
		// TODO Load all the images here

		bombImage[0] = ImageIO.read(new File("image/bomb/bomb.png"));
		bombImage[1] = ImageIO.read(new File("image/bomb/power.png"));

		itemImage[BULLET] = ImageIO.read(new File("image/item/bullet.png"));
        itemImage[VELOCITY_UP]=   ImageIO.read(new File("image/item/velocity.png"));

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
			JLabel mainPanel = (JLabel) mainFrame.getContentPane();

			switch (this.name) {
			case "pause":
				if (game.getPauseFlag() == 0) {
					game.setPauseFlag(1);
					buttonPauseLabel.setIcon(buttonStartOffIcon);
				} else {
					game.setPauseFlag(0);
					buttonPauseLabel.setIcon(buttonPauseOffIcon);
				}
				break;
			case "restart":
				// Close the previous game
				game.setPauseFlag(1);
				
				Game newGame = new Game(game.getWallMatrix(),game.getMonsterX(), game.getMonsterY(), game.getMonsterID(),game.getGameMode(),
						game.getStageNumber(), game.getTheme(),game.getPlayer1CID(), game.getPlayer2CID());
				MapPanel newMapPanel = new MapPanel(newGame, mainFrame);
				StatusPanel newStatusPanel = new StatusPanel(newGame, mainFrame);

				mainPanel.removeAll();

				mainFrame.setContentPane(new JLabel(new ImageIcon("image/background/mapBackground.png")));
				mainFrame.validate();

				mainFrame.add(newMapPanel);
				mainFrame.validate();// repaint

				mainFrame.add(newStatusPanel);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				newMapPanel.setLocation(325, 33);
				newMapPanel.setSize(MAP_WIDTH, MAP_HEIGHT);

				newStatusPanel.setLocation(38, 38);
				newStatusPanel.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);

				TimerListener newTimerListener = new TimerListener(newGame, newMapPanel, newStatusPanel);
				Timer newTimer = new Timer(REFRESH, newTimerListener);
				newTimer.start();
				break;
			case "back":
				int gameMode = game.getGameMode();
				StagePanel newStagePanel = new StagePanel(mainFrame, gameMode, game.getPlayer1CID(),
						game.getPlayer2CID());

				mainPanel.removeAll();

				mainFrame.add(newStagePanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				newStagePanel.setLocation(0, 0);
				newStagePanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "pause":
				if (game.getPauseFlag() == 0) {
					buttonPauseLabel.setIcon(buttonPauseOnIcon);
				} else {
					buttonPauseLabel.setIcon(buttonStartOnIcon);
				}
				break;
			case "restart":
				buttonRestartLabel.setIcon(buttonRestartOnIcon);
				break;
			case "back":
				buttonBackLabel.setIcon(buttonBackOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "pause":
				if (game.getPauseFlag() == 0) {
					buttonPauseLabel.setIcon(buttonPauseOffIcon);
				} else {
					buttonPauseLabel.setIcon(buttonStartOffIcon);
				}
				break;
			case "restart":
				buttonRestartLabel.setIcon(buttonRestartOffIcon);
				break;
			case "back":
				buttonBackLabel.setIcon(buttonBackOffIcon);
				break;
			}
		}

	}

}