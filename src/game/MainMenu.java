package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JPanel mainMenu;
	private JButton buttonPve;
	private JButton buttonPvp;
	private JButton buttonExit;
	private JButton buttonHelp;
	private JButton buttonAbout;
	private ImageIcon menuIcon;
	private JLabel backgroundLabel;
	

	
	public MainMenu() {
		this.setBounds(50, 50, 930, 670);
		this.setTitle("Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		menuIcon = new ImageIcon("image//menu//menuBackground.png");
		menuIcon.setImage(menuIcon.getImage().getScaledInstance(930, 670, 1));
	
		
		mainMenu = new JPanel();
		this.addButton();
		this.addBackground();
	
		this.add(mainMenu);
		this.setVisible(true);
		this.setFocusable(true);
	}

	
	public void addButton() {
		
		buttonPve = new JButton("PVE mode");
		buttonPve.setBounds(50, 150, 150, 50);
//		buttonPve.setBackground(Color.BLUE);
//		buttonPve.setOpaque(true);
//		buttonPve.setBorderPainted(false);
//		buttonPve.setForeground(Color.white);
//		buttonPve.setFocusPainted(true);
//		Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
//		buttonPve.setForeground(Color.WHITE);
//		buttonPve.setBorder(originBorder);
//		buttonPve.setBackground(Color.blue);
//		buttonPve.setOpaque(true);
		
		buttonPvp = new JButton("PVP mode");
		buttonPvp.setBounds(50, 250, 150, 50);
		
		buttonHelp = new JButton("Help");
		buttonHelp.setBounds(50, 350, 150, 50);
		
		
		buttonExit = new JButton("Exit");
		buttonExit.setBounds(700, 200, 150, 50);
		
		
		
		buttonAbout = new JButton("About us");
		buttonAbout.setBounds(700, 300, 150, 50);
		
		mainMenu.setLayout(null);
		mainMenu.add(buttonPve);
		mainMenu.add(buttonPvp);
		mainMenu.add(buttonExit);
		mainMenu.add(buttonHelp);
		mainMenu.add(buttonAbout);

		buttonPve.addActionListener(new ButtonListener(this, "pve"));
		buttonPvp.addActionListener(new ButtonListener(this, "pvp"));
		buttonExit.addActionListener(new ButtonListener(this, "exit"));
		//TODO Add buttons, combine them to listener, make them nice-looking
		
	}
	
	
	public void addBackground() {
		backgroundLabel = new JLabel(menuIcon);
		backgroundLabel.setBounds(0, 0, 930 ,670);
		
		mainMenu.add(backgroundLabel);
	}

	class ButtonListener implements ActionListener {
		JFrame jframe;
		String name;

		public ButtonListener(JFrame jframe, String name) {
			this.jframe = jframe;
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
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
				System.exit(0);
				break;
				
			}
		}
	}

	public static void main(String[] args) {
		new MainMenu();

	}

}
