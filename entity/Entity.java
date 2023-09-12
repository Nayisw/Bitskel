package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    GamePanel gp;

    public BufferedImage image, image2, image3;
    public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
    public BufferedImage atkup1, atkup2, atkdown1, atkdown2, atkleft1, atkleft2, atkright1, atkright2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogue[] = new String[20];

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCount = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    // CHARACTER
    public int speed;
    public String name;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenceValue;
    public String description = "";
    public int useCost;

    //TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword =3;
    public final int type_shield = 4;
    public final int type_axe = 5;
    public final int type_consumable = 6;
    public final int type_obtainable = 7;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    //EMPTY METHODS
    
    public void setAction() {}
    public void damageReaction() {}
    public void use (Entity entity){}
    public void checkDrop(){}
    public void dropItem(Entity droppedItem){

        for(int i = 0; i < gp.obj.length; i ++){
            if(gp.obj[i] == null ){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void speak() {
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }
    
    //GENERAL METHODs
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

            if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
            }

            if (collisionOn == false) {
                switch (direction) {
                    case "up":worldY -= speed;break;
                    case "down":worldY += speed;break;
                    case "left":worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            if (invincible == true) {
                invincibleCount++;
                if (invincibleCount > 30) {
                    invincible = false;
                    invincibleCount = 0;
                }
            }
            if(shotAvailableCounter < 30){
                shotAvailableCounter++;
            }
        
    }
    public void damagePlayer(int attack){
        if (gp.player.invincible == false) {

                int damage = attack - gp.player.defence;
                if (damage < 0) {
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;

            }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;

                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;

                case "left":

                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;

                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;

            }
            if (type == 2 && hpBarOn == true) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;
                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changAlpha(g2, 0.4F);

            }
            if (dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            changAlpha(g2, 1F);

        }
    }

    //OTHER STUFFS (Not necessary)
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        if (dyingCounter <= 5) {
            changAlpha(g2, 0F);
        }
        if (dyingCounter > 5 && dyingCounter <= 10) {
            changAlpha(g2, 1F);
        }
        if (dyingCounter > 10 && dyingCounter <= 15) {
            changAlpha(g2, 0F);
        }
        if (dyingCounter > 15 && dyingCounter <= 20) {
            changAlpha(g2, 1F);
        }
        if (dyingCounter > 20 && dyingCounter <= 25) {
            changAlpha(g2, 0F);
        }
        if (dyingCounter > 25 && dyingCounter <= 30) {
            changAlpha(g2, 1F);
        }
        if (dyingCounter > 30 && dyingCounter <= 35) {
            changAlpha(g2, 0F);
        }
        if (dyingCounter > 35 && dyingCounter <= 40) {
            changAlpha(g2, 1F);
        }

        if (dyingCounter > 40) {
            alive = false;
        }
    }
    public void changAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }


    //IMAGES
    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaledImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
