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
	private int lastDirection;
	private int imageDirection;// This variable is for deciding the image of DIRECTION_STOP 
	private int x;
	private int y;
	private int stopflag =0;

	
	private Map playerMap; //Relate the player with the map
	private int mapX;
	private int mapY;
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
     */
    public void setVelocity(int v) {
        this.velocity = v;
    }

    /**
     * Get the velocity of the player.
     */
    public int getVelocity() {
        return this.velocity;
    }
    
    public void addVelocityByItems() {
		switch(this.getDirection()) {
		case DIRECTION_UP:
			this.setX(60*(this.getX()/60));
			this.setY(60*(this.getY()/60+1));
		
		case DIRECTION_DOWN:
			this.setX(60*(this.getX()/60-1));
			this.setY(60*(this.getY()/60));
		
		case DIRECTION_LEFT:
			this.setX(60*(this.getX()/60+1));
			this.setY(60*(this.getY()/60));
		
		case DIRECTION_RIGHT:
			this.setX(60*(this.getX()/60));
			this.setY(60*(this.getY()/60));
		}
		
		this.setVelocity(10);
    }

    /**
     * Use setDirection() to set the direction of the player.
     */
    public void setDirection(int d) {
        this.lastDirection = this.getDirection();
        
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
	 * Stop the player and clear the flag
	 */
	public void playerStop() {
		if (this.getX()%60==0&&this.getY()%60==0) //Stop only when the x and y of the player are  multiples of 60.
		{
			if (this.stopflag==1) {
				this.setDirection(DIRECTION_STOP);
				this.stopflag=0;
			}
			this.setLocationOnMap(this, this.playerMap, this.x/60, this.y/60);//Update mapX and mapY
		}
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

				playerNextCell = this.playerMap.getCell( (int)(Math.ceil(mapx)), (int)(Math.ceil(mapy)-1));//Use math.ceil to round up
				
				if (playerNextCell.isAvailable()) //If the cell which the player is going to step on is available
				{
					this.setY(this.getY() - this.getVelocity());	
					this.playerStop();
				}
			break;
		case DIRECTION_DOWN:
			
				playerNextCell = this.playerMap.getCell( (int)(Math.floor(mapx)),(int)(Math.floor(mapy)+1) );
				
				if (playerNextCell.isAvailable()) {
					this.setY(this.getY() + this.getVelocity());
					this.playerStop();
				}
			break;
			
		case DIRECTION_LEFT:
		
				playerNextCell = this.playerMap.getCell((int)(Math.ceil(mapx)-1),(int)(Math.ceil(mapy)));
				
				if (playerNextCell.isAvailable()) {
					this.setX(this.getX() - this.getVelocity());
					this.playerStop();
				}

			break;
		case DIRECTION_RIGHT:
		
				playerNextCell = this.playerMap.getCell( (int)(Math.floor(mapx)+1), (int)(Math.floor(mapy)) );
				if (playerNextCell.isAvailable()) {
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
	 * Respond to the keyboard events.
	 */
	@Override
	public void eventDispatched(AWTEvent event) {
		
		/*
		 * Enable to set the direction of the player only when the x and y of the player are multiples of 60. As
		 * a result, the player will keep moving when the x and y of the player are not multiples of 60.
		 */
		if (event.getID() == KeyEvent.KEY_PRESSED) {
			KeyEvent e = (KeyEvent) event;
				switch(e.getKeyCode()) {
				
				case KeyEvent.VK_UP:
					if (this.getX()%60==0&&this.getY()%60==0) this.setDirection(DIRECTION_UP);
					break;
				case KeyEvent.VK_DOWN:
					if (this.getX()%60==0&&this.getY()%60==0) this.setDirection(DIRECTION_DOWN);
					break;
				case KeyEvent.VK_LEFT:
					if (this.getX()%60==0&&this.getY()%60==0) this.setDirection(DIRECTION_LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					if (this.getX()%60==0&&this.getY()%60==0) this.setDirection(DIRECTION_RIGHT);
					break;
				case KeyEvent.VK_SPACE:
					this.plantBomb(this.playerMap,this.mapX,this.mapY);//Use the space key to plant bomb
					break;
				default:
					this.setDirection(this.getDirection());		
				}

			}
		/*
		 * When the keys are released, set the direction of the player to DIRECTION_STOP.
		 */
		if (event.getID() == KeyEvent.KEY_RELEASED) {
			KeyEvent e = (KeyEvent) event;
		
			if (e.getKeyCode()== KeyEvent.VK_UP||e.getKeyCode()== KeyEvent.VK_DOWN||e.getKeyCode()== KeyEvent.VK_LEFT||e.getKeyCode()== KeyEvent.VK_RIGHT)
			{
				this.stopflag = 1;
				if (this.getX()%60==0&&this.getY()%60==0)  {
					this.setDirection(DIRECTION_STOP);
					
				}
			}
		}
		
	}
	
	/**
	 * Use map to decide the player's location after moving. Can not receive any
	 * command before it moves completely into a new integral cell.
	 * @throws InterruptedException 
	 */
	public void setLocationOnMap(Player player, Map mi, int mapX, int mapY)  {
		 //TODO Move the player, consider walls, bombs. Use setLocation()				
		
		Cell playerCell = mi.getCell(mapX, mapY);

			if (playerCell.isAvailable()==true) {
				
				player.setMapX(mapX);
				player.setMapY(mapY);
			}
	}
	
	
	/**
	 * Update mapinfo BOMB_INFO
	 */
	public void plantBomb(Map mi, int mapx,int mapy ) {
		
		mi.setBomb(mapx,mapy,30);
		
	}
//
//	/**
//	 * Use mapinfo and monsters to decide whether the player is alive, whether the
//	 * player acquire any item, etc.
//	 */
//	public void decideState(Player player, Map mi) {
//
//	}

}