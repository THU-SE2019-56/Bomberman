package game;

import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * The entry for project.
 * <p>
 * Provide main menu to select mode, setting, tutorial, etc.
 * 
 * @author Wang
 * @version 0.1
 */
public class MainMenu extends JFrame {
	private JPanel mainMenu;
	private JButton button_pve;
	private JButton button_pvp;
	private JButton button_exit;

	public MainMenu() {
		this.setBounds(50, 50, 750, 670);
		this.setTitle("Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainMenu = new JPanel();
		button_pve = new JButton("PVE mode");
		button_pvp = new JButton("PVP mode");
		button_exit = new JButton("Exit");
		mainMenu.add(button_pve);
		mainMenu.add(button_pvp);
		mainMenu.add(button_exit);

		button_pve.addActionListener(new ButtonListener(this, "pve"));
		//TODO Add buttons, combine them to listener, make them nice-looking
		
		this.add(mainMenu);
		this.setVisible(true);
		this.setFocusable(true);
	}

	class ButtonListener implements ActionListener {
		JFrame jframe;
		String name;

		public ButtonListener(JFrame jframe, String name) {
			this.jframe = jframe;
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (this.name) {
			case "pve":
				jframe.remove(mainMenu);
				jframe.add(new JPanel());// TODO Redirect to pve map
				jframe.validate();// repaint
			case "pvp":
				jframe.remove(mainMenu);
				jframe.add(new JPanel());// TODO Redirect to pvp map
				jframe.validate();// repaint
			case "exit":
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		new MainMenu();

	}

}
