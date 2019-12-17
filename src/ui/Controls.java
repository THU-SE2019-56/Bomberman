package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Controls {
	
	public Controls() {
		
	}
	
	public void initializeButton(JButton button,int x,int y,int width,int height) {
		
		button.setBounds(x, y, width, height);
		
		Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		// This is the default border of WIN10 system.For macOS, use this border to make
		// sure the buttons are correctly initialized.

		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);

		button.setForeground(Color.BLACK);
		button.setBorder(originBorder);
		button.setBackground(Color.WHITE);
		button.setFont(buttonFont);
		button.setOpaque(true);

	}
	
	/**
	 * Highlight the buttons when the mouse is on them
	 */
	public void highLightButton(JButton button) {
		button.setBackground(Color.ORANGE);
		button.setBounds(button.getX() - 20, button.getY() - 10, button.getWidth() + 40, button.getHeight() + 20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 20);
		button.setFont(buttonFont);
	}
	
	/**
	 * Reset the buttons when the mouse leaves
	 */
	public void resetButton(JButton button) {

		button.setBackground(Color.WHITE);
		button.setBounds(button.getX() + 20, button.getY() + 10, button.getWidth() - 40, button.getHeight() - 20);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);
		button.setFont(buttonFont);

	}
	
	/**
	 * Highlight one button and reset the others 
	 */
	public void chooseButtonFrom(JButton chosenButton,JButton b1, JButton b2,JButton b3 ) {
		 chosenButton.setBackground(Color.PINK);
		 b1.setBackground(Color.white);
		 b2.setBackground(Color.white);
		 b3.setBackground(Color.white);
	}
	
	/**
	 * Initialize text area
	 */
	public void initializeTextArea(JTextArea jta,int x, int y, int width, int height) {
		
		
		jta.setLineWrap(true);
		jta.setVisible(true);
		
		jta.setBounds(x, y, width, height);
		
		Font textAreaFont = new Font("Times New Roman Italic", Font.ITALIC, 14);
		jta.setFont(textAreaFont);
		jta.setBackground(Color.pink);
		jta.setForeground(Color.WHITE);
		jta.setEditable(false);
	}
	
	/**
	 * 
	 *Initialize text field
	 */
	public void initializeTextField(JTextField jtf,int x,int y,int width,int height) {

		
		jtf.setBounds(x, y, width, height);
		
		Font textFieldFont = new Font("Times New Roman Italic", Font.BOLD, 14);
		jtf.setFont(textFieldFont);
		jtf.setBackground(null);
		//jtf.setForeground(Color.white);
		jtf.setEditable(false);
		jtf.setBorder(null);
	
	}

}
