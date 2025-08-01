package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class PlayerLeaveMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String username;
    
    public PlayerLeaveMessage(String username) {
        super(MessageType.PLAYER_LEAVE);
        this.username = username;
    }
    
    public String getUsername() { return username; }
} 