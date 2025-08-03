import com.StardewValley.network.GameServer;

public class TestServer {
    public static void main(String[] args) {
        System.out.println("Testing GameServer creation...");
        
        try {
            GameServer server = new GameServer(8080);
            System.out.println("GameServer created successfully!");
            
            // Test that we can access the game state
            System.out.println("Game state initialized: " + (server.getGameState() != null));
            System.out.println("Players in game: " + server.getGameState().getPlayers().size());
            
            System.out.println("Test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error creating server: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 