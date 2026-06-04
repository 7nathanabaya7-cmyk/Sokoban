package tile;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    public Tile [] tile;
    
    public int [][] levelTileNum;
    
    public TileManager(GamePanel gp, String level) {
        this.gp = gp;
        
        levelTileNum = new int [gp.maxScreenCol][gp.maxScreenRow];
        tile = new Tile[5];
        
        getTileImage();
        loadLevel(level);
    }
    
    public void getTileImage() {
        try{
            tile [0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/floor.png"));
            
            tile [1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/borderVertical.png"));
            tile[1].collision = true;
            
            tile [2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/borderHorizontal.png"));
            tile[2].collision = true;
            
            tile [3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/borderCorner.png"));
            tile[3].collision = true;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadLevel(String level) {
        try {
            InputStream is = getClass().getResourceAsStream(level);
            
            if (is == null) {
                System.err.println("Error: Level file not found at path: " + level);
                return; 
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            // Loop through each ROW
            while (row < gp.maxScreenRow) {
                String line = br.readLine();
                
                // If end of file is reached prematurely
                if (line == null) break; 
                
                String [] numbers = line.split(" "); // Split the line into numbers (columns)
                col = 0; // Reset column counter for the new row

                // Loop through each COLUMN in the current row
                while (col < gp.maxScreenCol && col < numbers.length) {
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    // Assign the tile number to the map array
                    levelTileNum[col][row] = num; 
                    
                    col++;
                }
                row++; // Move to the next row
            }
            br.close();
        }
        catch(Exception e) {
            // IMPORTANT: Print the stack trace to see file loading errors!
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        int col, row, x, y;
        col = row = x = y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = levelTileNum[col][row];
            
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
        
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
