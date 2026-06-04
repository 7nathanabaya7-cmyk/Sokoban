package entity;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    
    public int moveCounter = 0;
    
    long lastMoveTime = 0;
    long movementDelay;
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        solidArea = new Rectangle(x, y, gp.getTileSize()-1, gp.getTileSize()-1); //declared gp.getTileSize() to -1 so it wouldnt go out of bounds
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues() {
        x = 4*gp.getTileSize();
        y = 8*gp.getTileSize();
        speed = gp.getTileSize();
        movementDelay = 250000000;
        direction = "down";
    }
    
    public void getPlayerImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("/resources/player/upMovement.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/resources/player/downMovement.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/resources/player/leftMovement.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/resources/player/rightMovement.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void update() {
        long currentTime = System.nanoTime();
        
        if ((keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true ||
                keyH.rightPressed == true) && currentTime - lastMoveTime >= movementDelay) {
            
            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            } else {
                return;
            }
            
            //COLLISION FEATURE
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            //OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            
            //IF COLLISION TRUE, PLAYER CANNOT MOVE, IF FALSE, PLAYER CAN MOVE
            if(collisionOn == false) {
                switch(direction) {
                    case "up": y -= speed; moveCounter++; break;
                    case "down": y += speed; moveCounter++; break;
                    case "left": x -= speed; moveCounter++; break;
                    case "right": x += speed; moveCounter++; break;
                }
            }
            lastMoveTime = currentTime;
        }
    }
    
    public void draw (Graphics2D g2) {
        BufferedImage image = null;
        
        switch (direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }
        
        g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }
}
