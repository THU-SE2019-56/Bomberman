package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import game.GameConstants;
import map.MapEditor;

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
	
	private ImageIcon buttonPveOffIcon;
	private ImageIcon buttonPveOnIcon;
	private JLabel buttonPveLabel;
	
	private ImageIcon buttonPvpOffIcon;
	private ImageIcon buttonPvpOnIcon;
	private JLabel buttonPvpLabel;
	
	private ImageIcon buttonStageEditorOffIcon;
	private ImageIcon buttonStageEditorOnIcon;
	private JLabel buttonStageEditorLabel;
	
	private ImageIcon buttonHelpOffIcon;
	private ImageIcon buttonHelpOnIcon;
	private JLabel buttonHelpLabel;
	
	private ImageIcon buttonExitOffIcon;
	private ImageIcon buttonExitOnIcon;
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
		buttonPveOffIcon = new ImageIcon("image/buttons/pve_off.png");
		buttonPveOffIcon.setImage(buttonPveOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonPveOnIcon = new ImageIcon("image/buttons/pve_on.png");
		buttonPveOnIcon.setImage(buttonPveOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonPveLabel = new JLabel(buttonPveOffIcon);
		buttonPveLabel.setBounds(100, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonPveLabel);
		
		buttonPvpOffIcon = new ImageIcon("image/buttons/pvp_off.png");
		buttonPvpOffIcon.setImage(buttonPvpOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonPvpOnIcon = new ImageIcon("image/buttons/pvp_on.png");
		buttonPvpOnIcon.setImage(buttonPvpOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonPvpLabel = new JLabel(buttonPvpOffIcon);
		buttonPvpLabel.setBounds(100, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonPvpLabel);
		
		buttonStageEditorOffIcon = new ImageIcon("image/buttons/stageEditor_off.png");
		buttonStageEditorOffIcon.setImage(buttonStageEditorOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonStageEditorOnIcon = new ImageIcon("image/buttons/stageEditor_on.png");
		buttonStageEditorOnIcon.setImage(buttonStageEditorOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonStageEditorLabel = new JLabel(buttonStageEditorOffIcon);
		buttonStageEditorLabel.setBounds(100, 600, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonStageEditorLabel);
		
		buttonHelpOffIcon = new ImageIcon("image/buttons/help_off.png");
		buttonHelpOffIcon.setImage(buttonHelpOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonHelpOnIcon = new ImageIcon("image/buttons/help_on.png");
		buttonHelpOnIcon.setImage(buttonHelpOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonHelpLabel = new JLabel(buttonHelpOffIcon);
		buttonHelpLabel.setBounds(500, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonHelpLabel);
		
		buttonExitOffIcon = new ImageIcon("image/buttons/exit_off.png");
		buttonExitOffIcon.setImage(buttonExitOffIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonExitOnIcon = new ImageIcon("image/buttons/exit_on.png");
		buttonExitOnIcon.setImage(buttonExitOnIcon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, 1));
		
		buttonExitLabel = new JLabel(buttonExitOffIcon);
		buttonExitLabel.setBounds(500, 600, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.add(buttonExitLabel);
	}

	public void addBackground() {
		backgroundIcon = new ImageIcon("image/background/menuPanel.png");
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
				MapEditor mapEditor=new MapEditor();
				
				EditorPanel editorPanel = new EditorPanel(mainFrame,mapEditor);
				EditorButtonsPanel editorButtonsPanel = new EditorButtonsPanel(mainFrame,mapEditor);
				
				mainFrame.remove(MenuPanel.this);
				
				mainFrame.setContentPane(new JLabel(new ImageIcon("image/background/mapBackground.png")));
				mainFrame.validate();
				
				mainFrame.add(editorPanel);
				mainFrame.validate();// repaint

				mainFrame.add(editorButtonsPanel);
				mainFrame.validate();// repaint
				
				mainFrame.setLayout(null);

				editorPanel.setLocation(325, 33);
				editorPanel.setSize(MAP_WIDTH, MAP_HEIGHT);

				editorButtonsPanel.setLocation(38, 38);
				editorButtonsPanel.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);

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
			switch (this.name) {
			case "pve":
				buttonPveLabel.setIcon(buttonPveOnIcon);
				break;
			case "pvp":
				buttonPvpLabel.setIcon(buttonPvpOnIcon);
				break;
			case "stage editor":
				buttonStageEditorLabel.setIcon(buttonStageEditorOnIcon);
				break;
			case "help":
				buttonHelpLabel.setIcon(buttonHelpOnIcon);
				break;
			case "exit":
				buttonExitLabel.setIcon(buttonExitOnIcon);
				break;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			switch (this.name) {
			case "pve":
				buttonPveLabel.setIcon(buttonPveOffIcon);
				break;
			case "pvp":
				buttonPvpLabel.setIcon(buttonPvpOffIcon);
				break;
			case "stage editor":
				buttonStageEditorLabel.setIcon(buttonStageEditorOffIcon);
				break;
			case "help":
				buttonHelpLabel.setIcon(buttonHelpOffIcon);
				break;
			case "exit":
				buttonExitLabel.setIcon(buttonExitOffIcon);
				break;
			}
		}

	}

}
