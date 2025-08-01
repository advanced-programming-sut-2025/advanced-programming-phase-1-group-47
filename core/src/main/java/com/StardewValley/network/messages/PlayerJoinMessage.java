package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class PlayerJoinMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String username;
    
    public PlayerJoinMessage(String username) {
        super(MessageType.PLAYER_JOIN);
        this.username = username;
    }
    
    public String getUsername() { return username; }
} 