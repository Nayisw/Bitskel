package object;

import entity.Entity;
import main.GamePanel;

public class obj_mana extends Entity{
    GamePanel gp;

    public obj_mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obtainable;
        value = 1;
        name = "Mana Crystal";
        down1 = setup("/res/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setup("/res/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        
    }
    public void use(Entity entity){
        gp.playerSE(2);
        gp.ui.addMessage("Mana +"+value);
        entity.mana += value;
    }

}
