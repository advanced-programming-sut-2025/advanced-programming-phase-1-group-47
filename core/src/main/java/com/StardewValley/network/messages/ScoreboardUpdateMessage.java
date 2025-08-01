package com.StardewValley.network.messages;

import java.util.Map;

import com.StardewValley.network.NetworkMessage;

public class ScoreboardUpdateMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final Map<String, Integer> scores;
    private final String gameId;
    
    public ScoreboardUpdateMessage(Map<String, Integer> scores, String gameId) {
        super(MessageType.SCOREBOARD_UPDATE);
        this.scores = scores;
        this.gameId = gameId;
    }
    
    public Map<String, Integer> getScores() { return scores; }
    public String getGameId() { return gameId; }
} 