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
	int PLAYER_HEIGHT = 57;
	int PLAYER_ID_P1 = 0;
	int PLAYER_ID_P2 = 1;
	int PLAYER_MAX_BOMB = 4;
	int MAX_PLAYER_NUMBER = 2;
	
	int HP_LOSS_BY_MONSTER = 5;
	int HP_LOSS_BY_BOMB = 50;
	int HP_MAX = 100;
	
	
	int PLAYER_CHARACTER1_HP_MAX = 100;
	int PLAYER_CHARACTER2_HP_MAX = 90;
	int PLAYER_CHARACTER3_HP_MAX = 80;
	int PLAYER_CHARACTER4_HP_MAX = 80;
	
	int PLAYER_CHARACTER1_BOMB_MAX = 2;
	int PLAYER_CHARACTER2_BOMB_MAX = 1;
	int PLAYER_CHARACTER3_BOMB_MAX = 2;
	int PLAYER_CHARACTER4_BOMB_MAX = 3;
	
	
	int PLAYER_CHARACTER1_BOMB_POWER = 1;
	int PLAYER_CHARACTER2_BOMB_POWER = 1;
	int PLAYER_CHARACTER3_BOMB_POWER = 3;
	int PLAYER_CHARACTER4_BOMB_POWER = 2;

	// Monster constants
	int MAX_MONSTER_NUMBER = 5;
	int MONSTER_WIDTH = 45;
	int MONSTER_HEIGHT = 45;
	int ALERT_DISTANCE = 5;
	double MONSTER_SPEED_LOW = 1.5;
	double MONSTER_SPEED_HIGH = 2.5;

	// Item constants
	int ITEM_NUM = 4;
	int BOMB_UP = 0;
	int VELOCITY_UP = 1;
	int POWER_UP = 2;
	int HP_UP = 3;
	
	int ITEM_WIDTH = 45;
	int ITEM_HEIGHT = 45;
	
	int HP_ADDED = 20; 

	// Bomb constants
	int BOMB_TIME = 100; // time between plant and explode
	int BOMB_MAX_POWER  = 6;

	// Chance for item to be created
	float ITEM_CHANCE = 0.4f;

	// For display
	int CELL_WIDTH = 45;
	int CELL_HEIGHT = 45;

	int CELL_NUM_X = 16;
	int CELL_NUM_Y = 16;

	int MAP_WIDTH = CELL_WIDTH*CELL_NUM_X;
	int MAP_HEIGHT = CELL_HEIGHT*CELL_NUM_Y;
	
	int STATUS_PANEL_WIDTH = CELL_WIDTH * 5;
	int STATUS_PANEL_HEIGHT = CELL_HEIGHT * 16;
	
	int WINDOW_WIDTH = CELL_WIDTH*CELL_NUM_X+STATUS_PANEL_WIDTH;
	int WINDOW_HEIGHT = CELL_HEIGHT*CELL_NUM_Y;

	// For thumbnail panel
	int SCALE_FACTOR = 3;


	// For map image
	int GROUND_1 = 0;
	int GROUND_2 = 1;
	int DESTRUCTIBLE_WALL = 2;
	int INDESTRUCTIBLE_WALL = 3;

	// For bomb image
	int EXPLODE = 0;
	int BOMB = 1;
	int BOMB_WIDTH = 45;
	int BOMB_HEIGHT = 45;

	// For game
	int GAMEOVER = 0;
	int PVE_MODE = 0;
	int PVP_MODE = 1;

	// For MatMatrix
	int NONE = 0;
	int DESTRUCTIBLE = -1;
	int INDESTRUCTIBLE = 1;

	// For Refresh
	int REFRESH = 30;
}