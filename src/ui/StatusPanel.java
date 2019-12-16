package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

import game.Game;
import game.GameConstants;
import game.TimerListener;

/**
 * Create a panel on the right side of mapManel. Show the status of player(s)
 * and provide buttons for specific usage. Related to mainFrame in order to
 * realize the "back" function.
 *
 * @author Chengsong Xiong, Wang
 * @version 0.9
 */
public class StatusPanel extends JPanel implements GameConstants {
    private BufferedImage playerImage[] = new BufferedImage[2];

    private BufferedImage bombImage[] = new BufferedImage[2];
    private JTextField bombNum[] = new JTextField[2];
    private JTextField bombPow[] = new JTextField[2];


    private JTextField playerLifeText[] = new JTextField[2];
    private JButton pauseButton = new JButton("Pause");
    private JButton backButton = new JButton("Back");
    private JButton restartButton = new JButton("Restart");
    private Game game;
    private MainFrame mainFrame;
    
	private BufferedImage player1Image[] = new BufferedImage[4];
	private BufferedImage player2Image[] = new BufferedImage[4];
	private BufferedImage player3Image[] = new BufferedImage[4];
	private BufferedImage player4Image[] = new BufferedImage[4];
	
	private Controls control;

    public StatusPanel(Game game, MainFrame mainFrame) {

        this.game = game;
        this.mainFrame = mainFrame;
        
        control = new Controls();

        try {
            loadImage();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        this.setLayout(null);

        for (int i = 0; i < game.getPlayerNum(); i++) {
            int refY = 50 + 150 * i;
            this.playerLifeText[i] = new JTextField("");
            this.playerLifeText[i].setBounds(180, 70 + refY, 40, 20);
            this.bombNum[i] = new JTextField("");
            this.bombNum[i].setBounds(180, refY, 40, 20);
            this.bombPow[i] = new JTextField("");
            this.bombPow[i].setBounds(180, 30 + refY, 40, 20);


            Font textFont = new Font("Times New Roman Italic", Font.BOLD, 14);
            this.playerLifeText[i].setEditable(false);
            this.playerLifeText[i].setForeground(Color.BLUE);
            this.playerLifeText[i].setBorder(null);
            this.playerLifeText[i].setFont(textFont);
            this.playerLifeText[i].setVisible(true);
            this.playerLifeText[i].setBackground(null);
            this.bombNum[i].setEditable(false);
            this.bombNum[i].setBorder(null);
            this.bombNum[i].setFont(textFont);
            this.bombNum[i].setVisible(true);
            this.bombNum[i].setBackground(null);
            this.bombPow[i].setEditable(false);
            this.bombPow[i].setBorder(null);
            this.bombPow[i].setFont(textFont);
            this.bombPow[i].setVisible(true);
            this.bombPow[i].setBackground(null);

            this.add(this.playerLifeText[i]);
            this.add(this.bombNum[i]);
            this.add(this.bombPow[i]);
        }

        // Pause
        control.initializeButton(pauseButton, CELL_WIDTH, 8 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);

        // Back to main menu
        control.initializeButton(backButton, CELL_WIDTH, 10 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);

        // Restart game
        control.initializeButton(restartButton,CELL_WIDTH, 12 * CELL_HEIGHT, 2 * CELL_WIDTH, CELL_HEIGHT);
        
        pauseButton.addMouseListener(new ButtonListener(this.mainFrame));
        backButton.addMouseListener(new ButtonListener(this.mainFrame));
        restartButton.addMouseListener(new ButtonListener(this.mainFrame));
        
        this.add(backButton);
        this.add(pauseButton);
        this.add(restartButton);

    }



    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        for (int i = 0; i < game.getPlayerNum(); i++) {
            int playerHP = game.getPlayer()[i].getHP();
            int playerMaxHP = game.getPlayer()[i].getMaxHP();
            // int playerNormHP = playerHP / playerMaxHP * 100;
            int bombNum = game.getPlayer()[i].getBombMaxNumber() - game.getPlayer()[i].getBombPlantedNumber();
            int bombPow = game.getPlayer()[i].getBombPower();
            int refY = 50 + 150 * i;
      
			//Player	
				switch (game.getPlayer()[i].getPlayerCharacterID()) {
				case 0:
					g.drawImage(player1Image[DIRECTION_DOWN], 50, refY, 50, 50, this);
					break;
				case 1:
					g.drawImage(player2Image[DIRECTION_DOWN], 50, refY, 50, 50, this); 
					break;
				case 2:
					g.drawImage(player3Image[DIRECTION_DOWN], 50, refY, 50, 50, this);
					break;
				case 3:
					g.drawImage(player4Image[DIRECTION_DOWN], 50, refY, 50, 50, this);
					break;
				}
			

            //HP bar
            this.playerLifeText[i].setText(String.valueOf(playerHP));
            g.setColor(Color.BLUE);
            g.drawRect(50, 75 + refY, playerMaxHP, 10);
            g.setColor(Color.getHSBColor((float) playerHP / 300, 1, 1));
            g.fillRect(50, 75 + refY, playerHP, 10);

            // bomb
            this.bombNum[i].setText(" × " + String.valueOf(bombNum));
            this.bombPow[i].setText(" × " + String.valueOf(bombPow));
            g.drawImage(bombImage[0], 150, refY, 20, 20, this);
            g.drawImage(bombImage[1], 150, 30 + refY, 20, 20, this);


        }

    }

