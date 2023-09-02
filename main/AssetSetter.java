package main;

import object.obj_chest;
import object.obj_key;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp=gp;

    }

    public void setObject(){
        gp.obj[0]=new obj_key();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY=10 * gp.tileSize;

        gp.obj[1]=new obj_chest();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY=8 * gp.tileSize;

    }
}
