package object;

import entity.Entity;
import main.GamePanel;

public class obj_chest_2 extends Entity {
    public obj_chest_2(GamePanel gp) {
        super(gp);

        name = "Chest";
        down1 = setup ("/res/objects/chest2", gp.tileSize,gp.tileSize);
        collision = true;
    }

}
