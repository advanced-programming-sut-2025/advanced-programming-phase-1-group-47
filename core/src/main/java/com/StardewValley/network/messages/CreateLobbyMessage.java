package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class CreateLobbyMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String lobbyName;
    private final String creatorUsername;
    
    public CreateLobbyMessage(String lobbyName, String creatorUsername) {
        super(MessageType.CREATE_LOBBY);
        this.lobbyName = lobbyName;
        this.creatorUsername = creatorUsername;
    }
    
    public String getLobbyName() { return lobbyName; }
    public String getCreatorUsername() { return creatorUsername; }
} 