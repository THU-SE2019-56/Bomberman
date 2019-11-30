package player;

import java.awt.AWTEvent;
//import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import map.Map;

import game.GameConstants;
import map.Cell;
import items.Item;
//import map.Map;
//import monster.Monster;

/**
 * The player can be controlled by the keyboard. Its motion is related to the
 * maps now.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */

public class Player implements AWTEventListener, GameConstants {

	private int playerID;// ID of the player
	private int velocity; // The velocity should be divisible by 60

	private int bombMaxNumber = 1;
	private int bombPlantedNumber = 0;

	private int bombPower;
	private boolean isImmune;
	private boolean isAlive;
	private int direction;
	private int imageDirection;// This variable is for deciding the image of DIRECTION_STOP
	private int x;
	private int y;
	private int stopflag = 0;
	private int playerHurtDelayCount = 0;
	private int playerCanBeHurt = 1;

	private Map playerMap; // Relate the player with the map
	private int mapX;
	private int mapY;
	private int playerHP;

	public Player(Map newmap, int id) {
		this.direction = DIRECTION_STOP;
		this.imageDirection = DIRECTION_DOWN;
		this.x = 0;
		this.y = 0;
		this.velocity = 5;
		this.playerMap = newmap;
		this.playerID = id;
		this.playerHurtDelayCount = 0;
		this.playerCanBeHurt = 1;
		this.playerHP = HP_MAX;
		this.bombPower = 1;
	}

	/*
	 * For bombs
	 */
	public void setBombPlantedNumber(int bombPlantedNum) {
		this.bombPlantedNumber = bombPlantedNum;
	}

	public void reduceBombPlantedNumber() {
		this.bombPlantedNumber--;
	}

	public void addBombPlantedNumber() {
		this.bombPlantedNumber++;
	}

	public int getBombPlantedNumber() {
		return this.bombPlantedNumber;
	}

	public void setBombMaxNumber(int bombMaxNum) {
		this.bombMaxNumber = bombMaxNum;
	}

	public int getBombMaxNumber() {
		return this.bombMaxNumber;
	}
	
	public int getBombPower() {
		return this.bombPower;
	}
	
	public void SetBombPower(int power) {
			this.bombPower = power;
	}
	
	/*
	 * For ID
	 */
	public void setPlayerID(int id) {
		this.playerID = id;
	}

	public int getPlayerID() {
		return this.playerID;
	}


	/*
	 * For HP
	 */
	public void setHP(int hp) {
		if (hp<0) this.playerHP = 0;
		else this.playerHP = hp;
	}

	public int getHP() {
		return this.playerHP;
	}

	/*
	 * For velocity
	 */
	public void setVelocity(int v) {
		this.velocity = v;
	}

	public int getVelocity() {
		return this.velocity;
	}

	public void addVelocityByItems() {

		this.setVelocity(9);
	}

	/*
	 * For direction
	 */
	public void setDirection(int d) {

		if (d != DIRECTION_STOP) {
			this.direction = d;
			this.imageDirection = d;
		} else {
			this.direction = d;
		}
	}


	public int getDirection() {
		return this.direction;
	}

	public int getImageDirection() {
		return this.imageDirection;
	}

	/*
	 * For location
	 */
	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setLocation(int X, int Y) {
		this.x = X;
		this.y = Y;
	}

	public void setMapX(int mapx) {
		this.mapX = mapx;
	}

	public void setMapY(int mapy) {
		this.mapY = mapy;
	}

	public int getMapX() {
		return this.mapX;
	}

	public int getMapY() {
		return this.mapY;
	}
	
	/**
	 * Use map to decide the player's location after moving. Can not receive any
	 * command before it moves completely into a new integral cell.
	 * 
	 * @throws InterruptedException
	 */
	public void setLocationOnMap(Player player, Map mi, int mapX, int mapY) {

		if (mi.isAvailable(mapX,mapY)) {

			player.setMapX(mapX);
			player.setMapY(mapY);
		}
	}

