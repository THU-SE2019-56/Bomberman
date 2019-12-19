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
	private Monster[] monsters;
	private Player[] player = new Player[MAX_PLAYER_NUMBER];
	private Item item;
	private boolean gameOver = false;
	private int playerNum = 0;// Number of the players
	private int pauseFlag;
	private int gameMode;
	private int player1CID;
	private int player2CID;
	private int theme;// 0,1,2,3
	private int stageNumber;
	
	private int[][] wallMatrix;
	private int[] monsterX;
	private int[] monsterY;
	private int[] monsterID;
	

	/**
	 * Only for PVE mode, choose stage Different type of monsters, number of
	 * monsters...
	 */
	public Game(int[][] wallMatrix, int[] monsterX, int[] monsterY, int[] monsterID, int gameMode, int theme, int stageNumber, int player1CharacterID,
			int player2CharacterID) {
		this.map = new Map(wallMatrix);
		this.setWallMatrix(wallMatrix);
		this.setMonsterX(monsterX);
		this.setMonsterY(monsterY);
		this.setMonsterID(monsterID);
		
		monsters=new Monster[monsterX.length];
		if (gameMode == PVE_MODE) {
			for (int i = 0; i < monsters.length; i++) {		// if monster's position is -1, monster will random generate
				if (monsterID[i]==-1 && (monsterX[i]==-1 || monsterY[i]==-1))
					this.monsters[i] = MonsterFactory.getRandomMonster(this.map);
				else if (monsterID[i]==-1)
					this.monsters[i] = MonsterFactory.getRandomMonster(monsterX[i]*CELL_WIDTH,
							monsterY[i]*CELL_HEIGHT);
				else if (monsterX[i]==-1 || monsterY[i]==-1)
					this.monsters[i] = MonsterFactory.getMonster(monsterID[i], this.map);
				else
					this.monsters[i] = MonsterFactory.getMonster(monsterID[i],
							monsterX[i]*CELL_WIDTH,monsterY[i]*CELL_HEIGHT);
			}
		}

		if (gameMode == PVE_MODE) {
			this.playerNum = 1;
		}
		if (gameMode == PVP_MODE) {
			this.playerNum = 2;
		}
		for (int i = 0; i < getPlayerNum(); i++) {
			if (i == PLAYER_ID_P1) {
				this.player[i] = new Player(getMap(), i, player1CharacterID);
			} else if (i == PLAYER_ID_P2) {
				this.player[i] = new Player(getMap(), i, player2CharacterID);
			}
		}

		this.item = new Item(2, 2);
		// Items should be generated when wall explodes, not when game starts.

		this.gameMode = gameMode;
		this.theme = theme;
		this.stageNumber=stageNumber;
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

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	/**
	 * @return the stageNumber
	 */
	public int getStageNumber() {
		return stageNumber;
	}

	/**
	 * @param stageNumber the stageNumber to set
	 */
	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public String refresh() {
		if (this.getGameMode() == PVE_MODE) {
			if (this.getPlayer()[PLAYER_ID_P1].getHP() <= 0) {
				this.setGameOver(true);
				return "monsterWin";
			} else {
				boolean monstersAlive = false;
				for (Monster m : this.getMonsters()) {
					monstersAlive |= m.isAlive();
				}
				if (!monstersAlive) {
					this.setGameOver(true);
					return "playerWin";
				}
			}
		} else if (this.getGameMode() == PVP_MODE) {
			if (this.getPlayer()[PLAYER_ID_P1].getHP() <= 0 && this.getPlayer()[PLAYER_ID_P2].getHP() > 0) {
				this.setGameOver(true);
				return "player1Win";
			} else if (this.getPlayer()[PLAYER_ID_P2].getHP() <= 0 && this.getPlayer()[PLAYER_ID_P1].getHP() > 0) {
				this.setGameOver(true);
				return "player2Win";
			} else if (this.getPlayer()[PLAYER_ID_P1].getHP() <= 0 && this.getPlayer()[PLAYER_ID_P2].getHP() <= 0) {
				this.setGameOver(true);
				return "tie";
			}
		}
		return "";
	}

	/**
	 * @return the wallMatrix
	 */
	public int[][] getWallMatrix() {
		return wallMatrix;
	}

	/**
	 * @param wallMatrix the wallMatrix to set
	 */
	public void setWallMatrix(int[][] wallMatrix) {
		this.wallMatrix = wallMatrix;
	}

	/**
	 * @return the monsterX
	 */
	public int[] getMonsterX() {
		return monsterX;
	}

	/**
	 * @param monsterX the monsterX to set
	 */
	public void setMonsterX(int[] monsterX) {
		this.monsterX = monsterX;
	}

	/**
	 * @return the monsterY
	 */
	public int[] getMonsterY() {
		return monsterY;
	}

	/**
	 * @param monsterY the monsterY to set
	 */
	public void setMonsterY(int[] monsterY) {
		this.monsterY = monsterY;
	}

	/**
	 * @return the monsterID
	 */
	public int[] getMonsterID() {
		return monsterID;
	}

	/**
	 * @param monsterID the monsterID to set
	 */
	public void setMonsterID(int[] monsterID) {
		this.monsterID = monsterID;
	}
}
