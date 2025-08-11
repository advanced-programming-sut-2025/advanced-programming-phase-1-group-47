package com.StardewValley.network;

import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.network.PlayerPositionUpdate;
import com.StardewValley.network.Lobby;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;

public class ClientNetworkManager {
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 8080;
    private static final int POSITION_UPDATE_RATE = 60; // Updates per second
    
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String serverHost;
    private final int serverPort;
    private volatile boolean isConnected = false;
    private volatile boolean isConnecting = false;
    
    private final ScheduledExecutorService scheduler;
    private final BlockingQueue<NetworkMessage> messageQueue;
    private final List<NetworkMessageListener> listeners;
    
    private Player localPlayer;
    private String playerId;
    
    public interface NetworkMessageListener {
        void onPlayerJoined(String playerId, Player player);
        void onPlayerLeft(String playerId);
        void onPositionUpdate(String playerId, Point position, int direction, boolean moving);
        void onGameStateUpdate(List<PlayerPositionUpdate> updates);
        void onConnectionStatusChanged(boolean connected);
        void onLobbyCreated(String lobbyId, Lobby lobby);
        void onLobbyJoined(String lobbyId, Lobby lobby);
        void onLobbyLeft(String lobbyId);
        void onLobbyUpdate(String lobbyId, Lobby lobby);
        void onLobbyListReceived(List<Lobby> lobbies);
        void onChatMessage(String playerId, String message);
    }
    
    public ClientNetworkManager() {
        this(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
    }
    
    public ClientNetworkManager(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.messageQueue = new LinkedBlockingQueue<>();
        this.listeners = new CopyOnWriteArrayList<>();
    }
    
    public boolean connect() {
        if (isConnecting || isConnected) {
            return false;
        }
        
        isConnecting = true;
        
        try {
            socket = new Socket(serverHost, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            isConnecting = false;
            
            // Start message handling
            startMessageHandling();
            
            // Notify listeners
            notifyConnectionStatusChanged(true);
            
            System.out.println("Connected to multiplayer server at " + serverHost + ":" + serverPort);
            return true;
            
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            isConnecting = false;
            notifyConnectionStatusChanged(false);
            return false;
        }
    }
    
    public void disconnect() {
        isConnected = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
        
        notifyConnectionStatusChanged(false);
        System.out.println("Disconnected from multiplayer server");
    }
    
    public void joinGame(Player player) {
        if (!isConnected) {
            System.err.println("Cannot join game: not connected to server");
            return;
        }
        
        this.localPlayer = player;
        this.playerId = player.getUsername();
        
        // Send join message to server
        NetworkMessage joinMessage = new NetworkMessage(
            NetworkMessage.MessageType.PLAYER_JOIN,
            playerId,
            player
        );
        sendMessage(joinMessage);
        
        // Start position update scheduler
        startPositionUpdateScheduler();
    }
    
    public void updatePosition(Point position, int direction, boolean moving) {
        if (!isConnected || localPlayer == null) {
            return;
        }
        
        PlayerPositionUpdate update = new PlayerPositionUpdate(
            playerId,
            position,
            direction,
            moving
        );
        
        NetworkMessage message = new NetworkMessage(
            NetworkMessage.MessageType.PLAYER_POSITION_UPDATE,
            playerId,
            update
        );
        
        sendMessage(message);
    }
    
    private void startMessageHandling() {
        scheduler.submit(() -> {
            while (isConnected && !socket.isClosed()) {
                try {
                    Object input = in.readObject();
                    if (input instanceof NetworkMessage) {
                        handleMessage((NetworkMessage) input);
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Error reading message: " + e.getMessage());
                } catch (IOException e) {
                    if (isConnected) {
                        System.err.println("Connection lost: " + e.getMessage());
                        isConnected = false;
                        notifyConnectionStatusChanged(false);
                        break;
                    }
                }
            }
        });
    }
    
    private void startPositionUpdateScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            if (isConnected && localPlayer != null) {
                updatePosition(
                    localPlayer.getCoordinates(),
                    getCurrentMoveDirection(),
                    isCurrentlyMoving()
                );
            }
        }, 0, 1000 / POSITION_UPDATE_RATE, TimeUnit.MILLISECONDS);
    }
    
