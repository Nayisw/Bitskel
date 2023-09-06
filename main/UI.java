package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.SuperObject;
import object.obj_heart;

public class UI {
    Graphics2D g2;
    GamePanel gp;
    Font FSEX300,MicroSix;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int msgCounter = 0;

    public String currentDialogue = "";
    public int commandNum = 0;



    public UI(GamePanel gp) {
        this.gp = gp;

        
        try {
        InputStream is = getClass().getResourceAsStream("/res/font/FSEX300.ttf");
        FSEX300 = Font.createFont(Font.TRUETYPE_FONT, is);
        
        is = getClass().getResourceAsStream("/res/font/MicroSix-MVRjw.ttf");
        MicroSix = Font.createFont(Font.TRUETYPE_FONT, is);

        }catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        SuperObject heart = new obj_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void ShowMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(FSEX300);
        // g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
            gp.music.stop();
        }
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        if (gp.gameState == gp.deathState){
            drawDeathScreen();
        }
    }
    public void drawPlayerLife(){   
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        while(i<gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        while(i<gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,  x, y, null);
            }
            i++;
            x += gp.tileSize;

        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(FSEX300.deriveFont(Font.BOLD, 96F));
        String text = "Bitskel";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        x = gp.screenWidth/2 - (gp.tileSize * 2)/2;
        y += gp.tileSize * 1.5;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    
        g2.setFont(FSEX300.deriveFont(Font.BOLD, 48F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);

        if(commandNum == 0){
            gp.gameState= gp.titleState;
            g2.drawString(">", x-gp.tileSize, y);
        }

        g2.setFont(FSEX300.deriveFont(Font.BOLD, 48F));
        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        g2.setFont(FSEX300.deriveFont(Font.BOLD, 48F));
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }


    }
    

    public void drawPauseScreen() {

        String text = "PAUSED";
        int y;
        int x;
        x = getXforCenteredText(text);
        y = gp.screenWidth / 2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen(){
        int x= gp.tileSize*2;
        int y= gp.tileSize/2;
        int width= gp.screenWidth- (gp.tileSize * 4);
        int height= gp.tileSize * 5;
        drawSubWindow(x, y, width, height);
        

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")){
        g2.drawString(currentDialogue, x, y);
        y += 40;

        }
    }
    public void drawDeathScreen(){
        
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(FSEX300.deriveFont(Font.BOLD, 96F));
            String text = "You died";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            x = gp.screenWidth/2 - (gp.tileSize * 2)/2;
            y += gp.tileSize * 1.5;

            gp.music.stop();

            g2.setFont(FSEX300.deriveFont(Font.BOLD, 48F));
            text = "Try Again?";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            
            if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
            }

            g2.setFont(FSEX300.deriveFont(Font.BOLD, 48F));
            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);

            if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
            }
        

    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c= new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c= new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width -10, height-10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;

    }
}