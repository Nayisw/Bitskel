package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("./res/maps/world01.txt");

    }

    // TEXTURES

    public void getTileImage() {

        try {

            File file = new File("./res/tiles/grass.png");

            FileInputStream fis = new FileInputStream(file);

            tile[0] = new Tile();

            tile[0].image = ImageIO.read(fis);

            file = new File("./res/tiles/wall.png");

            fis = new FileInputStream(file);

            tile[1] = new Tile();

            tile[1].image = ImageIO.read(fis);

            file = new File("./res/tiles/water.png");

            fis = new FileInputStream(file);

            tile[2] = new Tile();

            tile[2].image = ImageIO.read(fis);

            file = new File("./res/tiles/earth.png");

            fis = new FileInputStream(file);

            tile[3] = new Tile();

            tile[3].image = ImageIO.read(fis);

            file = new File("./res/tiles/sand.png");

            fis = new FileInputStream(file);

            tile[4] = new Tile();

            tile[4].image = ImageIO.read(fis);

            file = new File("./res/tiles/tree.png");

            fis = new FileInputStream(file);

            tile[5] = new Tile();

            tile[5].image = ImageIO.read(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //TEXTURE LOADER

    public void loadMap(String filePatth) {
        try {
            InputStream is = new FileInputStream(filePatth);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();
                String numbers[] = line.split(" ");

                while (col < gp.maxWorldCol) {

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize; 
            int worldY = worldRow * gp.tileSize; 
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (    worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }worldCol++;

            if (worldCol == 50) {
                worldCol = 0;
                worldRow++;

            }
        }
    }

}