    private void handleMessage(NetworkMessage message) {
        switch (message.getType()) {
            case PLAYER_JOIN:
                if (message.getData() instanceof Player) {
                    Player newPlayer = (Player) message.getData();
                    notifyPlayerJoined(message.getPlayerId(), newPlayer);
                }
                break;
                
            case PLAYER_LEAVE:
                notifyPlayerLeft(message.getPlayerId());
                break;
                
            case GAME_STATE_UPDATE:
                if (message.getData() instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<PlayerPositionUpdate> updates = (List<PlayerPositionUpdate>) message.getData();
                    notifyGameStateUpdate(updates);
                }
                break;
                
            case LOBBY_CREATED:
                if (message.getData() instanceof Lobby) {
                    Lobby createdLobby = (Lobby) message.getData();
                    notifyLobbyCreated(message.getLobbyId(), createdLobby);
                }
                break;
                
            case LOBBY_JOINED:
                if (message.getData() instanceof Lobby) {
                    Lobby joinedLobby = (Lobby) message.getData();
                    notifyLobbyJoined(message.getLobbyId(), joinedLobby);
                }
                break;
                
            case LOBBY_LEFT:
                notifyLobbyLeft(message.getLobbyId());
                break;
                
            case LOBBY_UPDATE:
                if (message.getData() instanceof Lobby) {
                    Lobby updatedLobby = (Lobby) message.getData();
                    notifyLobbyUpdate(message.getLobbyId(), updatedLobby);
                }
                break;
                
            case LOBBY_LIST_RESPONSE:
                if (message.getData() instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Lobby> lobbies = (List<Lobby>) message.getData();
                    notifyLobbyListReceived(lobbies);
                }
                break;
                
            case LOBBY_CHAT_MESSAGE:
                String chatMessage = (String) message.getData();
                notifyChatMessage(message.getPlayerId(), chatMessage);
                break;
                
            default:
                System.out.println("Received message: " + message.getType());
        }
    }
    
    public void sendMessage(NetworkMessage message) {
        try {
            if (isConnected && out != null) {
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
            isConnected = false;
            notifyConnectionStatusChanged(false);
        }
    }
    
    // Helper methods to get current player state
    private int getCurrentMoveDirection() {
        // This should be implemented to get the current move direction from the game
        // For now, return a default value
        return 2;
    }
    
    private boolean isCurrentlyMoving() {
        // This should be implemented to check if the player is currently moving
        // For now, return false
        return false;
    }
    
    // Listener management
    public void addListener(NetworkMessageListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(NetworkMessageListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyPlayerJoined(String playerId, Player player) {
        for (NetworkMessageListener listener : listeners) {
            listener.onPlayerJoined(playerId, player);
        }
    }
    
    private void notifyPlayerLeft(String playerId) {
        for (NetworkMessageListener listener : listeners) {
            listener.onPlayerLeft(playerId);
        }
    }
    
    private void notifyGameStateUpdate(List<PlayerPositionUpdate> updates) {
        for (NetworkMessageListener listener : listeners) {
            listener.onGameStateUpdate(updates);
        }
    }
    
    private void notifyConnectionStatusChanged(boolean connected) {
        for (NetworkMessageListener listener : listeners) {
            listener.onConnectionStatusChanged(connected);
        }
    }
    
    private void notifyLobbyCreated(String lobbyId, Lobby lobby) {
        for (NetworkMessageListener listener : listeners) {
            listener.onLobbyCreated(lobbyId, lobby);
        }
    }
    
    private void notifyLobbyJoined(String lobbyId, Lobby lobby) {
        for (NetworkMessageListener listener : listeners) {
            listener.onLobbyJoined(lobbyId, lobby);
        }
    }
    
    private void notifyLobbyLeft(String lobbyId) {
        for (NetworkMessageListener listener : listeners) {
            listener.onLobbyLeft(lobbyId);
        }
    }
    
    private void notifyLobbyUpdate(String lobbyId, Lobby lobby) {
        for (NetworkMessageListener listener : listeners) {
            listener.onLobbyUpdate(lobbyId, lobby);
        }
    }
    
    private void notifyLobbyListReceived(List<Lobby> lobbies) {
        for (NetworkMessageListener listener : listeners) {
            listener.onLobbyListReceived(lobbies);
        }
    }
    
    private void notifyChatMessage(String playerId, String message) {
        for (NetworkMessageListener listener : listeners) {
            listener.onChatMessage(playerId, message);
        }
    }
    
    // Getters
    public boolean isConnected() { return isConnected; }
    public boolean isConnecting() { return isConnecting; }
    public String getPlayerId() { return playerId; }
    public Player getLocalPlayer() { return localPlayer; }
}
