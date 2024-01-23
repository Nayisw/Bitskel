package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
// import java.awt.GraphicsDevice;
// import java.awt.GraphicsEnvironment;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // All settings related to Game Screen or Game WIndow and Tiles
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 pixels
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    // public final int worldWidth = tileSize * maxWorldCol;
    // public final int worldHeight = tileSize * maxWorldRow;
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EvenHandler eHandler = new EvenHandler(this);
    Thread gameThread;

    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[]= new Entity[10];
    public Entity monster[]= new Entity[20];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    
    
   

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    

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
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2= (Graphics2D)tempScreen.getGraphics();

        setFullScreen();
    }
    public void setFullScreen(){
        
        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // GraphicsDevice gd = ge.getDefaultScreenDevice();
        //  gd.setFullScreenWindow(Main.window);


        // screenWidth2 = Main.window.getWidth();
        // screenHeight2 = Main.window.getHeight();


    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if ( delta >= 1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta --;
                drawCount ++;
            }
            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }
    
    }
    public void update() {

        if (gameState == playState) {
            player.update();

            for (int i = 0; i<npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            for(int i=0; i < monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }     
                    if(monster[i].alive == false){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i=0; i<projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive == true ){
                        projectileList.get(i).update();
                    }     
                    if(projectileList.get(i).alive == false){
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState) {

        }

    }
    public void drawToTempScreen(){

        long drawStart = 0;
        if (keyH.checkDrawTime == true) {

            drawStart = System.nanoTime();

        }
        if(gameState == titleState){
            ui.draw(g2);

        }
        else {
             tileM.draw(g2);

             //Adds Entities to the list
             entityList.add(player);
             for(int i = 0; i< npc.length;i++) {
                if(npc[i] != null){
                    entityList.add(npc[i]);

                }
            }
             for(int i =0; i< obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
             }
              for(int i =0; i< monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
             }
             for(int i =0; i< projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
             }

             Collections.sort(entityList, new Comparator<Entity>(){

                @Override
                public int compare(Entity e1, Entity e2){
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
                
            });
                         
             //Draws Entities
             for(int i= 0; i<entityList.size(); i++){
                entityList.get(i).draw(g2);
             }
             //EMPTY the drawn Entity
             entityList.clear();
            
            ui.draw(g2);

        }

       
        if (keyH.checkDrawTime == true) {

            long passed = drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time:" + passed, 10, 400);
            System.out.println("Draw time:" + passed);
        }
       
    }   
    public void drawToScreen(){

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0,   0, screenWidth2, screenHeight2 , null);
        g.dispose();
    }


    //SOUND
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playerSE(int i) {

        se.setFile(i);
        se.play();
    }
}
