
    package object;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;


public class obj_chest extends SuperObject{
    public obj_chest(){
        name= "chest";
        try {
            File file = new File("./res/objects/chest.png");
            FileInputStream fis = new FileInputStream(file);
            image = ImageIO.read(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
}

