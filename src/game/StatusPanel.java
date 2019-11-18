package game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Create a panel on the right side of mapManel. Related to mapPanel.
 * 
 * @author Wang
 * @version 0.9
 */
public class StatusPanel extends JPanel implements MouseListener, GameConstants{
	private JTextField playerLifeText[] = new JTextField[2];
	private JButton pauseButton = new JButton("Pause");
	private MapPanel mp;
	private Timer timer;
	
	public StatusPanel(MapPanel mp) {
		this.mp = mp;
		
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

		Font buttonFont = new Font("Times New Roman Italic", Font.BOLD, 16);
		this.pauseButton.setBounds( CELL_WIDTH, 8 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);
		this.pauseButton.setVisible(true);
		this.pauseButton.setFont(buttonFont);
		this.pauseButton.addMouseListener(this);
		this.add(pauseButton);
	
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.pauseButton) {
			if (mp.getPauseFlag() == 0) {
				mp.setPauseFlag(1);
				this.pauseButton.setText("Start");
			} else {
				mp.setPauseFlag(0);
				this.pauseButton.setText("Pause");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
