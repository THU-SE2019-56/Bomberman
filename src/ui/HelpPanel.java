package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;

/**
 * Help Panel.
 * 
 * @author Wang
 * @version 1.0
 */
public class HelpPanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private ImageIcon buttonOffIcon;
	private ImageIcon buttonOnIcon;
	private JLabel buttonLabel;

	public HelpPanel(MainFrame mainFrame) {
		this.setLayout(null);
		this.addButton();
		this.addBackground();
		
		buttonLabel.addMouseListener(new ButtonListener(mainFrame));
	}

	public void addButton() {
		buttonOffIcon = new ImageIcon("image/buttons/back_off.png");
		buttonOffIcon.setImage(buttonOffIcon.getImage().getScaledInstance((int) (0.7*BUTTON_WIDTH), (int) (0.7*BUTTON_HEIGHT), 1));

		buttonOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonOnIcon.setImage(buttonOnIcon.getImage().getScaledInstance((int) (0.7*BUTTON_WIDTH), (int) (0.7*BUTTON_HEIGHT), 1));

		buttonLabel = new JLabel(buttonOffIcon);
		buttonLabel.setBounds(WINDOW_WIDTH - 195, WINDOW_HEIGHT-71, (int) (0.7*BUTTON_WIDTH), (int) (0.7*BUTTON_HEIGHT));
		this.add(buttonLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/background/helpPanel.png");// Background image
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
			MenuPanel newMenuPanel = new MenuPanel(mainFrame);

			JPanel mainPanel = (JPanel) mainFrame.getContentPane();
			mainPanel.removeAll();

			mainFrame.add(newMenuPanel);
			mainFrame.validate();

			mainFrame.setLayout(null);

			newMenuPanel.setLocation(0, 0);
			newMenuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
