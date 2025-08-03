import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleGameServer {
    private ServerSocket serverSocket;
    private int port;
    private AtomicBoolean running;
    private ExecutorService clientExecutor;
    private Map<String, ClientConnection> clients;
    private List<String> bannedPlayers;
    private Map<String, String> adminPlayers;

    public SimpleGameServer(int port) {
        this.port = port;
        this.running = new AtomicBoolean(false);
        this.clientExecutor = Executors.newCachedThreadPool();
        this.clients = new ConcurrentHashMap<>();
        this.bannedPlayers = new ArrayList<>();
        this.adminPlayers = new HashMap<>();
        
        // Add default admin
        adminPlayers.put("admin", "admin123");
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running.set(true);
            System.out.println("Simple Game server started on port " + port);
            
            // Accept client connections
            while (running.get()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientConnection clientConnection = new ClientConnection(clientSocket, this);
                    clientExecutor.submit(clientConnection);
                } catch (IOException e) {
                    if (running.get()) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }

    public void stop() {
        running.set(false);
        
        // Disconnect all clients
        for (ClientConnection client : clients.values()) {
            client.disconnect();
        }
        clients.clear();
        
        // Shutdown executors
        clientExecutor.shutdown();
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
        
        System.out.println("Simple Game server stopped");
    }

    public void addClient(String playerId, ClientConnection client) {
        clients.put(playerId, client);
        System.out.println("Player " + playerId + " connected. Total players: " + clients.size());
    }

    public void removeClient(String playerId) {
        clients.remove(playerId);
        System.out.println("Player " + playerId + " disconnected. Total players: " + clients.size());
    }

    public void broadcastMessage(String message) {
        for (ClientConnection client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public boolean isPlayerBanned(String playerId) {
        return bannedPlayers.contains(playerId);
    }

    public void banPlayer(String playerId) {
        bannedPlayers.add(playerId);
        ClientConnection client = clients.get(playerId);
        if (client != null) {
            client.disconnect();
        }
    }

    public boolean isAdmin(String playerId, String password) {
        String storedPassword = adminPlayers.get(playerId);
        return storedPassword != null && storedPassword.equals(password);
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 8080.");
            }
        }
        
        SimpleGameServer server = new SimpleGameServer(port);
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down server...");
            server.stop();
        }));
        
        server.start();
    }

    private static class ClientConnection implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private SimpleGameServer server;
        private String playerId;
        private AtomicBoolean connected;

        public ClientConnection(Socket socket, SimpleGameServer server) {
            this.clientSocket = socket;
            this.server = server;
            this.connected = new AtomicBoolean(true);
            
            try {
                this.out = new PrintWriter(socket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Error creating client connection: " + e.getMessage());
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

        private void handleMessage(String message) {
            // Simple message handling
            System.out.println("Received message: " + message);
            
            // Echo back to client
            sendMessage("Server received: " + message);
        }

        public void sendMessage(String message) {
            if (connected.get() && out != null) {
                try {
                    out.println(message);
                } catch (Exception e) {
                    System.err.println("Error sending message to client: " + e.getMessage());
                    disconnect();
                }
            }
        }

        public void disconnect() {
            connected.set(false);
            
            if (playerId != null) {
                server.removeClient(playerId);
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

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }
    }
} 