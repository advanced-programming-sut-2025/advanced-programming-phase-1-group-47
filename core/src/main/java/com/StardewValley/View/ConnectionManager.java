package com.StardewValley.View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Singleton class to manage server connections between different views
 * This prevents connection reset issues when transitioning from lobby to game
 */
public class ConnectionManager {
    private static ConnectionManager instance;
    
    // Server connection
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerNickname;
    private String currentLobbyId;
    private boolean isConnected = false;
    
    // Private constructor for singleton pattern
    private ConnectionManager() {}
    
    /**
     * Get the singleton instance
     */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    /**
     * Set the server connection from LobbyView
     */
    public void setConnection(Socket socket, PrintWriter out, BufferedReader in, String nickname, String lobbyId) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.playerNickname = nickname;
        this.currentLobbyId = lobbyId;
        this.isConnected = true;
        
        System.out.println("ConnectionManager: Server connection established");
        System.out.println("ConnectionManager: Player: " + nickname + ", LobbyId: " + lobbyId);
    }
    
    /**
     * Get the server connection for GameScreen to use
     */
    public Socket getSocket() {
        return socket;
    }
    
    public PrintWriter getWriter() {
        return out;
    }
    
    public BufferedReader getReader() {
        return in;
    }
    
    public String getPlayerNickname() {
        return playerNickname;
    }
    
    public String getCurrentLobbyId() {
        return currentLobbyId;
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    /**
     * Send a chat message to the server
     */
    public void sendChatMessage(String message) {
        if (isConnected && out != null) {
            try {
                String chatMessage = "GAME_CHAT:" + playerNickname + ":" + currentLobbyId + ":" + message;
                System.out.println("ConnectionManager: Sending chat message: [" + chatMessage + "]");
                out.println(chatMessage);
                out.flush();
                System.out.println("ConnectionManager: Chat message sent successfully");
            } catch (Exception e) {
                System.err.println("ConnectionManager: Error sending chat message: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("ConnectionManager: Cannot send chat message - not connected to server");
        }
    }
    
    /**
     * Close the connection when no longer needed
     */
    public void closeConnection() {
        if (isConnected) {
            try {
                System.out.println("ConnectionManager: Closing server connection");
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
                isConnected = false;
            } catch (Exception e) {
                System.err.println("ConnectionManager: Error closing connection: " + e.getMessage());
            }
        }
    }
}
