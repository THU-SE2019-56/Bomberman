package ui;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import game.Game;
import game.GameConstants;
import game.TimerListener;


/**
 * Main menu panel. Fill in the MainFrame. Contain buttons to jump to other
 * panels.
 * 
 * @author Wang
 * @version 0.9
 */
public class ChoosePlayerPanel extends JPanel implements  AWTEventListener, GameConstants {
	private MainFrame mainFrame;
	private JFrame thumbFrame;
	
	private JButton buttonBack;
	private  JButton buttonOk;
	
	private JTextArea playerCharacterInfo1;
	private JTextArea playerCharacterInfo2;
	private JTextArea playerCharacterInfo3;
	private JTextArea playerCharacterInfo4;
	
	private JTextField p1Text;
	private JTextField p2Text;
	
	private ImageIcon stageBackgroundIcon;
	private ImageIcon p1Icon;
	private ImageIcon p2Icon;
	private ImageIcon p3Icon;
	private ImageIcon p4Icon;
	private ImageIcon p1ArrayIcon;
	private ImageIcon p2ArrayIcon;

	
	private JLabel stageBackgroundLabel;
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p3Label;
	private JLabel p4Label;
	
	private JLabel p1Array;
	private JLabel p2Array;
	
	private int gameMode;
	
	private int player1CID = 0;
	private int player2CID = 0;
	
	private int chooseCount = 0;
	
	private BufferedImage player1Image[] = new BufferedImage[4];
	private BufferedImage player2Image[] = new BufferedImage[4];
	private BufferedImage player3Image[] = new BufferedImage[4];
	private BufferedImage player4Image[] = new BufferedImage[4];
	
	private Controls control;
	
	public ChoosePlayerPanel (MainFrame mainFrame,int gamemode) {
		this.mainFrame = mainFrame;
		this.gameMode = gamemode;
		
		control = new Controls();

		thumbFrame = new JFrame();
		thumbFrame.setAlwaysOnTop(true);
		thumbFrame.setUndecorated(true);
		thumbFrame.setVisible(false);
		thumbFrame.setSize(CELL_WIDTH/SCALE_FACTOR*CELL_NUM_X,
				CELL_HEIGHT/SCALE_FACTOR*CELL_NUM_Y);

		
		this.addArray();
		this.addButton();
	
		this.addPlayerInfo();
		this.addPlayerLabel();
		this.addBackground();
		
		try {
			loadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.getToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);

	}

	public void addBackground() {
		stageBackgroundIcon = new ImageIcon("image/menu/ChoosePlayerPanelBackground.png");// Background image
		stageBackgroundIcon.setImage(stageBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		stageBackgroundLabel = new JLabel(stageBackgroundIcon);
		stageBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(stageBackgroundLabel);
	}
	
	public void addArray() {
		
		p1ArrayIcon = new ImageIcon("image/menu/p1Array.png");
		p1ArrayIcon.setImage(p1ArrayIcon .getImage().getScaledInstance(50, 100, 1));
		p1Array = new JLabel(p1ArrayIcon);
		p1Array.setBounds(190,50,50, 100);
		
		this.add(p1Array);
		
		if (gameMode == PVP_MODE) {
			
			p2ArrayIcon = new ImageIcon("image/menu/p2Array.png");
			p2ArrayIcon.setImage(p2ArrayIcon .getImage().getScaledInstance(50, 100, 1));
			p2Array = new JLabel(p2ArrayIcon);
			p2Array.setBounds(130,50,50, 100);
			this.add(p2Array);
		}

	}
	
	public void addPlayerLabel() {
		
		
		p1Icon = new ImageIcon("image/player/p1DOWN.png");// Background image
		p2Icon = new ImageIcon("image/player/p2DOWN.png");// Background image
		p3Icon = new ImageIcon("image/player/p3DOWN.png");// Background image
		p4Icon = new ImageIcon("image/player/p4DOWN.png");// Background image
		
		p1Icon.setImage(p1Icon.getImage().getScaledInstance(180, 180, 1));
		p2Icon.setImage(p2Icon.getImage().getScaledInstance(180, 180, 1));
		p3Icon.setImage(p3Icon.getImage().getScaledInstance(180, 180, 1));
		p4Icon.setImage(p4Icon.getImage().getScaledInstance(180, 180, 1));
		
		p1Label = new JLabel(p1Icon);
		p2Label = new JLabel(p2Icon);
		p3Label = new JLabel(p3Icon);
		p4Label = new JLabel(p4Icon);
		
		p1Label.setBounds(100, 150, 180, 180);
		p2Label.setBounds(300, 150, 180, 180);
		p3Label.setBounds(500, 150, 180, 180);
		p4Label.setBounds(700, 150, 180, 180);
		this.add(p1Label);
		this.add(p2Label);
		this.add(p3Label);
		this.add(p4Label);
	}

	/**
	 * Add buttons
	 */
	public void addButton() {

		buttonBack = new JButton("Back");
		buttonOk = new JButton("OK");
		
		control.initializeButton(buttonBack,100, 600, 180, 50);
		control.initializeButton(buttonOk,700, 600, 180, 50);
		
		this.setLayout(null);
	
		this.add(buttonBack);
		this.add(buttonOk);
	
		buttonBack.addMouseListener(new ButtonListener(mainFrame, "Back"));
		buttonOk.addMouseListener(new ButtonListener(mainFrame, "Ok"));

	}
	
	/**
	 * Add text areas showing players' info
	 */
	public void addPlayerInfo() {
		playerCharacterInfo1 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER1_HP_MAX)+ 
											  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER1_BOMB_MAX)+
											  "\r\n\r\n" +" Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER1_BOMB_POWER));
		
