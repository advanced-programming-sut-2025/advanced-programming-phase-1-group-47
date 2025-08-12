package com.StardewValley.network;

import com.StardewValley.model.Player;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lobby implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String lobbyId;
    private String lobbyName;
    private String hostPlayerId;
    private final List<Player> players;
    private final List<ChatMessage> chatMessages;
    private final int maxPlayers;
    private boolean isGameStarted;
    private long createdAt;
    
    public static class ChatMessage implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String playerId;
        private String message;
        private long timestamp;
        
        public ChatMessage(String playerId, String message) {
            this.playerId = playerId;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getters
        public String getPlayerId() { return playerId; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return playerId + ": " + message;
        }
    }
    
    public Lobby(String lobbyId, String lobbyName, String hostPlayerId, int maxPlayers) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.hostPlayerId = hostPlayerId;
        this.maxPlayers = maxPlayers;
        this.players = new CopyOnWriteArrayList<>();
        this.chatMessages = new CopyOnWriteArrayList<>();
        this.isGameStarted = false;
        this.createdAt = System.currentTimeMillis();
    }
    
    public boolean addPlayer(Player player) {
        if (players.size() >= maxPlayers || isGameStarted) {
            return false;
        }
        
        // Check if player is already in lobby
        for (Player existingPlayer : players) {
            if (existingPlayer.getUsername().equals(player.getUsername())) {
                return false;
            }
        }
        
        players.add(player);
        return true;
    }
    
    public boolean removePlayer(String playerId) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getUsername().equals(playerId)) {
                iterator.remove();
                
                // If host leaves, transfer host to next player
                if (playerId.equals(hostPlayerId) && !players.isEmpty()) {
                    hostPlayerId = players.get(0).getUsername();
                }
                
                return true;
            }
        }
        return false;
    }
    
    public void addChatMessage(String playerId, String message) {
        ChatMessage chatMessage = new ChatMessage(playerId, message);
        chatMessages.add(chatMessage);
        
        // Keep only last 100 messages to prevent memory issues
        if (chatMessages.size() > 100) {
            chatMessages.remove(0);
        }
    }
    
    public boolean isPlayerInLobby(String playerId) {
        for (Player player : players) {
            if (player.getUsername().equals(playerId)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isHost(String playerId) {
        return hostPlayerId.equals(playerId);
    }
    
    public boolean canStartGame() {
        return players.size() >= 1 && isHost(hostPlayerId);
    }
    
    // Getters
    public String getLobbyId() { return lobbyId; }
    public String getLobbyName() { return lobbyName; }
    public String getHostPlayerId() { return hostPlayerId; }
    public List<Player> getPlayers() { return new ArrayList<>(players); }
    public List<ChatMessage> getChatMessages() { return new ArrayList<>(chatMessages); }
    public int getMaxPlayers() { return maxPlayers; }
    public int getCurrentPlayerCount() { return players.size(); }
    public boolean isGameStarted() { return isGameStarted; }
    public long getCreatedAt() { return createdAt; }
    
    // Setters
    public void setGameStarted(boolean gameStarted) { this.isGameStarted = gameStarted; }
    
    @Override
    public String toString() {
        return "Lobby{" +
                "id='" + lobbyId + '\'' +
                ", name='" + lobbyName + '\'' +
                ", host='" + hostPlayerId + '\'' +
                ", players=" + players.size() + "/" + maxPlayers +
                ", gameStarted=" + isGameStarted +
                '}';
    }
}
