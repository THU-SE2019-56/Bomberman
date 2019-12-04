package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameConstants;

/**
 * Main menu panel. Fill in the MainFrame. Contain buttons to jump to other
 * panels.
 * 
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class MenuPanel extends JPanel implements GameConstants {
	private MainFrame mainFrame;
	private JButton buttonPve;
	private JButton buttonPvp;
	private JButton buttonStageEditor;
	private JButton buttonHelp;
	private JButton buttonExit;
	private JButton buttonAbout;
	private ImageIcon menuBackgroundIcon;
	private JLabel menuBackgroundLabel;
	
	private Controls control;

	public MenuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		control = new Controls();

		this.addButton();
		this.addBackground();
		
	}

	public void addBackground() {
		menuBackgroundIcon = new ImageIcon("image/menu/menuBackground.png");// Background image
		menuBackgroundIcon.setImage(menuBackgroundIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, 1));
		menuBackgroundLabel = new JLabel(menuBackgroundIcon);
		menuBackgroundLabel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(menuBackgroundLabel);
	}

	public void addButton() {
		
		buttonPve = new JButton("PVE Mode");
		buttonPvp = new JButton("PVP Mode");
		buttonHelp = new JButton("Help");
		buttonStageEditor = new JButton("StageEditor");
		buttonExit = new JButton("Exit");
		buttonAbout = new JButton("About us");

		control.initializeButton(buttonPve,50, 150, 150, 50);//PVE Mode
		control.initializeButton(buttonPvp,50, 250, 150, 50);//PVP mode
		control.initializeButton(buttonHelp,50, 350, 150, 50);//Help
		control.initializeButton(buttonStageEditor,50, 450, 150, 50);//Stage Editor
		control.initializeButton(buttonExit,700, 200, 150, 50);//Exit;
		control.initializeButton(buttonAbout,700, 300, 150, 50);//About us

		this.setLayout(null);

		this.add(buttonPve);
		this.add(buttonPvp);
		this.add(buttonHelp);
		this.add(buttonStageEditor);
		this.add(buttonExit);
		this.add(buttonAbout);

		buttonPve.addMouseListener(new ButtonListener(mainFrame, "pve"));
		buttonPvp.addMouseListener(new ButtonListener(mainFrame, "pvp"));
		buttonHelp.addMouseListener(new ButtonListener(mainFrame, "help"));
		buttonStageEditor.addMouseListener(new ButtonListener(mainFrame, "stage editor"));
		buttonExit.addMouseListener(new ButtonListener(mainFrame, "exit"));
		buttonAbout.addMouseListener(new ButtonListener(mainFrame, "about us"));

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
			case "pve":
				control.highLightButton(buttonPve);
				break;
			case "pvp":
				control.highLightButton(buttonPvp);
				break;
			case "help":
				control.highLightButton(buttonHelp);
				break;
			case "stage editor":
				control.highLightButton(buttonStageEditor);
				break;
			case "exit":
				control.highLightButton(buttonExit);
				break;
			case "about us":
				control.highLightButton(buttonAbout);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "pve":
				control.resetButton(buttonPve);
				break;
			case "pvp":
				control.resetButton(buttonPvp);
				break;
			case "help":
				control.resetButton(buttonHelp);
				break;
			case "stage editor":
				control.resetButton(buttonStageEditor);
				break;
			case "exit":
				control.resetButton(buttonExit);
				break;
			case "about us":
				control.resetButton(buttonAbout);
				break;
			}
		}

	}

}
