package object;

import entity.Entity;
import main.GamePanel;

public class obj_shield_blue extends Entity{
    public obj_shield_blue(GamePanel gp){
        super(gp);
        
        type= type_shield;
        name = "Blue Shield";
        down1 = setup("/res/objects/shield_blue",gp.tileSize,gp.tileSize);
        defenceValue = 1;
        description = "["+ name + "]\n  Give you resistence to damage";
    }
    
}
