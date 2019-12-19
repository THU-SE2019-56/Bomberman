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
	private MainFrame mainFrame;
	private ImageIcon backgroundHelpIcon;
	private ImageIcon backgroundMonsterIcon;
	private JLabel backgroundLabel;

	private ImageIcon buttonBackOffIcon;
	private ImageIcon buttonBackOnIcon;
	private JLabel buttonBackLabel;

	private ImageIcon buttonForwardOffIcon;
	private ImageIcon buttonForwardOnIcon;
	private JLabel buttonForwardLabel;

	private ImageIcon buttonBackwardOffIcon;
	private ImageIcon buttonBackwardOnIcon;
	private JLabel buttonBackwardLabel;
	
	private int whichLabel=0;//0 help,1 monster

	public HelpPanel(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		
		this.setLayout(null);
		this.addButton();
		this.addBackground();
	}

	public void addButton() {
		buttonBackOffIcon = new ImageIcon("image/buttons/back_off.png");
		buttonBackOffIcon
				.setImage(buttonBackOffIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackOnIcon = new ImageIcon("image/buttons/back_on.png");
		buttonBackOnIcon
				.setImage(buttonBackOnIcon.getImage().getScaledInstance(SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT, 1));

		buttonBackLabel = new JLabel(buttonBackOffIcon);
		buttonBackLabel.setBounds(WINDOW_WIDTH - 195, WINDOW_HEIGHT - 71, SCALED_BUTTON_WIDTH, SCALED_BUTTON_HEIGHT);
		this.add(buttonBackLabel);

		// forward
		buttonForwardOffIcon = new ImageIcon("image/buttons/forward_off.png");
		buttonForwardOffIcon.setImage(buttonForwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardOnIcon = new ImageIcon("image/buttons/forward_on.png");
		buttonForwardOnIcon.setImage(buttonForwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonForwardLabel = new JLabel(buttonForwardOffIcon);
		buttonForwardLabel.setBounds(985, WINDOW_HEIGHT/2-ARROW_HEIGHT/2, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonForwardLabel);

		// backward
		buttonBackwardOffIcon = new ImageIcon("image/buttons/backward_off.png");
		buttonBackwardOffIcon
				.setImage(buttonBackwardOffIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardOnIcon = new ImageIcon("image/buttons/backward_on.png");
		buttonBackwardOnIcon.setImage(buttonBackwardOnIcon.getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT, 1));

		buttonBackwardLabel = new JLabel(buttonBackwardOffIcon);
		buttonBackwardLabel.setBounds(0, WINDOW_HEIGHT/2-ARROW_HEIGHT/2, ARROW_WIDTH, ARROW_HEIGHT);
		this.add(buttonBackwardLabel);
		
		buttonBackLabel.addMouseListener(new ButtonListener(mainFrame, "back"));
		buttonForwardLabel.addMouseListener(new ButtonListener(mainFrame, "forward"));
		buttonBackwardLabel.addMouseListener(new ButtonListener(mainFrame, "backward"));
	}

	public void addBackground() {
		backgroundHelpIcon = new ImageIcon("image/background/helpPanel.png");// Background image
		backgroundHelpIcon.setImage(backgroundHelpIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		backgroundMonsterIcon = new ImageIcon("image/background/monsterPanel.png");// Background image
		backgroundMonsterIcon
				.setImage(backgroundMonsterIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));

		backgroundLabel = new JLabel(backgroundHelpIcon);
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
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (this.name) {
			case "back":
				MenuPanel newMenuPanel = new MenuPanel(mainFrame);

				if (mainFrame.getContentPane() instanceof JPanel) {
					JPanel mainPanel = (JPanel) mainFrame.getContentPane();
					mainPanel.removeAll();
				} else {
					JLabel mainPanel = (JLabel) mainFrame.getContentPane();
					mainPanel.removeAll();
				}

				mainFrame.add(newMenuPanel);
				mainFrame.validate();

				mainFrame.setLayout(null);

				newMenuPanel.setLocation(0, 0);
				newMenuPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				break;
			case "forward":
				if(whichLabel==0) {
					backgroundLabel.setIcon(backgroundMonsterIcon);
					whichLabel=1;
				}else {
					backgroundLabel.setIcon(backgroundHelpIcon);
					whichLabel=0;
				}
				break;
			case "backward":
				if(whichLabel==0) {
					backgroundLabel.setIcon(backgroundMonsterIcon);
					whichLabel=1;
				}else {
					backgroundLabel.setIcon(backgroundHelpIcon);
					whichLabel=0;
				}
				break;
			}
			
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			switch (this.name) {
			case "back":
				buttonBackLabel.setIcon(buttonBackOnIcon);
				break;
			case "forward":
				buttonForwardLabel.setIcon(buttonForwardOnIcon);
				break;
			case "backward":
				buttonBackwardLabel.setIcon(buttonBackwardOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "back":
				buttonBackLabel.setIcon(buttonBackOffIcon);
				break;
			case "forward":
				buttonForwardLabel.setIcon(buttonForwardOffIcon);
				break;
			case "backward":
				buttonBackwardLabel.setIcon(buttonBackwardOffIcon);
				break;
			}
		}

	}

}
