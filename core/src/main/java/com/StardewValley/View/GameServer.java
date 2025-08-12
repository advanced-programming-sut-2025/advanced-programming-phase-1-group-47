package com.StardewValley.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Game Server - runs on port 8081
 * Handles in-game multiplayer functionality and chat
 */
public class GameServer {
    private static final int PORT = 8081;
    private ServerSocket serverSocket;
    private final Map<String, GameClientHandler> connectedClients = new ConcurrentHashMap<>();
    private final Map<String, String> playerLobbyMap = new ConcurrentHashMap<>();
    private boolean isRunning = false;
    
    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.start();
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;
            System.out.println("=== GAME SERVER STARTED ===");
            System.out.println("Game Server listening on port " + PORT);
            System.out.println("Ready to accept game connections...");
            System.out.println("================================");
            
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Game Server: New client connected from " + clientSocket.getInetAddress());
                    
                    GameClientHandler clientHandler = new GameClientHandler(clientSocket, this);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    if (isRunning) {
                        System.err.println("Game Server: Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Game Server: Could not start server on port " + PORT + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Game Server: Server stopped");
        } catch (IOException e) {
            System.err.println("Game Server: Error stopping server: " + e.getMessage());
        }
    }
    
    public void addClient(String playerId, GameClientHandler client) {
        connectedClients.put(playerId, client);
        System.out.println("Game Server: Player " + playerId + " connected. Total players: " + connectedClients.size());
        System.out.println("Game Server: Connected clients: " + connectedClients.keySet());
        System.out.println("Game Server: playerLobbyMap before adding client: " + playerLobbyMap);
    }
    
    public void removeClient(String playerId) {
        connectedClients.remove(playerId);
        playerLobbyMap.remove(playerId);
        System.out.println("Game Server: Player " + playerId + " disconnected. Total players: " + connectedClients.size());
        System.out.println("Game Server: Remaining players: " + connectedClients.keySet());
        System.out.println("Game Server: Remaining lobby assignments: " + playerLobbyMap);
    }
    
    public void setPlayerLobby(String playerId, String lobbyId) {
        System.out.println("Game Server: Setting player " + playerId + " to lobby " + lobbyId);
        System.out.println("Game Server: playerLobbyMap before assignment: " + playerLobbyMap);
        playerLobbyMap.put(playerId, lobbyId);
        System.out.println("Game Server: Player " + playerId + " assigned to lobby " + lobbyId);
        System.out.println("Game Server: playerLobbyMap after assignment: " + playerLobbyMap);
    }
    
    public void broadcastToLobby(String lobbyId, String message) {
        System.out.println("Game Server: Broadcasting to lobby " + lobbyId + ": " + message);
        System.out.println("Game Server: Current playerLobbyMap: " + playerLobbyMap);
        System.out.println("Game Server: Current connectedClients: " + connectedClients.keySet());
        
        int sentCount = 0;
        
        System.out.println("Game Server: Looking for players in lobby: " + lobbyId);
        for (Map.Entry<String, String> entry : playerLobbyMap.entrySet()) {
            System.out.println("Game Server: Checking entry: " + entry.getKey() + " -> " + entry.getValue());
            if (lobbyId.equals(entry.getValue())) {
                String playerId = entry.getKey();
                System.out.println("Game Server: Found player " + playerId + " in lobby " + lobbyId);
                GameClientHandler client = connectedClients.get(playerId);
                if (client != null) {
                    try {
                        client.sendMessage(message);
                        sentCount++;
                        System.out.println("Game Server: Sent message to " + playerId);
                    } catch (Exception e) {
                        System.err.println("Game Server: Error sending to " + playerId + ": " + e.getMessage());
                    }
                } else {
                    System.err.println("Game Server: Client handler is null for " + playerId);
                }
            } else {
                System.out.println("Game Server: Player " + entry.getKey() + " is in lobby " + entry.getValue() + " (not " + lobbyId + ")");
            }
        }
        
        System.out.println("Game Server: Message sent to " + sentCount + " players in lobby " + lobbyId);
    }
    
    public boolean isClientConnected(String playerId) {
        return connectedClients.containsKey(playerId);
    }
    
    public void markAsGameClient(String playerId, String lobbyId) {
        System.out.println("Game Server: Marking client " + playerId + " as game client for lobby " + lobbyId);
        setPlayerLobby(playerId, lobbyId);
        System.out.println("Game Server: Marked client " + playerId + " as game client for lobby " + lobbyId);
        System.out.println("Game Server: Current playerLobbyMap: " + playerLobbyMap);
    }
    
    /**
     * Gets the list of players in a specific lobby
     */
    public List<String> getPlayersInLobby(String lobbyId) {
        List<String> playersInLobby = new ArrayList<>();
        for (Map.Entry<String, String> entry : playerLobbyMap.entrySet()) {
            if (lobbyId.equals(entry.getValue())) {
                playersInLobby.add(entry.getKey());
            }
        }
        return playersInLobby;
    }
}

/**
 * Handles individual client connections to the game server
 */
class GameClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerId;
    private GameServer server;
    
    public GameClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Game Server: Client handler created successfully for " + socket.getInetAddress());
            System.out.println("Game Server: Socket is connected: " + socket.isConnected());
            System.out.println("Game Server: Socket is closed: " + socket.isClosed());
        } catch (IOException e) {
            System.err.println("Game Server: Error creating client handler: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                handleMessage(message);
            }
            System.out.println("Game Server: Client " + (playerId != null ? playerId : "unknown") + " disconnected normally");
        } catch (IOException e) {
            if (socket != null && !socket.isClosed()) {
                System.err.println("Game Server: Client connection error: " + e.getMessage());
                System.err.println("Game Server: Error details: " + e.getClass().getSimpleName());
                e.printStackTrace();
            }
        } finally {
            System.out.println("Game Server: Client handler thread ending for " + (playerId != null ? playerId : "unknown"));
            cleanup();
        }
    }
    
    private void handleMessage(String message) {
        System.out.println("Game Server: Received from " + (playerId != null ? playerId : "unknown") + ": " + message);
        System.out.println("Game Server: Current playerId in handler: " + playerId);
        
        if (message.startsWith("GAME_CLIENT:")) {
            // Handle game client identification
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String playerNickname = parts[1];
                String lobbyId = parts[2];
                
                System.out.println("Game Server: Processing GAME_CLIENT message for " + playerNickname + " in lobby " + lobbyId);
                
                // Set the playerId for this handler
                this.playerId = playerNickname;
                
                if (server.isClientConnected(playerNickname)) {
                    // Player is already connected, just update their lobby association
                    System.out.println("Game Server: Game client identified: " + playerNickname + " for lobby: " + lobbyId + " (already connected)");
                    sendMessage("GAME_CLIENT_CONFIRMED:" + lobbyId);
                    server.markAsGameClient(playerNickname, lobbyId);
                } else {
                    // This is a new connection
                    System.out.println("Game Server: Adding new client " + playerNickname + " to connected clients");
                    server.addClient(playerNickname, this);
                    System.out.println("Game Server: Game client connected: " + playerNickname + " for lobby: " + lobbyId);
                    sendMessage("GAME_CLIENT_CONFIRMED:" + lobbyId);
                    // Mark as game client after adding to connected clients
                    System.out.println("Game Server: Marking " + playerNickname + " as game client for lobby " + lobbyId);
                    server.markAsGameClient(playerNickname, lobbyId);
                }
            } else {
                System.err.println("Game Server: Invalid GAME_CLIENT message format: " + message);
            }
        } else if (message.startsWith("GAME_CHAT:")) {
            // Handle in-game chat messages
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String lobbyId = parts[1];
                String chatMessage = parts[2];
                System.out.println("Game Server: Processing chat message from " + playerId + " in lobby " + lobbyId + ": " + chatMessage);
                // Broadcast in-game chat to all players in the lobby
                server.broadcastToLobby(lobbyId, "GAME_CHAT:" + playerId + ":" + chatMessage);
            } else {
                System.err.println("Game Server: Invalid GAME_CHAT message format: " + message);
            }
        } else if (message.startsWith("PLAYER_LIST_REQUEST:")) {
            // Handle player list requests
            String[] parts = message.split(":", 2);
            if (parts.length == 2) {
                String lobbyId = parts[1];
                System.out.println("Game Server: Player list requested for lobby: " + lobbyId);
                
                // Get all players in the specified lobby
                List<String> playersInLobby = server.getPlayersInLobby(lobbyId);
                
                // Send player list back to the requesting client
                String playerListMessage = "PLAYER_LIST_RESPONSE:" + String.join(",", playersInLobby);
                System.out.println("Game Server: Sending player list: " + playerListMessage);
                sendMessage(playerListMessage);
            }
        } else if (message.startsWith("PING")) {
            // Handle ping messages silently
            // sendMessage("PONG");
        } else {
            System.out.println("Game Server: Unknown message format: " + message);
        }
    }
    
    public void sendMessage(String message) {
        if (out != null) {
            try {
                out.println(message);
                out.flush();
                System.out.println("Game Server: Sent message to " + playerId + ": " + message);
            } catch (Exception e) {
                System.err.println("Game Server: Error sending message to " + playerId + ": " + e.getMessage());
            }
        } else {
            System.err.println("Game Server: Cannot send message - output stream is null for " + playerId);
        }
    }
    
    private void cleanup() {
        try {
            if (playerId != null) {
                System.out.println("Game Server: Cleaning up connection for " + playerId);
                server.removeClient(playerId);
            }
            if (out != null) {
                out.close();
                System.out.println("Game Server: Output stream closed for " + (playerId != null ? playerId : "unknown"));
            }
            if (in != null) {
                in.close();
                System.out.println("Game Server: Input stream closed for " + (playerId != null ? playerId : "unknown"));
            }
            if (socket != null) {
                socket.close();
                System.out.println("Game Server: Socket closed for " + (playerId != null ? playerId : "unknown"));
            }
        } catch (IOException e) {
            System.err.println("Game Server: Error during cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
