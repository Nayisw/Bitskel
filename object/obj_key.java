package object;

import entity.Entity;
import main.GamePanel;

public class obj_key extends Entity {
    public obj_key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup ("/res/objects/key", gp.tileSize,gp.tileSize);
    }

}
