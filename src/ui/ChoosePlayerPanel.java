package ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
public class ChoosePlayerPanel extends JPanel implements GameConstants {
	private MainFrame mainFrame;
	private JFrame thumbFrame;
	private JButton p1Button1;
	private JButton p1Button2;
	private JButton p1Button3;
	private JButton p1Button4;
	
	private JButton p2Button1;
	private JButton p2Button2;
	private JButton p2Button3;
	private JButton p2Button4;
	
	private JButton buttonBack;
	private  JButton buttonOk;
	
	private JTextArea playerCharacterInfo1;
	private JTextArea playerCharacterInfo2;
	private JTextArea playerCharacterInfo3;
	private JTextArea playerCharacterInfo4;
	
	private JTextField p1Text;
	private JTextField p2Text;
	
	private ImageIcon stageBackgroundIcon;
	private JLabel stageBackgroundLabel;

	private int gameMode;
	
	private int player1CID;
	private int player2CID;
	
	public ChoosePlayerPanel (MainFrame mainFrame,int gamemode) {
		this.mainFrame = mainFrame;
		this.gameMode = gamemode;


		thumbFrame = new JFrame();
		thumbFrame.setAlwaysOnTop(true);
		thumbFrame.setUndecorated(true);
		thumbFrame.setVisible(false);
		thumbFrame.setSize(CELL_WIDTH/SCALE_FACTOR*CELL_NUM_X,
				CELL_HEIGHT/SCALE_FACTOR*CELL_NUM_Y);

		this.addButton();
		
		this.addPlayerInfo();
		this.addJTextField();
		
		this.addBackground();

	}

	public void addBackground() {
		stageBackgroundIcon = new ImageIcon("image/menu/menuBackground.png");// Background image
		stageBackgroundIcon.setImage(stageBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		stageBackgroundLabel = new JLabel(stageBackgroundIcon);
		stageBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(stageBackgroundLabel);
	}

	/**
	 * Add buttons
	 */
	public void addButton() {

		
		/*
		 * Choose player 1
		 */
		p1Button1 = new JButton("Character 1");
		p1Button1.setBounds(100, 400, 180, 50);
		initializeButton(p1Button1);

		p1Button2 = new JButton("Character 2");
		p1Button2.setBounds(300, 400, 180, 50);
		initializeButton(p1Button2);

		p1Button3 = new JButton("Character 3");
		p1Button3.setBounds(500, 400, 180, 50);
		initializeButton(p1Button3);
		
		p1Button4 = new JButton("Character 4");
		p1Button4.setBounds(700, 400, 180, 50);
		initializeButton(p1Button4);

		/*
		 * Choose player2
		 */
		if(gameMode == PVP_MODE){ 
			p2Button1 = new JButton("Character 1");
			p2Button1.setBounds(100, 500, 180, 50);
			initializeButton(p2Button1);
	
			p2Button2 = new JButton("Character 2");
			p2Button2.setBounds(300, 500, 180, 50);
			initializeButton(p2Button2);
	
			p2Button3 = new JButton("Character 3");
			p2Button3.setBounds(500, 500, 180, 50);
			initializeButton(p2Button3);
			
			p2Button4 = new JButton("Character 4");
			p2Button4.setBounds(700, 500, 180, 50);
			initializeButton(p2Button4);
			
			this.add(p2Button1);
			this.add(p2Button2);
			this.add(p2Button3);
			this.add(p2Button4);
			
			p2Button1.addMouseListener(new ButtonListener(mainFrame, "5"));
			p2Button2.addMouseListener(new ButtonListener(mainFrame, "6"));
			p2Button3.addMouseListener(new ButtonListener(mainFrame, "7"));
			p2Button4.addMouseListener(new ButtonListener(mainFrame, "8"));
		}
		
		/*
		 * Back
		 */
		buttonBack = new JButton("Back");
		buttonBack.setBounds(100, 600, 180, 50);
		initializeButton(buttonBack);
		
		/*
		 * OK
		 */
		buttonOk = new JButton("OK");
		buttonOk.setBounds(700, 600, 180, 50);
		initializeButton(buttonOk);
		
		this.setLayout(null);

		this.add(p1Button1);
		this.add(p1Button2);
		this.add(p1Button3);
		this.add(p1Button4);
	
		this.add(buttonBack);
		this.add(buttonOk);

		p1Button1.addMouseListener(new ButtonListener(mainFrame, "1"));
		p1Button2.addMouseListener(new ButtonListener(mainFrame, "2"));
		p1Button3.addMouseListener(new ButtonListener(mainFrame, "3"));
		p1Button4.addMouseListener(new ButtonListener(mainFrame, "4"));
		
		buttonBack.addMouseListener(new ButtonListener(mainFrame, "Back"));
		buttonOk.addMouseListener(new ButtonListener(mainFrame, "Ok"));
	}
	
	/**
	 * Add text areas showing players' info
	 */
	public void addPlayerInfo() {
		playerCharacterInfo1 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER1_HP_MAX)+ 
											  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER1_BOMB_MAX)+
											  "\r\n\r\n" +"Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER1_BOMB_POWER));
		initializeButtonTextArea(playerCharacterInfo1);
		playerCharacterInfo1.setBounds(100, 250,180, 100);
		
