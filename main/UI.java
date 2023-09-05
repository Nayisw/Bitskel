package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    Graphics2D g2;
    GamePanel gp;
    Font arial_40;
    public boolean messageOn = false;
    public String message = "";
    int msgCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.BOLD, 30);
    }

    public void ShowMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            gp.music.play();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            gp.music.stop();
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

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;

    }
}