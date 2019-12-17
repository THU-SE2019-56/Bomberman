package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;

/**
 * GameOver Panel.
 * 
 * @author Wang
 * @version 1.0
 */
public class GameOverPanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private String endMessage;

	private ImageIcon buttonRestartOffIcon;
	private ImageIcon buttonRestartOnIcon;
	private JLabel buttonRestartLabel;

	private ImageIcon buttonExitOffIcon;
	private ImageIcon buttonExitOnIcon;
	private JLabel buttonExitLabel;

	public GameOverPanel(MainFrame mainFrame, String endMessage) {
		this.endMessage = endMessage;

		this.setLayout(null);
		this.addButton();
		this.addBackground();

		buttonRestartLabel.addMouseListener(new ButtonListener(mainFrame, "restart"));
		buttonExitLabel.addMouseListener(new ButtonListener(mainFrame, "exit"));
	}

	public void addButton() {
		buttonRestartOffIcon = new ImageIcon("image/buttons/restart_off.png");
		buttonRestartOffIcon
				.setImage(buttonRestartOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonRestartOnIcon = new ImageIcon("image/buttons/restart_on.png");
		buttonRestartOnIcon.setImage(buttonRestartOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonRestartLabel = new JLabel(buttonRestartOffIcon);
		buttonRestartLabel.setBounds(WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2, 310, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonRestartLabel);

		buttonExitOffIcon = new ImageIcon("image/buttons/exit_off.png");
		buttonExitOffIcon.setImage(buttonExitOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonExitOnIcon = new ImageIcon("image/buttons/exit_on.png");
		buttonExitOnIcon.setImage(buttonExitOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));

		buttonExitLabel = new JLabel(buttonExitOffIcon);
		buttonExitLabel.setBounds(WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2, 410, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonExitLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/background/" + endMessage + ".png");// Background image
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
		String name;

		public ButtonListener(MainFrame mainFrame, String name) {
			this.mainFrame = mainFrame;
			this.name = name;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

			switch (this.name) {

			case "restart":
				MenuPanel menuPanel = new MenuPanel(mainFrame);

				mainFrame.remove(GameOverPanel.this);
				mainFrame.add(menuPanel);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				menuPanel.setLocation(0, 0);
				menuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
			case "restart":
				buttonRestartLabel.setIcon(buttonRestartOnIcon);
				break;
			case "exit":
				buttonExitLabel.setIcon(buttonExitOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "restart":
				buttonRestartLabel.setIcon(buttonRestartOffIcon);
				break;
			case "exit":
				buttonExitLabel.setIcon(buttonExitOffIcon);
				break;
			}
		}
	}
}