package game;

import java.io.Serializable;

import items.Item;
import map.Map;
import monster.Monster;
import monster.MonsterFactory;
import player.Player;

/**
 * Integrate all the game information. Reduce coupling. Contains get() and set()
 * methods.
 * 
 * @author Wang
 * @version 0.9
 */
public class Game implements GameConstants, Serializable {
	private Map map;
	private Monster[] monsters = new Monster[MAX_MONSTER_NUMBER];
	private Player[] player = new Player[MAX_PLAYER_NUMBER];
	private Item item;
	private boolean gameOver = false;
	private int playerNum = 0;// Number of the players
	private int pauseFlag;
	private int gameMode;
	private int stageNumber;
	private int player1CID;
	private int player2CID;
	
	/**
	 * Only for PVE mode, choose stage Different type of monsters, number of
	 * monsters...
	 */
	public Game(int[][] wallMatrix, int playerX, int playerY, int[] monsterX, int[] monsterY, int gameMode,
			int stageNumber,int player1CharacterID,int player2CharacterID) {
		this.map = new Map(wallMatrix);

		// TODO Generate player and monsters according to X and Y
		for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
			this.monsters[i] = MonsterFactory.getRandomMonster(map);
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
			if(i == PLAYER_ID_P1)  this.player[i] = new Player(getMap(), i,player1CharacterID);
			else if (i==PLAYER_ID_P2) this.player[i] = new Player(getMap(),i,player2CharacterID);
		}

		this.item = new Item(2, 2);
		// Items should be generated when wall explodes, not when game starts.

		this.gameMode = gameMode;
		this.stageNumber = stageNumber;
		this.pauseFlag = 0;
		this.player1CID = player1CharacterID;
		this.player2CID = player2CharacterID;
		this.pauseFlag = 0;

	}

	public int getPlayer1CID() {
		return player1CID;
	}
	public int getPlayer2CID() {
		return player2CID;
	}

	public int getPauseFlag() {
		return pauseFlag;
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

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}
}
