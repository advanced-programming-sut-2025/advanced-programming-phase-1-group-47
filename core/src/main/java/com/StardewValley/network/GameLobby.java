package com.StardewValley.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLobby implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String id;
    private final String name;
    private final String creatorUsername;
    private final List<String> players;
    private final int maxPlayers;
    private boolean isPrivate;
    private boolean isGameStarted;
    private String gameId;
    
    public GameLobby(String name, String creatorUsername) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.players = new ArrayList<>();
        this.maxPlayers = 4; // Default max players for Stardew Valley multiplayer
        this.isPrivate = false;
        this.isGameStarted = false;
        this.gameId = null;
    }
    
    public void addPlayer(String username) {
        if (!players.contains(username) && players.size() < maxPlayers && !isGameStarted) {
            players.add(username);
        }
    }
    
    public void removePlayer(String username) {
        players.remove(username);
    }
    
    public boolean canJoin() {
        return !isGameStarted && players.size() < maxPlayers;
    }
    
    public boolean isEmpty() {
        return players.isEmpty();
    }
    
    public void startGame(String gameId) {
        this.isGameStarted = true;
        this.gameId = gameId;
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCreatorUsername() { return creatorUsername; }
    public List<String> getPlayers() { return new ArrayList<>(players); }
    public int getMaxPlayers() { return maxPlayers; }
    public int getCurrentPlayerCount() { return players.size(); }
    public boolean isPrivate() { return isPrivate; }
    public boolean isGameStarted() { return isGameStarted; }
    public String getGameId() { return gameId; }
    
    // Setters
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    
    @Override
    public String toString() {
        return String.format("Lobby[id=%s, name=%s, players=%d/%d, started=%s]", 
                           id, name, players.size(), maxPlayers, isGameStarted);
    }
} 