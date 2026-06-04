package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class End extends SuperObject{
    public End() {
        name = "end";
        collision = false;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/end.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
