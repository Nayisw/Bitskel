package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class obj_key extends SuperObject {
    GamePanel gp;

    public obj_key(GamePanel gp) {

        this.gp = gp;

        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
