package main;

import entity.Npc;
import monster.mon_GreenSlime;
import object.obj_chest;
import object.obj_key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {
    gp.obj[0]= new obj_chest(gp);
    gp.obj[0].worldX = gp.tileSize * 21;
    gp.obj[0].worldY = gp.tileSize * 20;

    gp.obj[1]= new obj_key(gp);
    gp.obj[1].worldX = gp.tileSize * 23;
    gp.obj[1].worldY = gp.tileSize * 21;
    }
    public void setNPC(){

    gp.npc[0]= new Npc(gp);
    gp.npc[0].worldX = gp.tileSize * 22;
    gp.npc[0].worldY = gp.tileSize * 18;
    }
    public void setMonster(){

    gp.monster[0]= new mon_GreenSlime(gp);
    gp.monster[0].worldX = gp.tileSize * 21;
    gp.monster[0].worldY = gp.tileSize * 10;
    }
}
