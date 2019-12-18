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

	MobSpawner monsters[] = new MobSpawner[10];
	int monsterNum;//TODO to be defined, should use this instead of 10

	public MapEditor() {
		this.mapMatrix = new MapMatrix();
		undoStack = new Stack<MapMatrix>();
		redoStack = new Stack<MapMatrix>();
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

	/**
	 * Edit given position according to given mode
	 * 
	 * @param yPos
	 * @param xPos
	 */
	public void editCell(int yPos, int xPos) {
		if (!isInMap(yPos, xPos))
			return;
		switch (getEditingMode()) {
		case REMOVE_WALL:
			getMapMatrix().removeWall(yPos, xPos);
			break;
		case SET_DESTRUCTIBLE_WALL:
			getMapMatrix().setWall(yPos, xPos, true);
			break;
		case SET_INDESTRUCTIBLE_WALL:
			getMapMatrix().setWall(yPos, xPos, false);
			break;
		case REMOVE_MOB:
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
		File savePath = new File("data");
		File fileList[] = savePath.listFiles();
		int totalStageNum = fileList.length;

		FileWriter out;
		try {
			out = new FileWriter(new File("data/stage"+totalStageNum+".txt"));
			
			//save mapMatrix
			for (int i = 0; i < CELL_NUM_X; i++) {
				for (int j = 0; j < CELL_NUM_Y; j++) {
					out.write(mapMatrix.getWall()[i][j] + "\t");
				}
				out.write("\r\n");
			}
			
			
			//save monsterX
			for (int i = 0; i < monsterNum; i++) {
				out.write(monsters[i].getX() + "\t");
			}
			out.write("\r\n");
			
			//save monsterY
			for (int i = 0; i < monsterNum; i++) {
				out.write(monsters[i].getY() + "\t");
			}
			out.write("\r\n");
			
			//save monsterID
			for (int i = 0; i < monsterNum; i++) {
				out.write(monsters[i].getMonsterID() + "\t");
			}
			out.write("\r\n");
			
			//save theme
			out.write(theme);			
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class MobSpawner {
		private int spawnX;
		private int spawnY;
		private int monsterID;

		public MobSpawner(int monsterID, int x, int y) {
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
