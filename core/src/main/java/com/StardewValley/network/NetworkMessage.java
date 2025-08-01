package com.StardewValley.network;

import java.io.Serializable;

public abstract class NetworkMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final MessageType type;
    
    public NetworkMessage(MessageType type) {
        this.type = type;
    }
    
    public MessageType getType() {
        return type;
    }
    
    public enum MessageType {
        LOGIN,
        LOGIN_RESPONSE,
        CREATE_LOBBY,
        JOIN_LOBBY,
        LEAVE_LOBBY,
        LOBBY_UPDATE,
        LOBBY_LIST,
        REQUEST_LOBBY_LIST,
        PLAYER_JOIN,
        PLAYER_LEAVE,
        GAME_START,
        CHAT_MESSAGE,
        PLAYER_ACTION,
        SCOREBOARD_UPDATE
    }
} 