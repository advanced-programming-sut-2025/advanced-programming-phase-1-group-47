package com.StardewValley.network;

import com.StardewValley.model.Player;
import com.StardewValley.model.Game;
import com.StardewValley.model.Time;
import com.StardewValley.model.Point;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class GameServer {
    private ServerSocket serverSocket;
    private int port;
    private AtomicBoolean running;
    private ExecutorService clientExecutor;
    private ConcurrentHashMap<String, ClientHandler> clients;
    private Game gameState;
    private Json json;
    private ScheduledExecutorService gameLoopExecutor;
    private List<String> bannedPlayers;
    private Map<String, String> adminPlayers;

    public GameServer(int port) {
        this.port = port;
        this.running = new AtomicBoolean(false);
        this.clientExecutor = Executors.newCachedThreadPool();
        this.clients = new ConcurrentHashMap<>();
        this.gameState = new Game();
        this.json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        this.gameLoopExecutor = Executors.newScheduledThreadPool(1);
        this.bannedPlayers = new ArrayList<>();
        this.adminPlayers = new HashMap<>();
        
        // Add some default admin players
        adminPlayers.put("admin", "admin123");
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running.set(true);
            System.out.println("Game server started on port " + port);
            
            // Start game loop
            startGameLoop();
            
            // Accept client connections
            while (running.get()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clientExecutor.submit(clientHandler);
                } catch (IOException e) {
                    if (running.get()) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }

    public void stop() {
        running.set(false);
        
        // Disconnect all clients
        for (ClientHandler client : clients.values()) {
            client.disconnect();
        }
        clients.clear();
        
        // Shutdown executors
        clientExecutor.shutdown();
        gameLoopExecutor.shutdown();
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
        
        System.out.println("Game server stopped");
    }

    private void startGameLoop() {
        gameLoopExecutor.scheduleAtFixedRate(() -> {
            try {
                // Update game state
                updateGameState();
                
                // Broadcast game state to all clients
                broadcastGameState();
                
                // Clean up disconnected clients
                cleanupDisconnectedClients();
                
            } catch (Exception e) {
                System.err.println("Error in game loop: " + e.getMessage());
            }
        }, 0, 100, TimeUnit.MILLISECONDS); // 10 FPS game loop
    }

    private void updateGameState() {
        // Update time - increment hour
        Time currentTime = gameState.getTime();
        currentTime.setHour(currentTime.getHour() + 1);
        
        // Update weather periodically
        if (currentTime.getHourOfDay() == 0) { // At midnight
            gameState.nextDayWeather();
        }
        
        // Note: NPCs, crops, and animals updates would need to be implemented
        // in the Game class if they don't exist yet
    }

    private void broadcastGameState() {
        NetworkMessage message = new NetworkMessage();
        message.setType(MessageType.GAME_STATE_UPDATE);
        message.setData(json.toJson(gameState));
        
        broadcastMessage(message);
    }

    public void broadcastMessage(NetworkMessage message) {
        for (ClientHandler client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public void broadcastMessageToOthers(NetworkMessage message, String excludePlayerId) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if (!entry.getKey().equals(excludePlayerId)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    public void addClient(String playerId, ClientHandler client) {
        clients.put(playerId, client);
        System.out.println("Player " + playerId + " connected. Total players: " + clients.size());
    }

    public void removeClient(String playerId) {
        clients.remove(playerId);
        System.out.println("Player " + playerId + " disconnected. Total players: " + clients.size());
    }

    public void handlePlayerJoin(Player player, ClientHandler client) {
        String playerId = String.valueOf(player.getId());
        addClient(playerId, client);
        
        // Add player to game state
        gameState.addPlayer(player);
        
        // Notify other players
        NetworkMessage joinMessage = new NetworkMessage();
        joinMessage.setType(MessageType.PLAYER_JOIN);
        joinMessage.setPlayerId(playerId);
        joinMessage.setData(json.toJson(player));
        
        broadcastMessageToOthers(joinMessage, playerId);
        
        // Send current game state to new player
        NetworkMessage gameStateMessage = new NetworkMessage();
        gameStateMessage.setType(MessageType.GAME_STATE_UPDATE);
        gameStateMessage.setData(json.toJson(gameState));
        client.sendMessage(gameStateMessage);
    }

    public void handlePlayerLeave(String playerId) {
        removeClient(playerId);
        gameState.removePlayer(playerId);
        
        // Notify other players
        NetworkMessage leaveMessage = new NetworkMessage();
        leaveMessage.setType(MessageType.PLAYER_LEAVE);
        leaveMessage.setPlayerId(playerId);
        
        broadcastMessage(leaveMessage);
    }

    public void handleChatMessage(String playerId, String message) {
        NetworkMessage chatMessage = new NetworkMessage();
        chatMessage.setType(MessageType.CHAT);
        chatMessage.setPlayerId(playerId);
        chatMessage.setData(message);
        
        broadcastMessage(chatMessage);
    }

    public void handlePlayerPosition(String playerId, float x, float y) {
        Player player = gameState.getPlayer(playerId);
        if (player != null) {
            Point newPosition = new Point((int)x, (int)y);
            player.setCoordinates(newPosition);
            
            // Broadcast position to other players
            NetworkMessage positionMessage = new NetworkMessage();
            positionMessage.setType(MessageType.PLAYER_POSITION);
            positionMessage.setPlayerId(playerId);
            positionMessage.setData(json.toJson(new NetworkManager.PositionData(x, y)));
            
            broadcastMessageToOthers(positionMessage, playerId);
        }
    }

    public void handleGameAction(String playerId, GameAction action) {
        // Process the game action
        boolean success = processGameAction(playerId, action);
        
        if (success) {
            // Broadcast action to other players
            NetworkMessage actionMessage = new NetworkMessage();
            actionMessage.setType(MessageType.GAME_ACTION);
            actionMessage.setPlayerId(playerId);
            actionMessage.setData(json.toJson(action));
            
            broadcastMessageToOthers(actionMessage, playerId);
        }
    }

    private boolean processGameAction(String playerId, GameAction action) {
        Player player = gameState.getPlayer(playerId);
        if (player == null) return false;
        
        switch (action.getActionType()) {
            case PLANT_CROP:
                return gameState.plantCrop(player, action.getX(), action.getY(), action.getData());
            case HARVEST_CROP:
                return gameState.harvestCrop(player, action.getX(), action.getY());
            case WATER_CROP:
                return gameState.waterCrop(player, action.getX(), action.getY());
            case MINE_ROCK:
                return gameState.mineRock(player, action.getX(), action.getY());
            case CHOP_TREE:
                return gameState.chopTree(player, action.getX(), action.getY());
            case FISH:
                return gameState.fish(player, action.getX(), action.getY());
            case SLEEP:
                return gameState.sleep(player);
            default:
                return false;
        }
    }

    private void cleanupDisconnectedClients() {
        clients.entrySet().removeIf(entry -> !entry.getValue().isConnected());
    }

    public boolean isPlayerBanned(String playerId) {
        return bannedPlayers.contains(playerId);
    }

    public void banPlayer(String playerId) {
        bannedPlayers.add(playerId);
        ClientHandler client = clients.get(playerId);
        if (client != null) {
            client.disconnect();
        }
    }

    public boolean isAdmin(String playerId, String password) {
        String storedPassword = adminPlayers.get(playerId);
        return storedPassword != null && storedPassword.equals(password);
    }

    public Game getGameState() {
        return gameState;
    }

    public ConcurrentHashMap<String, ClientHandler> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 8080.");
            }
        }
        
        GameServer server = new GameServer(port);
        server.start();
    }
} 