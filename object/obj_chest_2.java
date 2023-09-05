
package object;

import javax.imageio.ImageIO;

import main.GamePanel;

public class obj_chest_2 extends SuperObject {
    GamePanel gp;

    public obj_chest_2(GamePanel gp) {
        this.gp = gp;
        name = "Chest_2";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest2.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }

}
