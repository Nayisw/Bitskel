package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX=gp.screenWidth/2-(gp.tileSize/2);
        screenY=gp.screenHeight/2-(gp.tileSize/2);

        solidArea= new Rectangle(8,16,32,32);

        getPlayerImage();
        setDefaultValues();
    }

    public void setDefaultValues() {

        worldX = 23*gp.tileSize;
        worldY = 21*gp.tileSize;
        speed = 4;
        direction = "down";

    }

    public void getPlayerImage() {
        try {
            File f1 = new File("./res/player/boy_up_1.png");

            File f2 = new File("./res/player/boy_up_2.png");

            File f3 = new File("./res/player/boy_down_1.png");

            File f4 = new File("./res/player/boy_down_2.png");

            File f5 = new File("./res/player/boy_left_1.png");

            File f6 = new File("./res/player/boy_left_2.png");

            File f7 = new File("./res/player/boy_right_1.png");

            File f8 = new File("./res/player/boy_right_2.png");

            up1 = ImageIO.read(f1);
            up2 = ImageIO.read(f2);

            down1 = ImageIO.read(f3);
            down2 = ImageIO.read(f4);

            left1 = ImageIO.read(f5);
            left2 = ImageIO.read(f6);

            right1 = ImageIO.read(f7);
            right2 = ImageIO.read(f8);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.leftPressed==true || keyH.rightPressed==true || keyH.upPressed==true|| keyH.downPressed==true){


            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";      
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";         
            }

            collisionOn= false;
            gp.cChecker.checkTile(this);

            if(collisionOn == false){
                switch(direction){
                    case "up": worldY -= speed;
                    break;
                    case "down": worldY += speed;
                    break;
                    case "left": worldX -= speed;
                    break;
                    case "right":  worldX += speed;
                    break;
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
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
