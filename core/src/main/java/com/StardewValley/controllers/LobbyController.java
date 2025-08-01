package com.StardewValley.controllers;

import java.util.List;

import com.StardewValley.View.LobbyView;
import com.StardewValley.network.GameLobby;

public class LobbyController {
    private LobbyView view;
    private String currentUsername;
    
    public LobbyController(String username) {
        this.currentUsername = username;
        setupNetworkCallbacks();
    }
    
    public void setView(LobbyView view) {
        this.view = view;
    }
    
    private void setupNetworkCallbacks() {
        // This would be called when network messages are received
        // For now, we'll handle this in the NetworkManager
    }
    
    public void handleLobbyUpdate(List<GameLobby> lobbies) {
        if (view != null) {
            view.updateLobbyList(lobbies);
        }
    }
    
    public void handleChatMessage(String sender, String message) {
        if (view != null) {
            view.addChatMessage(sender, message);
        }
    }
    
    public void handlePlayerJoin(String username) {
        // Handle player join notification
        System.out.println("Player joined: " + username);
    }
    
    public void handlePlayerLeave(String username) {
        // Handle player leave notification
        System.out.println("Player left: " + username);
    }
    
    public void handleGameStart(String gameId, List<String> playerNames) {
        // Handle game start notification
        System.out.println("Game starting with players: " + playerNames);
        // Transition to game screen
    }
    
    public String getCurrentUsername() {
        return currentUsername;
    }
} 