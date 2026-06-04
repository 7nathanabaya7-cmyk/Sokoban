package Main;

import entity.Player;
import java.awt.*;

import javax.swing.*;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS
    final int originalTileSize = 16; 
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 13;
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeight = tileSize * maxScreenRow; 
    int FPS = 60;
    
    public Level level = new Level(this);
    TileManager tileM = new TileManager(this, level.levelFileName + level.levelIndex + ".txt"); 
    KeyHandler keyH = new KeyHandler(this); 
    Thread gameThread; 
    public Player player = new Player(this, keyH);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[2];
    public JFrame frame = new JFrame();
    
    public GamePanel(JFrame frame) {
        this.frame = frame;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        setupGame();
    }
    
    public void setWindowTitle(String newTitle) {
        if (frame != null) {
            frame.setTitle(newTitle);
        }
    }
    
    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this); 
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                update(); 
                repaint(); 
                delta--;
            }
        }
    }
    
    public void update() {
        player.update();
        level.nextLevel();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        //TILE
        tileM.draw(g2);
        
        //OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) { 
                obj[i].draw(g2, this);
            }
        }
        
        //PLAYER
        player.draw(g2);
        
        //UI
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.BLACK);

        String text = "Moves: " + player.moveCounter + " | Press R to reset";

        // B. Calculate Position (Center the text horizontally in the bottom row)

        //Put text into bottom left with margin of 15px
        int textX = 16;
        int textY = maxScreenRow*tileSize - 16; 

        // C. Draw the text
        g2.drawString(text, textX, textY);
        
        g2.dispose();
    }
}