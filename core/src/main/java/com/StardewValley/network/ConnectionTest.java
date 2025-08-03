package com.StardewValley.network;

import com.StardewValley.model.Player;
import com.badlogic.gdx.utils.Json;

import java.util.Scanner;

public class ConnectionTest {
    public static void main(String[] args) {
        System.out.println("Stardew Valley Multiplayer Connection Test");
        System.out.println("==========================================");
        
        String serverAddress = "localhost";
        int serverPort = 8080;
        
        if (args.length >= 1) {
            serverAddress = args[0];
        }
        if (args.length >= 2) {
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 8080.");
            }
        }
        
        System.out.println("Connecting to server: " + serverAddress + ":" + serverPort);
        
        // Create test player
        Player testPlayer = new Player("TestPlayer", "password", "email", "TestPlayer", 
                                      com.StardewValley.model.enums.Gender.Male, "question", "answer");
        testPlayer.setId(1);
        testPlayer.setCoordinates(new java.awt.Point(100, 100));
        
        // Create network manager
        NetworkManager networkManager = NetworkManager.getInstance();
        
        // Set up message listener
        networkManager.setMessageListener(new NetworkManager.NetworkMessageListener() {
            @Override
            public void onPlayerJoined(Player player) {
                System.out.println("Player joined: " + player.getNickname());
            }
            
            @Override
            public void onPlayerLeft(String playerId) {
                System.out.println("Player left: " + playerId);
            }
            
            @Override
            public void onGameStateUpdate(com.StardewValley.model.Game game) {
                System.out.println("Game state updated");
            }
            
            @Override
            public void onChatMessage(String playerId, String message) {
                System.out.println("Chat: " + playerId + " - " + message);
            }
            
            @Override
            public void onPlayerPositionUpdate(String playerId, float x, float y) {
                System.out.println("Position update: " + playerId + " at (" + x + ", " + y + ")");
            }
            
            @Override
            public void onError(String error) {
                System.err.println("Error: " + error);
            }
        });
        
        // Connect to server
        boolean connected = networkManager.connect(serverAddress, serverPort);
        if (!connected) {
            System.err.println("Failed to connect to server!");
            return;
        }
        
        System.out.println("Successfully connected to server!");
        
        // Set player ID and send join message
        networkManager.setPlayerId(String.valueOf(testPlayer.getId()));
        networkManager.sendPlayerJoin(testPlayer);
        
        // Interactive test loop
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nCommands:");
        System.out.println("  chat <message> - Send chat message");
        System.out.println("  move <x> <y> - Update position");
        System.out.println("  quit - Disconnect and exit");
        System.out.println();
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("quit")) {
                break;
            } else if (input.startsWith("chat ")) {
                String message = input.substring(5);
                networkManager.sendChatMessage(message);
                System.out.println("Sent chat message: " + message);
            } else if (input.startsWith("move ")) {
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    try {
                        float x = Float.parseFloat(parts[1]);
                        float y = Float.parseFloat(parts[2]);
                        networkManager.sendPlayerPosition(x, y);
                        System.out.println("Sent position update: (" + x + ", " + y + ")");
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid coordinates. Use: move <x> <y>");
                    }
                } else {
                    System.err.println("Invalid move command. Use: move <x> <y>");
                }
            } else if (!input.isEmpty()) {
                System.err.println("Unknown command. Use: chat, move, or quit");
            }
        }
        
        // Disconnect
        networkManager.disconnect();
        System.out.println("Disconnected from server.");
        scanner.close();
    }
} 