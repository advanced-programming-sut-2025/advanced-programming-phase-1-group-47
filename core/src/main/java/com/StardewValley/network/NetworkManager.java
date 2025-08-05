package com.StardewValley.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.StardewValley.model.Game;
import com.StardewValley.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class NetworkManager {
    private static NetworkManager instance;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isConnected = false;
    private String serverAddress = "localhost";
    private int serverPort = 8080;
    private String playerId;
    private Json json;
    private ExecutorService executorService;
    private LinkedBlockingQueue<NetworkMessage> messageQueue;
    private ConcurrentHashMap<String, Player> connectedPlayers;
    private NetworkMessageListener messageListener;
    private Thread receiveThread;
    private boolean running = false;

    public interface NetworkMessageListener {
        void onPlayerJoined(Player player);
        void onPlayerLeft(String playerId);
        void onGameStateUpdate(Game game);
        void onChatMessage(String playerId, String message);
        void onPlayerPositionUpdate(String playerId, float x, float y);
        void onError(String error);
    }

    private NetworkManager() {
        json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        executorService = Executors.newCachedThreadPool();
        messageQueue = new LinkedBlockingQueue<>();
        connectedPlayers = new ConcurrentHashMap<>();
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public boolean connect(String address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
        
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            isConnected = true;
            running = true;
            
            // Start receiving messages
            startReceiveThread();
            
            Gdx.app.log("NetworkManager", "Connected to server: " + address + ":" + port);
            return true;
        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "Failed to connect to server: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        running = false;
        isConnected = false;
        
        if (receiveThread != null) {
            receiveThread.interrupt();
        }
        
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "Error closing socket: " + e.getMessage());
        }
        
        executorService.shutdown();
        connectedPlayers.clear();
        messageQueue.clear();
    }

    public void sendMessage(NetworkMessage message) {
        if (!isConnected) {
            Gdx.app.error("NetworkManager", "Not connected to server");
            return;
        }
        
        try {
            String jsonMessage = json.toJson(message);
            out.println(jsonMessage);
        } catch (Exception e) {
            Gdx.app.error("NetworkManager", "Failed to send message: " + e.getMessage());
        }
    }

    public void sendPlayerJoin(Player player) {
        NetworkMessage message = new NetworkMessage();
        message.setType(MessageType.PLAYER_JOIN);
        message.setPlayerId(String.valueOf(player.getId()));
        message.setData(json.toJson(player));
        Gdx.app.log("NetworkManager", "Sending player join with ID: " + player.getId() + " and name: " + player.getNickname());
        sendMessage(message);
    }

    public void sendPlayerLeave() {
        NetworkMessage message = new NetworkMessage();
        message.setType(MessageType.PLAYER_LEAVE);
        message.setPlayerId(playerId);
        sendMessage(message);
    }

    public void sendPlayerPosition(float x, float y) {
        NetworkMessage message = new NetworkMessage();
        message.setType(MessageType.PLAYER_POSITION);
        message.setPlayerId(playerId);
        message.setData(json.toJson(new PositionData(x, y)));
        sendMessage(message);
    }

    public void sendChatMessage(String message) {
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setType(MessageType.CHAT);
        networkMessage.setPlayerId(playerId);
        networkMessage.setData(message);
        sendMessage(networkMessage);
    }

    public void sendGameAction(GameAction action) {
        NetworkMessage message = new NetworkMessage();
        message.setType(MessageType.GAME_ACTION);
        message.setPlayerId(playerId);
        message.setData(json.toJson(action));
        sendMessage(message);
    }

    private void startReceiveThread() {
        receiveThread = new Thread(() -> {
            while (running && isConnected) {
                try {
                    String line = in.readLine();
                    if (line != null) {
                        NetworkMessage message = json.fromJson(NetworkMessage.class, line);
                        handleMessage(message);
                    }
                } catch (IOException e) {
                    if (running) {
                        Gdx.app.error("NetworkManager", "Error receiving message: " + e.getMessage());
                        if (messageListener != null) {
                            messageListener.onError("Connection lost: " + e.getMessage());
                        }
                    }
                    break;
                }
            }
        });
        receiveThread.start();
    }

    private void handleMessage(NetworkMessage message) {
        Gdx.app.postRunnable(() -> {
            try {
                switch (message.getType()) {
                    case PLAYER_JOIN:
                        Player newPlayer = json.fromJson(Player.class, message.getData());
                        connectedPlayers.put(String.valueOf(newPlayer.getId()), newPlayer);
                        Gdx.app.log("NetworkManager", "Added player to connected players: " + newPlayer.getId() + " - " + newPlayer.getNickname());
                        if (messageListener != null) {
                            messageListener.onPlayerJoined(newPlayer);
                        }
                        break;
                        
                    case PLAYER_LEAVE:
                        connectedPlayers.remove(message.getPlayerId());
                        if (messageListener != null) {
                            messageListener.onPlayerLeft(message.getPlayerId());
                        }
                        break;
                        
                    case PLAYER_POSITION:
                        PositionData posData = json.fromJson(PositionData.class, message.getData());
                        if (messageListener != null) {
                            messageListener.onPlayerPositionUpdate(message.getPlayerId(), posData.x, posData.y);
                        }
                        break;
                        
                    case CHAT:
                        Gdx.app.log("NetworkManager", "Received chat message from " + message.getPlayerId() + ": " + message.getData());
                        if (messageListener != null) {
                            messageListener.onChatMessage(message.getPlayerId(), message.getData());
                        }
                        break;
                        
                    case GAME_STATE_UPDATE:
                        Game gameState = json.fromJson(Game.class, message.getData());
                        if (messageListener != null) {
                            messageListener.onGameStateUpdate(gameState);
                        }
                        break;
                        
                    case ERROR:
                        if (messageListener != null) {
                            messageListener.onError(message.getData());
                        }
                        break;
                }
            } catch (Exception e) {
                Gdx.app.error("NetworkManager", "Error handling message: " + e.getMessage());
            }
        });
    }

    public void setMessageListener(NetworkMessageListener listener) {
        this.messageListener = listener;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public ConcurrentHashMap<String, Player> getConnectedPlayers() {
        return connectedPlayers;
    }

    public static class PositionData {
        public float x, y;
        
        public PositionData() {}
        
        public PositionData(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
} 