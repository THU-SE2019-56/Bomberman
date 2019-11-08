package player;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import map.Map;

import game.GameConstants;
import map.Cell;
//import map.Map;
//import monster.Monster;

/**
 * The player can be controlled by the keyboard. However its motion is not
 * related to the maps now. The character runs on a blank JPanel without the
 * limitation of the map info.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */

public class Player implements AWTEventListener, GameConstants {
	private int velocity; //The velocity should be divisible by 60
	private int bombNumber;
	private int bombPower;
	private boolean isImmune;
	private boolean isAlive;
	private int direction;
	private int imageDirection;// This variable is for deciding the image of DIRECTION_STOP 
	private int x;
	private int y;

	
	private Map playerMap; //Relate the player with the map
	private byte mapX;
	private byte mapY;
	private int playerHP = 60;

	

	public Player(Map newmap) {
		this.direction=DIRECTION_STOP;
		this.imageDirection=DIRECTION_DOWN;
		this.x=0;
		this.y=0;
		this.velocity= 5;
		this.playerMap = newmap;
	}

	/**
	 * Set the HP of the player
	 */
	public void setHP(int hp) {
		this.playerHP = hp;
	}
	
	/**
	 * Get the HP of the player
	 */
	public int getHP() {
		return this.playerHP;
	}

    /**
     * Use setVelocity() to set the velocity of the player.
     *
     * @param v Velocity of the player.
     */
    public void setVelocity(int v) {
        this.velocity = v;
    }

    /**
     * Get the velocity of the player.
     *
     * @return Return an integer value.
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * Use setDirection() to set the direction of the player.
     *
     * @param d Direction of the player.
     */
    public void setDirection(int d) {
        if(d!=DIRECTION_STOP) {
            this.direction = d;
            this.imageDirection=d;
        }
        else {
            this.direction=d;
        }
    }

    /**
     * Get the direction of the players.
     *
     * @return Return an integer value.
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * Get the image direction of the players.
     *
     * @return Return an integer value.
     */
    public int getImageDirection() {
        return this.imageDirection;
    }

    /**
     * Set the x of the player.
     *
     * @param X
     */
    public void setX(int X) {
        this.x = X;
    }

    /**
     * Set the y of the player.
     *
     * @param Y
     */
    public void setY(int Y) {
        this.y = Y;
    }

    /**
     * Get the x of the player.
     *
     * @return Return an integer value.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y of the player.
     *
     * @return Return an integer value.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the location of the player.
     *
     * @param X
     * @param Y
     */
    public void setLocation(int X, int Y) {
        this.x = X;
        this.y = Y;
    }

	public void setMapX(byte mapx) {
		this.mapX = mapx;
	}

	public void setMapY(byte mapy) {
		this.mapY = mapy;
	}

	public byte getMapX() {
		return this.mapX;
	}

	public byte getMapY() {
		return this.mapY;
	}
	
