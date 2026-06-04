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
                
                // 1. Increment level counter
                levelIndex++;
                
                // 2. Build the path for the NEXT level (assuming .txt files)
                String nextLevelPath = levelFileName + levelIndex + ".txt";
                
                gp.setWindowTitle("Sokoban - Level " + levelIndex);
                
                // 3. Re-initialize the existing TileManager field with the new path
                gp.tileM = new TileManager(gp, nextLevelPath);
                
                // 4. Reset assets for the new level
                gp.aSetter.setObject();
                
                // 5. Reset player position (e.g., to the center of the screen)
                gp.player.setDefaultValues();

                javax.swing.SwingUtilities.invokeLater(() -> gp.requestFocusInWindow()); // request focus in window again
                
                if (levelIndex == 2) {
                    gp.obj[0].x = 10 * gp.tileSize;
                    gp.obj[0].y = 7 * gp.tileSize;
                    
                    gp.obj[1].x = 7*gp.tileSize;
                    gp.obj[1].y = 5*gp.tileSize;
                }
                if (levelIndex == 3) {
                    gp.obj[0].x = 7*gp.tileSize;
                    gp.obj[0].y = 7*gp.tileSize;

                    gp.obj[1].x = 7*gp.tileSize;
                    gp.obj[1].y = 8*gp.tileSize;
                    
                    gp.player.x = 6*gp.tileSize;
                    gp.player.y = 8*gp.tileSize;
                }
                if (levelIndex == 4) {
                    gp.obj[0].x = 7 * gp.tileSize;
                    gp.obj[0].y = 5 * gp.tileSize;
                    
                    gp.obj[1].x = 3*gp.tileSize;
                    gp.obj[1].y = 6*gp.tileSize;
                    
                    gp.player.x = 10*gp.tileSize;
                    gp.player.y = 6*gp.tileSize;
                }
                if (levelIndex == 5) {
                    gp.obj[0].x = 9 * gp.tileSize;
                    gp.obj[0].y = 3 * gp.tileSize;
                    
                    gp.obj[1].x = 3*gp.tileSize;
                    gp.obj[1].y = 9*gp.tileSize;
                    
                    gp.player.x = 2*gp.tileSize;
                    gp.player.y = 7*gp.tileSize;
                }
                if (levelIndex >= 6 || levelIndex <= 0) { //prevents level to go out of bounds, exits game.
                    showLevelEndMessage();
                }
            }
        }
    }
    
    public void resetLevel() {
        if (gp.level.levelIndex == 1) {
            gp.obj[0].x = 6 * gp.tileSize;
            gp.obj[0].y = 7 * gp.tileSize;

            gp.player.x = 4*gp.tileSize;
            gp.player.y = 8*gp.tileSize;
        }
        if (gp.level.levelIndex == 2) {
            gp.obj[0].x = 10 * gp.tileSize;
            gp.obj[0].y = 7 * gp.tileSize;

            gp.player.x = 4*gp.tileSize;
            gp.player.y = 8*gp.tileSize;
        }
        if (gp.level.levelIndex == 3) {
            gp.obj[0].x = 7*gp.tileSize;
            gp.obj[0].y = 7*gp.tileSize;

            gp.obj[1].x = 7*gp.tileSize;
            gp.obj[1].y = 8*gp.tileSize;

            gp.player.x = 6*gp.tileSize;
            gp.player.y = 8*gp.tileSize;
        }
        if (gp.level.levelIndex == 4) {
            gp.obj[0].x = 7 * gp.tileSize;
            gp.obj[0].y = 5 * gp.tileSize;

            gp.obj[1].x = 3*gp.tileSize;
            gp.obj[1].y = 6*gp.tileSize;

            gp.player.x = 10*gp.tileSize;
            gp.player.y = 6*gp.tileSize;
        }
        if (gp.level.levelIndex == 5) {
            gp.obj[0].x = 9 * gp.tileSize;
            gp.obj[0].y = 3 * gp.tileSize;

            gp.obj[1].x = 3*gp.tileSize;
            gp.obj[1].y = 9*gp.tileSize;

            gp.player.x = 2*gp.tileSize;
            gp.player.y = 7*gp.tileSize;
        }
    }
    
    public void showLevelEndMessage() {
        JOptionPane.showMessageDialog(gp, "You Have Completed The Game. Be Proud.", "END", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
