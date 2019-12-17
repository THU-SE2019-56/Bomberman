package game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import monster.Monster;
import ui.MapPanel;
import ui.StatusPanel;

/**
 * The actionListener for timer. Refresh game, mapPanel and statusPanel.
 * 
 * @author Wang
 * @version 0.9
 */
public class TimerListener implements ActionListener, GameConstants {
	private MapPanel mapPanel;
	private StatusPanel statusPanel;
	private Game game;

	public TimerListener(Game game, MapPanel mapPanel, StatusPanel statusPanel) {
		this.game = game;
		this.mapPanel = mapPanel;
		this.statusPanel = statusPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (game.getPauseFlag() == 0) {
			for (int i = 0; i < game.getPlayerNum(); i++) {
				game.getPlayer()[i].refresh(game.getMap());// refresh player
			}

			for (Monster m : game.getMonsters()) { // Change the location of monsters
				int p = (int) (game.getPlayerNum() * Math.random()); // select a bad luck player randomly
				m.monsterMove(game.getPlayer()[p], game.getMap());
			}

			game.getMap().refresh(); // Refresh the map, for bomb

			if (game.isGameOver() == false) {
				mapPanel.repaint();
			}
			
			if (game.getPlayerNum()==2) {
				int player1X = game.getPlayer()[PLAYER_ID_P1].getX();
				int player1Y = game.getPlayer()[PLAYER_ID_P1].getY();
				
				int player2X = game.getPlayer()[PLAYER_ID_P2].getX();
				int player2Y = game.getPlayer()[PLAYER_ID_P2].getY();
				
				int player1ActiveItemX=-100;
				int player1ActiveItemY=-100;
				int player2ActiveItemX=-100;
				int player2ActiveItemY=-100;
				if (game.getPlayer()[PLAYER_ID_P1].getActiveItem()!=null) {
					player1ActiveItemX = game.getPlayer()[PLAYER_ID_P1].getActiveItem().getX();
					player1ActiveItemY = game.getPlayer()[PLAYER_ID_P1].getActiveItem().getY();
					Rectangle p1ActiveItemRectangle =  new Rectangle(player1ActiveItemX , player1ActiveItemY ,CELL_WIDTH,CELL_HEIGHT);
					Rectangle p2Rectangle  = new Rectangle(player2X, player2Y,CELL_WIDTH,CELL_HEIGHT);
					if (p2Rectangle.intersects(p1ActiveItemRectangle)) {
						game.getPlayer()[PLAYER_ID_P2].getHurt(20);
						game.getPlayer()[PLAYER_ID_P1].setIsUsingBulletFlag(0);
						game.getPlayer()[PLAYER_ID_P1].setActiveItem(null);
					}
				}			
				if (game.getPlayer()[PLAYER_ID_P2].getActiveItem()!=null) {
					player2ActiveItemX = game.getPlayer()[PLAYER_ID_P2].getActiveItem().getX();
					player2ActiveItemY = game.getPlayer()[PLAYER_ID_P2].getActiveItem().getY();
					
					Rectangle p1Rectangle  = new Rectangle(player1X, player1Y,CELL_WIDTH,CELL_HEIGHT);
					Rectangle p2ActiveItemRectangle =  new Rectangle(player2ActiveItemX , player2ActiveItemY ,CELL_WIDTH,CELL_HEIGHT);
					if (p1Rectangle.intersects(p2ActiveItemRectangle)) {
						game.getPlayer()[PLAYER_ID_P1].getHurt(20);
						game.getPlayer()[PLAYER_ID_P2].setIsUsingBulletFlag(0);
						game.getPlayer()[PLAYER_ID_P2].setActiveItem(null);
					}
				}			
				
				
			}
		}

		statusPanel.repaint();
	}

}
