package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("./res/maps/world2.txt");
    }

    // TEXTURES
    public void getTileImage() {

        //PLACEHOLDERS
        setup(0, "001", false);
        setup(1, "001", false);
        setup(2, "001", false);
        setup(3, "001", false);
        setup(4, "001", false);
        setup(5, "001", false);
        setup(6, "001", false);
        setup(7, "001", false);
        setup(8, "001", false);
        setup(9, "001", false);

        setup(10, "001", false);
        setup(11, "002", false);
        setup(12, "003", false);
        setup(13, "004", false);
        setup(14, "005", false);
        setup(15, "006", false);
        setup(16, "007", false);
        setup(17, "008", false);
        setup(18, "009", false);
        setup(19, "010", false);
        setup(20, "011", false);
        setup(21, "012", false);
        setup(22, "013", false);
        setup(23, "014", false);
        setup(24, "015", false);
        setup(25, "016", true);
        setup(26, "017", false);
        setup(27, "018", true);
        setup(28, "019", true);
        setup(29, "020", true);
        setup(30, "021", true);
        setup(31, "022", true);
        setup(32, "023", true);
        setup(33, "024", true);
        setup(34, "025", true);
        setup(35, "026", true);
        setup(36, "027", true);
        setup(37, "028", true);
        setup(38, "029", true);
        setup(39, "030", true);
        setup(40, "031", true);
        setup(41, "032", true);
        setup(42, "033", true);
        setup(43, "034", false);
        setup(44, "035", true);
        setup(45, "036", false);
        setup(46, "037", false);
        setup(47, "000", true);
    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // TEXTURE LOADER
    public void loadMap(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
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
            int screenX = (worldX - gp.player.worldX) + gp.player.screenX;
            int screenY = (worldY - gp.player.worldY) + gp.player.screenY;

            //RENDERING STUFF
            // Number 1
            // if (gp.player.screenX > gp.player.worldX) {
            //     screenX = worldX;
            // }
            // if (gp.player.screenY > gp.player.worldY) {
            //     screenY = worldY;
            // }
            // int rightOffset = gp.screenWidth - gp.player.screenX;
            // if (rightOffset > gp.worldWidth - gp.player.worldX) {
            //     screenX = gp.screenWidth - (gp.worldWidth - worldX);
            // }
            // int bottomOffset = gp.screenHeight - gp.player.screenY;
            // if (bottomOffset > gp.worldHeight - gp.player.worldY) {
            //     screenY = gp.screenHeight - (gp.worldHeight - worldY);
            // }

            // if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            //         worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            //         worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            //         worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            //     g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            // } else if (gp.player.screenX > gp.player.worldX ||
            //         gp.player.screenX > gp.player.worldY ||
            //         rightOffset > gp.worldWidth - gp.player.worldX ||
            //         bottomOffset > gp.worldHeight - gp.player.worldY) {

            //     g2.drawImage(tile[tileNum].image, screenX, screenY, null);

            // }
            //Number 2
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
               }

            //Number 3
            // g2.drawImage(tile[tileNum].image, screenX, screenY, null);


            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }
    }

}
