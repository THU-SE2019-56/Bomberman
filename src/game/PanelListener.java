package game;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import monster.Monster;

public class PanelListener implements ActionListener,GameConstants{
	private MapPanel mp;
	private StatusPanel sp;
	public PanelListener(MapPanel mp,StatusPanel sp) {
		this.mp=mp;
		this.sp=sp;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (mp.getPauseFlag() == 0) {
			for (int i = 0; i < mp.getPlayerNum(); i++) {
				mp.getPlayer()[i].refresh(mp.getMap());// refresh player
			}

			for (Monster m : mp.getMonsters()) { // Change the location of monsters
				if (m.isAlive()) {
					int p = (int) (mp.getPlayerNum() * Math.random()); // select a bad luck player randomly
					m.monsterMove(mp.getPlayer()[p], mp.getMap());
				}
			}

			mp.getMap().refresh(); // Refresh the map, for bomb

			if (mp.gameOver == false) {
				mp.repaint();
			}
		}
		
		sp.repaint();
		Toolkit.getDefaultToolkit().sync();
	}

}
