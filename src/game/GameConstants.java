package game;

public interface GameConstants {
	// For All
	int NONE = 0;
	
	// DirectionConstants
	int DIRECTION_STOP = -1;
	int DIRECTION_UP = 0;
	int DIRECTION_RIGHT = 1;
	int DIRECTION_DOWN = 2;
	int DIRECTION_LEFT = 3;

	// Player constants
	int PLAYER_WIDTH = 45;
	int PLAYER_HEIGHT = 55;
	int PLAYER_ID_P1 = 0;
	int PLAYER_ID_P2 = 1;
	int PLAYER_MAX_BOMB = 4;
	int MAX_PLAYER_NUMBER = 2;
	
	int HP_LOSS_BY_MONSTER = 20;
	int HP_LOSS_BY_BOMB = 20;
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
	int PLAYER_CHARACTER2_BOMB_POWER = 3;
	int PLAYER_CHARACTER3_BOMB_POWER = 3;
	int PLAYER_CHARACTER4_BOMB_POWER = 2;
	
	int VELOCITY_0 = 5;
	int VELOCITY_1 = 9;
	int VELOCITY_2 = 15;

	// Monster constants
	int MAX_MONSTER_NUMBER = 5;
	int MONSTER_WIDTH = 45;
	int MONSTER_HEIGHT = 45;
	int ALERT_DISTANCE = 5;
	double MONSTER_SPEED_LOW = 2.0;
	double MONSTER_SPEED_HIGH = 2.5;
	double MONSTER_SPEED_FAST = 4.0;

	// Item constants
	int ITEM_NUM = 7;
	int BOMB_UP = 0;
	int VELOCITY_UP = 1;
	int POWER_UP = 2;
	int HP_UP = 3;
	int BULLET = 4;
	int LANDMINE = 5;
	int IMMUNE = 6;
	int NO_ACTIVE_ITEM = -1;
	
	int ACTIVE_ITEM_NUM = 2;

	int ITEM_WIDTH = 45;
	int ITEM_HEIGHT = 45;
	
	int BULLET_WIDTH = 25;
	int BULLET_HEIGHT = 45;
	
	int HP_ADDED = 20; 

	// Bomb constants
	int BOMB_TIME = 100; // time between plant and explode
	int BOMB_MAX_POWER  = 6;

	// Chance for item to be created
	float ITEM_CHANCE = 0.4f;

	// For display
	int CELL_NUM_X = 16;
	int CELL_NUM_Y = 16;

	int CELL_WIDTH =45;
	int CELL_HEIGHT = 45;
	
	int MAP_WIDTH = CELL_WIDTH*CELL_NUM_X;
	int MAP_HEIGHT = CELL_HEIGHT*CELL_NUM_Y;

	int STATUS_PANEL_WIDTH = 253;
	int STATUS_PANEL_HEIGHT = 712;
	
	int WINDOW_WIDTH = 1080;
	int WINDOW_HEIGHT = 785;

	// For thumbnail panel
	int THUMBNAIL_WIDTH=420;
	int THUMBNAIL_HEIGHT=420;
	int SCALED_THUMBNAIL_WIDTH=400;
	int SCALED_THUMBNAIL_HEIGHT=400;

	// For map image
	int GRASS_1 = 0;
	int GRASS_2 = 1;
	int SAND_1 = 2;
	int SAND_2 = 3;
	
	int GROUND_1 = 0;
	int GROUND_2 = 1;
	int INDESTRUCTIBLE_WALL = 2;
	int DESTRUCTIBLE_WALL = 3;

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
	int DESTRUCTIBLE = -1;
	int INDESTRUCTIBLE = 1;

	// For Refresh
	int REFRESH = 30;
	
	// For Stage Editor
	int REMOVE_WALL = 1;
	int SET_DESTRUCTIBLE_WALL = 2;
	int SET_INDESTRUCTIBLE_WALL = 3;
	int REMOVE_MOB = 4;
	int ADD_TYARNNOSAURUS = 5;
	int ADD_TRICERATOPS = 6;
	int ADD_FROG = 7;
	int ADD_PARROT = 8;
	
	int PLAYER_SPAWNER = 1;
	int MONSTER_SPAWNER = 2;
	
	// For Buttons
	int BUTTON_WIDTH=280;
	int BUTTON_HEIGHT=113;
	int SCALED_BUTTON_WIDTH=196;
	int SCALED_BUTTON_HEIGHT=79;
	int SMALL_BUTTON_WIDTH=120;
	int SMALL_BUTTON_HEIGHT=48;
	int STORY_WIDTH=774;
	int STORY_HEIGHT=210;
	int ARROW_WIDTH=100;
	int ARROW_HEIGHT=213;
}