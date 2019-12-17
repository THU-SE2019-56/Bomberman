package player;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import game.GameConstants;

public class PlayerController implements AWTEventListener,GameConstants{
	/**
     * Respond to the keyboard events. Use up, down, right, left, enter to control
     * p1 and A, S, W, D, space to control p2
     */
	private Player player;
	public PlayerController(Player player) {
		this.player=player;
	}

    @Override
    public void eventDispatched(AWTEvent event) {

        if (player.getPlayerID() == PLAYER_ID_P1) {
            /*
             * Enable to set the direction of the player only when the x and y of the player
             * are multiples of 60. As a result, the player will keep moving when the x and
             * y of the player are not multiples of 60.
             */

            if (event.getID() == KeyEvent.KEY_PRESSED) {
                KeyEvent e = (KeyEvent) event;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        player.clearFlag();
                        player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                            player.setUpflag(1);
                        }
                        if (player.getUpflag() == 0) {
                            player.setFutureUpflag(1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setDownflag(1);
                        }
                        if (player.getDownflag() == 0) {
                        	player.setFutureDownflag(1);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setLeftflag(1);
                        }
                        if (player.getLeftflag() == 0) {
                        	player.setFutureLeftflag(1);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setRightflag(1);
                        }
                        if (player.getRightflag() == 0) {
                        	player.setFutureRightflag(1);
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                    	player.plantBomb(player.getPlayerMap(), player.getMapX(), player.getMapY());
                        break;
                    case KeyEvent.VK_SHIFT:
                    	player.useActiveItem();
                        break;

                }
            }
            /*
             * When the keys are released, set the direction of the player to
             * DIRECTION_STOP.
             */
            if (event.getID() == KeyEvent.KEY_RELEASED) {
                KeyEvent e = (KeyEvent) event;
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                	player.setStopflag(1);
                	player.clearFlag();
                    if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                    	player.setDirection(DIRECTION_STOP);
                    }
                }
            }
        }

        if (player.getPlayerID() == PLAYER_ID_P2) {
            /*
             * Enable to set the direction of the player only when the x and y of the player
             * are multiples of 60. As a result, the player will keep moving when the x and
             * y of the player are not multiples of 60.
             */

            if (event.getID() == KeyEvent.KEY_PRESSED) {
                KeyEvent e = (KeyEvent) event;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        player.clearFlag();
                        player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                            player.setUpflag(1);
                        }
                        if (player.getUpflag() == 0) {
                            player.setFutureUpflag(1);
                        }
                        break;
                    case KeyEvent.VK_S:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setDownflag(1);
                        }
                        if (player.getDownflag() == 0) {
                        	player.setFutureDownflag(1);
                        }
                        break;
                    case KeyEvent.VK_A:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setLeftflag(1);
                        }
                        if (player.getLeftflag() == 0) {
                        	player.setFutureLeftflag(1);
                        }
                        break;
                    case KeyEvent.VK_D:
                    	player.clearFlag();
                    	player.clearFutureFlag();
                        if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                        	player.setRightflag(1);
                        }
                        if (player.getRightflag() == 0) {
                        	player.setFutureRightflag(1);
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                    	player.plantBomb(player.getPlayerMap(), player.getMapX(), player.getMapY());
                        break;
                    case KeyEvent.VK_CONTROL:
                    	player.useActiveItem();
                        break;

                }
            }
            /*
             * When the keys are released, set the direction of the player to
             * DIRECTION_STOP.
             */
            if (event.getID() == KeyEvent.KEY_RELEASED) {
                KeyEvent e = (KeyEvent) event;
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S
                        || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                	player.setStopflag(1);
                	player.clearFlag();
                    if (player.getX() % CELL_WIDTH == 0 && player.getY() % CELL_HEIGHT == 0) {
                    	player.setDirection(DIRECTION_STOP);
                    }
                }
            }
        }

    }
}
