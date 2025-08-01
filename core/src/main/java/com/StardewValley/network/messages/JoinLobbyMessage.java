package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class JoinLobbyMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String lobbyId;
    private final String username;
    
    public JoinLobbyMessage(String lobbyId, String username) {
        super(MessageType.JOIN_LOBBY);
        this.lobbyId = lobbyId;
        this.username = username;
    }
    
    public String getLobbyId() { return lobbyId; }
    public String getUsername() { return username; }
} 