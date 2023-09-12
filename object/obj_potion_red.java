package object;

import entity.Entity;
import main.GamePanel;

public class obj_potion_red extends Entity {
    
    GamePanel gp;
    public obj_potion_red(GamePanel gp) {
        super(gp);
        this.gp= gp;

        
        
        type = type_consumable;
        name = "Red Potion";
        value = 5;
        down1 = setup("/res/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "["+name+"] Heals your Life by "+value;
    }
    public void use(Entity entity){

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the"+name+"!\n"+"Your Life has been recovered by "+value;

        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }

        //SOUND 
        // gp.playerSE();
    }

    
}
