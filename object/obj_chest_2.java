
package object;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;


public class obj_chest_2 extends SuperObject{
    public obj_chest_2(){
        name= "Chest_2";
        try {
            File file = new File("./res/objects/chest2.png");
            FileInputStream fis = new FileInputStream(file);
            image = ImageIO.read(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;

    }
    
}