		playerCharacterInfo2 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER2_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER2_BOMB_MAX)+
				  "\r\n\r\n" +" Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER2_BOMB_POWER));
		
		
		playerCharacterInfo3 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER3_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER3_BOMB_MAX)+
				  "\r\n\r\n" +" Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER3_BOMB_POWER));
	

		playerCharacterInfo4 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER4_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER4_BOMB_MAX)+
				  "\r\n\r\n" +" Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER4_BOMB_POWER));

		
		control.initializeTextArea(playerCharacterInfo1, 100, 350,180, 100);
		control.initializeTextArea(playerCharacterInfo2, 300, 350,180, 100);
		control.initializeTextArea(playerCharacterInfo3, 500, 350,180, 100);
		control.initializeTextArea(playerCharacterInfo4, 700, 350,180, 100);

		this.add(playerCharacterInfo1);
		this.add(playerCharacterInfo2);
		this.add(playerCharacterInfo3);
		this.add(playerCharacterInfo4);
	}
	
		
	
	public void loadImage() throws IOException {
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}
	
	public void eventDispatched(AWTEvent event) {
			/*
			 * Enable to set the direction of the player only when the x and y of the player
			 * are multiples of 60. As a result, the player will keep moving when the x and
			 * y of the player are not multiples of 60.
			 */

			if (event.getID() == KeyEvent.KEY_PRESSED) {
				
				KeyEvent e = (KeyEvent) event;
				
				switch (e.getKeyCode()){
				case KeyEvent.VK_RIGHT:
					if(player1CID<3)  player1CID++;
					break;
				case KeyEvent.VK_LEFT:
					if(player1CID>0)  player1CID--;
					break;	
				}

				switch (player1CID) {
				case 0:
					p1Array.setBounds(190, 50, 50, 100); 
					break;
				case 1:
					p1Array.setBounds(390, 50, 50, 100);
					break;
				case 2:
					p1Array.setBounds(590, 50, 50, 100);
					break;
				case 3:
					p1Array.setBounds(790, 50, 50, 100);
					break;
				}
				
				if (gameMode ==PVP_MODE) {
				
					switch (e.getKeyCode()) {
					case KeyEvent.VK_D:
						if(player2CID<3)  player2CID++;
						break;
					case KeyEvent.VK_A:
						if(player2CID>0)  player2CID--;
						break;
					}
						
					switch (player2CID) {
					case 0:
						p2Array.setBounds(130, 50, 50, 100); 
						break;
					case 1:
						p2Array.setBounds(330, 50, 50, 100);
						break;
					case 2:
						p2Array.setBounds(530, 50, 50, 100);
						break;
					case 3:
						p2Array.setBounds(730, 50, 50, 100);
						break;
					}
				}
		
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
		
		public void generatePlayer(int p1CID,int p2CID) {
			StagePanel stagePanelPve = new StagePanel(mainFrame,gameMode,p1CID,p2CID);
			mainFrame.remove(ChoosePlayerPanel.this);
			mainFrame.add(stagePanelPve);
			mainFrame.validate();// repaint
			mainFrame.setLayout(null);
			stagePanelPve.setLocation(0, 0);
			stagePanelPve.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			thumbFrame.setVisible(false);
			thumbFrame.dispose();

			switch (this.name) {
				
			case "Back":
				MenuPanel newMenuPanel = new MenuPanel(mainFrame);

				JPanel mainPanel = (JPanel) mainFrame.getContentPane();
				mainPanel.removeAll();

				mainFrame.add(newMenuPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				newMenuPanel.setLocation(0, 0);
				newMenuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "Ok":
				generatePlayer(player1CID,player2CID);
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "Back":
				control.highLightButton(buttonBack);
				break;
			case "Ok":
				control.highLightButton(buttonOk);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			thumbFrame.setVisible(false);
			thumbFrame.dispose();	// necessary when mouse move too quickly

			switch (this.name) {
			case "Back":
				control.resetButton(buttonBack);
				break;
			case "Ok":
				control.resetButton(buttonOk);
				break;
			}
		}
	}
}
