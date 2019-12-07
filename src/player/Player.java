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
	private boolean isProtectedByItem;
		
	private boolean isAlive;
	private int direction;
	private int imageDirection;// This variable is for deciding the image of DIRECTION_STOP
	private int x;
	private int y;

	private int playerHurtDelayCount = 0;
	private int protectedByItemCount = 0;

	private Map playerMap; // Relate the player with the map
	private int mapX;
	private int mapY;
	private int playerHP;
	private int playerMaxHP;
	private int playerCharacterID;

	private int stopflag = 0;

	private int upflag = 0;
	private int downflag = 0;
	private int leftflag = 0;
	private int rightflag = 0;

	private int futureUpflag = 0;
	private int futureDownflag = 0;
	private int futureLeftflag = 0;
	private int futureRightflag = 0;

	public Player(Map newmap, int id, int playerCharacterID) {
		this.direction = DIRECTION_STOP;
		this.imageDirection = DIRECTION_DOWN;
		this.x = 0;
		this.y = 0;
		this.velocity = 5;
		this.playerMap = newmap;
		this.playerID = id;
		this.playerHurtDelayCount = 0;
		this.protectedByItemCount = 0;
		this.isImmune = false;
		this.isProtectedByItem = false;
		this.bombPower = 1;
		this.playerCharacterID = playerCharacterID;

		this.generateCharacter(playerCharacterID);
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
	
	public void setPlayerCharacterID(int cid) {
		this.playerCharacterID = cid;
	}
	
	public int getPlayerCharacterID() {
		return this.playerCharacterID;
	}

	public void generateCharacter(int cid) {
		switch (cid) {
		case 0:
			this.playerHP = PLAYER_CHARACTER1_HP_MAX;
			this.playerMaxHP = PLAYER_CHARACTER1_HP_MAX;
			this.bombMaxNumber = PLAYER_CHARACTER1_BOMB_MAX;
			this.bombPower = PLAYER_CHARACTER1_BOMB_POWER;
			break;
		case 1:
			this.playerHP = PLAYER_CHARACTER2_HP_MAX;
			this.playerMaxHP = PLAYER_CHARACTER2_HP_MAX;
			this.bombMaxNumber = PLAYER_CHARACTER2_BOMB_MAX;
			this.bombPower = PLAYER_CHARACTER2_BOMB_POWER;
			break;
		case 2:
			this.playerHP = PLAYER_CHARACTER3_HP_MAX;
			this.playerMaxHP = PLAYER_CHARACTER3_HP_MAX;
			this.bombMaxNumber = PLAYER_CHARACTER3_BOMB_MAX;
			this.bombPower = PLAYER_CHARACTER3_BOMB_POWER;
			break;
		case 3:
			this.playerHP = PLAYER_CHARACTER4_HP_MAX;
			this.playerMaxHP = PLAYER_CHARACTER4_HP_MAX;
			this.bombMaxNumber = PLAYER_CHARACTER4_BOMB_MAX;
			this.bombPower = PLAYER_CHARACTER4_BOMB_POWER;
			break;

		}
	}

	/*
	 * For HP
	 */
	public void setHP(int hp) {
		if (hp < 0)
			this.playerHP = 0;
		else
			this.playerHP = hp;
	}

	public int getHP() {
		return this.playerHP;
	}

	public void setMaxHP(int mhp) {
		this.playerMaxHP = mhp;
	}

	public int getMaxHP() {
		return this.playerMaxHP;
	}
	
	public void getHurt(int hpLoss) {
		if ((!this.isImmune)&&(!this.isProtectedByItem)) {
			this.setHP(this.getHP()-hpLoss);
		}
	}
	
	public boolean isImmune() {
		return this.isImmune;
	}
	
	public void setIsImmune(boolean isImmune) {
		this.isImmune = isImmune;
	}
	
	public boolean proectedByItem() {
		return this.isProtectedByItem;
	}
	
	public void setProtectedByItem(boolean protectedByItem) {
		this.isProtectedByItem = protectedByItem;
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

		if (mi.isAvailable(mapX, mapY)) {

			player.setMapX(mapX);
			player.setMapY(mapY);
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
	 * Stop the player and clear the flag
	 */
	public void playerStop() {
		// Stop only when the x and y of the player are multiples of 45
		if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
			if (this.futureUpflag == 1) {
				this.setDirection(DIRECTION_UP);
			}
			if (this.futureDownflag == 1) {
				this.setDirection(DIRECTION_DOWN);
			}
			if (this.futureRightflag == 1) {
				this.setDirection(DIRECTION_RIGHT);
			}
			if (this.futureLeftflag == 1) {
				this.setDirection(DIRECTION_LEFT);
			}
			if (this.futureDownflag == 0 & this.futureLeftflag == 0 & this.futureRightflag == 0
					& this.futureUpflag == 0) {
				if (this.stopflag == 1) {
					this.setDirection(DIRECTION_STOP);
					this.stopflag = 0;
				}
			}
			// update mapX and mapY
			this.setLocationOnMap(this, this.playerMap, this.x / CELL_WIDTH, this.y / CELL_HEIGHT);
			clearFutureFlag();
		}
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
				// If the cell which the player is going to step on is available
				if (this.playerMap.isAvailable((int) (Math.ceil(mapx)), (int) (Math.ceil(mapy) - 1))) {
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
				if (this.playerMap.isAvailable((int) (Math.ceil(mapx) - 1), (int) (Math.ceil(mapy)))) {
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

	public void clearFlag() {
		this.upflag = 0;
		this.downflag = 0;
		this.leftflag = 0;
		this.rightflag = 0;
	}

	public void clearFutureFlag() {
		this.futureUpflag = 0;
		this.futureDownflag = 0;
		this.futureLeftflag = 0;
		this.futureRightflag = 0;
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
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.upflag = 1;
					}
					if(this.upflag==0) {
						this.futureUpflag = 1;
					}
					break;
				case KeyEvent.VK_DOWN:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.downflag = 1;
					}
					if(this.downflag==0) {
						this.futureDownflag = 1;
					}
					break;
				case KeyEvent.VK_LEFT:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.leftflag = 1;
					}
					if(this.leftflag==0) {
						this.futureLeftflag = 1;
					}
					break;
				case KeyEvent.VK_RIGHT:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.rightflag = 1;
					}
					if(this.rightflag==0) {
						this.futureRightflag = 1;
					}
					break;
				case KeyEvent.VK_ENTER:
					this.plantBomb(this.playerMap, this.mapX, this.mapY);
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
					this.stopflag = 1;
					clearFlag();
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
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.upflag = 1;
					}
					if(this.upflag==0) {
						this.futureUpflag = 1;
					}
					break;
				case KeyEvent.VK_S:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.downflag = 1;
					}
					if(this.downflag==0) {
						this.futureDownflag = 1;
					}
					break;
				case KeyEvent.VK_A:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.leftflag = 1;
					}
					if(this.leftflag==0) {
						this.futureLeftflag = 1;
					}
					break;
				case KeyEvent.VK_D:
					clearFlag();
					clearFutureFlag();
					if (this.getX() % CELL_WIDTH == 0 && this.getY() % CELL_HEIGHT == 0) {
						this.rightflag = 1;
					}
					if(this.rightflag==0) {
						this.futureRightflag = 1;
					}
					break;
				case KeyEvent.VK_SPACE:
					this.plantBomb(this.playerMap, this.mapX, this.mapY);
					break;

				}
			}

			if (event.getID() == KeyEvent.KEY_RELEASED) {
				KeyEvent e = (KeyEvent) event;

				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S
						|| e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
					this.stopflag = 1;
					clearFlag();
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
		if (this.x % CELL_WIDTH == 0 && this.y % CELL_HEIGHT == 0 && mi.isWithItem(this.getMapX(), this.getMapY())) {
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
		if (upflag == 1) {
			this.setDirection(DIRECTION_UP);
		}
		if (downflag == 1) {
			this.setDirection(DIRECTION_DOWN);
		}
		if (leftflag == 1) {
			this.setDirection(DIRECTION_LEFT);
		}
		if (rightflag == 1) {
			this.setDirection(DIRECTION_RIGHT);
		}

		this.playerMove();// Change the location of the player

		if (mi.isAtExplosion(this.getMapX(), this.getMapY())) {
				this.getHurt(HP_LOSS_BY_BOMB);
				this.setIsImmune(true);			
		}
		
		if (this.isImmune) {
			this.playerHurtDelayCount++;			
			if (this.playerHurtDelayCount == 30) {
				this.playerHurtDelayCount = 0;
				this.setIsImmune(false);
			}
		}
		
		if (this.isProtectedByItem) {
			this.protectedByItemCount++;			
			if (this.protectedByItemCount == 330) {
				this.protectedByItemCount = 0;
				this.setIsImmune(false);
			}
		}
	


	}

}