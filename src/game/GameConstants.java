package game;

public interface GameConstants {	
	// DirectionConstants
	int DIRECTION_STOP = -1;
	int DIRECTION_UP = 0;
	int DIRECTION_RIGHT = 1;
	int DIRECTION_DOWN = 2;
	int DIRECTION_LEFT = 3;

	// Player constants
	int PLAYER_WIDTH = 45;
	int PLAYER_HEIGHT = 45;
	int PLAYER_ID_P1 = 0;
	int PLAYER_ID_P2 = 1;

	// Monster constants
	int MONSTER_NUMBER = 5;
	int MONSTER_WIDTH = 45;
	int MONSTER_HEIGHT = 45;
	int HP_LOSS_BY_MONSTER = 15;
	int ALERT_DISTANCE = 5;

	// Item constants
	int BOMB_UP = 0;
	int VELOCITY_UP = 1;
	int POWER_UP = 2;
	int ITEM_WIDTH = 45;
	int ITEM_HEIGHT = 45;

	// Bomb constants
	int BOMB_TIME = 100; // time between plant and explode
	

	// Chance for item to be created
	float ITEM_CHANCE = 0.5f;

	// Display constants
	int CELL_SIZE = 45;
	int CELL_WIDTH = 45;
	int CELL_HEIGHT = 45;
	
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
	int EXPLODE = 0;
	int BOMB = 1;
	int BOMB_WIDTH = 45;
	int BOMB_HEIGHT = 45;
	
	// For game
	int GAMEOVER = 0;
	
	// For MatMatrix
	int NONE = 0;
	int DESTRUCTIBLE = -1;
	int INDESTRUCTIBLE = 1;
}