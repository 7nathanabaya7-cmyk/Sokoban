package Main;

import entity.Entity;
import java.awt.Rectangle; // Import Rectangle
import object.Box; // Make sure to import your Box class
import object.SuperObject;

public class CollisionChecker {
    
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    // This method is perfectly fine. No changes needed.
    public void checkTile(Entity e) {
        int entityLeftWorldX = e.x + e.solidArea.x;
        int entityRightWorldX = e.x + e.solidArea.x + e.solidArea.width;
        int entityTopWorldY = e.y + e.solidArea.y;
        int entityBottomWorldY = e.y + e.solidArea.y + e.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        int speed = e.speed; // Use the entity's speed

        switch (e.direction) {
            case "up":
                // Predict where the entity will be
                entityTopRow = (entityTopWorldY - speed) / gp.tileSize; 
                tileNum1 = gp.tileM.levelTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.levelTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + speed) / gp.tileSize;
                tileNum1 = gp.tileM.levelTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.levelTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - speed) / gp.tileSize;
                tileNum1 = gp.tileM.levelTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.levelTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + speed) / gp.tileSize;
                tileNum1 = gp.tileM.levelTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.levelTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;
        }
    }
    
    public int checkObject(Entity e, boolean player) {
        int index = 999;
        
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                
                // Get the entity's predicted solid area position
                Rectangle entityPredictedSolidArea = new Rectangle();
                entityPredictedSolidArea.x = e.x + e.solidArea.x;
                entityPredictedSolidArea.y = e.y + e.solidArea.y;
                entityPredictedSolidArea.width = e.solidArea.width;
                entityPredictedSolidArea.height = e.solidArea.height;

                // Get the object's current solid area position
                Rectangle objectCurrentSolidArea = new Rectangle();
                objectCurrentSolidArea.x = gp.obj[i].x + gp.obj[i].solidArea.x;
                objectCurrentSolidArea.y = gp.obj[i].y + gp.obj[i].solidArea.y;
                objectCurrentSolidArea.width = gp.obj[i].solidArea.width;
                objectCurrentSolidArea.height = gp.obj[i].solidArea.height;

                // Predict where the entity will move
                switch (e.direction) {
                    case "up": entityPredictedSolidArea.y -= e.speed; break;
                    case "down": entityPredictedSolidArea.y += e.speed; break;
                    case "left": entityPredictedSolidArea.x -= e.speed; break;
                    case "right": entityPredictedSolidArea.x += e.speed; break;
                }

                // Check if the entity's predicted path intersects the object
                if (entityPredictedSolidArea.intersects(objectCurrentSolidArea)) {
                    
                    // If the object is solid (like a chest or a box)...
                    if (gp.obj[i].collision == true) {
                        
                        // Check if it's a player hitting a pushable box
                        if (player == true && gp.obj[i] instanceof Box) {
                            
                            // Check if the BOX is blocked by a tile
                            boolean isBoxBlocked = checkTileForObject(gp.obj[i], e.direction, e.speed);
                            
                            // If the box is NOT blocked by a wall...
                            if (isBoxBlocked == false) {
                                // Move the box
                                switch (e.direction) {
                                    case "up": gp.obj[i].y -= e.speed; break;
                                    case "down": gp.obj[i].y += e.speed; break;
                                    case "left": gp.obj[i].x -= e.speed; break;
                                    case "right": gp.obj[i].x += e.speed; break;
                                }
                                // Since the box moved, the player can ALSO move
                                e.collisionOn = false;
                            } else {
                                // The box is blocked by a wall, so the player CANNOT move
                                e.collisionOn = true;
                            }
                        }
                        // It's a solid object, but not a pushable box (e.g., a chest)
                        else if (player == true) {
                            e.collisionOn = true; // Player cannot move
                        }
                    }
                    index = i; // Return the index of the object we hit
                }
            }
        }
        return index;
    }
    
    public boolean checkTileForObject(SuperObject obj, String direction, int speed) {
        // Get the object's absolute hitbox coordinates
        int objLeftWorldX = obj.x + obj.solidArea.x;
        int objRightWorldX = obj.x + obj.solidArea.x + obj.solidArea.width;
        int objTopWorldY = obj.y + obj.solidArea.y;
        int objBottomWorldY = obj.y + obj.solidArea.y + obj.solidArea.height;

        // Convert to tile columns/rows
        int objLeftCol = objLeftWorldX / gp.tileSize;
        int objRightCol = objRightWorldX / gp.tileSize;
        int objTopRow = objTopWorldY / gp.tileSize;
        int objBottomRow = objBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (direction) {
            case "up":
                objTopRow = (objTopWorldY - speed) / gp.tileSize;
                if (objTopRow < 0) { return true; } // Out of bounds
                tileNum1 = gp.tileM.levelTileNum[objLeftCol][objTopRow];
                tileNum2 = gp.tileM.levelTileNum[objRightCol][objTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    return true; // Collision found
                }
                break;
            case "down":
                objBottomRow = (objBottomWorldY + speed) / gp.tileSize;
                if (objBottomRow >= gp.tileM.levelTileNum[0].length) { return true; } // Out of bounds
                tileNum1 = gp.tileM.levelTileNum[objLeftCol][objBottomRow];
                tileNum2 = gp.tileM.levelTileNum[objRightCol][objBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    return true; // Collision found
                }
                break;
            case "left":
                objLeftCol = (objLeftWorldX - speed) / gp.tileSize;
                if (objLeftCol < 0) { return true; } // Out of bounds
                tileNum1 = gp.tileM.levelTileNum[objLeftCol][objTopRow];
                tileNum2 = gp.tileM.levelTileNum[objLeftCol][objBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    return true; // Collision found
                }
                break;
            case "right":
                objRightCol = (objRightWorldX + speed) / gp.tileSize;
                if (objRightCol >= gp.tileM.levelTileNum.length) { return true; } // Out of bounds
                tileNum1 = gp.tileM.levelTileNum[objRightCol][objTopRow];
                tileNum2 = gp.tileM.levelTileNum[objRightCol][objBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    return true; // Collision found
                }
                break;
        }
        
        return false; // No collision
    }
}