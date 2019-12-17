package ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;

/**
 * Map Background Panel.
 * 
 * @author Wang
 * @version 1.0
 */
public class MapBackgroundPanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;

	public MapBackgroundPanel(MainFrame mainFrame) {
		this.setLayout(null);
		this.addBackground();
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/background/mapBackground.png");// Background image
		backgroundIcon.setImage(backgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		this.add(backgroundLabel);
	}
}