    /**
     * Respond to button events
     */
    class ButtonListener implements MouseListener {

        MainFrame mainFrame;

        public ButtonListener(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            /*
             * Pause
             */
            if (e.getSource() == pauseButton) {
                if (game.getPauseFlag() == 0) {
                    game.setPauseFlag(1);
                    pauseButton.setText("Start");
                } else {
                    game.setPauseFlag(0);
                    pauseButton.setText("Pause");
                }
            }

			/*
			 * Back
			 */
			if (e.getSource() == backButton) {
				int gameMode=game.getGameMode();
				StagePanel newStagePanel = new StagePanel(mainFrame,gameMode,game.getPlayer1CID(),game.getPlayer2CID());

                JPanel mainPanel = (JPanel) mainFrame.getContentPane();
                mainPanel.removeAll();

                mainFrame.add(newStagePanel);
                mainFrame.validate();

                mainFrame.setLayout(null);

                newStagePanel.setLocation(0, 0);
                newStagePanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            }

			/*
			 * Restart
			 */
			if (e.getSource() == restartButton) {
				int[][] wallMatrix=StagePanel.loadStage(game.getStageNumber());
				Game newGame =  new Game(wallMatrix,0,0,new int[5],new int[5],game.getGameMode(),game.getStageNumber(),
										game.getPlayer1CID(),game.getPlayer2CID());
				MapPanel newMapPanel = new MapPanel(newGame);
				StatusPanel newStatusPanel = new StatusPanel(newGame, mainFrame);

				JPanel mainPanel = (JPanel) mainFrame.getContentPane();
				mainPanel.removeAll();

				mainFrame.add(newMapPanel);
				mainFrame.validate();// repaint

				mainFrame.add(newStatusPanel);
				mainFrame.validate();// repaint

				mainFrame.setLayout(null);

				newMapPanel.setLocation(0, 0);
				newMapPanel.setSize(MAP_WIDTH, MAP_HEIGHT);

				newStatusPanel.setLocation(MAP_WIDTH, 0);
				newStatusPanel.setSize(STATUS_PANEL_WIDTH, STATUS_PANEL_HEIGHT);

				TimerListener newTimerListener = new TimerListener(newGame, newMapPanel, newStatusPanel);
				Timer newTimer = new Timer(REFRESH, newTimerListener);
				newTimer.start();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {

			if (e.getSource() == pauseButton)
				control.highLightButton(pauseButton);
			else if (e.getSource() == backButton)
				control.highLightButton(backButton);
			else if (e.getSource() == restartButton)
				control.highLightButton(restartButton);

		}

		@Override
		public void mouseExited(MouseEvent e) {

			if (e.getSource() == pauseButton)
				control.resetButton(pauseButton);
			else if (e.getSource() == backButton)
				control.resetButton(backButton);
			else if (e.getSource() == restartButton)
				control.resetButton(restartButton);

		}
	}

	public void loadImage() throws Exception {
		// TODO Load all the images here

        bombImage[0] = ImageIO.read(new File("image/bomb/bomb.png"));
        bombImage[1] = ImageIO.read(new File("image/bomb/explode.png"));

    		
    		player1Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p1UP.png"));
    		player1Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p1RIGHT.png"));
    		player1Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p1DOWN.png"));
    		player1Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p1LEFT.png"));

    		player2Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p2UP.png"));
    		player2Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p2RIGHT.png"));
    		player2Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p2DOWN.png"));
    		player2Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p2LEFT.png"));
    		
    		
    		player3Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p3UP.png"));
    		player3Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p3RIGHT.png"));
    		player3Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p3DOWN.png"));
    		player3Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p3LEFT.png"));

    		player4Image[DIRECTION_UP] = ImageIO.read(new File("image/player/p4UP.png"));
    		player4Image[DIRECTION_RIGHT] = ImageIO.read(new File("image/player/p4RIGHT.png"));
    		player4Image[DIRECTION_DOWN] = ImageIO.read(new File("image/player/p4DOWN.png"));
    		player4Image[DIRECTION_LEFT] = ImageIO.read(new File("image/player/p4LEFT.png"));
    	
    }


}