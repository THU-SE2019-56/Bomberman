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


    public ThumbnailPanel(int stageNumber) {
        this.loadStage(stageNumber);
        this.map = new Map(wallMatrix);

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
                    g.drawImage(mapImage[GROUND_1], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
                            tinyCellHeight, this);
                else
                    g.drawImage(mapImage[GROUND_2], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight), tinyCellWidth,
                            tinyCellHeight, this);
                if (this.map.isWithDestructibleWall(i, j))
                    g.drawImage(mapImage[DESTRUCTIBLE_WALL], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight),
                            tinyCellWidth, tinyCellHeight, this);
                if (this.map.isWithIndestructibleWall(i, j))
                    g.drawImage(mapImage[INDESTRUCTIBLE_WALL], (int) (i * tinyCellWidth), (int) (j * tinyCellHeight),
                            tinyCellWidth, tinyCellHeight, this);
            }
    }


    private void loadMapImage() throws Exception {
        mapImage[GROUND_1] = ImageIO.read(new File("image/maps/grass1.png"));
        mapImage[GROUND_2] = ImageIO.read(new File("image/maps/grass2.png"));
        mapImage[DESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_destructible.png"));
        mapImage[INDESTRUCTIBLE_WALL] = ImageIO.read(new File("image/maps/wall_indestructibel.png"));
    }
}
