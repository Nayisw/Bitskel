package object;

import entity.Entity;
import main.GamePanel;

public class obj_shield_wood extends Entity {

    public obj_shield_wood(GamePanel gp) {
        super(gp);
        
        
        name = "Wood Shield";
        down1 = setup("/res/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenceValue = 1;
    }
    
}
