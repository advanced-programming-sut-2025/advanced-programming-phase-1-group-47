package com.StardewValley.network;

import com.StardewValley.model.Point;
import java.io.Serializable;

public class PlayerPositionUpdate implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String playerId;
    private Point position;
    private int moveDirection;
    private long timestamp;
    private boolean isMoving;
    
    public PlayerPositionUpdate(String playerId, Point position, int moveDirection, boolean isMoving) {
        this.playerId = playerId;
        this.position = position;
        this.moveDirection = moveDirection;
        this.isMoving = isMoving;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and setters
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    
    public Point getPosition() { return position; }
    public void setPosition(Point position) { this.position = position; }
    
    public int getMoveDirection() { return moveDirection; }
    public void setMoveDirection(int moveDirection) { this.moveDirection = moveDirection; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { isMoving = moving; }
    
    @Override
    public String toString() {
        return "PlayerPositionUpdate{" +
                "playerId='" + playerId + '\'' +
                ", position=" + position +
                ", moveDirection=" + moveDirection +
                ", timestamp=" + timestamp +
                ", isMoving=" + isMoving +
                '}';
    }
}
