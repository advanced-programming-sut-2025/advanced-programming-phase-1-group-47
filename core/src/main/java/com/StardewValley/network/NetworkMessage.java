package com.StardewValley.network;

import com.StardewValley.model.Point;
import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum MessageType {
        PLAYER_JOIN,
        PLAYER_LEAVE,
        PLAYER_POSITION_UPDATE,
        PLAYER_ACTION,
        GAME_STATE_UPDATE,
        CHAT_MESSAGE,
        PING,
        PONG,
        // Lobby messages
        CREATE_LOBBY,
        JOIN_LOBBY,
        LEAVE_LOBBY,
        LOBBY_LIST_REQUEST,
        LOBBY_LIST_RESPONSE,
        LOBBY_UPDATE,
        LOBBY_CHAT_MESSAGE,
        START_GAME,
        LOBBY_CREATED,
        LOBBY_JOINED,
        LOBBY_LEFT
    }
    
    private MessageType type;
    private String playerId;
    private long timestamp;
    private Object data;
    
    public NetworkMessage(MessageType type, String playerId) {
        this.type = type;
        this.playerId = playerId;
        this.timestamp = System.currentTimeMillis();
    }
    
    public NetworkMessage(MessageType type, String playerId, Object data) {
        this(type, playerId);
        this.data = data;
    }
    
    // Getters and setters
    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    
    public String getLobbyId() {
        if (data instanceof String) {
            return (String) data;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "NetworkMessage{" +
                "type=" + type +
                ", playerId='" + playerId + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
