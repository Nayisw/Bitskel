
package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class obj_chest extends SuperObject {

    GamePanel gp;

    public obj_chest(GamePanel gp) {

        this.gp = gp;
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }

}
