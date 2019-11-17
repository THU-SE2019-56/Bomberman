package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * <p>
 * The entry for project.
 * <p>
 * Provide main menu to select mode, setting, tutorial, etc.
 * 
 * @author Wang
 * @version 0.1
 */
public class MainMenu extends JFrame implements GameConstants{
	
	private static final long serialVersionUID = 1L;
	private JPanel mainMenu;
	private JButton buttonPve;
	private JButton buttonPvp;
	private JButton buttonExit;
	private JButton buttonHelp;
	private JButton buttonAbout;
	private ImageIcon menuIcon;
	private JLabel backgroundLabel;
	

	
	public MainMenu() {
	
		this.setTitle("Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
		this.pack();
		this.setResizable(false);

		menuIcon = new ImageIcon("image//menu//menuBackground.png");//Background image
		menuIcon.setImage(menuIcon.getImage().getScaledInstance(MENU_WIDTH, MENU_HEIGHT, 1));
			
		mainMenu = new JPanel();
		this.addButton();
		this.addBackground();
	
		this.add(mainMenu);
		this.setVisible(true);
		this.setFocusable(true);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setLocation((ge.getMaximumWindowBounds().width-MENU_WIDTH)/2,
				(ge.getMaximumWindowBounds().height-MENU_HEIGHT)/2);
	}

	public void addBackground() {
		backgroundLabel = new JLabel(menuIcon);
		backgroundLabel.setBounds(0, 0, MENU_WIDTH ,MENU_HEIGHT);
		mainMenu.add(backgroundLabel);
	}
	
	public void addButton() {
	
		/*
		 * PVE mode
		 */
		buttonPve = new JButton("PVE mode");
		buttonPve.setBounds(50, 150, 150, 50);
		initializeButton(buttonPve);
			
		/*
		 * PVP mode
		 */
		buttonPvp = new JButton("PVP mode");
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
		
		mainMenu.setLayout(null);
		mainMenu.add(buttonPve);
		mainMenu.add(buttonPvp);
		mainMenu.add(buttonExit);
		mainMenu.add(buttonHelp);
		mainMenu.add(buttonAbout);
		
		buttonPve.addMouseListener(new ButtonListener(this, "pve"));
		buttonPvp.addMouseListener(new ButtonListener(this, "pvp"));
		buttonExit.addMouseListener(new ButtonListener(this, "exit"));
		buttonHelp.addMouseListener(new ButtonListener(this, "help"));
		buttonAbout.addMouseListener(new ButtonListener(this, "about us"));
		
	}
	
	/*
	 * Initialize buttons
	 */
	public void initializeButton(JButton button) {
		
		Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		//This is the default border of WIN10 system.For macOS, use this border to make sure the buttons are correctly initialized.
		
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
		button.setBounds(button.getX()-20,button.getY()-10,button.getWidth()+40,button.getHeight()+20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 20);
		button.setFont(buttonFont);
	}
	
	/**
	 * Reset the buttons when the mouse leaves
	 */
	public void resetButton(JButton button) {
		button.setBackground(Color.WHITE);
		button.setBounds(button.getX()+20,button.getY()+10,button.getWidth()-40,button.getHeight()-20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);
		button.setFont(buttonFont);
	}
	
	/**
	 * Response to button events 
	 */
	class ButtonListener implements MouseListener{
		
		JFrame jframe;
		String name;

		public ButtonListener(JFrame jframe, String name) {
			this.jframe = jframe;
			this.name = name;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
				
			switch (this.name) {
			case "pve":
				
				Display dp1 = new Display(PVE_MODE);	
				jframe.remove(mainMenu);
				jframe.validate();// repaint		
				dp1.createPanel(jframe,PVE_MODE);	
				break;
			case "pvp":
				
				Display dp2 = new Display(PVP_MODE);	
				jframe.remove(mainMenu);
				jframe.validate();// repaint		
				dp2.createPanel(jframe,PVP_MODE);		
				break;
				
			case "exit":
				System.exit(0);//End game
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
			case "exit":
				highLightButton(buttonExit);
				break;
			case "help":
				highLightButton(buttonHelp);
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
			case "exit":
				resetButton(buttonExit);
				break;
			case "help":
				resetButton(buttonHelp);
				break;
			case "about us":
				resetButton(buttonAbout);
				break;
			}
			
		}
		
	}

	public static void main(String[] args) {
		new MainMenu();

	}



}
