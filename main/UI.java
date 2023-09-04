package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.obj_key;

public class UI {
    
    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int msgCounter=0;

    public UI(GamePanel gp){
        this.gp=gp;

        arial_40= new Font("Arial", Font.BOLD, 30);
        obj_key key= new obj_key();
        keyImage = key.image;
    }
    public void ShowMessage(String text){

        message = text;
        messageOn= true;
    }
    public void draw(Graphics2D g2){
        
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("Key: " + gp.player.hasKey, 75, 50);

        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(24f));
            g2.drawString(message, gp.tileSize*6, gp.tileSize*5);

            msgCounter++;
            if(msgCounter > 120){
                msgCounter = 0;
                messageOn = false;
            }
        }
    }
}
