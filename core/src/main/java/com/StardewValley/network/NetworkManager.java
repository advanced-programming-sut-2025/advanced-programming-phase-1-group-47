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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.StardewValley.model.User;
import com.StardewValley.network.messages.ChatMessage;
import com.StardewValley.network.messages.CreateLobbyMessage;
import com.StardewValley.network.messages.GameStartMessage;
import com.StardewValley.network.messages.JoinLobbyMessage;
import com.StardewValley.network.messages.LeaveLobbyMessage;
import com.StardewValley.network.messages.LobbyListMessage;
import com.StardewValley.network.messages.LobbyUpdateMessage;
import com.StardewValley.network.messages.LoginMessage;
import com.StardewValley.network.messages.LoginResponseMessage;
import com.StardewValley.network.messages.PlayerActionMessage;
import com.StardewValley.network.messages.PlayerJoinMessage;
import com.StardewValley.network.messages.PlayerLeaveMessage;
import com.StardewValley.network.messages.RequestLobbyListMessage;
import com.StardewValley.network.messages.ScoreboardUpdateMessage;

public class NetworkManager {
    private static NetworkManager instance;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isServer = false;
    private boolean isConnected = false;
    
    // Server-side collections
    private Map<String, ClientHandler> connectedClients = new ConcurrentHashMap<>();
    private List<GameLobby> lobbies = new ArrayList<>();
    private List<User> onlineUsers = new ArrayList<>();
    
    // Client-side
    private String serverAddress = "localhost";
    private int serverPort = 8080;
    private String currentUsername;
    
    // Thread management
    private ExecutorService clientThreadPool;
    private Thread serverThread;
    private Thread clientListenerThread;
    
