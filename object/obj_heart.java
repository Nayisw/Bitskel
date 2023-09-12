package object;

import entity.Entity;
import main.GamePanel;

public class obj_heart extends Entity{
    GamePanel gp;
    public obj_heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obtainable;

        name = "Heart";
        value = 2;
        down1 = setup("/res/objects/heart_full", gp.tileSize,gp.tileSize);
        image = setup("/res/objects/heart_full", gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/heart_half", gp.tileSize,gp.tileSize);
        image3 = setup("/res/objects/heart_blank", gp.tileSize,gp.tileSize);
    }
    public void use(Entity entity){
        gp.playerSE(2);
        gp.ui.addMessage("Life +"+value);
        entity.life += value;
    }
}