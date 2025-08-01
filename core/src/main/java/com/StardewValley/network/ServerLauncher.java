package com.StardewValley.network;

public class ServerLauncher {
    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 8080.");
            }
        }
        
        System.out.println("Starting Stardew Valley Multiplayer Server on port " + port);
        
        NetworkManager networkManager = NetworkManager.getInstance();
        boolean success = networkManager.startServer(port);
        
        if (success) {
            System.out.println("Server started successfully!");
            System.out.println("Waiting for clients to connect...");
            
            // Keep the server running
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                System.out.println("Server interrupted. Shutting down...");
            }
        } else {
            System.err.println("Failed to start server!");
            System.exit(1);
        }
    }
} 