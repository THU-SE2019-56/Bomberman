package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import items.Item;

import player.Player;
import map.Cell;
import map.Map;
import monster.Monster;

/**
 * v0.1 <br>
 * Display the map. <br>
 * Draw all the players, enemies, walls, roads, etc. In fact, this class should
 * mainly just focuses on "drawing". Logical methods may be implemented in other
 * classes.
 * <p>
 * v0.2 <br>
 * Draw the player and achieve simple visual animation effects.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.2
 */
public class Display extends JPanel implements ActionListener, GameConstants {
	private Timer timer;
	private final int REFRESH = 30; // Refresh(repaint) every 30 milliseconds
	private Map map;
	private Player player;
	private Monster[] monsters = new Monster[MONSTER_NUMBER];
	private Item item;
	private boolean gameOver = false;

    BufferedImage characterImage[] = new BufferedImage[4];
    BufferedImage itemImage[] = new BufferedImage[3];
    BufferedImage monsterImage[] = new BufferedImage[4];
	BufferedImage mapImage[] = new BufferedImage[2];
	BufferedImage gameImage[] = new BufferedImage[3];

    /**
     *
     * The method “public static void main(String args[]) 17 is achieved here to test
     * the effects of the Player class. Remove it when you don't need it.
     */
    public static void main(String args[]) {
        createPanel();
    }

    /**
     * Initialize the Display class.
     */
    public Display() {
        try {
            loadImage();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        timer = new Timer(REFRESH, this);
        timer.start();

        // initializeMap(); May delete.

		map = new Map();
        player = new Player(map);
        item = new Item();
        for (int i = 0; i < MONSTER_NUMBER; i++) {
            monsters[i] = new Monster(map);
        }

        this.setFocusable(true);
        this.getToolkit().addAWTEventListener(player, AWTEvent.KEY_EVENT_MASK);// Initialize the AWTEventListener.
    }

    /**
     * to detect if the player and items are collided
     * @return
     */
    public boolean itemCollisionDetection() {
        int playerX = player.getX();
        int playerY = player.getY();
        int itemX = item.getX();
        int itemY = item.getY();
        int itemSize = 60;

        Rectangle playerRectangle = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        Rectangle itemRectangle = new Rectangle(itemX, itemY, 60, 60);

        if(playerRectangle.intersects(itemRectangle)) {
      
            item.getItem(player);
            
            item.setIsAcquired(true);
            return item.getIsAcquired();
        }
        else return item.getIsAcquired();


    }

	/**
	 * Create JFrame and JPanel. Temp method.
	 */
	public static void createPanel() {
		JFrame f = new JFrame();
		Display jp = new Display();
		f.setTitle("Bomberman");
        f.getContentPane().setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        //f.setLocationRelativeTo(null);  // set location to screen center  
        //--this method will lead to the failure of the game, and I don't know why..... Perhaps owning to the macOS system
		f.setVisible(true);
		jp.setVisible(true);
		f.add(jp);
	}

	public void endGame(Graphics g) {
		g.drawImage(gameImage[GAMEOVER], 230, 230, 500, 300, this);
		this.gameOver = true;
	}
	
    /**
     * All painting methods are invoked in "paintComponent(Graphics g)".
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		paintMap(g);
        paintPlayer(g);
        paintMonsters(g);
        if(!itemCollisionDetection()) {
            paintItem(g);
        }
        if (player.getHP()==0) {
        	endGame(g);
        
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Paint the image of the player.
     */
    public void paintPlayer(Graphics g) {
        g.drawImage(characterImage[player.getImageDirection()], player.getX(), player.getY(), PLAYER_WIDTH, PLAYER_HEIGHT, this);
        g.setColor(Color.BLUE);
        g.drawRect(player.getX(), player.getY()-15, 60, 10);
        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY()-15, player.getHP(), 10);
    }

    public void paintItem(Graphics g) {
        g.drawImage(itemImage[item.getItemID()], item.getX(), item.getY(), 60, 60, this);
    }


	/**
	 * Paint a simple map for testing.
	 */
	public void paintMap(Graphics g) {
		for (byte i=0;i<map.getSizeX();i++)
		{
			for (byte j=0;j<map.getSizeY();j++) {
				if((j==4||j==9||j==11)&&(i==3||i==6||i==9||i==10||i==11)) {
					Cell mapCell = map.map(j, i);
					mapCell.setWall(true);
					g.drawImage(mapImage[WALL], (int)(i*60), (int)(j*60), 60,60,this);
				}
				else {
					g.drawImage(mapImage[GROUND], (int)(i*60), (int)(j*60), 60,60,this);
				}
			}
		}
	}

    public void paintMonsters(Graphics g) {
        for (Monster m: monsters) {
            if (m.isAlive()) {
                g.drawImage(monsterImage[m.getImageDirection()],
                        m.getX(), m.getY(), MONSTER_WIDTH, MONSTER_HEIGHT, this);
            }
        }
    }

    /**
     * Repaint the whole component. All the logical methods necessary should have
     * been implemented in other classes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * TODO Every 30 milliseconds, do (at least) the following things: Move the
         * player to new location, player.Player.setNewLocation(). Move the monsters to
         * new location, monster.Monster.setNewLocation(). Decide whether the player is
         * alive. If new bombs are planted? Have bombs exploded? Eliminate those walls
         * and monsters that have been boomed.
         */
        player.playerMove();// Change the location of the player
        for (Monster m: monsters) {	// Change the location of monsters
            if (m.isAlive()) {
                m.monsterMove(player, map);
                m.refresh();
            }
        }
        
        if(this.gameOver == false) {
            repaint();
        }
    }

    /**
     * This method should be invoked in the constructor to initialize. <br>
     * Consider all components including player, walls, monsters, etc. <br>
     * Perhaps using paintComponent(Graphics g) will be a more appropriate method＄1�7
     * ---Comment from Chengsong Xiong <br>
     * What I mean is, for example, setting the player's initial place or velocity
     * here, not painting. ---Comment from Wang <br>
     * Another thought, we should do all these in the construction methods. Maybe
     * this method should be deleted. ---Comment from Wang
     */
    public void initializeMap() {
        // TODO
    }

    public void loadImage() throws Exception {
        // TODO Load all the images here

        characterImage[DIRECTION_UP] = ImageIO.read(new File("image/character/characterBack.png"));
        characterImage[DIRECTION_RIGHT] = ImageIO.read(new File("image/character/characterRight.png"));
        characterImage[DIRECTION_DOWN] = ImageIO.read(new File("image/character/characterFront.png"));
        characterImage[DIRECTION_LEFT] = ImageIO.read(new File("image/character/characterLeft.png"));
        itemImage[VELOCITY_UP] = ImageIO.read(new File("image/Item/velocity.png"));
        monsterImage[DIRECTION_UP] = ImageIO.read(new File("image/monster/up.png"));
        monsterImage[DIRECTION_DOWN] = ImageIO.read(new File("image/monster/down.png"));
        monsterImage[DIRECTION_RIGHT] = ImageIO.read(new File("image/monster/right.png"));
        monsterImage[DIRECTION_LEFT] = ImageIO.read(new File("image/monster/left.png"));
		mapImage[GROUND] =ImageIO.read(new File("image/maps/ground.png"));
		mapImage[WALL] = ImageIO.read(new File("image/maps/wall.png"));
		gameImage[GAMEOVER] = ImageIO.read(new File("image/game/gameover.jpg"));
    }
}