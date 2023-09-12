package object;

import entity.Entity;
import main.GamePanel;

public class obj_sword_normal extends Entity {

    public obj_sword_normal(GamePanel gp) {
        super(gp);
        
        type = type_sword;
        name= "Normal Sword";
        down1= setup("/res/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "["+ name + "]\n Iron sword.";
        
    }
    
}
