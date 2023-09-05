package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // All settings related to Game Screen or Game WIndow and Tiles
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();

    Thread gameThread;
    public Player player = new Player(this, keyH);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject obj[] = new SuperObject[10];
    public UI ui = new UI(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    // GamePanel Settings
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {

        if (gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {

        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;

        if (keyH.checkDrawTime == true) {

            drawStart = System.nanoTime();

        }

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        player.draw(g2);

        ui.draw(g2);

        if (keyH.checkDrawTime == true) {

            long passed = drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time:" + passed, 10, 400);
            System.out.println("Draw time:" + passed);
        }

        g2.dispose();

    }

    public void playMusic(int i) {

        music.setFile(i);
        music.setVolume(0.7f);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playerSE(int i) {

        se.setVolume(1.0f);
        ;
        se.setFile(i);
        se.play();
    }
}
