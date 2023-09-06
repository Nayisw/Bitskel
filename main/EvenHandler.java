package main;

import java.awt.Rectangle;

public class EvenHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    
    public EvenHandler(GamePanel gp){
        this.gp= gp;

        eventRect = new Rectangle();
        eventRect.x= 23;
        eventRect.y= 23;
        eventRect.width= 2;
        eventRect.height= 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }
    public void checkEvent(){
        if( hit(27,16,"right")==true){
            damagePit(gp.dialogueState);
        }
        if(hit(23,12,"up")== true){
            HealingPool(gp.dialogueState);
        }
    }
    public boolean hit(int eventCol, int eventRow, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol* gp.tileSize + eventRect.x;
        eventRect.y = eventRow* gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection)|| reqDirection.contentEquals("any")){
                hit = true;
            }
        }
        gp.player.solidArea.x= gp.player.solidAreaDefaultX;
        gp.player.solidArea.y= gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState){

        gp.gameState= gameState;
        gp.ui.currentDialogue  = "You took a hit nigga";
        gp.player.life -= 1;
        if(gp.player.life==0){
            gp.gameState = gp.deathState;
            gp.playerSE(2);
        }

    }
    public void HealingPool(int gameState){

        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank healing water nigga";
            gp.player.life = gp.player.maxLife;
        }
    }
    
}
