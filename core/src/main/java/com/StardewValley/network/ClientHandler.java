package com.StardewValley.network;

import com.StardewValley.model.Player;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private GameServer server;
    private String playerId;
    private AtomicBoolean connected;
    private Json json;

    public ClientHandler(Socket socket, GameServer server) {
        this.clientSocket = socket;
        this.server = server;
        this.connected = new AtomicBoolean(true);
        this.json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error creating client handler: " + e.getMessage());
            connected.set(false);
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while (connected.get() && (inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (IOException e) {
            if (connected.get()) {
                System.err.println("Error reading from client: " + e.getMessage());
            }
        } finally {
            disconnect();
        }
    }

    private void handleMessage(String messageJson) {
        try {
            NetworkMessage message = json.fromJson(NetworkMessage.class, messageJson);
            
            switch (message.getType()) {
                case PLAYER_JOIN:
                    Player player = json.fromJson(Player.class, message.getData());
                    this.playerId = player.getId();
                    server.handlePlayerJoin(player, this);
                    break;
                    
                case PLAYER_LEAVE:
                    server.handlePlayerLeave(message.getPlayerId());
                    break;
                    
                case PLAYER_POSITION:
                    NetworkManager.PositionData posData = json.fromJson(NetworkManager.PositionData.class, message.getData());
                    server.handlePlayerPosition(message.getPlayerId(), posData.x, posData.y);
                    break;
                    
                case CHAT:
                    server.handleChatMessage(message.getPlayerId(), message.getData());
                    break;
                    
                case GAME_ACTION:
                    GameAction action = json.fromJson(GameAction.class, message.getData());
                    server.handleGameAction(message.getPlayerId(), action);
                    break;
                    
                case ADMIN_COMMAND:
                    handleAdminCommand(message);
                    break;
                    
                case VOTE:
                    handleVote(message);
                    break;
                    
                default:
                    System.out.println("Unknown message type: " + message.getType());
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
            
            // Send error message back to client
            NetworkMessage errorMessage = new NetworkMessage();
            errorMessage.setType(MessageType.ERROR);
            errorMessage.setData("Error processing message: " + e.getMessage());
            sendMessage(errorMessage);
        }
    }

    private void handleAdminCommand(NetworkMessage message) {
        // Parse admin command
        String[] parts = message.getData().split(" ");
        if (parts.length < 2) {
            sendErrorMessage("Invalid admin command format");
            return;
        }
        
        String command = parts[0];
        String targetPlayer = parts[1];
        
        switch (command.toLowerCase()) {
            case "kick":
                server.banPlayer(targetPlayer);
                sendMessage(new NetworkMessage(MessageType.ADMIN_COMMAND, playerId, "Player " + targetPlayer + " has been kicked"));
                break;
                
            case "ban":
                server.banPlayer(targetPlayer);
                sendMessage(new NetworkMessage(MessageType.ADMIN_COMMAND, playerId, "Player " + targetPlayer + " has been banned"));
                break;
                
            default:
                sendErrorMessage("Unknown admin command: " + command);
                break;
        }
    }

    private void handleVote(NetworkMessage message) {
        // Handle voting system
        // This would implement the voting functionality described in phase3.txt
        System.out.println("Vote received from " + message.getPlayerId() + ": " + message.getData());
    }

    public void sendMessage(NetworkMessage message) {
        if (connected.get() && out != null) {
            try {
                String jsonMessage = json.toJson(message);
                out.println(jsonMessage);
            } catch (Exception e) {
                System.err.println("Error sending message to client: " + e.getMessage());
                disconnect();
            }
        }
    }

    public void sendErrorMessage(String error) {
        NetworkMessage errorMessage = new NetworkMessage();
        errorMessage.setType(MessageType.ERROR);
        errorMessage.setData(error);
        sendMessage(errorMessage);
    }

    public void disconnect() {
        connected.set(false);
        
        if (playerId != null) {
            server.handlePlayerLeave(playerId);
        }
        
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
        
        System.out.println("Client disconnected: " + (playerId != null ? playerId : "unknown"));
    }

    public boolean isConnected() {
        return connected.get() && clientSocket != null && !clientSocket.isClosed();
    }

    public String getPlayerId() {
        return playerId;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
} 