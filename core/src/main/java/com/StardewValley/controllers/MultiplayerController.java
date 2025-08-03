package com.StardewValley.controllers;

import com.StardewValley.network.NetworkManager;

import com.StardewValley.network.GameAction;
import java.awt.Point;
import com.StardewValley.model.Player;
import com.StardewValley.model.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ConcurrentHashMap;

public class MultiplayerController implements NetworkManager.NetworkMessageListener {
    private NetworkManager networkManager;
    private Player localPlayer;
    private Game game;
    private boolean isHost;
    private String serverAddress;
    private int serverPort;
    private Array<String> chatMessages;
    private ConcurrentHashMap<String, Player> otherPlayers;
    private MultiplayerEventListener eventListener;

    public interface MultiplayerEventListener {
        void onPlayerJoined(Player player);
        void onPlayerLeft(String playerId);
        void onChatMessage(String playerId, String message);
        void onPlayerPositionUpdate(String playerId, float x, float y);
        void onGameStateUpdate(Game game);
        void onConnectionError(String error);
        void onConnectionEstablished();
    }

    public MultiplayerController() {
        this.networkManager = NetworkManager.getInstance();
        this.networkManager.setMessageListener(this);
        this.chatMessages = new Array<>();
        this.otherPlayers = new ConcurrentHashMap<>();
    }

    public boolean connectToServer(String address, int port, Player player) {
        this.serverAddress = address;
        this.serverPort = port;
        this.localPlayer = player;
        this.isHost = false;

        boolean connected = networkManager.connect(address, port);
        if (connected) {
            networkManager.setPlayerId(String.valueOf(player.getId()));
            networkManager.sendPlayerJoin(player);
            if (eventListener != null) {
                eventListener.onConnectionEstablished();
            }
        }
        return connected;
    }

    public void disconnect() {
        if (networkManager.isConnected()) {
            networkManager.sendPlayerLeave();
            networkManager.disconnect();
        }
    }

    public void sendPlayerPosition(float x, float y) {
        if (networkManager.isConnected()) {
            networkManager.sendPlayerPosition(x, y);
        }
    }

    public void sendChatMessage(String message) {
        if (networkManager.isConnected()) {
            networkManager.sendChatMessage(message);
            // Add to local chat
            addChatMessage(localPlayer.getNickname(), message);
        }
    }

    public void sendGameAction(GameAction action) {
        if (networkManager.isConnected()) {
            networkManager.sendGameAction(action);
        }
    }

    public void setEventListener(MultiplayerEventListener listener) {
        this.eventListener = listener;
    }

    public boolean isConnected() {
        return networkManager.isConnected();
    }

    public Array<String> getChatMessages() {
        return chatMessages;
    }

    public ConcurrentHashMap<String, Player> getOtherPlayers() {
        return otherPlayers;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public boolean isHost() {
        return isHost;
    }

    private void addChatMessage(String playerName, String message) {
        String chatEntry = playerName + ": " + message;
        chatMessages.add(chatEntry);
        
        // Keep only last 50 messages
        if (chatMessages.size > 50) {
            chatMessages.removeIndex(0);
        }
    }

    // NetworkMessageListener implementations
    @Override
    public void onPlayerJoined(Player player) {
        if (!String.valueOf(player.getId()).equals(String.valueOf(localPlayer.getId()))) {
            otherPlayers.put(String.valueOf(player.getId()), player);
            addChatMessage("System", player.getNickname() + " joined the game");
            
            if (eventListener != null) {
                eventListener.onPlayerJoined(player);
            }
        }
    }

    @Override
    public void onPlayerLeft(String playerId) {
        Player leftPlayer = otherPlayers.remove(playerId);
        if (leftPlayer != null) {
            addChatMessage("System", leftPlayer.getNickname() + " left the game");
            
            if (eventListener != null) {
                eventListener.onPlayerLeft(playerId);
            }
        }
    }

    @Override
    public void onGameStateUpdate(Game game) {
        this.game = game;
        
        if (eventListener != null) {
            eventListener.onGameStateUpdate(game);
        }
    }

    @Override
    public void onChatMessage(String playerId, String message) {
        Player sender = otherPlayers.get(playerId);
        if (sender != null) {
            addChatMessage(sender.getNickname(), message);
            
            if (eventListener != null) {
                eventListener.onChatMessage(playerId, message);
            }
        }
    }

    @Override
    public void onPlayerPositionUpdate(String playerId, float x, float y) {
        Player player = otherPlayers.get(playerId);
        if (player != null) {
            player.setCoordinates(new Point((int)x, (int)y));
            
            if (eventListener != null) {
                eventListener.onPlayerPositionUpdate(playerId, x, y);
            }
        }
    }

    @Override
    public void onError(String error) {
        Gdx.app.error("MultiplayerController", "Network error: " + error);
        addChatMessage("System", "Error: " + error);
        
        if (eventListener != null) {
            eventListener.onConnectionError(error);
        }
    }

    public void update() {
        // Update local player position periodically
        if (networkManager.isConnected() && localPlayer != null) {
            // This could be optimized to only send when position actually changes
            Point coords = localPlayer.getCoordinates();
            networkManager.sendPlayerPosition(coords.x, coords.y);
        }
    }
} 