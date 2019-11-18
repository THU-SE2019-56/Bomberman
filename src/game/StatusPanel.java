package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import game.MainMenu.ButtonListener;

/**
 * Create a panel on the right side of mapManel. Related to mapPanel.
 * 
 * @author Wang
 * @version 0.9
 */
public class StatusPanel extends JPanel implements MouseListener, GameConstants{

	private static final long serialVersionUID = 1L;
	private JTextField playerLifeText[] = new JTextField[2];
	private JButton pauseButton = new JButton("Pause");
	private JButton backButton = new JButton("Back");
	private JButton restartButton = new JButton("Restart");
	
	private MapPanel mp;
	private JFrame thisJFrame;
	
	private Timer timer;
	
	
	public StatusPanel(MapPanel mp, JFrame jf) {
		this.mp = mp;
		this.thisJFrame = jf;
		
		this.setLayout(null);

		for (int i = 0; i < mp.getPlayerNum(); i++) {
			
			if (i == PLAYER_ID_P1) {
				this.playerLifeText[i] = new JTextField("");
				this.playerLifeText[i].setBounds(4 * CELL_WIDTH, 3 * CELL_HEIGHT, 4 * CELL_WIDTH,
						CELL_HEIGHT / 2);
			}
			if (i == PLAYER_ID_P2) {
				this.playerLifeText[i] = new JTextField("");
				this.playerLifeText[i].setBounds(4 * CELL_WIDTH, 6 * CELL_HEIGHT, 4 * CELL_WIDTH,
						CELL_HEIGHT/ 2);
			}
			this.playerLifeText[i].setEditable(false);
			this.playerLifeText[i].setForeground(Color.BLUE);
			this.playerLifeText[i].setBorder(null);
			Font textFont = new Font("Times New Roman Italic", Font.BOLD, 14);
			this.playerLifeText[i].setFont(textFont);
			this.playerLifeText[i].setVisible(true);
			this.playerLifeText[i].setBackground(null);

			this.add(this.playerLifeText[i]);
		}

		//Pause
		this.pauseButton.setBounds( CELL_WIDTH, 8 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);
		initializeButton(pauseButton);
	
		//Back to main menu
		this.backButton.setBounds( CELL_WIDTH, 10 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);
		initializeButton(backButton);
	
		//Restart game
		this.restartButton.setBounds( CELL_WIDTH, 12* CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);
		initializeButton(restartButton);

	
	}
	
	
	public void initializeButton(JButton button) {

		Border originBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		// This is the default border of WIN10 system.For macOS, use this border to make sure the buttons are correctly initialized.
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);

		button.setForeground(Color.BLACK);
		button.setBorder(originBorder);
		button.setBackground(Color.WHITE);
		button.setFont(buttonFont);
		button.setOpaque(true);
		button.setVisible(true);
		button.addMouseListener(this);
		this.add(button);

	}
	
	/**
	 * Highlight the buttons when the mouse is on them
	 */
	public void highlightButton(JButton button) {
		button.setBackground(Color.CYAN);
		button.setBounds(button.getX() - CELL_WIDTH/2, button.getY() - CELL_HEIGHT/4, button.getWidth() + CELL_WIDTH, button.getHeight() + CELL_HEIGHT/2);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 20);
		button.setFont(buttonFont);
	}

	/**
	 * Reset the buttons when the mouse leaves
	 */
	public void resetButton(JButton button) {
		button.setBackground(Color.WHITE);
		button.setBounds(button.getX() + CELL_WIDTH/2, button.getY() + CELL_WIDTH/4, button.getWidth() - CELL_WIDTH, button.getHeight() - CELL_HEIGHT/2);
		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 14);
		button.setFont(buttonFont);
	}
	
	public void paintComponent(Graphics g) {
		 
		super.paintComponent(g);

		for (int i = 0; i < mp.getPlayerNum(); i++) {
			int playerHP = mp.getPlayer()[i].getHP();
			if (i == PLAYER_ID_P1) {
				this.playerLifeText[i].setText(String.valueOf(playerHP));
				g.setColor(Color.BLUE);
				g.drawRect(CELL_WIDTH, 3 * CELL_HEIGHT, 100, CELL_HEIGHT / 2);
				g.setColor(Color.getHSBColor((float) playerHP / 300, 1, 1));
				g.fillRect( CELL_WIDTH, 3 * CELL_HEIGHT, mp.getPlayer()[i].getHP(),
						CELL_HEIGHT / 2);

				g.drawImage(mp.player1Image[DIRECTION_DOWN],CELL_WIDTH, CELL_HEIGHT,
						CELL_WIDTH + CELL_WIDTH/ 2, CELL_HEIGHT + CELL_HEIGHT / 2, this);
			}

			if (i == PLAYER_ID_P2) {
				this.playerLifeText[i].setText(String.valueOf(playerHP));
				g.setColor(Color.BLUE);
				g.drawRect( CELL_WIDTH, 6 * CELL_HEIGHT, 100,CELL_HEIGHT / 2);
				g.setColor(Color.getHSBColor((float) playerHP / 300, 1, 1));
				g.fillRect(CELL_WIDTH, 6 * CELL_SIZE, mp.getPlayer()[i].getHP(),
						CELL_HEIGHT/ 2);

				g.drawImage(mp.player2Image[DIRECTION_DOWN], CELL_WIDTH, 4 * CELL_HEIGHT,
						CELL_WIDTH + CELL_WIDTH/ 2, CELL_HEIGHT+ CELL_HEIGHT/ 2, this);
			}
		}
	}



	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		/*
		 * Pause
		 */
		if (e.getSource() == this.pauseButton) {
			if (mp.getPauseFlag() == 0) {
				mp.setPauseFlag(1);
				this.pauseButton.setText("Start");
			} else {
				mp.setPauseFlag(0);
				this.pauseButton.setText("Pause");
			}
		}
		
		/*
		 * Back
		 */
		if (e.getSource()==this.backButton) {
		    this.thisJFrame.dispose();
		    new MainMenu();
		}
		
		/*
		 * Restart
		 */
		if (e.getSource()==this.restartButton) {
			
			MapPanel newMp = new MapPanel(this.mp.getMode());
			StatusPanel newSp=new StatusPanel(newMp,thisJFrame);
			
			thisJFrame.remove(this);//Remove current StatusPanel
			thisJFrame.remove(this.mp);//Remove current MapPanel
			
			thisJFrame.add(newMp);
			thisJFrame.validate();// repaint
			thisJFrame.add(newSp);
			thisJFrame.validate();// repaint
			
			thisJFrame.setLayout(null);
			
			newMp.setLocation(0,0);
			newMp.setSize(CELL_NUM_X*CELL_WIDTH, CELL_NUM_Y*CELL_HEIGHT);
			
			newSp.setLocation(CELL_NUM_X*CELL_WIDTH, 0);
			newSp.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);
			
			PanelListener newPanelListener=new PanelListener(newMp,newSp);
			Timer newTimer = new Timer(REFRESH, newPanelListener);
			newTimer.start();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		if (e.getSource() == this.pauseButton) highlightButton(pauseButton);
		else if (e.getSource() == this.backButton) highlightButton(backButton);
		else if (e.getSource() == this.restartButton) highlightButton(restartButton);

	}

	@Override
	public void mouseExited(MouseEvent e) {

		if (e.getSource() == this.pauseButton) resetButton(pauseButton);
		else if (e.getSource() == this.backButton) resetButton(backButton);
		else if (e.getSource() == this.restartButton) resetButton(restartButton);

	}
	
	
}
