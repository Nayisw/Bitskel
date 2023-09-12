package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.text.PlainDocument;

import entity.Entity;
import object.obj_heart;
import object.obj_mana;

public class UI {
    Graphics2D g2;
    GamePanel gp;
    Font FSEX300,MicroSix;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;

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

        Entity heart = new obj_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new obj_mana(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        
    }
    public void addMessage(String text) {
        
        
        message.add(text);
        messageCounter.add(0);
        g2.setFont(MicroSix);
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
            gp.music.play();
            drawMessage();
            
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
        if (gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory();
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

        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x , y, null);
            i++;
            x += 35;
        }
        x = (gp.tileSize/2)-5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x , y, null);
            i++;
            x += 35;
        }

    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(MicroSix.deriveFont(Font.PLAIN, 18F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2); //TEXT
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY); //SHADOW

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 30;

                if(messageCounter.get(i) > 120){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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
        g2.drawImage(gp.player.down, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
    
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
        g2.setFont(FSEX300.deriveFont(Font.BOLD, 96F));
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
        g2.drawString(line, x, y);
        y += 40;
        }
    }
    private void drawCharacterScreen() {

        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defence", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        int tailX = (frameX + frameWidth) - 30;

        textY = frameY +gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/"+ gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX-28, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/"+ gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX-28, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defence);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, tailX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);

    }
    public void drawInventory(){
        //FRAME
        int frameX= gp.tileSize *9;
        int frameY= gp.tileSize;
        int frameWidth = gp.tileSize *6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY =slotYstart;
        int slotSize = gp.tileSize +3;

        //DRAW ITEMS
        for(int i = 0; i < gp.player.inventory.size(); i++){
            //EQUIP
            if(gp.player.inventory.get(i)== gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield){
                
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null );
            slotX += slotSize;

            if(i == 4 || i ==9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        
        //DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY,  cursorWidth, cursorHeight, 10 , 10);       

        //DESCRIPTION
        int dframeX = frameX;
        int dframeY = frameY + frameHeight;
        int dframeWidth = frameWidth;
        int dframeHeight = gp.tileSize * 3;     
        int textX = dframeX + 20;
        int textY = dframeY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont( 28F));
        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()){

            drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

            for(String line : gp.player.inventory.get(itemIndex).description.split("\n")){
                 g2.drawString(line, textX, textY);
                 textY+= 32;
            }
           
        }

    }
    public int getItemIndexOnSlot(){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
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
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;

    }
}