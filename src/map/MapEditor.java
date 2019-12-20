package map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import game.GameConstants;

public class MapEditor implements GameConstants {

	// Editing MapMatrix
	private MapMatrix mapMatrix;
	// For undo and redo
	Stack<MapMatrix> undoStack;
	Stack<MapMatrix> redoStack;

	private int editingMode = NONE;
	private int theme = 0;
	private int stageNum;

	MonsterSpawner monsters[] = new MonsterSpawner[MAX_MONSTER_NUMBER];
	int monsterNum;// TODO to be defined, should use this instead of 10

	public MapEditor() {
		this.mapMatrix = new MapMatrix();
		undoStack = new Stack<MapMatrix>();
		redoStack = new Stack<MapMatrix>();

		// get stage number
		File savePath = new File("data");
		File fileList[] = savePath.listFiles();
		stageNum = fileList.length;
	}

	/**
	 * @return if given position is in current editing mmat
	 */
	public boolean isInMap(int yPos, int xPos) {
		return xPos >= 0 && xPos < getMapMatrix().getXSize() && yPos >= 0 && yPos < getMapMatrix().getYSize();
	}

	public void switchMode(int mode) {
		if (getEditingMode() == mode)
			setEditingMode(NONE);
		else
			setEditingMode(mode);
	}

	public boolean isWithPlayer(int yPos, int xPos) {
		return (yPos == 0 && xPos == 0) || (yPos == 15 && xPos == 15);
	}

	public boolean isWithMonster(int yPos, int xPos) {
		for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
			if (monsters[i] == null)
				continue;
			if (monsters[i].getX() == xPos && monsters[i].getY() == yPos)
				return true;
		}
		return false;
	}

	public boolean isAvailable(int yPos, int xPos) {
		return !isWithPlayer(yPos, xPos) && !isWithMonster(yPos, xPos) && !mapMatrix.isWithWall(yPos, xPos);
	}

	/**
	 * Edit given position according to given mode
	 * 
	 * @param yPos
	 * @param xPos
	 */
	public void editCell(int yPos, int xPos) {
		if (!isInMap(yPos, xPos))
			return;
		switch (editingMode) {
		case REMOVE_WALL:
			mapMatrix.removeWall(yPos, xPos);
			break;
		case SET_DESTRUCTIBLE_WALL:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				mapMatrix.setWall(yPos, xPos, true);
			}
			break;
		case SET_INDESTRUCTIBLE_WALL:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				mapMatrix.setWall(yPos, xPos, false);
			}
			break;
		case REMOVE_MONSTER:
			if (isWithMonster(yPos, xPos)) {
				saveStatus();
				removeMonster(yPos, xPos);
			}
			break;
		case ADD_TYRANNOSAURUS:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				addMonster(new MonsterSpawner(TYRANNOSAURUS_ID, yPos, xPos));
			}
			break;
		case ADD_TRICERATOPS:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				addMonster(new MonsterSpawner(TRICERATOPS_ID, yPos, xPos));
			}
			break;
		case ADD_FROG:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				addMonster(new MonsterSpawner(FROG_ID, yPos, xPos));
			}
			break;
		case ADD_PARROT:
			if (isAvailable(yPos, xPos)) {
				saveStatus();
				addMonster(new MonsterSpawner(PARROT_ID, yPos, xPos));
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Save current mapmatrix into undoStack
	 */
	public void saveStatus() {
		undoStack.push(new MapMatrix(getMapMatrix()));
		redoStack.clear();
	}

	/**
	 * Track back to last mapmatrix in undoStack
	 */
	public void undo() {
		if (undoStack.empty()) {
//			System.out.println("undo stack empty");
			return;
		}
		redoStack.push(new MapMatrix(getMapMatrix()));
		setMapMatrix(undoStack.pop());
	}

	/**
	 * 
	 */
	public void redo() {
		if (redoStack.empty()) {
//			System.out.println("redo stack empty");
			return;
		}
		undoStack.push(new MapMatrix(getMapMatrix()));
		setMapMatrix(redoStack.pop());
	}

	public MapMatrix getMapMatrix() {
		return mapMatrix;
	}

	public void setMapMatrix(MapMatrix mapMatrix) {
		this.mapMatrix = mapMatrix;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getEditingMode() {
		return editingMode;
	}

	public void setEditingMode(int editingMode) {
		this.editingMode = editingMode;
	}

	public void saveToTxt() {
		FileWriter out;
		try {
			out = new FileWriter(new File("data/stage" + stageNum + ".txt"));

			// save mapMatrix
			for (int i = 0; i < CELL_NUM_X; i++) {
				for (int j = 0; j < CELL_NUM_Y; j++) {
					out.write(mapMatrix.getWall()[i][j] + "\t");
				}
				out.write("\r\n");
			}

			// save monsterX
			for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
				if (monsters[i]!=null)
					out.write(monsters[i].getX() + "\t");
			}
			out.write("\r\n");

			// save monsterY
			for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
				if (monsters[i]!=null)
					out.write(monsters[i].getY() + "\t");
			}
			out.write("\r\n");

			// save monsterID
			for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
				if (monsters[i]!=null)
					out.write(monsters[i].getMonsterID() + "\t");
			}
			out.write("\r\n");

			// save theme
			out.write(""+theme);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean addMonster(MonsterSpawner ms) {
		if (!isInMap(ms.getY(), ms.getX()))
			return false;
		int i = 0;
		for (; i < MAX_MONSTER_NUMBER; i++) {
			if (monsters[i] == null) {
				monsters[i] = ms;
				return true;
			}
			if (monsters[i].getX() == ms.getX() && monsters[i].getY() == ms.getY())
				return false;
		}
		return false;
	}

	public boolean removeMonster(int yPos, int xPos) {
		for (int i = 0; i < MAX_MONSTER_NUMBER; i++) {
			if (monsters[i] == null)
				continue;
			if (monsters[i].getX() == xPos && monsters[i].getY() == yPos) {
				monsters[i] = null;
				return true;
			}
		}
		return false;
	}

	public MonsterSpawner getMonster(int i) {
		return monsters[i];
	}

	public int getMonsterID(int i) {
		if (monsters[i] == null)
			return -1;
		return monsters[i].getMonsterID();
	}

	public int getMonsterX(int i) {
		if (monsters[i] == null)
			return -1;
		return monsters[i].getX();
	}

	public int getMonsterY(int i) {
		if (monsters[i] == null)
			return -1;
		return monsters[i].getY();
	}

	class MonsterSpawner {
		private int spawnX;
		private int spawnY;
		private int monsterID;

		public MonsterSpawner(int monsterID, int y, int x) {
			this.monsterID = monsterID;
			this.spawnX = x;
			this.spawnY = y;
		}

		public int getX() {
			return spawnX;
		}

		public int getY() {
			return spawnY;
		}

		public int getMonsterID() {
			return monsterID;
		}
	}
}
