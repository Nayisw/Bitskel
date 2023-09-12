package object;

import entity.Entity;
import main.GamePanel;

public class obj_coin_bronze extends Entity {
    GamePanel gp;
    public obj_coin_bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obtainable;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("/res/objects/coin_bronze", gp.tileSize,gp.tileSize  );
        
        

    }
    public void use(Entity entity){

        gp.playerSE(1);
        gp.ui.currentDialogue = "Coin + "+value;
        gp.player.coin += value;

    }
    
}
