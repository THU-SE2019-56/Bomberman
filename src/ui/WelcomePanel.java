package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;
import ui.MenuPanel.ButtonListener;

/**
 * Welcome Panel.
 * 
 * @author Wang
 * @version 0.9
 */
public class WelcomePanel extends JPanel implements GameConstants {
	private MainFrame mainFrame;
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private ImageIcon buttonIcon;
	private JLabel buttonLabel;

	public WelcomePanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setLayout(null);
		this.addButton();
		this.addBackground();
		
		
		buttonLabel.addMouseListener(new ButtonListener(mainFrame));
	}

	public void addButton() {
		buttonIcon = new ImageIcon("image/buttons/StartGame.png");// Background image
		buttonIcon.setImage(buttonIcon.getImage().getScaledInstance(280, 113, 1));
		buttonLabel = new JLabel(buttonIcon);
		buttonLabel.setBounds(WINDOW_WIDTH/2-140, 510, 280, 113);
		this.add(buttonLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/menu/welcomePanel.png");// Background image
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(backgroundLabel);
	}

	/**
	 * Respond to button events
	 */
	class ButtonListener implements MouseListener {

		MainFrame mainFrame;

		public ButtonListener(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			MenuPanel menuPanel = new MenuPanel(mainFrame);

			mainFrame.remove(WelcomePanel.this);
			mainFrame.add(menuPanel);
			mainFrame.validate();// repaint

			mainFrame.setLayout(null);

			menuPanel.setLocation(0, 0);
			menuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}
