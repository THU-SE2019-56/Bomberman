package bomb;

import map.Map;

/**
 * Responsible for all the bomb methods, including exploding when time is up, chain exploding
 * 
 * @author Wang
 * @version 0.1
 */
public class Bomb{
	
	private int bomb_pow;
	private int x;
	private int y;
	
	public Bomb(int x,int y,int bomb_pow){
		this.x=x;
		this.y=y;
		this.bomb_pow=bomb_pow;
	}
	
	/**
	 * Update the mapinfo EXPLOSION_INFO
	 * 
	 * @param mapinfo
	 */
	public void explode(Map mapinfo) {
		
	}
}