    private NetworkManager() {
        clientThreadPool = Executors.newCachedThreadPool();
    }
    
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }
    
    // Server methods
    public boolean startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isServer = true;
            serverThread = new Thread(this::acceptClients);
            serverThread.start();
            System.out.println("Server started on port " + port);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            return false;
        }
    }
    
    private void acceptClients() {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientThreadPool.execute(clientHandler);
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        }
    }
    
    // Client methods
    public boolean connectToServer(String address, int port) {
        try {
            clientSocket = new Socket(address, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            isConnected = true;
            
            // Start listening for server messages
            clientListenerThread = new Thread(this::listenForServerMessages);
            clientListenerThread.start();
            
            System.out.println("Connected to server at " + address + ":" + port);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            return false;
        }
    }
    
    private void listenForServerMessages() {
        while (isConnected && !clientSocket.isClosed()) {
            try {
                NetworkMessage message = (NetworkMessage) in.readObject();
                handleIncomingMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                if (isConnected) {
                    System.err.println("Connection lost: " + e.getMessage());
                    disconnect();
                }
                break;
            }
        }
    }
    
    private void handleIncomingMessage(NetworkMessage message) {
        switch (message.getType()) {
            case LOBBY_UPDATE:
                handleLobbyUpdate((LobbyUpdateMessage) message);
                break;
            case PLAYER_JOIN:
                handlePlayerJoin((PlayerJoinMessage) message);
                break;
            case PLAYER_LEAVE:
                handlePlayerLeave((PlayerLeaveMessage) message);
                break;
            case GAME_START:
                handleGameStart((GameStartMessage) message);
                break;
            case CHAT_MESSAGE:
                handleChatMessage((ChatMessage) message);
                break;
            case PLAYER_ACTION:
                handlePlayerAction((PlayerActionMessage) message);
                break;
            case SCOREBOARD_UPDATE:
                handleScoreboardUpdate((ScoreboardUpdateMessage) message);
                break;
        }
    }
    
    // Message handling methods (to be implemented by UI)
    private void handleLobbyUpdate(LobbyUpdateMessage message) {
        // Notify UI of lobby changes
        System.out.println("Lobby updated: " + message.getLobbyId());
    }
    
    private void handlePlayerJoin(PlayerJoinMessage message) {
        System.out.println("Player joined: " + message.getUsername());
    }
    
    private void handlePlayerLeave(PlayerLeaveMessage message) {
        System.out.println("Player left: " + message.getUsername());
    }
    
    private void handleGameStart(GameStartMessage message) {
        System.out.println("Game starting with players: " + message.getPlayerNames());
    }
    
    private void handleChatMessage(ChatMessage message) {
        System.out.println("Chat: " + message.getSender() + ": " + message.getContent());
    }
    
    private void handlePlayerAction(PlayerActionMessage message) {
        System.out.println("Player action: " + message.getUsername() + " - " + message.getAction());
    }
    
    private void handleScoreboardUpdate(ScoreboardUpdateMessage message) {
        System.out.println("Scoreboard updated");
    }
    
    // Send methods
    public void sendMessage(NetworkMessage message) {
        if (isConnected && out != null) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.err.println("Failed to send message: " + e.getMessage());
                disconnect();
            }
        }
    }
    
    public void createLobby(String lobbyName, String creatorUsername) {
        sendMessage(new CreateLobbyMessage(lobbyName, creatorUsername));
    }
    
    public void joinLobby(String lobbyId, String username) {
        sendMessage(new JoinLobbyMessage(lobbyId, username));
    }
    
    public void leaveLobby(String lobbyId, String username) {
        sendMessage(new LeaveLobbyMessage(lobbyId, username));
    }
    
    public void sendChatMessage(String content, String sender, boolean isPrivate, String recipient) {
        sendMessage(new ChatMessage(content, sender, isPrivate, recipient));
    }
    
    public void sendPlayerAction(String action, String username) {
        sendMessage(new PlayerActionMessage(action, username));
    }
    
    public void requestLobbyList() {
        sendMessage(new RequestLobbyListMessage());
    }
    
    public void disconnect() {
        isConnected = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
    }
    
    // Getters
    public boolean isServer() { return isServer; }
    public boolean isConnected() { return isConnected; }
    public List<GameLobby> getLobbies() { return new ArrayList<>(lobbies); }
    public List<User> getOnlineUsers() { return new ArrayList<>(onlineUsers); }
    
    // ClientHandler inner class for server-side client management
    private class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private String username;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.err.println("Error creating client handler: " + e.getMessage());
            }
        }
        
        @Override
        public void run() {
            try {
                while (!socket.isClosed()) {
                    NetworkMessage message = (NetworkMessage) in.readObject();
                    handleServerMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                disconnectClient();
            }
        }
        
        private void handleServerMessage(NetworkMessage message) {
            switch (message.getType()) {
                case LOGIN:
                    handleLogin((LoginMessage) message);
                    break;
                case CREATE_LOBBY:
                    handleCreateLobby((CreateLobbyMessage) message);
                    break;
                case JOIN_LOBBY:
                    handleJoinLobby((JoinLobbyMessage) message);
                    break;
                case LEAVE_LOBBY:
                    handleLeaveLobby((LeaveLobbyMessage) message);
                    break;
                case CHAT_MESSAGE:
                    handleChatMessage((ChatMessage) message);
                    break;
                case REQUEST_LOBBY_LIST:
                    sendLobbyList();
                    break;
            }
        }
        
        private void handleLogin(LoginMessage message) {
            // Validate user credentials
            User user = validateUser(message.getUsername(), message.getPassword());
            if (user != null) {
                this.username = user.getUsername();
                connectedClients.put(username, this);
                onlineUsers.add(user);
                sendMessage(new LoginResponseMessage(true, "Login successful"));
                broadcastLobbyUpdate();
            } else {
                sendMessage(new LoginResponseMessage(false, "Invalid credentials"));
            }
        }
        
        private void handleCreateLobby(CreateLobbyMessage message) {
            GameLobby lobby = new GameLobby(message.getLobbyName(), message.getCreatorUsername());
            lobbies.add(lobby);
            lobby.addPlayer(message.getCreatorUsername());
            broadcastLobbyUpdate();
        }
        
        private void handleJoinLobby(JoinLobbyMessage message) {
            GameLobby lobby = findLobby(message.getLobbyId());
            if (lobby != null && lobby.canJoin()) {
                lobby.addPlayer(message.getUsername());
                broadcastLobbyUpdate();
                broadcastToLobby(lobby, new PlayerJoinMessage(message.getUsername()));
            }
        }
        
        private void handleLeaveLobby(LeaveLobbyMessage message) {
            GameLobby lobby = findLobby(message.getLobbyId());
            if (lobby != null) {
                lobby.removePlayer(message.getUsername());
                broadcastToLobby(lobby, new PlayerLeaveMessage(message.getUsername()));
                if (lobby.isEmpty()) {
                    lobbies.remove(lobby);
                }
                broadcastLobbyUpdate();
            }
        }
        
        private void handleChatMessage(ChatMessage message) {
            if (message.isPrivate()) {
                // Send to specific user
                ClientHandler recipient = connectedClients.get(message.getRecipient());
                if (recipient != null) {
                    recipient.sendMessage(message);
                }
            } else {
                // Broadcast to all or lobby
                if (message.getLobbyId() != null) {
                    GameLobby lobby = findLobby(message.getLobbyId());
                    if (lobby != null) {
                        broadcastToLobby(lobby, message);
                    }
                } else {
                    broadcastToAll(message);
                }
            }
        }
        
        private void sendLobbyList() {
            sendMessage(new LobbyListMessage(new ArrayList<>(lobbies)));
        }
        
        private void sendMessage(NetworkMessage message) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.err.println("Error sending message to client: " + e.getMessage());
            }
        }
        
        private void disconnectClient() {
            if (username != null) {
                connectedClients.remove(username);
                onlineUsers.removeIf(user -> user.getUsername().equals(username));
                broadcastLobbyUpdate();
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
        
        private void broadcastLobbyUpdate() {
            LobbyUpdateMessage update = new LobbyUpdateMessage(new ArrayList<>(lobbies));
            broadcastToAll(update);
        }
        
        private void broadcastToAll(NetworkMessage message) {
            for (ClientHandler client : connectedClients.values()) {
                client.sendMessage(message);
            }
        }
        
        private void broadcastToLobby(GameLobby lobby, NetworkMessage message) {
            for (String playerName : lobby.getPlayers()) {
                ClientHandler client = connectedClients.get(playerName);
                if (client != null) {
                    client.sendMessage(message);
                }
            }
        }
        
        private GameLobby findLobby(String lobbyId) {
            return lobbies.stream()
                    .filter(lobby -> lobby.getId().equals(lobbyId))
                    .findFirst()
                    .orElse(null);
        }
        
        private User validateUser(String username, String password) {
            // This should integrate with your existing user validation system
            // For now, return a mock user
            return new User(username, password, username + "@example.com", username, 
                          com.StardewValley.model.enums.Gender.Male, "What is your favorite color?", "Blue");
        }
    }
} 