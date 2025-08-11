package com.StardewValley.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClientTest {
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 8080;
    
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private volatile boolean isConnected = false;
    private final String serverHost;
    private final int serverPort;
    private final String playerId;
    
    public SimpleClientTest(String serverHost, int serverPort, String playerId) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.playerId = playerId;
    }
    
    public boolean connect() {
        try {
            socket = new Socket(serverHost, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            
            // Send player ID
            out.writeObject(playerId);
            out.flush();
            
            // Start message listener thread
            new Thread(this::listenForMessages).start();
            
            System.out.println("Connected to server at " + serverHost + ":" + serverPort);
            return true;
            
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            return false;
        }
    }
    
    public void sendMessage(String message) {
        try {
            if (isConnected && out != null) {
                out.writeObject(message);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
            isConnected = false;
        }
    }
    
    private void listenForMessages() {
        try {
            while (isConnected && !socket.isClosed()) {
                Object input = in.readObject();
                if (input instanceof String) {
                    System.out.println("Server: " + input);
                }
            }
        } catch (Exception e) {
            if (isConnected) {
                System.err.println("Connection lost: " + e.getMessage());
                isConnected = false;
            }
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
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    public static void main(String[] args) {
        String serverHost = DEFAULT_SERVER_HOST;
        int serverPort = DEFAULT_SERVER_PORT;
        String playerId = "Player" + (int)(Math.random() * 1000);
        
        if (args.length > 0) {
            serverHost = args[0];
        }
        if (args.length > 1) {
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number, using default: " + DEFAULT_SERVER_PORT);
            }
        }
        if (args.length > 2) {
            playerId = args[2];
        }
        
        SimpleClientTest client = new SimpleClientTest(serverHost, serverPort, playerId);
        
        if (!client.connect()) {
            System.err.println("Failed to connect to server. Exiting.");
            return;
        }
        
        System.out.println("Connected as: " + playerId);
        System.out.println("Type 'help' for available commands, 'quit' to exit");
        
        Scanner scanner = new Scanner(System.in);
        while (client.isConnected()) {
            System.out.print(playerId + "> ");
            String input = scanner.nextLine();
            
            if (input.trim().equalsIgnoreCase("quit")) {
                break;
            }
            
            if (!input.trim().isEmpty()) {
                client.sendMessage(input);
            }
        }
        
        client.disconnect();
        System.out.println("Disconnected from server");
        scanner.close();
    }
}
