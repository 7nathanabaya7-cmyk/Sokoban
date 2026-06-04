package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity { //superclass for all entities
    public int x, y; //declares position of entity
    public int speed;
    
    public BufferedImage up, down, left, right;
    public String direction;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}