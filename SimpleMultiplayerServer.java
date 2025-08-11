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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleMultiplayerServer {
    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_PLAYERS = 8;
    private static final int RECONNECTION_TIMEOUT_MINUTES = 2;
    
    private ServerSocket serverSocket;
    private final Map<String, SimpleClientHandler> connectedClients;
    private final Map<String, SimpleLobby> lobbies;
    private final Map<String, DisconnectedPlayer> disconnectedPlayers;
    private volatile boolean isRunning;
    private final int port;
    private final ScheduledExecutorService scheduler;
    
    public SimpleMultiplayerServer(int port) {
        this.port = port;
        this.connectedClients = new ConcurrentHashMap<>();
        this.lobbies = new ConcurrentHashMap<>();
        this.disconnectedPlayers = new ConcurrentHashMap<>();
        this.isRunning = false;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }
    
    public SimpleMultiplayerServer() {
        this(DEFAULT_PORT);
    }
    
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Simple Multiplayer server started on port " + port);
            System.out.println("Ready to accept connections...");
            
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
                    
                    SimpleClientHandler clientHandler = new SimpleClientHandler(clientSocket, this);
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
    
    public void addClient(String playerId, SimpleClientHandler client) {
        connectedClients.put(playerId, client);
        System.out.println("Player " + playerId + " connected. Total players: " + connectedClients.size());
    }
    
    public void removeClient(String playerId) {
        SimpleClientHandler client = connectedClients.remove(playerId);
        if (client != null) {
            // Check if player was in a lobby
            for (SimpleLobby lobby : lobbies.values()) {
                if (lobby.isPlayerInLobby(playerId)) {
                    // Mark player as disconnected but keep them in lobby temporarily
                    lobby.markPlayerDisconnected(playerId);
                    DisconnectedPlayer disconnectedPlayer = new DisconnectedPlayer(playerId, lobby.getLobbyId());
                    disconnectedPlayers.put(playerId, disconnectedPlayer);
                    
                    // Schedule removal after timeout
                    scheduler.schedule(() -> {
                        if (disconnectedPlayers.containsKey(playerId)) {
                            // Player didn't reconnect in time, remove them from lobby
                            SimpleLobby targetLobby = lobbies.get(lobby.getLobbyId());
                            if (targetLobby != null) {
                                targetLobby.removePlayer(playerId);
                                disconnectedPlayers.remove(playerId);
                                System.out.println("Player " + playerId + " kicked from lobby " + lobby.getLobbyId() + " due to timeout");
                                
                                // If lobby is empty, remove it
                                if (targetLobby.getCurrentPlayerCount() == 0) {
                                    lobbies.remove(lobby.getLobbyId());
                                    System.out.println("Lobby " + lobby.getLobbyId() + " removed (empty)");
                                } else {
                                    // Notify remaining players
                                    broadcastLobbyUpdate(lobby.getLobbyId());
                                }
                            }
                        }
                    }, RECONNECTION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
                    
                    System.out.println("Player " + playerId + " disconnected. Total players: " + connectedClients.size());
                    System.out.println("Player " + playerId + " has " + RECONNECTION_TIMEOUT_MINUTES + " minutes to reconnect");
                    return;
                }
            }
        }
        System.out.println("Player " + playerId + " disconnected. Total players: " + connectedClients.size());
    }
    
    public boolean isPlayerDisconnected(String playerId) {
        return disconnectedPlayers.containsKey(playerId);
    }
    
    public DisconnectedPlayer getDisconnectedPlayer(String playerId) {
        return disconnectedPlayers.get(playerId);
    }
    
    public boolean reconnectPlayer(String playerId, SimpleClientHandler client) {
        DisconnectedPlayer disconnectedPlayer = disconnectedPlayers.get(playerId);
        if (disconnectedPlayer != null) {
            System.out.println("Processing reconnection for " + playerId + " to lobby " + disconnectedPlayer.getLobbyId());
            
            // Check if the lobby still exists
            SimpleLobby lobby = lobbies.get(disconnectedPlayer.getLobbyId());
            if (lobby != null && lobby.isPlayerDisconnected(playerId)) {
                System.out.println("Lobby exists and player is marked as disconnected, proceeding with reconnection...");
                
                // Reconnect player to lobby
                lobby.reconnectPlayer(playerId);
                disconnectedPlayers.remove(playerId);
                connectedClients.put(playerId, client);
                
                // Send the player their current lobby info
                client.sendMessage("LOBBY_JOINED:" + disconnectedPlayer.getLobbyId());
                
                // Notify other players in the lobby
                broadcastLobbyUpdate(disconnectedPlayer.getLobbyId());
                
                System.out.println("Player " + playerId + " successfully reconnected to lobby " + disconnectedPlayer.getLobbyId());
                return true;
            } else {
                // Lobby no longer exists or player was already removed
                if (lobby == null) {
                    System.out.println("Lobby " + disconnectedPlayer.getLobbyId() + " no longer exists");
                } else {
                    System.out.println("Player " + playerId + " is not marked as disconnected in lobby");
                }
                disconnectedPlayers.remove(playerId);
                System.out.println("Player " + playerId + " could not reconnect - lobby no longer available");
                return false;
            }
        } else {
            System.out.println("No disconnected player record found for " + playerId);
        }
        return false;
    }
    
    public void broadcastToOthers(String excludePlayerId, String message) {
        for (Map.Entry<String, SimpleClientHandler> entry : connectedClients.entrySet()) {
            if (!entry.getKey().equals(excludePlayerId)) {
                entry.getValue().sendMessage(message);
            }
        }
    }
    
    public void broadcastToAll(String message) {
        for (SimpleClientHandler client : connectedClients.values()) {
            client.sendMessage(message);
        }
    }
    
    public String createLobby(String lobbyName, String hostPlayerId, int maxPlayers, String password, boolean isInvisible) {
        // Validate input parameters
        if (lobbyName == null || lobbyName.trim().isEmpty()) {
            System.err.println("Failed to create lobby: lobby name is null or empty");
            return null;
        }
        
        if (hostPlayerId == null || hostPlayerId.trim().isEmpty()) {
            System.err.println("Failed to create lobby: host player ID is null or empty");
            return null;
        }
        
        if (maxPlayers < 1 || maxPlayers > 8) {
            System.err.println("Failed to create lobby: max players must be between 1 and 8, got: " + maxPlayers);
            return null;
        }
        
        // Check if player is already in another lobby
        if (isPlayerInAnyLobby(hostPlayerId)) {
            System.err.println("Failed to create lobby: player " + hostPlayerId + " is already in another lobby");
            return null;
        }
        
        String lobbyId = generateLobbyId();
        System.out.println("Generated lobby ID: [" + lobbyId + "]");
        
        try {
            System.out.println("Creating lobby with parameters - name: [" + lobbyName + "], host: [" + hostPlayerId + "], maxPlayers: [" + maxPlayers + "], hasPassword: [" + (password != null && !password.trim().isEmpty()) + "], isInvisible: [" + isInvisible + "]");
            SimpleLobby lobby = new SimpleLobby(lobbyId, lobbyName, hostPlayerId, maxPlayers, password, isInvisible);
            lobbies.put(lobbyId, lobby);
            
            // Verify the lobby was created successfully
            if (lobbies.containsKey(lobbyId)) {
                System.out.println("Lobby created: " + lobbyName + " (ID: [" + lobbyId + "]) by " + hostPlayerId + (password != null && !password.trim().isEmpty() ? " (Private)" : " (Public)") + (isInvisible ? " (Invisible)" : " (Visible)"));
                System.out.println("Verification - lobby.isInvisible(): " + lobby.isInvisible());
                return lobbyId;
            } else {
                System.err.println("Failed to create lobby: lobby was not added to server's lobby list");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Failed to create lobby: " + e.getMessage());
            return null;
        }
    }
    
    public boolean joinLobby(String lobbyId, String playerId, String password) {
        // Check if player is already in any lobby
        if (isPlayerInAnyLobby(playerId)) {
            System.out.println("Join failed: Player " + playerId + " is already in another lobby");
            return false;
        }
        
        SimpleLobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            System.out.println("Join failed: Lobby " + lobbyId + " not found");
            return false;
        }
        
        // Check password for private lobbies
        if (lobby.isPrivate() && !lobby.checkPassword(password)) {
            System.out.println("Join failed: Incorrect password for private lobby " + lobbyId);
            return false;
        }
        
        // Check if player is already in this lobby
        if (lobby.isPlayerInLobby(playerId)) {
            System.out.println("Join failed: Player " + playerId + " is already in lobby " + lobbyId);
            return false;
        }
        
        // Check if lobby is full
        if (lobby.getCurrentPlayerCount() >= lobby.getMaxPlayers()) {
            System.out.println("Join failed: Lobby " + lobbyId + " is full (" + lobby.getCurrentPlayerCount() + "/" + lobby.getMaxPlayers() + ")");
            return false;
        }
        
        // Check if game has already started
        if (lobby.isGameStarted()) {
            System.out.println("Join failed: Game in lobby " + lobbyId + " has already started");
            return false;
        }
        
        if (lobby.addPlayer(playerId)) {
            System.out.println("Player " + playerId + " joined lobby " + lobby.getLobbyName() + " (ID: " + lobbyId + ")");
            broadcastLobbyUpdate(lobbyId);
            return true;
        }
        
        System.out.println("Join failed: Unknown error adding player " + playerId + " to lobby " + lobbyId);
        return false;
    }
    
    public boolean leaveLobby(String lobbyId, String playerId) {
        SimpleLobby lobby = lobbies.get(lobbyId);
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
            
            System.out.println("Player " + playerId + " left lobby " + lobbyId);
            return true;
        }
        
        return false;
    }
    
    public boolean deleteLobby(String lobbyId, String playerId) {
        SimpleLobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false;
        }
        
        // Only the host can delete the lobby
        if (!lobby.isHost(playerId)) {
            System.out.println("Player " + playerId + " attempted to delete lobby " + lobbyId + " but is not the host");
            return false;
        }
        
        // Get all players in the lobby before removing it
        List<String> allPlayers = new ArrayList<>();
        allPlayers.addAll(lobby.getPlayers());
        allPlayers.addAll(lobby.getDisconnectedPlayers());
        
        // Remove the lobby
        lobbies.remove(lobbyId);
        System.out.println("Lobby " + lobbyId + " deleted by host " + playerId);
        
        // Notify all players that they've been kicked
        for (String kickedPlayerId : allPlayers) {
            SimpleClientHandler client = connectedClients.get(kickedPlayerId);
            if (client != null) {
                client.sendMessage("LOBBY_DELETED:" + lobbyId + ":Lobby was deleted by the host");
                System.out.println("Notified player " + kickedPlayerId + " that lobby " + lobbyId + " was deleted");
            } else {
                // Player is not currently connected, but we should still track this
                System.out.println("Player " + kickedPlayerId + " is not currently connected, cannot notify of lobby deletion");
            }
        }
        
        // Also remove any disconnected player entries for this lobby
        disconnectedPlayers.entrySet().removeIf(entry -> entry.getValue().getLobbyId().equals(lobbyId));
        
        return true;
    }
    
    private boolean isPlayerInAnyLobby(String playerId) {
        for (SimpleLobby lobby : lobbies.values()) {
            if (lobby.isPlayerInLobby(playerId)) {
                return true;
            }
        }
        return false;
    }
    
    public void addChatMessage(String lobbyId, String playerId, String message) {
        SimpleLobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.addChatMessage(playerId, message);
            
            // Broadcast the chat message to all players in the lobby (including recently reconnected ones)
            String chatBroadcastMessage = "CHAT:" + playerId + ":" + lobbyId + ":" + message;
            
            // Send to active players
            for (String playerInLobby : lobby.getPlayers()) {
                SimpleClientHandler client = connectedClients.get(playerInLobby);
                if (client != null) {
                    client.sendMessage(chatBroadcastMessage);
                }
            }
            
            // Also send to any recently disconnected players who might be reconnecting
            for (String disconnectedPlayerId : lobby.getDisconnectedPlayers()) {
                SimpleClientHandler client = connectedClients.get(disconnectedPlayerId);
                if (client != null) {
                    client.sendMessage(chatBroadcastMessage);
                }
            }
            
            broadcastLobbyUpdate(lobbyId);
        }
    }
    
    public List<SimpleLobby> getLobbyList() {
        List<SimpleLobby> visibleLobbies = new ArrayList<>();
        System.out.println("getLobbyList() called - total lobbies: " + lobbies.size());
        for (SimpleLobby lobby : lobbies.values()) {
            System.out.println("Checking lobby: " + lobby.getLobbyName() + " (ID: " + lobby.getLobbyId() + ") - isInvisible: " + lobby.isInvisible());
            if (!lobby.isInvisible()) {
                visibleLobbies.add(lobby);
                System.out.println("  -> Added to visible list");
            } else {
                System.out.println("  -> Filtered out (invisible)");
            }
        }
        System.out.println("Returning " + visibleLobbies.size() + " visible lobbies");
        return visibleLobbies;
    }
    
    public SimpleLobby getLobbyByName(String lobbyName) {
        for (SimpleLobby lobby : lobbies.values()) {
            if (lobby.getLobbyName().equals(lobbyName)) {
                return lobby;
            }
        }
        return null;
    }
    
    private void broadcastLobbyUpdate(String lobbyId) {
        SimpleLobby lobby = lobbies.get(lobbyId);
        if (lobby == null) return;
        
        String lobbyUpdateMessage = "LOBBY_UPDATE:" + lobbyId + ":" + lobby.toString();
        
        // Send to all players in the lobby
        for (String playerId : lobby.getPlayers()) {
            SimpleClientHandler client = connectedClients.get(playerId);
            if (client != null) {
                client.sendMessage(lobbyUpdateMessage);
            }
        }
    }
    
    private String generateLobbyId() {
        String lobbyId = "LOBBY_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
        System.out.println("generateLobbyId() called, result: [" + lobbyId + "]");
        return lobbyId;
    }
    
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
                try {
                    if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                        scheduler.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    scheduler.shutdownNow();
                }
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

class SimpleClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private SimpleMultiplayerServer server;
    private String playerId;
    
    public SimpleClientHandler(Socket socket, SimpleMultiplayerServer server) {
        this.socket = socket;
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Read player ID from client
            playerId = in.readLine();
            if (playerId != null && !playerId.trim().isEmpty()) {
                // Check if this player was previously disconnected and is trying to reconnect
                if (server.isPlayerDisconnected(playerId)) {
                    System.out.println("Player " + playerId + " attempting to reconnect...");
                    if (server.reconnectPlayer(playerId, this)) {
                        // Send reconnection success message with lobby ID
                        DisconnectedPlayer disconnectedPlayer = server.getDisconnectedPlayer(playerId);
                        if (disconnectedPlayer != null) {
                            sendMessage("RECONNECTED:" + playerId + ":" + disconnectedPlayer.getLobbyId());
                            System.out.println("Player " + playerId + " reconnected successfully to lobby " + disconnectedPlayer.getLobbyId());
                        } else {
                            sendMessage("RECONNECTED:" + playerId + ":unknown");
                            System.out.println("Player " + playerId + " reconnected but lobby info unavailable");
                        }
                    } else {
                        // Reconnection failed
                        sendMessage("RECONNECT_FAILED:Lobby no longer available");
                        System.out.println("Player " + playerId + " reconnection failed");
                        return;
                    }
                } else {
                    // New player connection
                    server.addClient(playerId, this);
                }
                
                // Handle client messages
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    handleMessage(inputLine);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client " + playerId + ": " + e.getMessage());
        } finally {
            try {
                if (playerId != null) {
                    server.removeClient(playerId);
                }
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
    
    private void handleMessage(String message) {
        if (message.startsWith("CREATE_LOBBY:")) {
            String[] parts = message.split(":", 6);
            System.out.println("CREATE_LOBBY message received - parts.length: " + parts.length);
            for (int i = 0; i < parts.length; i++) {
                System.out.println("  Part " + i + ": [" + parts[i] + "]");
            }
            if (parts.length >= 4) {
                String lobbyName = parts[1];
                int maxPlayers = Integer.parseInt(parts[2]);
                String hostPlayerId = parts[3];
                String password = (parts.length >= 5) ? parts[4] : ""; // Password is optional
                boolean isInvisible = (parts.length >= 6) ? Boolean.parseBoolean(parts[5]) : false; // Invisible is optional
                System.out.println("Parsed CREATE_LOBBY parameters - name: [" + lobbyName + "], maxPlayers: [" + maxPlayers + "], host: [" + hostPlayerId + "], hasPassword: [" + !password.isEmpty() + "], isInvisible: [" + isInvisible + "]");
                
                String lobbyId = server.createLobby(lobbyName, hostPlayerId, maxPlayers, password, isInvisible);
                
                // Ensure the lobby is properly created and added to the server's list
                // before sending the response
                if (lobbyId != null && !lobbyId.isEmpty()) {
                    // Add a small delay to ensure the lobby is fully processed
                    try {
                        Thread.sleep(50); // 50ms delay
                    } catch (InterruptedException e) {
                        // Ignore interruption
                    }
                    sendMessage("LOBBY_CREATED:" + lobbyId);
                } else {
                    sendMessage("LOBBY_CREATE_FAILED:Failed to create lobby");
                }
            }
        } else if (message.startsWith("JOIN_LOBBY:")) {
            String[] parts = message.split(":", 4);
            if (parts.length >= 2) {
                String lobbyId = parts[1];
                String password = (parts.length >= 3) ? parts[2] : ""; // Password is optional
                // The playerId is already known from the connection, so we don't need it in the message
                if (server.joinLobby(lobbyId, playerId, password)) {
                    sendMessage("LOBBY_JOINED:" + lobbyId);
                    System.out.println("Player " + playerId + " successfully joined lobby " + lobbyId);
                } else {
                    sendMessage("LOBBY_JOIN_FAILED:Lobby is full, game started, lobby not found, or incorrect password");
                    System.out.println("Player " + playerId + " failed to join lobby " + lobbyId);
                }
            } else {
                sendMessage("LOBBY_JOIN_FAILED:Invalid message format");
            }
        } else if (message.startsWith("JOIN:")) {
            // Alternative join message format
            String[] parts = message.split(":", 4);
            if (parts.length >= 2) {
                String lobbyId = parts[1];
                String password = (parts.length >= 3) ? parts[2] : ""; // Password is optional
                if (server.joinLobby(lobbyId, playerId, password)) {
                    sendMessage("JOIN_SUCCESS:" + lobbyId);
                    System.out.println("Player " + playerId + " successfully joined lobby " + lobbyId + " (alt format)");
                } else {
                    sendMessage("JOIN_FAILED:Lobby is full, game started, lobby not found, or incorrect password");
                    System.out.println("Player " + playerId + " failed to join lobby " + lobbyId + " (alt format)");
                }
            } else {
                sendMessage("JOIN_FAILED:Invalid message format");
            }
        } else if (message.startsWith("JOIN_BY_NAME:")) {
            // Join lobby by name (for invisible lobbies)
            String[] parts = message.split(":", 3);
            if (parts.length >= 2) {
                String lobbyName = parts[1];
                String password = (parts.length >= 3) ? parts[2] : ""; // Password is optional
                
                SimpleLobby lobby = server.getLobbyByName(lobbyName);
                if (lobby != null) {
                    if (server.joinLobby(lobby.getLobbyId(), playerId, password)) {
                        sendMessage("LOBBY_JOINED:" + lobby.getLobbyId());
                        System.out.println("Player " + playerId + " successfully joined invisible lobby " + lobbyName + " by name");
                    } else {
                        sendMessage("LOBBY_JOIN_FAILED:Lobby is full, game started, or incorrect password");
                        System.out.println("Player " + playerId + " failed to join invisible lobby " + lobbyName + " by name");
                    }
                } else {
                    sendMessage("LOBBY_JOIN_FAILED:Lobby not found with name: " + lobbyName);
                    System.out.println("Player " + playerId + " failed to find lobby with name: " + lobbyName);
                }
            } else {
                sendMessage("LOBBY_JOIN_FAILED:Invalid message format");
            }
        } else if (message.startsWith("LEAVE_LOBBY:")) {
            String[] parts = message.split(":", 2);
            if (parts.length == 2) {
                String lobbyId = parts[1];
                server.leaveLobby(lobbyId, playerId);
                sendMessage("LOBBY_LEFT:" + lobbyId);
            }
        } else if (message.startsWith("DELETE_LOBBY:")) {
            String[] parts = message.split(":", 2);
            if (parts.length == 2) {
                String lobbyId = parts[1];
                if (server.deleteLobby(lobbyId, playerId)) {
                    sendMessage("LOBBY_DELETED_SUCCESS:" + lobbyId);
                    System.out.println("Player " + playerId + " successfully deleted lobby " + lobbyId);
                } else {
                    sendMessage("LOBBY_DELETE_FAILED:Only the host can delete the lobby");
                    System.out.println("Player " + playerId + " failed to delete lobby " + lobbyId + " (not the host)");
                }
            } else {
                sendMessage("LOBBY_DELETE_FAILED:Invalid message format");
            }
        } else if (message.startsWith("LOBBY_LIST_REQUEST")) {
            List<SimpleLobby> lobbies = server.getLobbyList();
            System.out.println("LOBBY_LIST_REQUEST received from " + playerId + ", found " + lobbies.size() + " lobbies");
            
            StringBuilder lobbyList = new StringBuilder("LOBBY_LIST:");
            for (SimpleLobby lobby : lobbies) {
                String lobbyData = lobby.toString();
                System.out.println("Adding lobby to list: [" + lobbyData + "]");
                lobbyList.append(lobbyData).append("|");
            }
            String finalList = lobbyList.toString();
            System.out.println("Sending lobby list to " + playerId + ": [" + finalList + "]");
            sendMessage(finalList);
        } else if (message.startsWith("CHAT:")) {
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String lobbyId = parts[1];
                String chatMessage = parts[2];
                server.addChatMessage(lobbyId, playerId, chatMessage);
            }
        } else if (message.startsWith("PING")) {
            // Handle ping messages silently - don't send PONG response
            // sendMessage("PONG");
        }
    }
    
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}

class SimpleLobby {
    private String lobbyId;
    private String lobbyName;
    private String hostPlayerId;
    private final List<String> players;
    private final List<String> chatMessages;
    private final int maxPlayers;
    private boolean isGameStarted;
    private final List<String> disconnectedPlayers; // Track disconnected players
    private String password; // Password for private lobbies
    private boolean isPrivate; // Whether the lobby requires a password
    private boolean isInvisible; // Whether the lobby is invisible in public listings
    
    public SimpleLobby(String lobbyId, String lobbyName, String hostPlayerId, int maxPlayers, String password, boolean isInvisible) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.hostPlayerId = hostPlayerId;
        this.maxPlayers = maxPlayers;
        this.password = password;
        this.isPrivate = (password != null && !password.trim().isEmpty());
        this.isInvisible = isInvisible;
        this.players = new CopyOnWriteArrayList<>();
        this.chatMessages = new CopyOnWriteArrayList<>();
        this.disconnectedPlayers = new CopyOnWriteArrayList<>();
        this.isGameStarted = false;
        
        // Add host as first player
        players.add(hostPlayerId);
    }
    
    public boolean addPlayer(String playerId) {
        if (players.size() >= maxPlayers || isGameStarted) {
            return false;
        }
        
        // Check if player is already in lobby
        if (players.contains(playerId)) {
            return false;
        }
        
        players.add(playerId);
        return true;
    }
    
    public boolean removePlayer(String playerId) {
        boolean removed = players.remove(playerId);
        
        if (removed && playerId.equals(hostPlayerId) && !players.isEmpty()) {
            // Transfer host to next player
            hostPlayerId = players.get(0);
        }
        
        return removed;
    }
    
    public void addChatMessage(String playerId, String message) {
        String chatMessage = playerId + ": " + message;
        chatMessages.add(chatMessage);
        
        // Keep only last 50 messages to prevent memory issues
        if (chatMessages.size() > 50) {
            chatMessages.remove(0);
        }
    }
    
    public boolean isPlayerInLobby(String playerId) {
        return players.contains(playerId);
    }
    
    public boolean isHost(String playerId) {
        return hostPlayerId.equals(playerId);
    }
    
    public String getLobbyId() { return lobbyId; }
    public String getLobbyName() { return lobbyName; }
    public String getHostPlayerId() { return hostPlayerId; }
    public List<String> getPlayers() { return new ArrayList<>(players); }
    public List<String> getChatMessages() { return new ArrayList<>(chatMessages); }
    public List<String> getDisconnectedPlayers() { return new ArrayList<>(disconnectedPlayers); }
    public int getMaxPlayers() { return maxPlayers; }
    public int getCurrentPlayerCount() { return players.size(); }
    public boolean isGameStarted() { return isGameStarted; }
    
    public void setGameStarted(boolean gameStarted) { this.isGameStarted = gameStarted; }
    
    public boolean isPrivate() { return isPrivate; }
    public String getPassword() { return password; }
    public boolean isInvisible() { return isInvisible; }
    
    public boolean checkPassword(String inputPassword) {
        if (!isPrivate) return true; // Public lobbies don't need password
        if (inputPassword == null) return false;
        return password.equals(inputPassword);
    }
    
    public void markPlayerDisconnected(String playerId) {
        if (players.contains(playerId)) {
            players.remove(playerId);
            disconnectedPlayers.add(playerId);
        }
    }

    public boolean isPlayerDisconnected(String playerId) {
        return disconnectedPlayers.contains(playerId);
    }

    public void reconnectPlayer(String playerId) {
        if (disconnectedPlayers.contains(playerId) && players.size() < maxPlayers) {
            disconnectedPlayers.remove(playerId);
            players.add(playerId);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(lobbyId).append(",");
        result.append(lobbyName).append(",");
        result.append(hostPlayerId).append(",");
        result.append(players.size()).append(",");
        result.append(maxPlayers).append(",");
        result.append(isGameStarted).append(",");
        result.append(isPrivate).append(",");
        result.append(isInvisible).append(",");
        
        // Add connected players (handle empty list case)
        if (players.isEmpty()) {
            result.append("");
        } else {
            result.append(String.join(";", players));
        }
        result.append(",");
        
        // Add disconnected players (handle empty list case)
        if (disconnectedPlayers.isEmpty()) {
            result.append("");
        } else {
            result.append(String.join(";", disconnectedPlayers));
        }
        
        String finalResult = result.toString();
        System.out.println("SimpleLobby.toString() called - lobbyId: [" + lobbyId + "], lobbyName: [" + lobbyName + "], players.size(): [" + players.size() + "], maxPlayers: [" + maxPlayers + "], result: [" + finalResult + "]");
        return finalResult;
    }
}

class DisconnectedPlayer {
    private String playerId;
    private String lobbyId;
    private long disconnectedTime;

    public DisconnectedPlayer(String playerId, String lobbyId) {
        this.playerId = playerId;
        this.lobbyId = lobbyId;
        this.disconnectedTime = System.currentTimeMillis();
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public long getDisconnectedTime() {
        return disconnectedTime;
    }
}
