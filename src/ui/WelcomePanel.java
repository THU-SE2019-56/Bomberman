package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;

/**
 * Welcome Panel.
 * 
 * @author Wang
 * @version 1.0
 */
public class WelcomePanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private ImageIcon buttonOffIcon;
	private ImageIcon buttonOnIcon;
	private JLabel buttonLabel;

	public WelcomePanel(MainFrame mainFrame) {
		this.setLayout(null);
		this.addButton();
		this.addBackground();

		buttonLabel.addMouseListener(new ButtonListener(mainFrame));
	}

	public void addButton() {
		buttonOffIcon = new ImageIcon("image/buttons/startGame_off.png");
		buttonOffIcon.setImage(buttonOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonOnIcon = new ImageIcon("image/buttons/startGame_on.png");
		buttonOnIcon.setImage(buttonOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonLabel = new JLabel(buttonOffIcon);
		buttonLabel.setBounds(WINDOW_WIDTH / 2 - BUTTON_WIDTH/2, 510, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/background/welcomePanel.png");// Background image
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
			buttonLabel.setIcon(buttonOnIcon);
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
			buttonLabel.setIcon(buttonOnIcon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			buttonLabel.setIcon(buttonOffIcon);
		}

	}

}
