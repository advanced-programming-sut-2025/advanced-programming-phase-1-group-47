package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class PlayerActionMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String action;
    private final String username;
    private final String gameId;
    
    public PlayerActionMessage(String action, String username) {
        super(MessageType.PLAYER_ACTION);
        this.action = action;
        this.username = username;
        this.gameId = null;
    }
    
    public PlayerActionMessage(String action, String username, String gameId) {
        super(MessageType.PLAYER_ACTION);
        this.action = action;
        this.username = username;
        this.gameId = gameId;
    }
    
    public String getAction() { return action; }
    public String getUsername() { return username; }
    public String getGameId() { return gameId; }
} 