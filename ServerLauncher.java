import com.StardewValley.network.GameServer;

public class ServerLauncher {
    public static void main(String[] args) {
        System.out.println("Starting Stardew Valley Multiplayer Server...");
        
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 8080.");
            }
        }
        
        System.out.println("Server will start on port: " + port);
        System.out.println("Press Ctrl+C to stop the server");
        
        GameServer server = new GameServer(port);
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server...");
            server.stop();
        }));
        
        // Start the server
        server.start();
    }
} 