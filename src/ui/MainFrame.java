package ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

import game.GameConstants;

/**
 * Create a frame that hold all panels.
 * 
 * @author Wang
 * @version 0.9
 */
public class MainFrame extends JFrame implements GameConstants {
	/**
	 * Initialize a JFrame and add the menuPanel into it.
	 */
	public MainFrame() {
		this.setTitle("Bomberman");
		//this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.pack();
		this.setFocusable(true);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setLocation((ge.getMaximumWindowBounds().width - WINDOW_WIDTH) / 2,
				(ge.getMaximumWindowBounds().height - WINDOW_HEIGHT) / 2);

		WelcomePanel welcomePanel = new WelcomePanel(this);
		this.add(welcomePanel);

		this.setVisible(true);
		this.setResizable(false);
	}
}
