package object;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;


public class obj_key extends SuperObject{
    public obj_key(){
        name= "key";
        try {
            File file = new File("./res/objects/key.png");
            FileInputStream fis = new FileInputStream(file);
            image = ImageIO.read(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
