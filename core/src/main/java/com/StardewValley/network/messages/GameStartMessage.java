package com.StardewValley.network.messages;

import java.util.List;

import com.StardewValley.network.NetworkMessage;

public class GameStartMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String gameId;
    private final List<String> playerNames;
    
    public GameStartMessage(String gameId, List<String> playerNames) {
        super(MessageType.GAME_START);
        this.gameId = gameId;
        this.playerNames = playerNames;
    }
    
    public String getGameId() { return gameId; }
    public List<String> getPlayerNames() { return playerNames; }
} 