	/**
	 * Stop the player and clear the flag
	 */
	public void playerStop() {
		
		// Stop only when the x and y of the player are multiples of 60
		if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) 
		{
			if (this.stopflag == 1) {
				this.setDirection(DIRECTION_STOP);
				this.stopflag = 0;
			}
			this.setLocationOnMap(this, this.playerMap, this.x / CELL_WIDTH, this.y / CELL_HEIGHT);// Update mapX and																							// mapY
		}
	}

	/**
	 * Use playerInMap() to ensure that the player is in the frame and avoid errors.
	 */
	public boolean playerInMap() {

		boolean canMove = true;
		switch (this.getDirection()) {
		case DIRECTION_UP:
			if (this.mapY > 0)
				canMove = true;
			else
				canMove = false;
			break;
		case DIRECTION_DOWN:
			if (this.mapY < this.playerMap.getSizeY() - 1)
				canMove = true;
			else
				canMove = false;
			break;
		case DIRECTION_LEFT:
			if (this.mapX > 0)
				canMove = true;
			else
				canMove = false;
			break;
		case DIRECTION_RIGHT:
			if (this.mapX < this.playerMap.getSizeX() - 1)
				canMove = true;
			else
				canMove = false;
			break;
		}
		return canMove;
	}

	/**
	 * Judge the direction of the player and change the coordinates.
	 */
	public void playerMove() {

		double mapx = (double) (this.x) / (double) (CELL_WIDTH);
		double mapy = (double) (this.y) / (double) (CELL_HEIGHT);

		if (playerInMap()) // Move the player only when it's in the range of the map
		{
			switch (this.getDirection()) {
			case DIRECTION_UP:
																					
				if (this.playerMap.isAvailable((int) (Math.ceil(mapx)), (int) (Math.ceil(mapy) - 1)) ) // If the cell which the player is going to step on is available
				{
					this.setY(this.getY() - this.getVelocity());
					this.playerStop();
				}
				break;
			case DIRECTION_DOWN:				
				
				if (this.playerMap.isAvailable((int) (Math.floor(mapx)), (int) (Math.floor(mapy) + 1))) {
					this.setY(this.getY() + this.getVelocity());
					this.playerStop();
				}
				break;

			case DIRECTION_LEFT:

				if (this.playerMap.isAvailable((int) (Math.ceil(mapx) - 1), (int) (Math.ceil(mapy))) ) {
					this.setX(this.getX() - this.getVelocity());
					this.playerStop();
				}

				break;
			case DIRECTION_RIGHT:
	
				if (this.playerMap.isAvailable((int) (Math.floor(mapx) + 1), (int) (Math.floor(mapy)))) {
					this.setX(this.getX() + this.getVelocity());
					this.playerStop();
				}
				break;
			case DIRECTION_STOP:
				break;
			}

		}

	}

	/**
	 * Respond to the keyboard events. Use up, down, right, left, enter to control
	 * p1 and A, S, W, D, space to control p2
	 */
	@Override
	public void eventDispatched(AWTEvent event) {

		if (this.playerID == PLAYER_ID_P1) {
			/*
			 * Enable to set the direction of the player only when the x and y of the player
			 * are multiples of 60. As a result, the player will keep moving when the x and
			 * y of the player are not multiples of 60.
			 */
			if (event.getID() == KeyEvent.KEY_PRESSED) {
				KeyEvent e = (KeyEvent) event;
				switch (e.getKeyCode()) {

				case KeyEvent.VK_UP:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_UP);
					break;
				case KeyEvent.VK_DOWN:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_RIGHT);
					break;
				case KeyEvent.VK_ENTER:
					this.plantBomb(this.playerMap, this.mapX, this.mapY);// Use the space key to plant bomb
					break;
				default:
					this.setDirection(this.getDirection());
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
					this.stopflag = 1;
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.setDirection(DIRECTION_STOP);

					}
				}
			}
		}

		if (this.playerID == PLAYER_ID_P2) {

			if (event.getID() == KeyEvent.KEY_PRESSED) {
				KeyEvent e = (KeyEvent) event;
				switch (e.getKeyCode()) {

				case KeyEvent.VK_W:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_UP);
					break;
				case KeyEvent.VK_S:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_DOWN);
					break;
				case KeyEvent.VK_A:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_LEFT);
					break;
				case KeyEvent.VK_D:
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0)
						this.setDirection(DIRECTION_RIGHT);
					break;
				case KeyEvent.VK_SPACE:
					this.plantBomb(this.playerMap, this.mapX, this.mapY);// Use the space key to plant bomb
					break;
				default:
					this.setDirection(this.getDirection());
				}
			}

			if (event.getID() == KeyEvent.KEY_RELEASED) {
				KeyEvent e = (KeyEvent) event;

				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S
						|| e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
					this.stopflag = 1;
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.setDirection(DIRECTION_STOP);

					}
				}
			}
		}

	}

	/**
	 * Update map BOMB_INFO
	 */
	public void plantBomb(Map mi, int mapx, int mapy) {
		if (this.bombPlantedNumber < this.bombMaxNumber) 
			mi.setBomb(mapx, mapy, this.bombPower, this);
	}


	public void acquireItemByMap(Map mi) {
		if (this.x%CELL_WIDTH==0&&this.y%CELL_HEIGHT==0&&mi.isWithItem(this.getMapX(), this.getMapY())) {	
			mi.giveItem(this.getMapX(), this.getMapY(), this);	
		}
	}
	
	/**
	 * Use map and monsters to decide whether the player is alive, whether the
	 * player acquire any item, etc.
	 */
	public void decideState(Player player, Map mi) {

	}

	/**
	 * Refresh the state of player. Player will be hurt when collided with bombs.
	 * The playerHurtDelayCount and playerCanBeHurt is used to avoid multiple hurts
	 * on player at the same time.
	 */
	public void refresh(Map mi) {
		this.playerMove();// Change the location of the player

		if (mi.isAtExplosion(this.getMapX(), this.getMapY())) {

			if (this.playerCanBeHurt == 1) {
				this.setHP(this.getHP() - HP_LOSS_BY_BOMB);
				this.playerCanBeHurt = 0;
			}
		}

		if (this.playerCanBeHurt == 0) {
			this.playerHurtDelayCount++;

			if (this.playerHurtDelayCount == 30) {
				this.playerHurtDelayCount = 0;
				this.playerCanBeHurt = 1;
			}
		}

	}

}