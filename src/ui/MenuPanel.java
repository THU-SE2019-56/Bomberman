package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import game.Game;
import game.GameConstants;
import game.TimerListener;

/**
 * Main menu panel. Fill in the MainFrame. Contain buttons to jump to other
 * panels.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class MenuPanel extends JPanel implements GameConstants {
	private MainFrame mainFrame;
	private JButton buttonPve;
	private JButton buttonPvp;
	private JButton buttonExit;
	private JButton buttonHelp;
	private JButton buttonAbout;
	private ImageIcon menuBackgroundIcon;
	private JLabel menuBackgroundLabel;
	
	

	public MenuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
         
	
		this.addButton();
		this.addBackground();
	}

	public void addBackground() {
		menuBackgroundIcon = new ImageIcon("image/menu/menuBackground.png");// Background image
		menuBackgroundIcon.setImage(menuBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		menuBackgroundLabel = new JLabel(menuBackgroundIcon);
		menuBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(menuBackgroundLabel);
	}

	public void addButton() {
		/*
		 * PVE mode
		 */
		
		buttonPve = new JButton("PVE Mode");
		buttonPve.setBounds(50, 150, 150, 50);
		initializeButton(buttonPve);

		/*
		 * PVP mode
		 */

		buttonPvp = new JButton("PVP Mode");
		buttonPvp.setBounds(50, 250, 150, 50);
		initializeButton(buttonPvp);

		/*
		 * Help
		 */

		buttonHelp = new JButton("Help");
		buttonHelp.setBounds(50, 350, 150, 50);
		initializeButton(buttonHelp);

		/*
		 * Exit
		 */
		
		buttonExit = new JButton("Exit");
		buttonExit.setBounds(700, 200, 150, 50);
		initializeButton(buttonExit);

		/*
		 * About us
		 */
	
		buttonAbout = new JButton("About us");
		buttonAbout.setBounds(700, 300, 150, 50);
		initializeButton(buttonAbout);

		this.setLayout(null);

		this.add(buttonPve);
		this.add(buttonPvp);
		this.add(buttonExit);
		this.add(buttonHelp);
		this.add(buttonAbout);

		buttonPve.addMouseListener(new ButtonListener(mainFrame, "pve"));
		buttonPvp.addMouseListener(new ButtonListener(mainFrame, "pvp"));
		buttonExit.addMouseListener(new ButtonListener(mainFrame, "exit"));
		buttonHelp.addMouseListener(new ButtonListener(mainFrame, "help"));
		buttonAbout.addMouseListener(new ButtonListener(mainFrame, "about us"));

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
			case "pve":
				StagePanel stagePanel=new StagePanel(mainFrame);
				
				mainFrame.remove(MenuPanel.this);
				mainFrame.add(stagePanel);
				mainFrame.validate();// repaint
				
				mainFrame.setLayout(null);

				stagePanel.setLocation(0, 0);
				stagePanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "pvp":
				Game gamePvp = new Game(PVP_MODE);
				MapPanel mapPanelPvp = new MapPanel(gamePvp);
				StatusPanel statusPanelPvp = new StatusPanel(gamePvp, mainFrame);

				mainFrame.remove(MenuPanel.this);

				mainFrame.add(mapPanelPvp);
				mainFrame.validate();// repaint

				mainFrame.add(statusPanelPvp);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				mapPanelPvp.setLocation(0, 0);
				mapPanelPvp.setSize(MAP_WIDTH, MAP_HEIGHT);

				statusPanelPvp.setLocation(MAP_WIDTH, 0);
				statusPanelPvp.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);

				TimerListener timerListenerPvp = new TimerListener(gamePvp, mapPanelPvp, statusPanelPvp);
				Timer timerPvp = new Timer(REFRESH, timerListenerPvp);
				timerPvp.start();
				break;
			case "exit":
				System.exit(0);// End game
				break;
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "pve":
				highLightButton(buttonPve);
				break;
			case "pvp":
				highLightButton(buttonPvp);
				break;
			case "help":
				highLightButton(buttonHelp);
				break;
			case "exit":
				highLightButton(buttonExit);
				break;	
			case "about us":
				highLightButton(buttonAbout);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "pve":
				resetButton(buttonPve);
				break;
			case "pvp":
				resetButton(buttonPvp);
				break;
			case "help":
				resetButton(buttonHelp);
				break;
			case "exit":
				resetButton(buttonExit);
				break;

			case "about us":
				resetButton(buttonAbout);
				break;
			}
		}
		
		
	}
	
}
