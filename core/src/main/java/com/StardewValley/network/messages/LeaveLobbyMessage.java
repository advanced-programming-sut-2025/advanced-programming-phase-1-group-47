package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class LeaveLobbyMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String lobbyId;
    private final String username;
    
    public LeaveLobbyMessage(String lobbyId, String username) {
        super(MessageType.LEAVE_LOBBY);
        this.lobbyId = lobbyId;
        this.username = username;
    }
    
    public String getLobbyId() { return lobbyId; }
    public String getUsername() { return username; }
} 