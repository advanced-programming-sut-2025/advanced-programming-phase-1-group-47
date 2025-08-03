package com.StardewValley.controllers;

import java.util.concurrent.ConcurrentHashMap;

import com.StardewValley.Main;
import com.StardewValley.View.InitPageView;
import com.StardewValley.View.LobbyView;
import com.StardewValley.View.MultiplayerGameScreen;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Player;
import com.StardewValley.network.NetworkManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class LobbyController implements NetworkManager.NetworkMessageListener {
    private LobbyView view;
    private NetworkManager networkManager;
    private Player localPlayer;
    private boolean isConnected = false;
    private boolean isHost = false;
    private String currentServerAddress;
    private int currentServerPort;
    
    public LobbyController() {
        this.networkManager = NetworkManager.getInstance();
        this.networkManager.setMessageListener(this);
    }
    
    public void setView(LobbyView view) {
        this.view = view;
    }
    
    public void connectToServer(String address, int port, String playerName) {
        if (isConnected) {
            view.updateStatus("Already connected to a server", Color.ORANGE);
            return;
        }
        
        view.updateStatus("Connecting to server...", Color.YELLOW);
        
        // Create local player
        localPlayer = new Player(playerName, "password", "email", playerName, 
                                com.StardewValley.model.enums.Gender.Male, "question", "answer");
        
        // Connect to server
        boolean connected = networkManager.connect(address, port);
        if (connected) {
            networkManager.setPlayerId(String.valueOf(localPlayer.getId()));
            networkManager.sendPlayerJoin(localPlayer);
            
            this.currentServerAddress = address;
            this.currentServerPort = port;
            this.isConnected = true;
            this.isHost = false;
            
            view.updateStatus("Connected to server!", Color.GREEN);
            view.addChatMessage("[System] Connected to server at " + address + ":" + port);
        } else {
            view.updateStatus("Failed to connect to server", Color.RED);
            view.addChatMessage("[System] Failed to connect to server");
        }
    }
    
    public void createServer(int port) {
        if (isConnected) {
            view.updateStatus("Already connected to a server", Color.ORANGE);
            return;
        }
        
        view.updateStatus("Creating server...", Color.YELLOW);
        
        // In a real implementation, this would start a local server
        // For now, we'll just simulate it
        try {
            // Start server in a separate thread
            Thread serverThread = new Thread(() -> {
                try {
                    // This would start the actual server
                    // For now, just simulate
                    Thread.sleep(1000);
                    Gdx.app.postRunnable(() -> {
                        view.updateStatus("Server created on port " + port, Color.GREEN);
                        view.addChatMessage("[System] Server created on port " + port);
                        
                        // Connect to our own server
                        connectToServer("localhost", port, "Host");
                        isHost = true;
                    });
                } catch (InterruptedException e) {
                    Gdx.app.postRunnable(() -> {
                        view.updateStatus("Failed to create server", Color.RED);
                        view.addChatMessage("[System] Failed to create server");
                    });
                }
            });
            serverThread.start();
            
        } catch (Exception e) {
            view.updateStatus("Failed to create server: " + e.getMessage(), Color.RED);
            view.addChatMessage("[System] Failed to create server: " + e.getMessage());
        }
    }
    
    public void refreshServerList() {
        view.updateStatus("Refreshing server list...", Color.YELLOW);
        
        // In a real implementation, this would discover servers on the network
        // For now, we'll just refresh the UI
        view.refreshServerList();
        view.updateStatus("Server list refreshed", Color.GREEN);
    }
    
    public void sendChatMessage(String message) {
        if (!isConnected) {
            view.addChatMessage("[System] Not connected to any server");
            return;
        }
        
        networkManager.sendChatMessage(message);
        view.addChatMessage("You: " + message);
    }
    
    public void goBackToMainMenu() {
        if (isConnected) {
            disconnectFromServer();
        }
        
        Main.getMain().setScreen(new InitPageView(new InitPageController(), 
                                                 GameAssetManager.getGameAssetManager().getSkin()));
    }
    
    public void disconnectFromServer() {
        if (isConnected) {
            networkManager.sendPlayerLeave();
            networkManager.disconnect();
            isConnected = false;
            isHost = false;
            view.updateStatus("Disconnected from server", Color.ORANGE);
            view.addChatMessage("[System] Disconnected from server");
        }
    }
    
    public void joinGame() {
        if (!isConnected) {
            view.addChatMessage("[System] Must be connected to a server to join game");
            return;
        }
        
        view.updateStatus("Joining game...", Color.YELLOW);
        
        // Switch to multiplayer game screen
        Main.getMain().setScreen(new MultiplayerGameScreen(this));
    }
    
    // NetworkMessageListener implementations
    @Override
    public void onPlayerJoined(Player player) {
        if (view != null) {
            Gdx.app.postRunnable(() -> {
                view.addChatMessage("[System] " + player.getNickname() + " joined the game");
                updatePlayerList();
            });
        }
    }
    
    @Override
    public void onPlayerLeft(String playerId) {
        if (view != null) {
            Gdx.app.postRunnable(() -> {
                view.addChatMessage("[System] A player left the game");
                updatePlayerList();
            });
        }
    }
    
    @Override
    public void onGameStateUpdate(com.StardewValley.model.Game game) {
        // Handle game state updates
        if (view != null) {
            Gdx.app.postRunnable(() -> {
                view.addChatMessage("[System] Game state updated");
            });
        }
    }
    
    @Override
    public void onChatMessage(String playerId, String message) {
        if (view != null) {
            Gdx.app.postRunnable(() -> {
                // Find player name by ID
                String playerName = "Unknown";
                ConcurrentHashMap<String, Player> players = networkManager.getConnectedPlayers();
                for (Player player : players.values()) {
                    if (String.valueOf(player.getId()).equals(playerId)) {
                        playerName = player.getNickname();
                        break;
                    }
                }
                view.addChatMessage(playerName + ": " + message);
            });
        }
    }
    
    @Override
    public void onPlayerPositionUpdate(String playerId, float x, float y) {
        // Handle player position updates
        // This would be used in the game screen, not lobby
    }
    
    @Override
    public void onError(String error) {
        if (view != null) {
            Gdx.app.postRunnable(() -> {
                view.updateStatus("Error: " + error, Color.RED);
                view.addChatMessage("[System] Error: " + error);
            });
        }
    }
    
    private void updatePlayerList() {
        if (view != null) {
            ConcurrentHashMap<String, Player> players = networkManager.getConnectedPlayers();
            String[] playerNames = new String[players.size() + 1]; // +1 for local player
            
            int index = 0;
            playerNames[index++] = localPlayer.getNickname() + " (You)";
            
            for (Player player : players.values()) {
                if (!String.valueOf(player.getId()).equals(String.valueOf(localPlayer.getId()))) {
                    playerNames[index++] = player.getNickname();
                }
            }
            
            String[] finalPlayerNames = new String[index];
            System.arraycopy(playerNames, 0, finalPlayerNames, 0, index);
            
            Gdx.app.postRunnable(() -> {
                view.updatePlayerList(finalPlayerNames);
            });
        }
    }
    
    // Getters
    public boolean isConnected() {
        return isConnected;
    }
    
    public boolean isHost() {
        return isHost;
    }
    
    public Player getLocalPlayer() {
        return localPlayer;
    }
    
    public NetworkManager getNetworkManager() {
        return networkManager;
    }
} 