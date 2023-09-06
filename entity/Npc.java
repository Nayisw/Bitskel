package entity;

import java.util.Random;

import main.GamePanel;

public class Npc extends Entity {

    public Npc(GamePanel gp) {
        super(gp);
        
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/res/player/boy_up_1");
        up2 = setup("/res/player/boy_up_2");
        down1 = setup("/res/player/boy_down_1");
        down2 = setup("/res/player/boy_down_2");
        right1 = setup("/res/player/boy_right_1");
        right2 = setup("/res/player/boy_right_2");
        left1 = setup("/res/player/boy_left_1");
        left2 = setup("/res/player/boy_left_2");
    }
    public void setDialogue(){
        dialogue[0]= "What is goodie my gang!?";
        dialogue[1]= "Get a job lil nigga";
        dialogue[2]= "Shyu ki mummy";
        dialogue[3]= "";
        dialogue[4]= "";
        dialogue[5]= "";
    }
    public void setAction(){

        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
        int i = random.nextInt(100)+1;

        if(i <= 25){
            direction = "up";
        }
        if(i > 25 && i <= 50){
            direction = "down";
        }
        if(i > 50 && i <= 75){
            direction = "left";
        }
        if(i > 75 && i <=100){
            direction = "right";
        }
        actionLockCounter = 0;
        }
    }
    public void speak(){
        super.speak();
    }
}
