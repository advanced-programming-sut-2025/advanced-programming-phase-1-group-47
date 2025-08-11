import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTestClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerId;
    private Scanner scanner;
    
    public SimpleTestClient(String playerId) {
        this.playerId = playerId;
        this.scanner = new Scanner(System.in);
    }
    
    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Send player ID to server
            out.println(playerId);
            
            // Read welcome message
            String welcomeMessage = in.readLine();
            if (welcomeMessage != null) {
                System.out.println("Server: " + welcomeMessage);
                return true;
            }
            
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
        return false;
    }
    
    public void startMessageListener() {
        Thread listenerThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            }
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
    
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
    
    public void disconnect() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error during disconnect: " + e.getMessage());
        }
    }
    
    public void showMenu() {
        System.out.println("\n=== Multiplayer Test Client ===");
        System.out.println("1. Create Lobby");
        System.out.println("2. Join Lobby");
        System.out.println("3. Leave Lobby");
        System.out.println("4. Request Lobby List");
        System.out.println("5. Send Chat Message");
        System.out.println("6. Send Custom Message");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
    
    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.print("Enter lobby name: ");
                String lobbyName = scanner.nextLine();
                System.out.print("Enter max players: ");
                int maxPlayers = Integer.parseInt(scanner.nextLine());
                sendMessage("CREATE_LOBBY:" + lobbyName + ":" + maxPlayers + ":" + playerId);
                break;
                
            case 2:
                System.out.print("Enter lobby ID: ");
                String lobbyId = scanner.nextLine();
                sendMessage("JOIN_LOBBY:" + lobbyId);
                break;
                
            case 3:
                System.out.print("Enter lobby ID: ");
                String leaveLobbyId = scanner.nextLine();
                sendMessage("LEAVE_LOBBY:" + leaveLobbyId);
                break;
                
            case 4:
                sendMessage("LOBBY_LIST_REQUEST");
                break;
                
            case 5:
                System.out.print("Enter lobby ID: ");
                String chatLobbyId = scanner.nextLine();
                System.out.print("Enter message: ");
                String chatMessage = scanner.nextLine();
                sendMessage("CHAT:" + chatLobbyId + ":" + chatMessage);
                break;
                
            case 6:
                System.out.print("Enter message: ");
                String customMessage = scanner.nextLine();
                sendMessage(customMessage);
                break;
                
            case 0:
                System.out.println("Disconnecting...");
                disconnect();
                System.exit(0);
                break;
                
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    public void run() {
        if (!connect()) {
            System.err.println("Failed to connect to server!");
            return;
        }
        
        startMessageListener();
        
        while (true) {
            try {
                showMenu();
                int choice = Integer.parseInt(scanner.nextLine());
                handleMenuChoice(choice);
                
                // Small delay to see server responses
                Thread.sleep(500);
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your player ID: ");
        String playerId = scanner.nextLine();
        
        if (playerId.trim().isEmpty()) {
            playerId = "Player" + (int)(Math.random() * 1000);
            System.out.println("Using default player ID: " + playerId);
        }
        
        SimpleTestClient client = new SimpleTestClient(playerId);
        client.run();
    }
}
