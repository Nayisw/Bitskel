package object;

import entity.Entity;
import main.GamePanel;

public class obj_axe extends Entity {

    public obj_axe(GamePanel gp){
        super(gp);

        type = type_axe;
        name = "Iron Axe";
        down1 = setup("/res/objects/axe", gp.tileSize,gp.tileSize);
        attackValue = 6;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "["+ name + "]\n can be used to cut trees \n or attack monster!";
    }
    
}
