package main;

import entity.Npc;
import monster.mon_GreenSlime;
import object.obj_axe;
import object.obj_chest;
import object.obj_coin_bronze;
import object.obj_potion_red;
import object.obj_shield_blue;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {
    int i = 0;
    gp.obj[i]= new obj_chest(gp);
    gp.obj[i].worldX = gp.tileSize * 52;
    gp.obj[i].worldY = gp.tileSize * 49;
    i++;

    gp.obj[i]= new obj_coin_bronze(gp);
    gp.obj[i].worldX = gp.tileSize * 52;
    gp.obj[i].worldY = gp.tileSize * 48;
    i++;

    gp.obj[i]= new obj_axe(gp);
    gp.obj[i].worldX = gp.tileSize * 53;
    gp.obj[i].worldY = gp.tileSize * 49;
    i++;

    gp.obj[i]= new obj_shield_blue(gp);
    gp.obj[i].worldX = gp.tileSize * 52;
    gp.obj[i].worldY = gp.tileSize * 50;
    i++;

    gp.obj[i]= new obj_potion_red(gp);
    gp.obj[i].worldX = gp.tileSize * 53;
    gp.obj[i].worldY = gp.tileSize * 48;
    i++;
    }
    public void setNPC(){

    gp.npc[0]= new Npc(gp);
    gp.npc[0].worldX = gp.tileSize * 49;
    gp.npc[0].worldY = gp.tileSize * 46;
    }
    public void setMonster(){
    int i= 0;

    gp.monster[i]= new mon_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 46;
    gp.monster[i].worldY = gp.tileSize * 49;
    i++;

    gp.monster[i]= new mon_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 46;
    gp.monster[i].worldY = gp.tileSize * 48;
    i++;

    gp.monster[i]= new mon_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 46;
    gp.monster[i].worldY = gp.tileSize * 47;
    i++;

    gp.monster[i]= new mon_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 46;
    gp.monster[i].worldY = gp.tileSize * 50;
    i++;
    //add more monsters here
    }
}
