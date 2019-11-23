package game;

import items.Item;
import map.Map;
import map.MapMatrix;
import monster.Monster;
import player.Player;

/**
 * Integrate all the game information. Reduce coupling. Contains get() and set()
 * methods.
 * 
 * @author Wang
 * @version 0.9
 */
public class Game implements GameConstants {
	private Map map;
	private Monster[] monsters = new Monster[MAX_MONSTER_NUMBER];
	private Player[] player = new Player[MAX_PLAYER_NUMBER];
	private Item item;
	private boolean gameOver = false;
	private int playerNum = 0;// Number of the players
	private int pauseFlag;
	private int gameMode;

	public Game(int gameMode) {

		// Initialize map, player, monsters
		this.map = new Map(new MapMatrix(CELL_NUM_X, CELL_NUM_Y));

		for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
			this.monsters[i] = new Monster(map);
		}

		if (gameMode == PVE_MODE) {
			this.playerNum = 1;
		}
		if (gameMode == PVP_MODE) {
			this.playerNum = 2;
			for (Monster m : getMonsters()) {
				m.eliminate();
			}
		}

		for (int i = 0; i < getPlayerNum(); i++) {
			this.player[i] = new Player(getMap(), i);
		}
		this.item = new Item(1, 1, 1);
		this.setGameMode(gameMode);
		this.pauseFlag = 0;

	}

	public int getPauseFlag() {
		return this.pauseFlag;
	}

	public void setPauseFlag(int pauseFlag) {
		this.pauseFlag = pauseFlag;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Monster[] getMonsters() {
		return monsters;
	}

	public void setMonsters(Monster[] monsters) {
		this.monsters = monsters;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
}
