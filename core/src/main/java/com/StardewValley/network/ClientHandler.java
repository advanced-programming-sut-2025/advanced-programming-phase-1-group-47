package com.StardewValley.network;

import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.network.PlayerPositionUpdate;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final MultiplayerGameServer server;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Player player;
    private String playerId;
    private int moveDirection = 2; // Default direction
    private boolean isMoving = false;
    private volatile boolean isConnected = true;
    
    public ClientHandler(Socket socket, MultiplayerGameServer server) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }
    
    @Override
    public void run() {
        try {
            // Handle client communication
            while (isConnected && !clientSocket.isClosed()) {
                try {
                    Object input = in.readObject();
                    if (input instanceof NetworkMessage) {
                        handleMessage((NetworkMessage) input);
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Error reading message: " + e.getMessage());
                } catch (IOException e) {
                    if (isConnected) {
                        System.err.println("Client disconnected: " + e.getMessage());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in client handler: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
    
    private void handleMessage(NetworkMessage message) {
        switch (message.getType()) {
            case PLAYER_JOIN:
                handlePlayerJoin(message);
                break;
            case PLAYER_POSITION_UPDATE:
                handlePositionUpdate(message);
                break;
            case PLAYER_ACTION:
                handlePlayerAction(message);
                break;
            case PING:
                sendMessage(new NetworkMessage(NetworkMessage.MessageType.PONG, playerId));
                break;
            // Lobby messages
            case CREATE_LOBBY:
                handleCreateLobby(message);
                break;
            case JOIN_LOBBY:
                handleJoinLobby(message);
                break;
            case LEAVE_LOBBY:
                handleLeaveLobby(message);
                break;
            case LOBBY_LIST_REQUEST:
                handleLobbyListRequest(message);
                break;
            case LOBBY_CHAT_MESSAGE:
                handleLobbyChatMessage(message);
                break;
            case START_GAME:
                handleStartGame(message);
                break;
            default:
                System.out.println("Unknown message type: " + message.getType());
        }
    }
    
    private void handlePlayerJoin(NetworkMessage message) {
        this.playerId = message.getPlayerId();
        if (message.getData() instanceof Player) {
            this.player = (Player) message.getData();
            server.addClient(playerId, this);
        }
    }
    
    private void handlePositionUpdate(NetworkMessage message) {
        if (message.getData() instanceof PlayerPositionUpdate) {
            PlayerPositionUpdate update = (PlayerPositionUpdate) message.getData();
            this.moveDirection = update.getMoveDirection();
            this.isMoving = update.isMoving();
            
            if (player != null) {
                player.setCoordinates(update.getPosition());
            }
        }
    }
    
    private void handlePlayerAction(NetworkMessage message) {
        // Handle player actions like using tools, planting, etc.
        // Broadcast to other clients
        server.broadcastToOthers(playerId, message);
    }
    
    // Lobby handling methods
    private void handleCreateLobby(NetworkMessage message) {
        if (message.getData() instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> lobbyData = (Map<String, Object>) message.getData();
            String lobbyName = (String) lobbyData.get("lobbyName");
            Integer maxPlayers = (Integer) lobbyData.get("maxPlayers");
            
            if (lobbyName != null && maxPlayers != null) {
                server.createLobby(lobbyName, playerId, maxPlayers);
            }
        }
    }
    
    private void handleJoinLobby(NetworkMessage message) {
        if (message.getData() instanceof String) {
            String lobbyId = (String) message.getData();
            server.joinLobby(lobbyId, playerId);
        }
    }
    
    private void handleLeaveLobby(NetworkMessage message) {
        if (message.getData() instanceof String) {
            String lobbyId = (String) message.getData();
            server.leaveLobby(lobbyId, playerId);
        }
    }
    
    private void handleLobbyListRequest(NetworkMessage message) {
        List<Lobby> lobbyList = server.getLobbyList();
        NetworkMessage response = new NetworkMessage(
            NetworkMessage.MessageType.LOBBY_LIST_RESPONSE,
            playerId,
            lobbyList
        );
        sendMessage(response);
    }
    
    private void handleLobbyChatMessage(NetworkMessage message) {
        if (message.getData() instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> chatData = (Map<String, Object>) message.getData();
            String lobbyId = (String) chatData.get("lobbyId");
            String chatMessage = (String) chatData.get("message");
            
            if (lobbyId != null && chatMessage != null) {
                server.addChatMessage(lobbyId, playerId, chatMessage);
            }
        }
    }
    
    private void handleStartGame(NetworkMessage message) {
        // Handle starting the game from lobby
        // This will be implemented later
        System.out.println("Start game requested by " + playerId);
    }
    
    public void sendMessage(NetworkMessage message) {
        try {
            if (isConnected && !clientSocket.isClosed()) {
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Error sending message to client: " + e.getMessage());
            isConnected = false;
        }
    }
    
    public void sendPositionUpdates(List<PlayerPositionUpdate> updates) {
        try {
            if (isConnected && !clientSocket.isClosed()) {
                NetworkMessage message = new NetworkMessage(
                    NetworkMessage.MessageType.GAME_STATE_UPDATE, 
                    playerId, 
                    updates
                );
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Error sending position updates: " + e.getMessage());
            isConnected = false;
        }
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public String getPlayerId() {
        return playerId;
    }
    
    public int getMoveDirection() {
        return moveDirection;
    }
    
    public boolean isMoving() {
        return isMoving;
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    private void cleanup() {
        isConnected = false;
        if (playerId != null) {
            server.removeClient(playerId);
        }
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}
