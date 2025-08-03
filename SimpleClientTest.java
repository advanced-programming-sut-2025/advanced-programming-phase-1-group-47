import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClientTest {
    public static void main(String[] args) {
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
        
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connected to server!");
            
            // Send a test message
            String testMessage = "Hello from client!";
            out.println(testMessage);
            System.out.println("Sent: " + testMessage);
            
            // Read response
            String response = in.readLine();
            System.out.println("Received: " + response);
            
            // Send another message
            String secondMessage = "Test message 2";
            out.println(secondMessage);
            System.out.println("Sent: " + secondMessage);
            
            response = in.readLine();
            System.out.println("Received: " + response);
            
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
} 