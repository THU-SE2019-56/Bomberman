package ui;

import game.GameConstants;
import map.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Display thumbnail of the mapPanel, only include ground & walls.
 * The size of thumbnail is defined in GameConstants:SCALE_FACTOR, which means
 * how many times is mapPanel larger than thumbnailPanel.
 *
 * @author Hang Chen
 * @version 0.1
 */

public class ThumbnailPanel extends JPanel implements GameConstants {
    private final static int tinyCellWidth = CELL_WIDTH/SCALE_FACTOR;
    private final static int tinyCellHeight = CELL_HEIGHT/SCALE_FACTOR;
    private Map map;
    private int[][] wallMatrix = new int[CELL_NUM_X][CELL_NUM_Y];
    BufferedImage mapImage[] = new BufferedImage[4];
	BufferedImage wallImage[][] = new BufferedImage[4][8];
	private int stageNumber;


    public ThumbnailPanel(int stageNumber) {
        this.loadStage(stageNumber);
        this.map = new Map(wallMatrix);
        this.stageNumber = stageNumber - 1;

        try {
            loadMapImage();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintMap(g);
    }


    private void loadStage(int stageNumber) {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(new File("data/stage"+stageNumber+".txt")));
            String line;
            int row = 0;
            while ((line = in.readLine()) != null) {
                String[] temp = line.split("\t");
                for (int j = 0; j < temp.length; j++) {
                    wallMatrix[row][j] = Integer.parseInt(temp[j]);
                }
                row++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void paintMap(Graphics g) {
        int xSize = this.map.getSizeX();
        int ySize = this.map.getSizeY();
        for (int i = 0; i < xSize; i++)
            for (int j = 0; j < ySize; j++) {
            	if ((i + j) % 2 == 0)
					g.drawImage(mapImage[GRASS_1], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
							tinyCellHeight, this);
				else
					g.drawImage(mapImage[GRASS_2], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
							tinyCellHeight, this);
				if (this.map.isWithWall(i, j))
					g.drawImage(wallImage[stageNumber][this.map.getWallID(i, j)], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight),
							tinyCellWidth, tinyCellHeight, this);
            }
    }


    private void loadMapImage() throws Exception {
    	mapImage[GRASS_1] = ImageIO.read(new File("image/maps/grass1.png"));
		mapImage[GRASS_2] = ImageIO.read(new File("image/maps/grass2.png"));
		mapImage[SAND_1] = ImageIO.read(new File("image/maps/wall_destructible.png"));
		mapImage[SAND_2] = ImageIO.read(new File("image/maps/wall_indestructibel.png"));

		wallImage[0][0] = ImageIO.read(new File("image/maps/wall1-1.png"));
		wallImage[0][1] = ImageIO.read(new File("image/maps/wall1-2.png"));
		wallImage[0][2] = ImageIO.read(new File("image/maps/wall1-3.png"));
		wallImage[0][3] = ImageIO.read(new File("image/maps/wall1-4.png"));
		wallImage[0][4] = ImageIO.read(new File("image/maps/wall1-5.png"));
		wallImage[0][5] = ImageIO.read(new File("image/maps/wall1-6.png"));
		wallImage[0][6] = ImageIO.read(new File("image/maps/wall1-7.png"));
		wallImage[0][7] = ImageIO.read(new File("image/maps/wall1-8.png"));
		
		wallImage[1][0] = ImageIO.read(new File("image/maps/wall2-1.png"));
		wallImage[1][1] = ImageIO.read(new File("image/maps/wall2-2.png"));
		wallImage[1][2] = ImageIO.read(new File("image/maps/wall2-3.png"));
		wallImage[1][3] = ImageIO.read(new File("image/maps/wall2-4.png"));
		wallImage[1][4] = ImageIO.read(new File("image/maps/wall2-5.png"));
		wallImage[1][5] = ImageIO.read(new File("image/maps/wall2-6.png"));
		wallImage[1][6] = ImageIO.read(new File("image/maps/wall2-7.png"));
		wallImage[1][7] = ImageIO.read(new File("image/maps/wall2-8.png"));

		wallImage[2][0] = ImageIO.read(new File("image/maps/wall3-1.png"));
		wallImage[2][1] = ImageIO.read(new File("image/maps/wall3-2.png"));
		wallImage[2][2] = ImageIO.read(new File("image/maps/wall3-3.png"));
		wallImage[2][3] = ImageIO.read(new File("image/maps/wall3-4.png"));
		wallImage[2][4] = ImageIO.read(new File("image/maps/wall3-5.png"));
		wallImage[2][5] = ImageIO.read(new File("image/maps/wall3-6.png"));
		wallImage[2][6] = ImageIO.read(new File("image/maps/wall3-7.png"));
		wallImage[2][7] = ImageIO.read(new File("image/maps/wall3-8.png"));
		
		wallImage[3][0] = ImageIO.read(new File("image/maps/wall4-1.png"));
		wallImage[3][1] = ImageIO.read(new File("image/maps/wall4-2.png"));
		wallImage[3][2] = ImageIO.read(new File("image/maps/wall4-3.png"));
		wallImage[3][3] = ImageIO.read(new File("image/maps/wall4-4.png"));
		wallImage[3][4] = ImageIO.read(new File("image/maps/wall4-5.png"));
		wallImage[3][5] = ImageIO.read(new File("image/maps/wall4-6.png"));
		wallImage[3][6] = ImageIO.read(new File("image/maps/wall4-7.png"));
		wallImage[3][7] = ImageIO.read(new File("image/maps/wall4-8.png"));
		
    }
}
