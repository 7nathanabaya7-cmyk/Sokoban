package Main;

import javax.swing.JOptionPane;
import tile.TileManager;

public class Level {
    
    GamePanel gp;
    
    public int levelIndex = 1; // Start at Level 1
    public String levelFileName = "/resources/levels/level";
    
    public Level(GamePanel gp) {
        this.gp = gp;
    }
    public void nextLevel() {
        // Check if both objects (Box and Target) exist
        if (gp.obj[0] != null && gp.obj[1] != null) {
            
            // Check win condition (Box and Target are in the same tile position)
            if (gp.obj[0].x == gp.obj[1].x && gp.obj[0].y == gp.obj[1].y) {
                System.out.println("OBJECTIVE COMPLETE! Loading next level...");
                
                showLevelNextMessage(); //added here
                gp.keyH.resetKeys();
                gp.requestFocusInWindow();
                
                levelIndex++;
                
                String nextLevelPath = levelFileName + levelIndex + ".txt";
                
                gp.setWindowTitle("Sokoban - Level " + levelIndex);
                
                gp.tileM = new TileManager(gp, nextLevelPath);
                
                gp.aSetter.setObject();
                
                gp.player.setDefaultValues();
                
                if (levelIndex == 2) {
                    gp.obj[0].x = 10 * gp.getTileSize();
                    gp.obj[0].y = 7 * gp.getTileSize();
                    
                    gp.obj[1].x = 7*gp.getTileSize();
                    gp.obj[1].y = 5*gp.getTileSize();
                }
                if (levelIndex == 3) {
                    gp.obj[0].x = 7*gp.getTileSize();
                    gp.obj[0].y = 7*gp.getTileSize();

                    gp.obj[1].x = 7*gp.getTileSize();
                    gp.obj[1].y = 8*gp.getTileSize();
                    
                    gp.player.x = 6*gp.getTileSize();
                    gp.player.y = 8*gp.getTileSize();
                }
                if (levelIndex == 4) {
                    gp.obj[0].x = 7 * gp.getTileSize();
                    gp.obj[0].y = 5 * gp.getTileSize();
                    
                    gp.obj[1].x = 3*gp.getTileSize();
                    gp.obj[1].y = 6*gp.getTileSize();
                    
                    gp.player.x = 10*gp.getTileSize();
                    gp.player.y = 6*gp.getTileSize();
                }
                if (levelIndex == 5) {
                    gp.obj[0].x = 9 * gp.getTileSize();
                    gp.obj[0].y = 3 * gp.getTileSize();
                    
                    gp.obj[1].x = 3*gp.getTileSize();
                    gp.obj[1].y = 9*gp.getTileSize();
                    
                    gp.player.x = 2*gp.getTileSize();
                    gp.player.y = 7*gp.getTileSize();
                }
                if (levelIndex >= 6 || levelIndex <= 0) { //prevents level to go out of bounds, exits game.
                    showLevelEndMessage();
                }
            }
        }
    }
    
    public void resetLevel() {
        if (gp.level.levelIndex == 1) {
            gp.obj[0].x = 6 * gp.getTileSize();
            gp.obj[0].y = 7 * gp.getTileSize();

            gp.player.x = 4*gp.getTileSize();
            gp.player.y = 8*gp.getTileSize();
        }
        if (gp.level.levelIndex == 2) {
            gp.obj[0].x = 10 * gp.getTileSize();
            gp.obj[0].y = 7 * gp.getTileSize();

            gp.player.x = 4*gp.getTileSize();
            gp.player.y = 8*gp.getTileSize();
        }
        if (gp.level.levelIndex == 3) {
            gp.obj[0].x = 7*gp.getTileSize();
            gp.obj[0].y = 7*gp.getTileSize();

            gp.obj[1].x = 7*gp.getTileSize();
            gp.obj[1].y = 8*gp.getTileSize();

            gp.player.x = 6*gp.getTileSize();
            gp.player.y = 8*gp.getTileSize();
        }
        if (gp.level.levelIndex == 4) {
            gp.obj[0].x = 7 * gp.getTileSize();
            gp.obj[0].y = 5 * gp.getTileSize();

            gp.obj[1].x = 3*gp.getTileSize();
            gp.obj[1].y = 6*gp.getTileSize();

            gp.player.x = 10*gp.getTileSize();
            gp.player.y = 6*gp.getTileSize();
        }
        if (gp.level.levelIndex == 5) {
            gp.obj[0].x = 9 * gp.getTileSize();
            gp.obj[0].y = 3 * gp.getTileSize();

            gp.obj[1].x = 3*gp.getTileSize();
            gp.obj[1].y = 9*gp.getTileSize();

            gp.player.x = 2*gp.getTileSize();
            gp.player.y = 7*gp.getTileSize();
        }
    }
    
    public void showLevelNextMessage() {
        JOptionPane.showMessageDialog(gp, "You Have Completed The Level", "COMPLETE!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void showLevelEndMessage() {
        JOptionPane.showMessageDialog(gp, "You Have Completed The Game. Be Proud.", "COMPLETE!!!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
