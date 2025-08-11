package com.StardewValley.network;

import com.StardewValley.model.Game;
import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.network.PlayerPositionUpdate;
import com.StardewValley.network.Lobby;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiplayerGameServer {
    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_PLAYERS = 8;
    private static final int POSITION_UPDATE_RATE = 60; // Updates per second
    
    private ServerSocket serverSocket;
    private final Map<String, ClientHandler> connectedClients;
    private final Map<String, Lobby> lobbies;
    private final Game gameState;
    private final ScheduledExecutorService scheduler;
    private volatile boolean isRunning;
    private final int port;
    
    public MultiplayerGameServer(int port) {
        this.port = port;
        this.connectedClients = new ConcurrentHashMap<>();
        this.lobbies = new ConcurrentHashMap<>();
        this.gameState = new Game(); // Initialize with empty game
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.isRunning = false;
    }
    
    public MultiplayerGameServer() {
        this(DEFAULT_PORT);
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Multiplayer server started on port " + port);
            
            // Start position update scheduler
            startPositionUpdateScheduler();
            
            // Accept client connections
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (connectedClients.size() >= MAX_PLAYERS) {
                        // Reject connection if server is full
                        clientSocket.close();
                        System.out.println("Server full, rejecting connection from " + clientSocket.getInetAddress());
                        continue;
                    }
                    
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    new Thread(clientHandler).start();
                    
                } catch (IOException e) {
                    if (isRunning) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
        }
    }
    
    private void startPositionUpdateScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            if (isRunning && !connectedClients.isEmpty()) {
                broadcastPositionUpdates();
            }
        }, 0, 1000 / POSITION_UPDATE_RATE, TimeUnit.MILLISECONDS);
    }
    
    private void broadcastPositionUpdates() {
        // Collect all player positions
        List<PlayerPositionUpdate> updates = new ArrayList<>();
        for (ClientHandler client : connectedClients.values()) {
            Player player = client.getPlayer();
            if (player != null) {
                PlayerPositionUpdate update = new PlayerPositionUpdate(
                    player.getUsername(),
                    player.getCoordinates(),
                    client.getMoveDirection(),
                    client.isMoving()
                );
                updates.add(update);
            }
        }
        
        // Broadcast to all clients
        for (ClientHandler client : connectedClients.values()) {
            client.sendPositionUpdates(updates);
        }
    }
    
    public void addClient(String playerId, ClientHandler client) {
        connectedClients.put(playerId, client);
        System.out.println("Player " + playerId + " connected. Total players: " + connectedClients.size());
        
        // Notify other clients about new player
        NetworkMessage joinMessage = new NetworkMessage(NetworkMessage.MessageType.PLAYER_JOIN, playerId);
        broadcastToOthers(playerId, joinMessage);
    }
    
    public void removeClient(String playerId) {
        connectedClients.remove(playerId);
        System.out.println("Player " + playerId + " disconnected. Total players: " + connectedClients.size());
        
        // Notify other clients about player leaving
        NetworkMessage leaveMessage = new NetworkMessage(NetworkMessage.MessageType.PLAYER_LEAVE, playerId);
        broadcastToOthers(playerId, leaveMessage);
    }
    
    public void broadcastToOthers(String excludePlayerId, NetworkMessage message) {
        for (Map.Entry<String, ClientHandler> entry : connectedClients.entrySet()) {
            if (!entry.getKey().equals(excludePlayerId)) {
                entry.getValue().sendMessage(message);
            }
        }
    }
    
    public void broadcastToAll(NetworkMessage message) {
        for (ClientHandler client : connectedClients.values()) {
            client.sendMessage(message);
        }
    }
    
    // Lobby management methods
    public String createLobby(String lobbyName, String hostPlayerId, int maxPlayers) {
        String lobbyId = generateLobbyId();
        Lobby lobby = new Lobby(lobbyId, lobbyName, hostPlayerId, maxPlayers);
        
        // Add host to lobby
        ClientHandler hostClient = connectedClients.get(hostPlayerId);
        if (hostClient != null && hostClient.getPlayer() != null) {
            lobby.addPlayer(hostClient.getPlayer());
        }
        
        lobbies.put(lobbyId, lobby);
        System.out.println("Lobby created: " + lobbyId + " by " + hostPlayerId);
        
        // Notify host that lobby was created
        if (hostClient != null) {
            NetworkMessage lobbyCreatedMessage = new NetworkMessage(
                NetworkMessage.MessageType.LOBBY_CREATED, 
                hostPlayerId, 
                lobby
            );
            hostClient.sendMessage(lobbyCreatedMessage);
        }
        
        return lobbyId;
    }
    
    public boolean joinLobby(String lobbyId, String playerId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }
        
        ClientHandler client = connectedClients.get(playerId);
        if (client == null || client.getPlayer() == null) {
            return false;
        }
        
        if (lobby.addPlayer(client.getPlayer())) {
            // Notify all players in lobby
            broadcastLobbyUpdate(lobbyId);
            
            // Notify player that they joined
            NetworkMessage lobbyJoinedMessage = new NetworkMessage(
                NetworkMessage.MessageType.LOBBY_JOINED, 
                playerId, 
                lobby
            );
            client.sendMessage(lobbyJoinedMessage);
            
            System.out.println("Player " + playerId + " joined lobby " + lobbyId);
            return true;
        }
        
        return false;
    }
    
    public boolean leaveLobby(String lobbyId, String playerId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }
        
        if (lobby.removePlayer(playerId)) {
            // If lobby is empty, remove it
            if (lobby.getCurrentPlayerCount() == 0) {
                lobbies.remove(lobbyId);
                System.out.println("Lobby " + lobbyId + " removed (empty)");
            } else {
                // Notify remaining players
                broadcastLobbyUpdate(lobbyId);
            }
            
            // Notify player that they left
            ClientHandler client = connectedClients.get(playerId);
            if (client != null) {
                NetworkMessage lobbyLeftMessage = new NetworkMessage(
                    NetworkMessage.MessageType.LOBBY_LEFT, 
                    playerId, 
                    lobbyId
                );
                client.sendMessage(lobbyLeftMessage);
            }
            
            System.out.println("Player " + playerId + " left lobby " + lobbyId);
            return true;
        }
        
        return false;
    }
    
    public void addChatMessage(String lobbyId, String playerId, String message) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.addChatMessage(playerId, message);
            broadcastLobbyUpdate(lobbyId);
        }
    }
    
    public List<Lobby> getLobbyList() {
        return new ArrayList<>(lobbies.values());
    }
    
    private void broadcastLobbyUpdate(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) return;
        
        NetworkMessage lobbyUpdateMessage = new NetworkMessage(
            NetworkMessage.MessageType.LOBBY_UPDATE, 
            "SERVER", 
            lobby
        );
        
        // Send to all players in the lobby
        for (Player player : lobby.getPlayers()) {
            ClientHandler client = connectedClients.get(player.getUsername());
            if (client != null) {
                client.sendMessage(lobbyUpdateMessage);
            }
        }
    }
    
    private String generateLobbyId() {
        return "LOBBY_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    public Game getGameState() {
        return gameState;
    }
    
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            scheduler.shutdown();
            System.out.println("Server stopped");
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number, using default: " + DEFAULT_PORT);
            }
        }
        
        MultiplayerGameServer server = new MultiplayerGameServer(port);
        server.start();
    }
}
