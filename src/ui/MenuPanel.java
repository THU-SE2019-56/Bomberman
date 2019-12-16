package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;
import ui.WelcomePanel.ButtonListener;

/**
 * Main menu panel. Fill in the MainFrame. Contain buttons to jump to other
 * panels.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class MenuPanel extends JPanel implements GameConstants {
	private ImageIcon backgroundIcon;
	private JLabel backgroundLabel;
	private ImageIcon buttonPveIcon;
	private JLabel buttonPveLabel;
	private ImageIcon buttonPvpIcon;
	private JLabel buttonPvpLabel;
	private ImageIcon buttonStageEditorIcon;
	private JLabel buttonStageEditorLabel;
	private ImageIcon buttonHelpIcon;
	private JLabel buttonHelpLabel;
	private ImageIcon buttonExitIcon;
	private JLabel buttonExitLabel;

	public MenuPanel(MainFrame mainFrame) {		
		this.setLayout(null);
		this.addButton();
		this.addBackground();
		
		buttonPveLabel.addMouseListener(new ButtonListener(mainFrame,"pve"));
		buttonPvpLabel.addMouseListener(new ButtonListener(mainFrame,"pvp"));
		buttonStageEditorLabel.addMouseListener(new ButtonListener(mainFrame,"stage editor"));
		buttonHelpLabel.addMouseListener(new ButtonListener(mainFrame,"help"));
		buttonExitLabel.addMouseListener(new ButtonListener(mainFrame,"exit"));
	}

	public void addButton() {
		buttonPvpIcon = new ImageIcon("image/buttons/pvp.png");// Background image
		buttonPvpIcon.setImage(buttonPvpIcon.getImage().getScaledInstance(280, 100, 1));
		buttonPvpLabel = new JLabel(buttonPvpIcon);
		buttonPvpLabel.setBounds(100, 200, 280, 100);
		this.add(buttonPvpLabel);
		
		buttonPveIcon = new ImageIcon("image/buttons/pve.png");// Background image
		buttonPveIcon.setImage(buttonPveIcon.getImage().getScaledInstance(280, 100, 1));
		buttonPveLabel = new JLabel(buttonPveIcon);
		buttonPveLabel.setBounds(100, 400, 280, 100);
		this.add(buttonPveLabel);
		
		buttonStageEditorIcon = new ImageIcon("image/buttons/stageEditor.png");// Background image
		buttonStageEditorIcon.setImage(buttonStageEditorIcon.getImage().getScaledInstance(280, 100, 1));
		buttonStageEditorLabel = new JLabel(buttonStageEditorIcon);
		buttonStageEditorLabel.setBounds(100, 600, 280, 100);
		this.add(buttonStageEditorLabel);
		
		buttonHelpIcon = new ImageIcon("image/buttons/help.png");// Background image
		buttonHelpIcon.setImage(buttonHelpIcon.getImage().getScaledInstance(280, 100, 1));
		buttonHelpLabel = new JLabel(buttonHelpIcon);
		buttonHelpLabel.setBounds(500, 400, 280, 100);
		this.add(buttonHelpLabel);
		
		buttonExitIcon = new ImageIcon("image/buttons/exit.png");// Background image
		buttonExitIcon.setImage(buttonExitIcon.getImage().getScaledInstance(280, 100, 1));
		buttonExitLabel = new JLabel(buttonExitIcon);
		buttonExitLabel.setBounds(500, 600, 280, 100);
		this.add(buttonExitLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/menu/menuPanel.png");// Background image
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
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {

			ChoosePlayerPanel chooseplayer;
			switch (this.name) {
			case "pve":
				chooseplayer = new ChoosePlayerPanel(mainFrame, PVE_MODE);

				mainFrame.remove(MenuPanel.this);
				mainFrame.add(chooseplayer);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				chooseplayer.setLocation(0, 0);
				chooseplayer.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

				break;
			case "pvp":

				chooseplayer = new ChoosePlayerPanel(mainFrame, PVP_MODE);

				mainFrame.remove(MenuPanel.this);
				mainFrame.add(chooseplayer);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				chooseplayer.setLocation(0, 0);
				chooseplayer.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

				break;
			case "stage editor":
				EditorPanel editor = new EditorPanel(mainFrame);

				mainFrame.remove(MenuPanel.this);
				mainFrame.add(editor);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				editor.setLocation(0, 0);
				editor.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

				break;
			case "help":
				HelpPanel helpPanel = new HelpPanel(mainFrame);

				mainFrame.remove(MenuPanel.this);
				mainFrame.add(helpPanel);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				helpPanel.setLocation(0, 0);
				helpPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
				
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
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

	}

}
