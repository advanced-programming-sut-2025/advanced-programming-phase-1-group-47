package com.StardewValley.network.messages;

import java.util.List;

import com.StardewValley.network.GameLobby;
import com.StardewValley.network.NetworkMessage;

public class LobbyListMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final List<GameLobby> lobbies;
    
    public LobbyListMessage(List<GameLobby> lobbies) {
        super(MessageType.LOBBY_LIST);
        this.lobbies = lobbies;
    }
    
    public List<GameLobby> getLobbies() { return lobbies; }
} 