package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0 ){
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 2 ){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playerSE(1);
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){

                    
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        


        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W) {
            upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
            downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
            leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
            rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            gp.playerSE(1);
            };
            if (code == KeyEvent.VK_F1) {
                if (checkDrawTime == false) {
                checkDrawTime = true;
                } else if (checkDrawTime == true) {
                checkDrawTime = false;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }

        }
        else if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.dialogueState){
            if(code== KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.deathState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0 ){
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState= gp.titleState;
                    gp.playerSE(1);
                    gp.player.life = gp.player.maxLife;
                    gp.player.worldX = 23 * gp.tileSize;
                    gp.player.worldY = 21* gp.tileSize;
                    gp.player.direction= "down";
                    
                }
                if(gp.ui.commandNum == 1){
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