		playerCharacterInfo2 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER2_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER2_BOMB_MAX)+
				  "\r\n\r\n" +"Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER2_BOMB_POWER));
		initializeButtonTextArea(playerCharacterInfo2);
		playerCharacterInfo2.setBounds(300, 250,180, 100);	
		
		
		playerCharacterInfo3 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER3_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER3_BOMB_MAX)+
				  "\r\n\r\n" +"Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER3_BOMB_POWER));
		initializeButtonTextArea(playerCharacterInfo3);
		playerCharacterInfo3.setBounds(500, 250,180, 100);		

		playerCharacterInfo4 = new JTextArea(" Max HP:"+String.valueOf(PLAYER_CHARACTER4_HP_MAX)+ 
				  "\r\n\r\n"+" Max Bomb Number:"+String.valueOf(PLAYER_CHARACTER4_BOMB_MAX)+
				  "\r\n\r\n" +"Max Bomb Power:"+String.valueOf(PLAYER_CHARACTER4_BOMB_POWER));
		initializeButtonTextArea(playerCharacterInfo4);
		playerCharacterInfo4.setBounds(700, 250,180, 100);	

		this.add(playerCharacterInfo1);
		this.add(playerCharacterInfo2);
		this.add(playerCharacterInfo3);
		this.add(playerCharacterInfo4);
	}
	
	public void addJTextField() {
		
		p1Text = new JTextField("P1");
		initializeTextField(p1Text);
		p1Text.setBounds(20,400,50,50);
		this.add(p1Text);
		
		if (gameMode == PVP_MODE) {
			p2Text = new JTextField("P2");
			initializeTextField(p2Text);
			p2Text.setBounds(20,500,50,50);
			this.add(p2Text);
		}
	}
	
	public void initializeTextField(JTextField jtf) {

		Font textFieldFont = new Font("Times New Roman Italic", Font.BOLD, 20);
		jtf.setFont(textFieldFont);
		jtf.setBackground(Color.pink);
		jtf.setForeground(Color.white);
		jtf.setEditable(false);
		jtf.setBorder(null);
	
	}
	

	/**
	 * Initialize buttons
	 */
	public void initializeButton(JButton button) {

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

	/**
	 * Highlight the buttons when the mouse is on them
	 */
	public void highLightButton(JButton button) {
		button.setBackground(Color.ORANGE);
		button.setBounds(button.getX() - 20, button.getY() - 10, button.getWidth() + 40, button.getHeight() + 20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 20);
		button.setFont(buttonFont);
	}

	/**
	 * Reset the buttons when the mouse leaves
	 */
	public void resetButton(JButton button) {
		button.setBackground(Color.WHITE);
		button.setBounds(button.getX() + 20, button.getY() + 10, button.getWidth() - 40, button.getHeight() - 20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);
		button.setFont(buttonFont);
	}
	
	public void highLightChooseButton(JButton button) {
		button.setBackground(Color.PINK);
	}
	public void resetChooseButton(JButton button) {
		button.setBackground(Color.WHITE);

	}
	
	
	public void initializeButtonTextArea(JTextArea jta) {
		jta.setLineWrap(true);
		jta.setVisible(true);
		
		Font textAreaFont = new Font("Times New Roman Italic", Font.BOLD, 12);
		jta.setFont(textAreaFont);
		jta.setBackground(Color.pink);
		jta.setForeground(Color.WHITE);
		jta.setEditable(false);
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
			case "1":
				player1CID = 0;			
				highLightChooseButton(p1Button1);
				resetChooseButton(p1Button2);
				resetChooseButton(p1Button3);
				resetChooseButton(p1Button4);
				break;			
			case "2":		
				highLightChooseButton(p1Button2);
				resetChooseButton(p1Button1);
				resetChooseButton(p1Button3);
				resetChooseButton(p1Button4);
				player1CID = 1;		
				break;
			case "3":
				highLightChooseButton(p1Button3);
				resetChooseButton(p1Button1);
				resetChooseButton(p1Button2);
				resetChooseButton(p1Button4);
				player1CID = 2;
				break;
			case "4":
				highLightChooseButton(p1Button4);
				resetChooseButton(p1Button1);
				resetChooseButton(p1Button2);
				resetChooseButton(p1Button3);
				player1CID = 3;		
				break;				
			case "5":			
				highLightChooseButton(p2Button1);
				resetChooseButton(p2Button2);
				resetChooseButton(p2Button3);
				resetChooseButton(p2Button4);
				player2CID = 0;	
				break;		
			case "6":		
				highLightChooseButton(p2Button2);
				resetChooseButton(p2Button1);
				resetChooseButton(p2Button3);
				resetChooseButton(p2Button4);
				player2CID = 1;		
				break;
			case "7":
				highLightChooseButton(p2Button3);
				resetChooseButton(p2Button1);
				resetChooseButton(p2Button2);
				resetChooseButton(p2Button4);
				player2CID = 2;
				break;
			case "8":
				highLightChooseButton(p2Button4);
				resetChooseButton(p2Button1);
				resetChooseButton(p2Button2);
				resetChooseButton(p2Button3);
				player2CID = 3;
				break;
				
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
				highLightButton(buttonBack);
				break;
			case "Ok":
				highLightButton(buttonOk);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			thumbFrame.setVisible(false);
			thumbFrame.dispose();	// necessary when mouse move too quickly

			switch (this.name) {
			case "Back":
				resetButton(buttonBack);
				break;
			case "Ok":
				resetButton(buttonOk);
				break;
			}
		}
	}
}
