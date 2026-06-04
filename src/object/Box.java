package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Box extends SuperObject{
    public Box() {
        name = "box";
        collision = true;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/box.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}