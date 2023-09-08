package object;

import entity.Entity;
import main.GamePanel;

public class obj_chest extends Entity {
    public obj_chest(GamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup ("/res/objects/chest", gp.tileSize,gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}
