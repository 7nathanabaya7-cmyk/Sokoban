package Main;

import object.Box;
import object.End;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {
        gp.obj[0] = new Box();
        gp.obj[0].x = 6 * gp.getTileSize();
        gp.obj[0].y = 7 * gp.getTileSize();
        
        gp.obj[1] = new End();
        gp.obj[1].x = 6*gp.getTileSize();
        gp.obj[1].y = 4*gp.getTileSize();
    }
}