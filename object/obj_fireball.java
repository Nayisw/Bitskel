package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class obj_fireball extends Projectile{
    GamePanel gp;
    public obj_fireball(GamePanel gp) {
        super(gp);
        this.gp= gp;

        name= "Fireball";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/res/projectiles/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/projectiles/fireball_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/res/projectiles/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/projectiles/fireball_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/res/projectiles/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/projectiles/fireball_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("/res/projectiles/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/projectiles/fireball_right_2", gp.tileSize, gp.tileSize);


    }
    
    public boolean haveResource(Entity user){

        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void substractResource(Entity user){

        user.mana -= useCost;
    }
}
