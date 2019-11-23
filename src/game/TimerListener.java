package game;

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
				if (m.isAlive()) {
					int p = (int) (game.getPlayerNum() * Math.random()); // select a bad luck player randomly
					m.monsterMove(game.getPlayer()[p], game.getMap());
				}
			}

			game.getMap().refresh(); // Refresh the map, for bomb

			if (game.isGameOver() == false) {
				mapPanel.repaint();
			}
		}

		statusPanel.repaint();
	}

}
