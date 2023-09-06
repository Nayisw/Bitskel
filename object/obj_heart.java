package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class obj_heart extends SuperObject{
    GamePanel gp;

    public obj_heart(GamePanel gp) {

        this.gp = gp;

        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaledImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaledImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    
    }
}