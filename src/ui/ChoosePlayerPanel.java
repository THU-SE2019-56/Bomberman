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
	
	private JButton buttonBack;
	private  JButton buttonOk;


	private ImageIcon ChoosePlayerBackgroundIcon;
	private ImageIcon p1Icon;
	private ImageIcon p2Icon;
	private ImageIcon p3Icon;
	private ImageIcon p4Icon;
	private ImageIcon p1ArrayIcon;
	private ImageIcon p2ArrayIcon;

	
	private JLabel ChoosePlayerBackgroundLabel;
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p3Label;
	private JLabel p4Label;
	
	private JLabel p1Array;
	private JLabel p2Array;
	
	private int gameMode;
	
	private int player1CID = 0;
	private int player2CID = 0;
	
	
	private BufferedImage player1Image[] = new BufferedImage[4];
	private BufferedImage player2Image[] = new BufferedImage[4];
	private BufferedImage player3Image[] = new BufferedImage[4];
	private BufferedImage player4Image[] = new BufferedImage[4];
	
	private Controls control;
	
	public ChoosePlayerPanel (MainFrame mainFrame,int gamemode) {
		this.mainFrame = mainFrame;
		this.gameMode = gamemode;
		
		control = new Controls();
		
		this.addArray();
		this.addButton();
	
		//this.addPlayerInfo();
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
		ChoosePlayerBackgroundIcon = new ImageIcon("image/menu/ChoosePlayer.png");// Background image
		ChoosePlayerBackgroundIcon.setImage(ChoosePlayerBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		ChoosePlayerBackgroundLabel = new JLabel(ChoosePlayerBackgroundIcon);
		ChoosePlayerBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(ChoosePlayerBackgroundLabel);
	}
	
	public void addArray() {
		
		p1ArrayIcon = new ImageIcon("image/menu/p1Array.png");
		p1ArrayIcon.setImage(p1ArrayIcon .getImage().getScaledInstance(50, 100, 1));
		p1Array = new JLabel(p1ArrayIcon);
		p1Array.setBounds(190,130,60,100);
		
		this.add(p1Array);
		
		if (gameMode == PVP_MODE) {
			
			p2ArrayIcon = new ImageIcon("image/menu/p2Array.png");
			p2ArrayIcon.setImage(p2ArrayIcon .getImage().getScaledInstance(50, 100, 1));
			p2Array = new JLabel(p2ArrayIcon);
			p2Array.setBounds(130,130,60, 100);
			this.add(p2Array);
		}

	}
	
	public void addPlayerLabel() {
		
		
		p1Icon = new ImageIcon("image/player/p1DOWN.png");// Background image
		p2Icon = new ImageIcon("image/player/p2DOWN.png");// Background image
		p3Icon = new ImageIcon("image/player/p3DOWN.png");// Background image
		p4Icon = new ImageIcon("image/player/p4DOWN.png");// Background image
		
		p1Icon.setImage(p1Icon.getImage().getScaledInstance(160, 160, 1));
		p2Icon.setImage(p2Icon.getImage().getScaledInstance(160, 160, 1));
		p3Icon.setImage(p3Icon.getImage().getScaledInstance(160, 160, 1));
		p4Icon.setImage(p4Icon.getImage().getScaledInstance(160, 160, 1));
		
		p1Label = new JLabel(p1Icon);
		p2Label = new JLabel(p2Icon);
		p3Label = new JLabel(p3Icon);
		p4Label = new JLabel(p4Icon);
		
		p1Label.setBounds(100, 230, 160, 160);
		p2Label.setBounds(300, 230, 160, 160);
		p3Label.setBounds(500, 230, 160, 160);
		p4Label.setBounds(700, 230, 160, 160);
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
					p1Array.setBounds(190, 130, 60, 100); 
					break;
				case 1:
					p1Array.setBounds(390, 130, 60, 100);
					break;
				case 2:
					p1Array.setBounds(590, 130, 60, 100);
					break;
				case 3:
					p1Array.setBounds(790, 130, 60, 100);
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
						p2Array.setBounds(130, 130, 60, 100); 
						break;
					case 1:
						p2Array.setBounds(330, 130, 60, 100);
						break;
					case 2:
						p2Array.setBounds(530, 130, 60, 100);
						break;
					case 3:
						p2Array.setBounds(730, 130, 60, 100);
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
