package entity;

import java.awt.AlphaComposite;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.obj_shield_wood;
import object.obj_sword_normal;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        attackArea.width = 36;
        attackArea.height = 36;
        
        getPlayerImage();
        setDefaultValues();
        getPlayerAtkImage();

    }

    public void setDefaultValues() {

        worldX = 49 * gp.tileSize;
        worldY = 49 * gp.tileSize;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new obj_sword_normal(gp);
        currentShield = new obj_shield_wood(gp);
        attack = getAttack();
        defence = getDefence();

    }
    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;

    }
    public int getDefence(){
        return defence = dexterity * currentShield.defenceValue;
    }
        

    public void getPlayerImage() {
        up = setup("/res/player/boy_up", gp.tileSize,gp.tileSize);
        up1 = setup("/res/player/boy_up_1", gp.tileSize,gp.tileSize);
        up2 = setup("/res/player/boy_up_2", gp.tileSize,gp.tileSize);

        down = setup("/res/player/boy_down", gp.tileSize,gp.tileSize);
        down1 = setup("/res/player/boy_down_1", gp.tileSize,gp.tileSize);
        down2 = setup("/res/player/boy_down_2", gp.tileSize,gp.tileSize);

        right = setup("/res/player/boy_right", gp.tileSize,gp.tileSize);
        right1 = setup("/res/player/boy_right_1", gp.tileSize,gp.tileSize);
        right2 = setup("/res/player/boy_right_2", gp.tileSize,gp.tileSize);

        left = setup("/res/player/boy_left", gp.tileSize,gp.tileSize);
        left1 = setup("/res/player/boy_left_1", gp.tileSize,gp.tileSize);
        left2 = setup("/res/player/boy_left_2", gp.tileSize,gp.tileSize);
    }
    public void getPlayerAtkImage(){

        //TEMPORARY MONSTER RES

        atkup1 = setup("/res/player/boy_up_1", gp.tileSize,gp.tileSize*2);
        atkup2 = setup("/res/player/boy_up_2", gp.tileSize,gp.tileSize*2);
        atkdown1 = setup("/res/player/boy_down_1", gp.tileSize,gp.tileSize*2);
        atkdown2 = setup("/res/player/boy_down_2", gp.tileSize,gp.tileSize*2);
        atkright1 = setup("/res/player/boy_right_1", gp.tileSize*2,gp.tileSize);
        atkright2 = setup("/res/player/boy_right_2", gp.tileSize*2,gp.tileSize);
        atkleft1 = setup("/res/player/boy_left_1", gp.tileSize*2,gp.tileSize);
        atkleft2 = setup("/res/player/boy_left_2", gp.tileSize*2,gp.tileSize);
    }
    public void update() {
        if(attacking == true){
            attacking();
        }

        else if (keyH.leftPressed == true || keyH.rightPressed == true || keyH.upPressed == true
                || keyH.downPressed == true || keyH.enterPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            //TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            
            gp.eHandler.checkEvent();
            

            
            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            if(keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;


            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
            
        }
        if(invincible == true){
            invincibleCount++;
            if(invincibleCount > 60){
                invincible = false;
                invincibleCount = 0;
            }
        }
    }
    public void attacking(){
        
            spriteCounter++;
        if(spriteCounter <=5){
            spriteNum=1;

        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum= 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;

            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            //MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);


            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum=1;
            spriteCounter = 0;
            attacking = false;
        }
        
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            if(invincible == false){
                life -= 1;
                invincible = true;
            }
        }

    }

    public void interactNPC(int i){
        if(keyH.enterPressed == true){
        if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } 
        }
    }
    public void contactMonster(int i){
        if( i != 999){

            if(invincible == false){
                life -= 1;
                invincible = true;
            }
        }
    }
    public void damageMonster(int i){
        if(i !=999){
         if(gp.monster[i].invincible == false){

            gp.monster[i].life -= 1;
            gp.monster[i].invincible = true;
            gp.monster[i].damageReaction();

            if(gp.monster[i].life <=0){
                gp.monster[i].dying = true;
            }
         }
        }
    }

    public void draw(Graphics2D g2) {

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image = null;
        switch (direction) {
        case "up":
            if(attacking ==false){
                if (spriteNum == 1) {image = up1;}
                if (spriteNum == 2) {image = up2;}
            }
            if(attacking == true){
                tempScreenY = screenY - gp.tileSize;
                if (spriteNum == 1) {image = atkup1;}
                if (spriteNum == 2) {image = atkup2;}
            }
            break;

        case "down":
            if(attacking == false){
                if (spriteNum == 1) {image = down1;}
                if (spriteNum == 2) {image = down2;}
            }
            if(attacking == true){
                if (spriteNum == 1) {image = atkdown1;}
                if (spriteNum == 2) {image = atkdown2;}
            }  
            break;

        case "left":
            if(attacking == false){
                if (spriteNum == 1) {image = left1;}
                if (spriteNum == 2) {image = left2;}
            }
            if(attacking == true){
                tempScreenX = screenX - gp.tileSize;
                if (spriteNum == 1) {image = atkleft1;}
                if (spriteNum == 2) {image = atkleft2;}
            }
            break;

        case "right":
            if(attacking == false){
                if (spriteNum == 1) {image = right1;}
                if (spriteNum == 2) {image = right2;}
            }
            if(attacking == true){
                if (spriteNum == 1) {image = atkright1;}
                if (spriteNum == 2) {image = atkright2;}
            }
            break;
        }

        int x = screenX;
        int y = screenY;
        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > gp.worldHeight - worldY) {
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if(invincible == true){
           g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F)); 
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)); 

    }
}
