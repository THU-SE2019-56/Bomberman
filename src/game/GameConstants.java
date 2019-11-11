package game;

public interface GameConstants {	
	// DirectionConstants
	int DIRECTION_STOP = -1;
	int DIRECTION_UP = 0;
	int DIRECTION_RIGHT = 1;
	int DIRECTION_DOWN = 2;
	int DIRECTION_LEFT = 3;

	// Player constants
	int PLAYER_WIDTH = 60;
	int PLAYER_HEIGHT = 60;

	// Monster constants
	int MONSTER_NUMBER = 5;
	int MONSTER_WIDTH = 60;
	int MONSTER_HEIGHT = 60;

	// Item constants
	int BOMB_UP = 0;
	int VELOCITY_UP = 1;
	int POWER_UP = 2;
	float ITEM_CHANCE = 0.5f;

	// Display constants
	int CELL_WIDTH = 60;
	int CELL_HEIGHT = 60;
	int CELL_NUM_X = 16;
	int CELL_NUM_Y = 16;
	int SCREEN_WIDTH = CELL_WIDTH * CELL_NUM_X;
	int SCREEN_HEIGHT = CELL_HEIGHT * CELL_NUM_Y;

	// for map
	int GROUND = 0;
	int WALL= 1;
	
	//for game
	int GAMEOVER = 0;
	
	//for bomb
	int BOMB = 0;
}