	/**
	 * Use playerInMap() to ensure that the player is in the frame and avoid errors.
	 */
	public boolean playerInMap() {
		
		boolean canMove = true;
		switch (this.getDirection()) {
		case DIRECTION_UP:
			if (this.mapY>0) canMove = true;
			else canMove = false;
			break;
		case DIRECTION_DOWN:
			if (this.mapY<this.playerMap.getSizeY()-1) canMove = true;
			else canMove = false;
			break;
		case DIRECTION_LEFT:
			if (this.mapX>0) canMove = true;
			else canMove = false;
			break;
		case DIRECTION_RIGHT:
			if (this.mapX<this.playerMap.getSizeX()-1) canMove = true;
			else canMove = false;
			break;
		}
		return canMove;
	}
	/**
	 * <p>
	 * Judge the direction of the player and change the coordinates.
	 * 
	 */
	public void playerMove() {
	
		double mapx = (double)(this.x)/(double)(60);
		double mapy = (double)(this.y)/(double)(60);
		Cell playerNextCell;//The next cell which the player is going to step on
		
		if (playerInMap()) //Move the player only when it's in the range of the map
		{
			
		switch (this.getDirection()) {
		case DIRECTION_UP:

				playerNextCell = this.playerMap.map((byte)(Math.ceil(mapy)-1),(byte)(Math.ceil(mapx)));
				//Use math.ceil to round up
				if (playerNextCell.isAvailable()) //If the cell which the player is going to step on is available
				{
					this.setY(this.getY() - this.getVelocity());	
					if (this.getX()%60==0&&this.getY()%60==0) //Stop only when the x and y of the player are  multiples of 60.
					{
						this.setDirection(DIRECTION_STOP);
						this.setLocationOnMap(this, this.playerMap, (byte)(this.x/60), (byte)(this.y/60));//Update mapX and mapY
					}
				}
			break;
		case DIRECTION_DOWN:
			
				playerNextCell = this.playerMap.map((byte)(Math.floor(mapy)+1),(byte)(Math.floor(mapx)));
				if (playerNextCell.isAvailable()) {
					this.setY(this.getY() + this.getVelocity());
			
					if (this.getX()%60==0&&this.getY()%60==0)
					{
						this.setDirection(DIRECTION_STOP);
						this.setLocationOnMap(this, this.playerMap, (byte)(this.x/60), (byte) (this.y/60));
					}
				}
			break;
			
		case DIRECTION_LEFT:
		
				playerNextCell = this.playerMap.map((byte)(Math.ceil(mapy)),(byte)(Math.ceil(mapx)-1));
				
				if (playerNextCell.isAvailable()) {
					this.setX(this.getX() - this.getVelocity());
	
					if (this.getX()%60==0&&this.getY()%60==0)
					{
						this.setDirection(DIRECTION_STOP);
						this.setLocationOnMap(this, this.playerMap, (byte)(this.x/60), (byte) (this.y/60));
					}
				}

			break;
		case DIRECTION_RIGHT:
		
				playerNextCell = this.playerMap.map((byte) (Math.floor(mapy)),(byte)(Math.floor(mapx)+1));
				if (playerNextCell.isAvailable()) {
					this.setX(this.getX() + this.getVelocity());
	
					if (this.getX()%60==0&&this.getY()%60==0)
					{
						this.setDirection(DIRECTION_STOP);
						this.setLocationOnMap(this, this.playerMap, (byte)(this.x/60), (byte) (this.y/60));
					}
				}
			break;
		case DIRECTION_STOP:
			break;
		}

	}
		
	}

	/**
	 * Respond to the keyboard events.
	 */
	@Override
	public void eventDispatched(AWTEvent event) {
		
		
		/*
		 * Enable to set the direction of the player only when the x and y of the player are multiples of 60. As
		 * a result, the player will keep moving when the x and y of the player are not multiples of 60.
		 */
		if (this.getX()%60==0&&this.getY()%60==0) {
		
		/*
		 * When the keys are pressed, set the direction of the player.
		 */
		if (event.getID() == KeyEvent.KEY_PRESSED) {
			KeyEvent e = (KeyEvent) event;
		
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				this.setDirection(DIRECTION_UP);

			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.setDirection(DIRECTION_DOWN);

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.setDirection(DIRECTION_LEFT);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.setDirection(DIRECTION_RIGHT);
			}
			}
		/*
		 * When the keys are released, set the direction of the player to DIRECTION_STOP.
		 */
		if (event.getID() == KeyEvent.KEY_RELEASED) {
			this.setDirection(DIRECTION_STOP);
		}
		
		}
	}
	/**
	 * Use map to decide the player's location after moving. Can not receive any
	 * command before it moves completely into a new integral cell.
	 * @throws InterruptedException 
	 */
	public void setLocationOnMap(Player player, Map mi, byte mapX,byte mapY)  {
		 //TODO Move the player, consider walls, bombs. Use setLocation()				
		
		Cell playerCell = mi.map(mapY, mapX);

			if (playerCell.isAvailable()==true) {
				
				player.setMapX(mapX);
				player.setMapY(mapY);
			}
	
	}
	
	
//	/**
//	 * Update mapinfo BOMB_INFO
//	 */
//	public void plantBomb(Player player, Map mi) {
//
//	}
//
//	/**
//	 * Use mapinfo and monsters to decide whether the player is alive, whether the
//	 * player acquire any item, etc.
//	 */
//	public void decideState(Player player, Map mi) {
//
//	}

}