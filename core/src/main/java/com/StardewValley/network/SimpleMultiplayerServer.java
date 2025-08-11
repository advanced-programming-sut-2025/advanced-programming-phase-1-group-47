package com.StardewValley.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleMultiplayerServer {
    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_PLAYERS = 8;
    
    private ServerSocket serverSocket;
    private final Map<String, ClientConnection> connectedClients;
    private final Map<String, SimpleLobby> lobbies;
    private volatile boolean isRunning;
    private final int port;
    
    public static class SimpleLobby {
        private String lobbyId;
        private String lobbyName;
        private String hostPlayerId;
        private final List<String> players;
        private final List<String> chatMessages;
        private final int maxPlayers;
        
        public SimpleLobby(String lobbyId, String lobbyName, String hostPlayerId, int maxPlayers) {
            this.lobbyId = lobbyId;
            this.lobbyName = lobbyName;
            this.hostPlayerId = hostPlayerId;
            this.players = new ArrayList<>();
            this.chatMessages = new ArrayList<>();
            this.maxPlayers = maxPlayers;
            this.players.add(hostPlayerId);
        }
        
        public boolean addPlayer(String playerId) {
            if (players.size() >= maxPlayers) {
                return false;
            }
            if (!players.contains(playerId)) {
                players.add(playerId);
                return true;
            }
            return false;
        }
        
        public boolean removePlayer(String playerId) {
            boolean removed = players.remove(playerId);
            if (removed && playerId.equals(hostPlayerId) && !players.isEmpty()) {
                hostPlayerId = players.get(0);
            }
            return removed;
        }
        
        public void addChatMessage(String playerId, String message) {
            chatMessages.add(playerId + ": " + message);
            if (chatMessages.size() > 100) {
                chatMessages.remove(0);
            }
        }
        
        // Getters
        public String getLobbyId() { return lobbyId; }
        public String getLobbyName() { return lobbyName; }
        public String getHostPlayerId() { return hostPlayerId; }
        public List<String> getPlayers() { return new ArrayList<>(players); }
        public List<String> getChatMessages() { return new ArrayList<>(chatMessages); }
        public int getMaxPlayers() { return maxPlayers; }
        public int getCurrentPlayerCount() { return players.size(); }
        
        @Override
        public String toString() {
            return "Lobby{" +
                    "id='" + lobbyId + '\'' +
                    ", name='" + lobbyName + '\'' +
                    ", host='" + hostPlayerId + '\'' +
                    ", players=" + players.size() + "/" + maxPlayers +
                    '}';
        }
    }
    
    public static class ClientConnection {
        private final Socket socket;
        private final ObjectOutputStream out;
        private final ObjectInputStream in;
        private String playerId;
        private volatile boolean isConnected = true;
        
        public ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        
        public void sendMessage(String message) {
            try {
                if (isConnected && !socket.isClosed()) {
                    out.writeObject(message);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Error sending message: " + e.getMessage());
                isConnected = false;
            }
        }
        
        public String readMessage() {
            try {
                if (isConnected && !socket.isClosed()) {
                    return (String) in.readObject();
                }
            } catch (Exception e) {
                isConnected = false;
            }
            return null;
        }
        
        public void close() {
            isConnected = false;
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error during cleanup: " + e.getMessage());
            }
        }
        
        public boolean isConnected() { return isConnected; }
        public String getPlayerId() { return playerId; }
        public void setPlayerId(String playerId) { this.playerId = playerId; }
    }
    
    public SimpleMultiplayerServer(int port) {
        this.port = port;
        this.connectedClients = new ConcurrentHashMap<>();
        this.lobbies = new ConcurrentHashMap<>();
        this.isRunning = false;
    }
    
    public SimpleMultiplayerServer() {
        this(DEFAULT_PORT);
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Simple multiplayer server started on port " + port);
            System.out.println("Server is ready to accept connections!");
            
            // Accept client connections
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (connectedClients.size() >= MAX_PLAYERS) {
                        System.out.println("Server full, rejecting connection from " + clientSocket.getInetAddress());
                        clientSocket.close();
                        continue;
                    }
                    
                    ClientConnection client = new ClientConnection(clientSocket);
                    new Thread(() -> handleClient(client)).start();
                    
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
    
    private void handleClient(ClientConnection client) {
        try {
            // Read player ID
            String playerId = client.readMessage();
            if (playerId != null && !playerId.trim().isEmpty()) {
                client.setPlayerId(playerId);
                connectedClients.put(playerId, client);
                System.out.println("Player " + playerId + " connected. Total players: " + connectedClients.size());
                
                // Send welcome message
                client.sendMessage("Welcome " + playerId + "! Type 'help' for commands.");
                
                // Handle client messages
                while (client.isConnected()) {
                    String message = client.readMessage();
                    if (message == null) break;
                    
                    handleClientMessage(client, message);
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            cleanupClient(client);
        }
    }
    
    private void handleClientMessage(ClientConnection client, String message) {
        String playerId = client.getPlayerId();
        String[] parts = message.split(" ", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        
        switch (command) {
            case "help":
                client.sendMessage("Available commands:");
                client.sendMessage("  create <name> <max_players> - Create a lobby");
                client.sendMessage("  list - List all lobbies");
                client.sendMessage("  join <lobby_id> - Join a lobby");
                client.sendMessage("  leave - Leave current lobby");
                client.sendMessage("  chat <message> - Send chat message");
                client.sendMessage("  players - Show players in current lobby");
                client.sendMessage("  quit - Disconnect from server");
                break;
                
            case "create":
                handleCreateLobby(client, args);
                break;
                
            case "list":
                handleListLobbies(client);
                break;
                
            case "join":
                handleJoinLobby(client, args);
                break;
                
            case "leave":
                handleLeaveLobby(client);
                break;
                
            case "chat":
                handleChatMessage(client, args);
                break;
                
            case "players":
                handleShowPlayers(client);
                break;
                
            case "quit":
                client.sendMessage("Goodbye!");
                client.close();
                break;
                
            default:
                client.sendMessage("Unknown command. Type 'help' for available commands.");
        }
    }
    
    private void handleCreateLobby(ClientConnection client, String args) {
        String playerId = client.getPlayerId();
        String[] parts = args.split(" ");
        
        if (parts.length < 2) {
            client.sendMessage("Usage: create <name> <max_players>");
            return;
        }
        
        try {
            String lobbyName = parts[0];
            int maxPlayers = Integer.parseInt(parts[1]);
            
            if (maxPlayers < 1 || maxPlayers > 8) {
                client.sendMessage("Max players must be between 1 and 8");
                return;
            }
            
            String lobbyId = "LOBBY_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
            SimpleLobby lobby = new SimpleLobby(lobbyId, lobbyName, playerId, maxPlayers);
            
            lobbies.put(lobbyId, lobby);
            client.sendMessage("Lobby created: " + lobbyId + " - " + lobbyName);
            System.out.println("Lobby created: " + lobbyId + " by " + playerId);
            
        } catch (NumberFormatException e) {
            client.sendMessage("Invalid max players number");
        }
    }
    
    private void handleListLobbies(ClientConnection client) {
        if (lobbies.isEmpty()) {
            client.sendMessage("No lobbies available");
            return;
        }
        
        client.sendMessage("Available lobbies:");
        for (SimpleLobby lobby : lobbies.values()) {
            client.sendMessage("  " + lobby.getLobbyId() + " - " + lobby.getLobbyName() + 
                             " (" + lobby.getCurrentPlayerCount() + "/" + lobby.getMaxPlayers() + ")");
        }
    }
    
    private void handleJoinLobby(ClientConnection client, String lobbyId) {
        String playerId = client.getPlayerId();
        SimpleLobby lobby = lobbies.get(lobbyId);
        
        if (lobby == null) {
            client.sendMessage("Lobby not found: " + lobbyId);
            return;
        }
        
        if (lobby.addPlayer(playerId)) {
            client.sendMessage("Joined lobby: " + lobby.getLobbyName());
            System.out.println("Player " + playerId + " joined lobby " + lobbyId);
        } else {
            client.sendMessage("Cannot join lobby: " + lobby.getLobbyName() + " (full or already in lobby)");
        }
    }
    
    private void handleLeaveLobby(ClientConnection client) {
        String playerId = client.getPlayerId();
        
        for (SimpleLobby lobby : lobbies.values()) {
            if (lobby.getPlayers().contains(playerId)) {
                if (lobby.removePlayer(playerId)) {
                    client.sendMessage("Left lobby: " + lobby.getLobbyName());
                    
                    // Remove empty lobbies
                    if (lobby.getCurrentPlayerCount() == 0) {
                        lobbies.remove(lobby.getLobbyId());
                        System.out.println("Lobby " + lobby.getLobbyId() + " removed (empty)");
                    }
                    return;
                }
            }
        }
        
        client.sendMessage("Not in any lobby");
    }
    
    private void handleChatMessage(ClientConnection client, String message) {
        String playerId = client.getPlayerId();
        
        // Find lobby containing this player
        for (SimpleLobby lobby : lobbies.values()) {
            if (lobby.getPlayers().contains(playerId)) {
                lobby.addChatMessage(playerId, message);
                
                // Send message to all players in lobby
                for (String lobbyPlayerId : lobby.getPlayers()) {
                    ClientConnection lobbyClient = connectedClients.get(lobbyPlayerId);
                    if (lobbyClient != null && lobbyClient.isConnected()) {
                        lobbyClient.sendMessage("[" + lobby.getLobbyName() + "] " + playerId + ": " + message);
                    }
                }
                return;
            }
        }
        
        client.sendMessage("You must be in a lobby to chat");
    }
    
    private void handleShowPlayers(ClientConnection client) {
        String playerId = client.getPlayerId();
        
        for (SimpleLobby lobby : lobbies.values()) {
            if (lobby.getPlayers().contains(playerId)) {
                client.sendMessage("Players in " + lobby.getLobbyName() + ":");
                for (String lobbyPlayerId : lobby.getPlayers()) {
                    String hostMark = lobbyPlayerId.equals(lobby.getHostPlayerId()) ? " (Host)" : "";
                    client.sendMessage("  " + lobbyPlayerId + hostMark);
                }
                return;
            }
        }
        
        client.sendMessage("Not in any lobby");
    }
    
    private void cleanupClient(ClientConnection client) {
        String playerId = client.getPlayerId();
        if (playerId != null) {
            connectedClients.remove(playerId);
            
            // Remove from lobbies
            for (SimpleLobby lobby : lobbies.values()) {
                if (lobby.getPlayers().contains(playerId)) {
                    handleLeaveLobby(client);
                    break;
                }
            }
            
            System.out.println("Player " + playerId + " disconnected. Total players: " + connectedClients.size());
        }
        
        client.close();
    }
    
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
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
        
        SimpleMultiplayerServer server = new SimpleMultiplayerServer(port);
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            server.stop();
        }));
        
        server.start();
    }
}
