import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Simple Game Launcher for testing the multiplayer lobby integration
 * This bypasses Gradle and directly tests the networking functionality
 */
public class SimpleGameLauncher {
    
    public static void main(String[] args) {
        System.out.println("=== Stardew Valley Multiplayer Test ===");
        System.out.println("This launcher tests the multiplayer lobby integration");
        System.out.println();
        
        // Test server connection
        if (!testServerConnection()) {
            System.out.println("ERROR: Cannot connect to server!");
            System.out.println("Please make sure the SimpleMultiplayerServer is running first.");
            System.out.println("Run: start_simple_server.bat");
            return;
        }
        
        System.out.println("SUCCESS: Server connection works!");
        System.out.println();
        System.out.println("Now you can:");
        System.out.println("1. Start the full game (if Gradle works)");
        System.out.println("2. Or test the lobby functionality with the test client");
        System.out.println();
        System.out.println("To test the full game lobby:");
        System.out.println("1. Make sure the server is running");
        System.out.println("2. Launch the game");
        System.out.println("3. Go to Multiplayer");
        System.out.println("4. Enter a nickname");
        System.out.println("5. Test lobby creation, joining, and chat");
    }
    
    private static boolean testServerConnection() {
        try {
            System.out.println("Testing connection to localhost:8080...");
            
            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Send test player ID
            String testPlayerId = "TestPlayer_" + System.currentTimeMillis();
            out.println(testPlayerId);
            
            // Read welcome message
            String welcomeMessage = in.readLine();
            if (welcomeMessage != null && welcomeMessage.contains("Welcome")) {
                System.out.println("Received: " + welcomeMessage);
                
                // Test basic commands
                System.out.println("Testing basic server commands...");
                
                // Test lobby list request
                out.println("LOBBY_LIST_REQUEST");
                String response = in.readLine();
                if (response != null && response.startsWith("LOBBY_LIST:")) {
                    System.out.println("✓ Lobby list request works");
                } else {
                    System.out.println("✗ Lobby list request failed");
                }
                
                // Test echo
                out.println("Hello Server!");
                response = in.readLine();
                if (response != null && response.startsWith("ECHO:")) {
                    System.out.println("✓ Echo functionality works");
                } else {
                    System.out.println("✗ Echo functionality failed");
                }
                
                // Clean up
                out.close();
                in.close();
                socket.close();
                
                return true;
            }
            
            // Clean up on failure
            out.close();
            in.close();
            socket.close();
            
        } catch (Exception e) {
            System.err.println("Connection test failed: " + e.getMessage());
        }
        
        return false;
    }
}
