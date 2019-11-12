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
	
	// Chance for item to be created
	float ITEM_CHANCE = 0.5f;

	// Display constants
	int CELL_SIZE = 60;
	int CELL_NUM_X = 16;
	int CELL_NUM_Y = 16;
	int SCREEN_WIDTH = CELL_SIZE * CELL_NUM_X;
	int SCREEN_HEIGHT = CELL_SIZE * CELL_NUM_Y;

	// For map image
	int GROUND_1 = 0;
	int GROUND_2 = 1;
	int DESTRUCTIBLE_WALL= 2;
	int INDESTRUCTIBLE_WALL = 3;

	// For bomb image
	int BOMB = 0;
	
	// For game
	int GAMEOVER = 0;
	
	// For MatMatrix
	int NONE = 0;
	int DESTRUCTIBLE = -1;
	int INDESTRUCTIBLE = 1;
}