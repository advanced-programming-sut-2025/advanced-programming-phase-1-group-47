package com.StardewValley.network.messages;

import java.util.List;

import com.StardewValley.network.GameLobby;
import com.StardewValley.network.NetworkMessage;

public class LobbyUpdateMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String lobbyId;
    private final List<GameLobby> lobbies;
    
    public LobbyUpdateMessage(List<GameLobby> lobbies) {
        super(MessageType.LOBBY_UPDATE);
        this.lobbyId = null;
        this.lobbies = lobbies;
    }
    
    public LobbyUpdateMessage(String lobbyId) {
        super(MessageType.LOBBY_UPDATE);
        this.lobbyId = lobbyId;
        this.lobbies = null;
    }
    
    public String getLobbyId() { return lobbyId; }
    public List<GameLobby> getLobbies() { return lobbies; }
} 