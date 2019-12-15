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
 * @version 0.9
 */
public class HelpPanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private ImageIcon buttonIcon;
	private JLabel buttonLabel;

	public HelpPanel(MainFrame mainFrame) {
		this.setLayout(null);
		this.addButton();
		this.addBackground();
		
		buttonLabel.addMouseListener(new ButtonListener(mainFrame));
	}

	public void addButton() {
		buttonIcon = new ImageIcon("image/buttons/back.png");// Background image
		buttonIcon.setImage(buttonIcon.getImage().getScaledInstance(280, 113, 1));
		buttonLabel = new JLabel(buttonIcon);
		buttonLabel.setBounds((int) (0.8*WINDOW_WIDTH), (int) (0.9*WINDOW_HEIGHT), 140, 55);
		this.add(buttonLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/menu/helpPanel.png");// Background image
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

		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}
