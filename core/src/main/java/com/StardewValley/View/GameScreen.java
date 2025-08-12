package com.StardewValley.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.StardewValley.View.Helpers.DialogUtils;
import com.StardewValley.View.Helpers.EnergyHelper;
import com.StardewValley.View.Helpers.InventoryDialog;
import com.StardewValley.View.Helpers.WeatherRenderer;
import com.StardewValley.controllers.GameMenuController;
import com.StardewValley.model.App;
import static com.StardewValley.model.App.batch;
import static com.StardewValley.model.App.camera;
import static com.StardewValley.model.App.currentGame;
import static com.StardewValley.model.App.isNpcTile;
import static com.StardewValley.model.App.returnCurrentFarm;
import static com.StardewValley.model.App.viewport;
import com.StardewValley.model.Energy;
import com.StardewValley.model.Game;
import com.StardewValley.model.GameAssetManager;
import static com.StardewValley.model.GameAssetManager.backgroundMusic;
import com.StardewValley.model.Ground;
import com.StardewValley.model.Map;
import com.StardewValley.model.NPC;
import com.StardewValley.model.Plant;
import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.model.Shop;
import com.StardewValley.model.Tile;
import com.StardewValley.model.TimeAndDate;
import com.StardewValley.model.buildings.Cottage;
import com.StardewValley.model.buildings.greenHouse;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    private int scrollDelta = 0;

    public static final int TILE_SIZE = 30;
    private Tile[][] tileMap;
    private Vector2 playerPosition;
    private float speed = 350f;
    private float stateTime;

    private int moveDirection = 2;
    private Rectangle currentDialogIconBounds;

    private HashMap<TileType, Texture> tileTextures;
    // UI
    public static Stage mainStage, dialogStage;
    private  static Skin skin;
    private EnergyHelper energyHelper;
    // Toolbar
    private boolean isToolAnimating = false;
    private float toolAnimTime = 0f;
    private final float toolAnimDuration = 0.3f;
    private static final int TOOLBAR_SIZE = 24;
    private static Stack[] toolbarSlots = new Stack[TOOLBAR_SIZE];
    private static int selectedToolIndex = 0;
    private ArrayList<Texture> toolTextures = new ArrayList<>();
    private static ArrayList<Item> toolbarItems = new ArrayList<>();
    private static Texture emptySlotTexture;
    GameMenuController controller = new GameMenuController();
    private boolean isFainted = false;
    private float playerRotation = 0f;
    private Texture energyBarTexture;
    private Pixmap energyBarPixmap;
    private Texture backgroundTexture;
    private Texture fillTexture;
    public static boolean isOutOfRealGame = false;
    
    // Multiplayer chat functionality
    private boolean isMultiplayerMode = false;
    private String playerNickname;
    private String currentLobbyId;
    private Socket gameSocket;
    private PrintWriter gameOut;
    private BufferedReader gameIn;
    private Thread gameMessageThread;

    
    // Chat UI components
    private Stage chatStage;
    private Table chatTable;
    private TextArea chatArea;
    private TextField chatInputField;
    private ScrollPane chatScrollPane;
    private boolean isChatVisible = false;
    private List<String> chatMessages;
    private Label statusLabel;
    
    // Player list dialog components
    private Dialog playerListDialog;
    private boolean isPlayerListVisible = false;
    
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camera);
        batch = new SpriteBatch();
        skin = GameAssetManager.getGameAssetManager().getSkin();
        setupGame();
        App.getCurrentGame().loadShops();
        App.getCurrentGame().setNpc();
        loadTextures();
        Point playerPos = App.returnCurrentFarm().getPersonPoint();
        playerPosition = new Vector2(playerPos.y * TILE_SIZE, playerPos.x * TILE_SIZE);
        stateTime = 0f;
        energyHelper = new EnergyHelper();
        energyHelper.initEnergyBar(); // Initialize energy bar
        
        // Initialize chat system for multiplayer
        initializeChatSystem();
    }
    
    /**
     * Sets multiplayer information and enables chat functionality
     */
    public void setMultiplayerInfo(String nickname, String lobbyId) {
        this.playerNickname = nickname;
        this.currentLobbyId = lobbyId;
        this.isMultiplayerMode = true;
        
        // Initialize the chat system
        initializeChatSystem();
        
        // Connect to the game server on port 8081
        connectToGameServer();
    }
    
    /**
     * Connects to the game server on port 8081
     */
    private void connectToGameServer() {
        try {
            System.out.println("GameScreen: Connecting to game server on port 8081...");
            updateStatusLabel("Connecting to Game Server...", Color.YELLOW);
            
            // Connect to game server
            gameSocket = new Socket("localhost", 8081);
            gameOut = new PrintWriter(gameSocket.getOutputStream(), true);
            gameIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            
            System.out.println("GameScreen: Connected to game server successfully");
            updateStatusLabel("Connected to Game Server", Color.GREEN);
            
            // Send identification to game server
            String identificationMessage = "GAME_CLIENT:" + playerNickname + ":" + currentLobbyId;
            System.out.println("GameScreen: Sending identification to game server: " + identificationMessage);
            gameOut.println(identificationMessage);
            gameOut.flush();
            
            // Start listening for messages from game server
            startGameMessageListener();
            
            addChatMessage("System", "Connected to game server - chat functionality enabled!");
            
        } catch (IOException e) {
            System.err.println("GameScreen: Failed to connect to game server: " + e.getMessage());
            e.printStackTrace();
            updateStatusLabel("Game Server Connection Failed", Color.RED);
            addChatMessage("System", "Failed to connect to game server: " + e.getMessage());
        }
    }
    

    
    /**
     * Initializes the chat system UI components
     */
    private void initializeChatSystem() {
        // Initialize player list dialog
        initializePlayerListDialog();
        
        chatMessages = new ArrayList<>();
        chatStage = new Stage();
        
        // Create chat table
        chatTable = new Table();
        chatTable.setVisible(false); // Hidden by default
        
        // Chat area
        chatArea = new TextArea("", skin);
        chatArea.setDisabled(false); // Enable the chat area so it can display text
        chatArea.setPrefRows(8); // Set preferred number of rows
        
        chatScrollPane = new ScrollPane(chatArea, skin);
        chatScrollPane.setFadeScrollBars(false); // Always show scroll bars
        chatScrollPane.setScrollBarPositions(false, true); // Show scroll bars on right and bottom
        chatTable.add(chatScrollPane).width(300).height(200).row();
        
        // Add player list button
        TextButton playerListButton = new TextButton("Players", skin);
        playerListButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePlayerList();
            }
        });
        chatTable.add(playerListButton).width(100).height(30);
        
        // Add exit button
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleChat();
            }
        });
        chatTable.add(exitButton).width(80).height(30).row();
        
        // Add initial message to show chat is working
        // Initialize chat messages list if it's null
        if (chatMessages == null) {
            chatMessages = new ArrayList<>();
        }
        addChatMessage("System", "Chat ready. Press C to toggle, P for players, Enter to send, or click Exit to close.");
        
        // Chat input field
        chatInputField = new TextField("", skin);
        chatInputField.setMessageText("Type a message... (Press C to toggle chat, Enter to send)");
        
        // Add listener for Enter key to send message
        chatInputField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // This will be called when Enter is pressed
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    sendChatMessage();
                }
            }
        });
        
        // Also add a key listener to handle Enter key properly
        chatInputField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    sendChatMessage();
                    return true;
                }
                return false;
            }
        });
        
        // Add focus listener to ensure proper input handling
        chatInputField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // When chat input field gains focus, ensure it can receive all keyboard input
                if (chatInputField.hasKeyboardFocus()) {
                    // The field is now focused and ready to receive input
                }
            }
        });
        
        chatTable.add(chatInputField).width(300).row();
        
        // Send button
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendChatMessage();
            }
        });
        chatTable.add(sendButton).width(80);
        
        // Test button to add sample messages (for debugging)
        TextButton testButton = new TextButton("Test", skin);
        testButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addChatMessage("TestPlayer", "This is a test message from another player");
                addChatMessage("System", "Testing chat functionality");
            }
        });
        chatTable.add(testButton).width(60);
        
        // Test connection button to verify server communication
        TextButton testConnButton = new TextButton("TestConn", skin);
        testConnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                testServerConnection();
            }
        });
        chatTable.add(testConnButton).width(60);
        
        // Test broadcast button to verify message broadcasting
        TextButton testBroadcastButton = new TextButton("TestBroad", skin);
        testBroadcastButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                testMessageBroadcast();
            }
        });
        chatTable.add(testBroadcastButton).width(60);
        
        // Clear chat button to help manage cluttered chat
        TextButton clearButton = new TextButton("Clear", skin);
        clearButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clearChat();
            }
        });
        chatTable.add(clearButton).width(60);
        
        // Debug button to show current chat state
        TextButton debugButton = new TextButton("Debug", skin);
        debugButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showChatDebugInfo();
            }
        });
        chatTable.add(debugButton).width(60);
        
        // Status indicator to show connection state
        statusLabel = new Label("Disconnected", skin);
        statusLabel.setColor(Color.RED);
        chatTable.add(statusLabel).width(120).row();
        
        // Add a row for status information
        chatTable.row();
        
        // Position chat at top-right corner
        chatTable.setPosition(Gdx.graphics.getWidth() - 320, Gdx.graphics.getHeight() - 250);
        chatStage.addActor(chatTable);
        
        // Now that chatStage is ready, add the player list dialog to it
        if (playerListDialog != null) {
            chatStage.addActor(playerListDialog);
        }
    }
    
    /**
     * Initializes the player list dialog
     */
    private void initializePlayerListDialog() {
        playerListDialog = new Dialog("Connected Players", skin);
        playerListDialog.setModal(false);
        playerListDialog.setMovable(true);
        playerListDialog.setResizable(false);
        
        // Set dialog size and position
        playerListDialog.setSize(250, 200);
        playerListDialog.setPosition(Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2 - 100);
        
        // Add close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hidePlayerList();
            }
        });
        
        // Add close button to dialog
        playerListDialog.button(closeButton);
        
        // Initially hide the dialog
        playerListDialog.setVisible(false);
        
        // Don't add to chatStage here - it will be added later when chatStage is ready
    }
    
    /**
     * Toggles the player list dialog visibility
     */
    private void togglePlayerList() {
        if (isPlayerListVisible) {
            hidePlayerList();
        } else {
            showPlayerList();
        }
    }
    
    /**
     * Shows the player list dialog with current connected players
     */
    private void showPlayerList() {
        if (playerListDialog != null) {
            // Ensure the dialog is added to the chat stage if it wasn't before
            if (chatStage != null && !chatStage.getActors().contains(playerListDialog, true)) {
                chatStage.addActor(playerListDialog);
                System.out.println("GameScreen: Player list dialog added to chat stage");
            }
            
            // Clear previous content
            playerListDialog.clearChildren();
            
            // Add title
            Label titleLabel = new Label("Connected Players", skin);
            titleLabel.setFontScale(1.2f);
            playerListDialog.add(titleLabel).row();
            
            // Add close button
            TextButton closeButton = new TextButton("Close", skin);
            closeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    hidePlayerList();
                }
            });
            
            // Get connected players from the game server
            if (isMultiplayerMode && gameSocket != null && !gameSocket.isClosed()) {
                // Request player list from server
                requestPlayerList();
                
                // Add a temporary message while waiting for server response
                Label tempLabel = new Label("Requesting player list...", skin);
                playerListDialog.add(tempLabel).row();
            } else {
                Label noConnectionLabel = new Label("Not connected to game server", skin);
                playerListDialog.add(noConnectionLabel).row();
            }
            
            playerListDialog.add(closeButton).width(80).height(30).row();
            
            // Show the dialog
            playerListDialog.setVisible(true);
            isPlayerListVisible = true;
            
            System.out.println("GameScreen: Player list dialog shown");
        }
    }
    
    /**
     * Hides the player list dialog
     */
    private void hidePlayerList() {
        if (playerListDialog != null) {
            playerListDialog.setVisible(false);
            isPlayerListVisible = false;
            System.out.println("GameScreen: Player list dialog hidden");
        }
    }
    
    /**
     * Requests the current player list from the game server
     */
    private void requestPlayerList() {
        if (gameOut != null) {
            try {
                String requestMessage = "PLAYER_LIST_REQUEST:" + currentLobbyId;
                System.out.println("GameScreen: Requesting player list: " + requestMessage);
                gameOut.println(requestMessage);
                gameOut.flush();
            } catch (Exception e) {
                System.err.println("GameScreen: Error requesting player list: " + e.getMessage());
            }
        }
    }
    
    /**
     * Updates the player list dialog with received player information
     */
    public void updatePlayerList(String[] playerNames) {
        if (playerListDialog != null && isPlayerListVisible) {
            // Ensure the dialog is added to the chat stage if it wasn't before
            if (chatStage != null && !chatStage.getActors().contains(playerListDialog, true)) {
                chatStage.addActor(playerListDialog);
                System.out.println("GameScreen: Player list dialog added to chat stage during update");
            }
            
            // Clear previous content
            playerListDialog.clearChildren();
            
            // Add title
            Label titleLabel = new Label("Connected Players", skin);
            titleLabel.setFontScale(1.2f);
            playerListDialog.add(titleLabel).row();
            
            // Add player names
            if (playerNames != null && playerNames.length > 0) {
                for (String playerName : playerNames) {
                    Label playerLabel = new Label("• " + playerName, skin);
                    playerLabel.setColor(Color.WHITE);
                    playerListDialog.add(playerLabel).row();
                }
            } else {
                Label noPlayersLabel = new Label("No players connected", skin);
                noPlayersLabel.setColor(Color.GRAY);
                playerListDialog.add(noPlayersLabel).row();
            }
            
            // Add close button
            TextButton closeButton = new TextButton("Close", skin);
            closeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    hidePlayerList();
                }
            });
            playerListDialog.add(closeButton).width(80).height(30).row();
        }
    }
    
    /**
     * Connects to the multiplayer game server
     */
    private void connectToMultiplayerGame() {
        try {
            System.out.println("GameScreen: Attempting to connect to multiplayer server...");
            updateStatusLabel("Connecting...", Color.YELLOW);
            
            // Check if server is reachable first
            if (!isServerReachable()) {
                updateStatusLabel("Server Unreachable", Color.RED);
                addChatMessage("System", "Server is not reachable. Make sure the multiplayer server is running on localhost:8080");
                return;
            }
            
            gameSocket = new Socket("localhost", 8080);
            gameOut = new PrintWriter(gameSocket.getOutputStream(), true);
            gameIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            
            System.out.println("GameScreen: Connected to server successfully");
            updateStatusLabel("Connected", Color.GREEN);
            
            // Send game client identification
            String identificationMessage = "GAME_CLIENT:" + playerNickname + ":" + currentLobbyId;
            System.out.println("GameScreen: Sending identification: " + identificationMessage);
            gameOut.println(identificationMessage);
            gameOut.flush(); // Ensure message is sent immediately
            
            // Add a small delay to ensure server processes the identification
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Ignore interruption
            }
            
            // Start message listener thread
            startGameMessageListener();
            
            // Add connection success message to chat
            addChatMessage("System", "Connected to multiplayer server");
            
            // Start a heartbeat thread to keep connection alive
            startHeartbeatThread();
            
        } catch (IOException e) {
            System.err.println("GameScreen: Failed to connect to multiplayer game server: " + e.getMessage());
            e.printStackTrace();
            updateStatusLabel("Connection Failed", Color.RED);
            addChatMessage("System", "Failed to connect to server: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("GameScreen: Unexpected error during connection: " + e.getMessage());
            e.printStackTrace();
            updateStatusLabel("Connection Error", Color.RED);
            addChatMessage("System", "Connection error: " + e.getMessage());
        }
    }
    
    /**
     * Starts the message listener thread for multiplayer communication
     */
    private void startGameMessageListener() {
        gameMessageThread = new Thread(() -> {
            System.out.println("GameScreen: Message listener thread started");
            try {
                String message;
                while (gameSocket != null && !gameSocket.isClosed() && (message = gameIn.readLine()) != null) {
                    final String finalMessage = message;
                    System.out.println("GameScreen: Raw message received: [" + finalMessage + "]");
                    System.out.println("GameScreen: Message length: " + finalMessage.length());
                    System.out.println("GameScreen: Message starts with: " + (finalMessage.length() > 0 ? finalMessage.substring(0, Math.min(10, finalMessage.length())) : "empty"));
                    
                    // Process message immediately to avoid delays
                    Gdx.app.postRunnable(() -> {
                        try {
                            handleGameMessage(finalMessage);
                        } catch (Exception e) {
                            System.err.println("GameScreen: Error in handleGameMessage: " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                if (gameSocket != null && !gameSocket.isClosed()) {
                    System.err.println("Game message listener error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Game message listener unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("GameScreen: Message listener thread ended");
        });
        gameMessageThread.setDaemon(true);
        gameMessageThread.start();
    }
    
    /**
     * Handles incoming game messages
     */
    private void handleGameMessage(String message) {
        System.out.println("GameScreen: Processing message: [" + message + "]");
        
        // Filter out unwanted server messages that clutter the chat
        if (message.contains("server has been initialized") || 
            message.contains("Server has been initialized") ||
            message.contains("SERVER HAS BEEN INITIALIZED")) {
            System.out.println("GameScreen: Filtering out server initialization message");
            return;
        }
        
        // Handle different chat message formats
        if (message.startsWith("GAME_CHAT:")) {
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String playerId = parts[1];
                String chatMessage = parts[2];
                System.out.println("GameScreen: Processing GAME_CHAT message from " + playerId + ": " + chatMessage);
                addChatMessage(playerId, chatMessage);
            } else {
                System.out.println("GameScreen: Invalid GAME_CHAT format, expected 3 parts, got " + parts.length);
            }
        } else if (message.startsWith("CHAT:")) {
            // Alternative chat message format
            String[] parts = message.split(":", 4);
            if (parts.length == 4) {
                String playerName = parts[1];
                String lobbyId = parts[2];
                String chatMessage = parts[3];
                System.out.println("GameScreen: Processing CHAT message from " + playerName + ": " + chatMessage);
                addChatMessage(playerName, chatMessage);
            } else {
                System.out.println("GameScreen: Invalid CHAT format, expected 4 parts, got " + parts.length);
            }
        } else if (message.startsWith("PLAYER_JOINED:")) {
            // Handle player joined notification
            String playerName = message.substring(14);
            System.out.println("GameScreen: Player joined: " + playerName);
            addChatMessage("System", playerName + " joined the game");
        } else if (message.startsWith("PLAYER_LEFT:")) {
            // Handle player left notification
            String playerName = message.substring(12);
            System.out.println("GameScreen: Player left: " + playerName);
            addChatMessage("System", playerName + " left the game");
        } else if (message.startsWith("INFO:")) {
            // Handle info messages, but filter out long system messages
            String infoMsg = message.substring(5);
            if (infoMsg.length() > 100) {
                System.out.println("GameScreen: Filtering out long info message: " + infoMsg.substring(0, 50) + "...");
                return;
            }
            System.out.println("GameScreen: Info message: " + infoMsg);
            addChatMessage("System", infoMsg);
        } else if (message.startsWith("ECHO:")) {
            // Handle echo messages (server responses)
            String echoMsg = message.substring(5);
            System.out.println("GameScreen: Echo message: " + echoMsg);
            // Don't add echo messages to chat to avoid spam
        } else if (message.startsWith("PONG")) {
            // Handle pong messages (server responses)
            System.out.println("GameScreen: Pong received");
            // Don't add pong messages to chat
        } else if (message.startsWith("PLAYER_LIST_RESPONSE:")) {
            // Handle player list response from server
            String[] parts = message.split(":", 2);
            if (parts.length == 2) {
                String playerListString = parts[1];
                if (!playerListString.isEmpty()) {
                    String[] playerNames = playerListString.split(",");
                    updatePlayerList(playerNames);
                } else {
                    updatePlayerList(new String[0]);
                }
            }
        } else if (message.startsWith("Welcome")) {
            // Handle welcome messages
            System.out.println("GameScreen: Welcome message: " + message);
            // Don't add welcome messages to chat
        } else if (message.contains("connected to the server")) {
            // Handle connection messages
            System.out.println("GameScreen: Connection message: " + message);
            // Don't add connection messages to chat
        } else {
            // Check if this might be a chat message in a different format
            if (message.contains(":") && message.length() < 150) {
                // This might be a simple chat message format like "PlayerName: Message"
                String[] parts = message.split(":", 2);
                if (parts.length == 2) {
                    String playerName = parts[0].trim();
                    String chatMessage = parts[1].trim();
                    if (!playerName.isEmpty() && !chatMessage.isEmpty()) {
                        System.out.println("GameScreen: Processing simple chat message from " + playerName + ": " + chatMessage);
                        addChatMessage(playerName, chatMessage);
                        return;
                    }
                }
            }
            
            // Filter out other long or unwanted messages
            if (message.length() > 150 || 
                message.toLowerCase().contains("initialized") ||
                message.toLowerCase().contains("server") ||
                message.toLowerCase().contains("started")) {
                System.out.println("GameScreen: Filtering out unwanted message: " + message.substring(0, Math.min(50, message.length())) + "...");
                return;
            }
            
            // Log unknown message format for debugging
            System.out.println("GameScreen: Unknown message format: " + message);
            System.out.println("GameScreen: Message length: " + message.length());
            System.out.println("GameScreen: Message contains colon: " + message.contains(":"));
        }
    }
    
    /**
     * Toggles the chat visibility
     */
    private void toggleChat() {
        isChatVisible = !isChatVisible;
        chatTable.setVisible(isChatVisible);
        
        if (isChatVisible) {
            chatInputField.setText("");
            chatStage.setKeyboardFocus(chatInputField);
            
            // Refresh the chat display to ensure all messages are visible
            refreshChatDisplay();
            
            // Ensure the chat input field gets focus and can receive keyboard input
            Gdx.app.postRunnable(() -> {
                chatInputField.setCursorPosition(chatInputField.getText().length());
                chatInputField.selectAll();
            });
        } else {
            // When hiding chat, clear focus to return to game input
            chatStage.setKeyboardFocus(null);
        }
    }
    
    /**
     * Refreshes the chat display to ensure all messages are visible
     */
    private void refreshChatDisplay() {
        if (chatMessages != null && !chatMessages.isEmpty()) {
            StringBuilder chatText = new StringBuilder();
            for (String msg : chatMessages) {
                chatText.append(msg).append("\n");
            }
            
            Gdx.app.postRunnable(() -> {
                try {
                    chatArea.setText(chatText.toString());
                    chatScrollPane.setScrollY(chatScrollPane.getMaxY());
                    chatArea.invalidate();
                    chatScrollPane.invalidate();
                    System.out.println("GameScreen: Chat display refreshed with " + chatMessages.size() + " messages");
                } catch (Exception e) {
                    System.err.println("GameScreen: Error refreshing chat display: " + e.getMessage());
                }
            });
        }
    }
    
    /**
     * Clears the chat to remove all messages
     */
    private void clearChat() {
        if (chatMessages != null) {
            chatMessages.clear();
            addChatMessage("System", "Chat cleared.");
            System.out.println("GameScreen: Chat cleared by user");
        }
    }
    
    /**
     * Tests the server connection by sending a test message
     */
    private void testServerConnection() {
        if (gameOut != null && gameSocket != null && !gameSocket.isClosed()) {
            try {
                String testMessage = "TEST_CONNECTION:" + playerNickname + ":" + currentLobbyId;
                System.out.println("GameScreen: Sending test connection message: " + testMessage);
                gameOut.println(testMessage);
                gameOut.flush();
                addChatMessage("System", "Test message sent to server");
            } catch (Exception e) {
                System.err.println("GameScreen: Error sending test message: " + e.getMessage());
                addChatMessage("System", "Error sending test message: " + e.getMessage());
            }
        } else {
            addChatMessage("System", "Not connected to server");
            // Try to reconnect if not connected
            if (isMultiplayerMode) {
                addChatMessage("System", "Attempting to reconnect...");
                connectToMultiplayerGame();
            }
        }
    }
    
    /**
     * Tests message broadcasting by sending a test chat message
     */
    private void testMessageBroadcast() {
        if (gameOut != null && gameSocket != null && !gameSocket.isClosed()) {
            try {
                String testMessage = "GAME_CHAT:" + playerNickname + ":" + currentLobbyId + ":TEST_BROADCAST_MESSAGE";
                System.out.println("GameScreen: Sending test broadcast message: [" + testMessage + "]");
                gameOut.println(testMessage);
                gameOut.flush();
                addChatMessage("System", "Test broadcast message sent to server");
                
                        // Also send a simple test message
        String simpleTest = "TEST:" + playerNickname + ":Hello from " + playerNickname;
        System.out.println("GameScreen: Sending simple test message: [" + simpleTest + "]");
        gameOut.println(simpleTest);
        gameOut.flush();
        
        // Send a message in the simple format that servers often expect
        String simpleChat = playerNickname + ": Test message from " + playerNickname;
        System.out.println("GameScreen: Sending simple chat format: [" + simpleChat + "]");
        gameOut.println(simpleChat);
        gameOut.flush();
                
            } catch (Exception e) {
                System.err.println("GameScreen: Error sending test broadcast message: " + e.getMessage());
                addChatMessage("System", "Error sending test broadcast: " + e.getMessage());
            }
        } else {
            addChatMessage("System", "Not connected to server");
        }
    }
    
    /**
     * Shows debug information about the current chat state
     */
    private void showChatDebugInfo() {
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append("=== CHAT DEBUG INFO ===\n");
        debugInfo.append("Multiplayer Mode: ").append(isMultiplayerMode).append("\n");
        debugInfo.append("Player Nickname: ").append(playerNickname).append("\n");
        debugInfo.append("Current Lobby ID: ").append(currentLobbyId).append("\n");
        debugInfo.append("Socket Connected: ").append(gameSocket != null && !gameSocket.isClosed()).append("\n");
        debugInfo.append("Chat Messages Count: ").append(chatMessages != null ? chatMessages.size() : 0).append("\n");
        debugInfo.append("Chat Visible: ").append(isChatVisible).append("\n");
        debugInfo.append("Message Thread Alive: ").append(gameMessageThread != null && gameMessageThread.isAlive()).append("\n");
        
        if (chatMessages != null && !chatMessages.isEmpty()) {
            debugInfo.append("Last 5 Messages:\n");
            int start = Math.max(0, chatMessages.size() - 5);
            for (int i = start; i < chatMessages.size(); i++) {
                debugInfo.append("  ").append(i + 1).append(". ").append(chatMessages.get(i)).append("\n");
            }
        }
        
        debugInfo.append("=====================");
        
        System.out.println(debugInfo.toString());
        addChatMessage("System", "Debug info printed to console");
    }
    
    /**
     * Starts a heartbeat thread to keep the connection alive
     */
    private void startHeartbeatThread() {
        Thread heartbeatThread = new Thread(() -> {
            System.out.println("GameScreen: Heartbeat thread started");
            try {
                while (gameSocket != null && !gameSocket.isClosed() && isMultiplayerMode) {
                    try {
                        // Send a ping every 30 seconds
                        Thread.sleep(30000);
                        if (gameOut != null && gameSocket != null && !gameSocket.isClosed()) {
                            gameOut.println("PING");
                            gameOut.flush();
                            System.out.println("GameScreen: Heartbeat ping sent");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("GameScreen: Heartbeat thread interrupted");
                        break;
                    } catch (Exception e) {
                        System.err.println("GameScreen: Error in heartbeat: " + e.getMessage());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("GameScreen: Heartbeat thread error: " + e.getMessage());
            }
            System.out.println("GameScreen: Heartbeat thread ended");
        });
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }
    
    /**
     * Updates the status label to show connection state
     */
    private void updateStatusLabel(String status, Color color) {
        if (statusLabel != null) {
            Gdx.app.postRunnable(() -> {
                try {
                    statusLabel.setText(status);
                    statusLabel.setColor(color);
                    System.out.println("GameScreen: Status updated to: " + status);
                } catch (Exception e) {
                    System.err.println("GameScreen: Error updating status label: " + e.getMessage());
                }
            });
        }
    }
    
    /**
     * Checks if the multiplayer server is reachable
     */
    private boolean isServerReachable() {
        try {
            Socket testSocket = new Socket();
            testSocket.connect(new java.net.InetSocketAddress("localhost", 8080), 3000); // 3 second timeout
            testSocket.close();
            return true;
        } catch (Exception e) {
            System.out.println("GameScreen: Server not reachable: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks the current connection status and updates the UI accordingly
     */
    private void checkConnectionStatus() {
        if (isMultiplayerMode) {
            if (gameSocket == null || gameSocket.isClosed()) {
                updateStatusLabel("Disconnected", Color.RED);
            } else if (gameSocket.isConnected()) {
                updateStatusLabel("Connected", Color.GREEN);
            } else {
                updateStatusLabel("Connecting...", Color.YELLOW);
            }
        }
    }
    
    /**
     * Sends a chat message
     */
    private void sendChatMessage() {
        if (!isMultiplayerMode) return;
        
        String message = chatInputField.getText().trim();
        if (!message.isEmpty()) {
            // Send through game server connection
            if (gameOut != null) {
                String chatMessage = "GAME_CHAT:" + currentLobbyId + ":" + message;
                System.out.println("GameScreen: Sending chat message: [" + chatMessage + "]");
                gameOut.println(chatMessage);
                gameOut.flush();
                
                // Add to local chat
                addChatMessage(playerNickname, message);
                
                // Clear input field
                chatInputField.setText("");
                
                System.out.println("GameScreen: Chat message sent successfully");
            } else {
                addChatMessage("System", "ERROR: Not connected to game server");
            }
        }
    }
    
    /**
     * Adds a message to the chat area
     */
    public void addChatMessage(String sender, String message) {
        // Truncate very long messages to prevent chat overflow
        String truncatedMessage = message;
        if (message.length() > 80) {
            truncatedMessage = message.substring(0, 77) + "...";
            System.out.println("GameScreen: Truncating long message from " + sender);
        }
        
        System.out.println("GameScreen: Adding chat message - " + sender + ": " + truncatedMessage);
        
        chatMessages.add(sender + ": " + truncatedMessage);
        
        // Keep only last 50 messages
        if (chatMessages.size() > 50) {
            chatMessages.remove(0);
        }
        
        // Update chat area
        StringBuilder chatText = new StringBuilder();
        for (String msg : chatMessages) {
            chatText.append(msg).append("\n");
        }
        
        // Ensure we're on the main thread when updating UI
        Gdx.app.postRunnable(() -> {
            try {
                chatArea.setText(chatText.toString());
                
                // Scroll to bottom
                chatScrollPane.setScrollY(chatScrollPane.getMaxY());
                
                // Force a redraw of the chat area
                chatArea.invalidate();
                chatScrollPane.invalidate();
                
                System.out.println("GameScreen: Chat area updated with " + chatMessages.size() + " messages");
            } catch (Exception e) {
                System.err.println("GameScreen: Error updating chat area: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void loadTextures() {
        tileTextures = new HashMap<>();
        Player player = currentGame.currentPlayer;
        player.playerAtlas = new TextureAtlas(Gdx.files.internal("game/character/sprites_Alex.atlas"));
        player.playerAnimations = new Animation[4];

        for (int dir = 0; dir < 4; dir++) {
            Array<TextureRegion> walkFrames = new Array<>();
            for (int frame = 0; frame < 4; frame++) {

                walkFrames.add(player.playerAtlas.findRegion("player_" + (13 - dir) + "_" + frame));
            }
            player.playerAnimations[dir] = new Animation<>(0.15f, walkFrames, Animation.PlayMode.LOOP);
        }
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        tileTextures.put(TileType.GRASS, GameAssetManager.GRASS);
        tileTextures.put(TileType.EMPTY, GameAssetManager.EMPTY);
        tileTextures.put(TileType.LAKE, GameAssetManager.WATER);
        tileTextures.put(TileType.WALL, GameAssetManager.WALL);
        tileTextures.put(TileType.TREE, GameAssetManager.TREE);
        tileTextures.put(TileType.ROBIN, GameAssetManager.ROBIN);
        tileTextures.put(TileType.ABIGEL, GameAssetManager.ABIGEL);
        tileTextures.put(TileType.LEAH, GameAssetManager.LEAH);
        tileTextures.put(TileType.TILLED, GameAssetManager.TILLED);
        tileTextures.put(TileType.SEBASTIAN, GameAssetManager.SEBASTIAN);
        tileTextures.put(TileType.HARVEY, GameAssetManager.HARVEY);
        emptySlotTexture = new Texture("bar.png");

        // Load energy bar textures with fallback
        try {
            backgroundTexture = GameAssetManager.ENERGY_BAR_EMPTY != null ? GameAssetManager.ENERGY_BAR_EMPTY : new Texture(Gdx.files.internal("energy_bar_empty.png"));
            fillTexture = GameAssetManager.GREEN_SQUARE != null ? GameAssetManager.GREEN_SQUARE : new Texture(Gdx.files.internal("green_square.png"));
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to load energy bar textures: " + e.getMessage());
            // Fallback to solid color textures
            backgroundTexture = createSolidColorTexture(32, 128, Color.GRAY);
            fillTexture = createSolidColorTexture(32, 128, Color.GREEN);
        }
        GameAssetManager.load();
        // Update foraging tiles
        for (int y = 0; y <= 160; y++) {
            for (int x = 0; x <= 120; x++) {
                if (x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length) {
                    Tile tile = tileMap[y][x];
                    if (tile.type == TileType.FORAGING) {
                        try {
                            Random rand = new Random();
                            int id = rand.nextInt(21) + 357;
                            tile.id = id;
                        } catch (Exception e) {
                            Gdx.app.error("error", e.getMessage());
                        }
                    }
                    else if (tile.type == TileType.STONE) {
                        try {
                            Random rand = new Random();
                            int id = rand.nextInt(16) + 380;
                            tile.id = id;
                        } catch (Exception e) {
                            Gdx.app.error("error", e.getMessage());
                        }
                    }
                }
            }
        }
        updateToolbarItems();
    }

    private Texture createSolidColorTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private void updateToolbarItems() {
        toolTextures.clear();
        toolbarItems.clear();

        for (Item x : App.getCurrentGame().getCurrentPlayer().getInvetory().getItems()) {
            if (x.getImage() != null) {
                toolTextures.add(x.getImage());
                toolbarItems.add(x);
            }
        }

        while (toolTextures.size() < TOOLBAR_SIZE) {
            toolTextures.add(null);
            toolbarItems.add(null);
        }
    }

    private void setupGame() {
        try {
            Game newGame = new Game();
            Map newMap = new Map(new String[]{"1", "2", "3", "4"});
            newGame.map = newMap;
            App.setCurrentGame(newGame);
            tileMap = App.currentGame.map.tiles;
        } catch (Exception e) {
            e.printStackTrace();
            tileMap = new Tile[160][120];
            for (int y = 0; y < 160; y++) {
                for (int x = 0; x < 120; x++) {
                    tileMap[y][x] = new Tile(new com.StardewValley.model.Point(x, y), TileType.EMPTY);
                }
            }
        }
    }

    private void EnergyCounter(int energy) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        try {
            currentPlayer.setEnergy(new Energy(currentPlayer.getEnergy().getEnergyCap(),currentPlayer.getEnergy().getCurrentEnergy() - energy));
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to decrease energy: " + e.getMessage());
        }
    }
    private int startX() {
        return (int)(camera.position.x - viewport.getWorldWidth() / 2) / TILE_SIZE - 1;
    }

    private int endX() {
        return (int)(camera.position.x + viewport.getWorldWidth() / 2) / TILE_SIZE + 1;
    }

    private int startY() {
        return (int)(camera.position.y - viewport.getWorldHeight() / 2) / TILE_SIZE - 1;
    }

    private int endY() {
        return (int)(camera.position.y + viewport.getWorldHeight() / 2) / TILE_SIZE + 1;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < tileMap[0].length && y < tileMap.length;
    }


    private void handleInput(float delta) {
        // If chat is visible and focused, ignore all game input
        if (isChatVisible && chatInputField.hasKeyboardFocus()) {
            return;
        }
        
        if (isOutOfRealGame)
            return;
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerPosition.x -= speed * delta;
            moveDirection = 3;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerPosition.x += speed * delta;
            moveDirection = 1;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerPosition.y += speed * delta;
            moveDirection = 2;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            controller.nextTurn();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerPosition.y -= speed * delta;
            moveDirection = 0;
            moving = true;
            EnergyCounter(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            try {
                controller.setUpNextDay();
            } catch (Exception e) {
                Gdx.app.log("GameScreen", "Failed to set up next day\n + " + e.getMessage());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                App.currentGame.time.setHour(App.currentGame.time.getHour() + 100000);
            } else {
                App.currentGame.time.setHour(App.currentGame.time.getHour() + 1);
            }
        }
        
        // Chat toggle for multiplayer (only when in multiplayer mode)
        if (isMultiplayerMode && Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            toggleChat();
        }
        
        // Player list toggle for multiplayer (only when in multiplayer mode)
        if (isMultiplayerMode && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            togglePlayerList();
        }
        
        // Close chat with Escape key when chat is visible
        if (isMultiplayerMode && isChatVisible && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            toggleChat();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            InventoryDialog.show(() -> {

            });
        }



        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            if (toolTextures.get(i) == null || toolTextures.get(i).equals(emptySlotTexture)) {
                continue;
            }
            if (i < 9 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 9 && Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 10 && Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            } else if (i == 11 && Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
                selectedToolIndex = i;
                updateToolbarHighlight();
            }
        }

        if (scrollDelta != 0) {
            int direction = scrollDelta > 0 ? 1 : -1;
            int originalIndex = selectedToolIndex;

            do {
                selectedToolIndex = (selectedToolIndex + direction + TOOLBAR_SIZE) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null ||
                    toolTextures.get(selectedToolIndex).equals(emptySlotTexture)) &&
                    selectedToolIndex != originalIndex);

            updateToolbarHighlight();
            scrollDelta = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            int originalIndex = selectedToolIndex;
            do {
                selectedToolIndex = (selectedToolIndex + 1) % TOOLBAR_SIZE;
            } while ((toolTextures.get(selectedToolIndex) == null ||
                    toolTextures.get(selectedToolIndex).equals(emptySlotTexture)) &&
                    selectedToolIndex != originalIndex);
            updateToolbarHighlight();
        }

        if (!moving) stateTime = 0;
    }

    private void updateToolbarHighlight() {
        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Stack stack = toolbarSlots[i];
            if (stack.getChildren().size > 0 && stack.getChild(0) instanceof Container) {
                Container<?> cont = (Container<?>) stack.getChild(0);
                cont.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            }
        }
    }

    private void updateToolbar() {
        updateToolbarItems();
        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Stack stack = toolbarSlots[i];
            Item item = (i < toolbarItems.size()) ? toolbarItems.get(i) : null;
            int count = (item != null) ? item.getAmount() : 0;

            Texture tex = (item != null && item.getImage() != null) ? item.getImage() : emptySlotTexture;
            Image icon = new Image(new TextureRegion(tex));
            icon.setSize(36, 36);

            Container<Image> container = new Container<>(icon);
            container.background(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            container.size(44, 44);

            Label countLabel = new Label(count > 0 ? String.valueOf(count) : "", skin);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setFontScale(0.7f);
            countLabel.setColor(Color.WHITE);

            Table labelTable = new Table();
            labelTable.setFillParent(true);
            labelTable.bottom().right();
            labelTable.add(countLabel).padRight(5).padBottom(5);

            stack.clear();
            stack.add(container);
            stack.add(labelTable);
        }
    }

    private Texture getTextureForTileType(TileType type) {
        Texture tex = tileTextures.get(type);
        return (tex != null) ? tex : tileTextures.get(TileType.EMPTY);
    }

    private void updateState(float delta) {
        stateTime += delta;
        energyBarTexture = energyHelper.updateEnergyBar();
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();
    }
    private void clearScreen() {
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private boolean dialogOpen = false;
    private void handleTouchInteraction() {
        try {
            boolean rightClick = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
            if (isOutOfRealGame || !Gdx.input.justTouched()) return;

            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            int tileX = (int) (touch.x / TILE_SIZE);
            int tileY = (int) (touch.y / TILE_SIZE);
            Tile tile = tileMap[tileY][tileX];
            Gdx.app.log("Touch", String.format("Screen touch at: (%.1f, %.1f)", touch.x, touch.y));
            Gdx.app.log("Touch", String.format("Mapped to tile: (%d, %d)", tileX, tileY));

            Vector2 touchPosition = new Vector2(tileX * TILE_SIZE, tileY * TILE_SIZE);
            float distanceToPlayer = playerPosition.dst(touchPosition);

            if (distanceToPlayer > TILE_SIZE * 3) {
                Gdx.app.log("Interaction", "Too far to interact (distance: " + distanceToPlayer + ")");
                return;
            }

            if (tile.type == TileType.GREENHOUSE && !returnCurrentFarm().getGreenHouses().hasRepeare) {
                DialogUtils.openDialog(
                        skin,
                        dialogStage,
                        "GreenHouse Maker",
                        "Would you give 500 Gold to repair the Greenhouse?\n",
                        "Greenhouse/greenhouse.png",
                        700, 400,
                        (Gdx.graphics.getWidth() - 700) / 2f,
                        Gdx.graphics.getHeight() * 0.7f,
                        new DialogUtils.DialogButton("OK", true, () -> {
                            App.returnCurrentFarm().getGreenHouses().hasRepeare = true;
                            App.getCurrentGame().getCurrentPlayer().addMoney(-500);
                        }),
                        new DialogUtils.DialogButton("Cancel", false, () -> {
                            Gdx.app.log("Dialog", "Greenhouse repair cancelled");
                        })
                );
            }
            else if (isNpcTile(tile.type)) {
                String npcName = tile.type.name().toLowerCase();
                Gdx.app.log("NPC", "Interacting with NPC: " + npcName);
                NPC npc = currentGame.getNpcs().get(0);
                for (NPC test : currentGame.getNpcs()) {
                    if (test.getName().toLowerCase().equals(npcName)) {
                        npc = test;
                        break;
                    }
                }
                npc.showMenu();
            }
            else
                handleToolUse(tileX, tileY);

            // NPC Interaction (2 tiles above)
            if (tileY - 2 >= 0) {
                Tile touchedTile = currentGame.map.tiles[tileY - 2][tileX];
                if (touchedTile != null && isNpcTile(touchedTile.type)) {
                    String npcName = touchedTile.type.name().toLowerCase();
                    Gdx.app.log("NPC", "Interacting with NPC: " + npcName);
                    DialogUtils.openDialog(
                            skin,
                            dialogStage,
                            npcName,
                            controller.TalkToNPC(npcName).getData(),
                            "NPC/"+npcName+".png",
                            700, 400,
                            (Gdx.graphics.getWidth() - 700) / 2f,
                            Gdx.graphics.getHeight() * 0.7f,
                            new DialogUtils.DialogButton("OK", true, () -> {
                            })
                    );
                }
            }

            // Trigger tool animation
            toolAnimTime = 0;
            isToolAnimating = true;

        } catch (Exception e) {
            Gdx.app.error("Interaction", "Error handling touch: " + e.getMessage());
        }
    }

    private void handleToolUse(int tileX, int tileY) {
        try {
            Item selectedTool = toolbarItems.get(selectedToolIndex);
            if (selectedTool != null) {
                if (selectedTool.getParentItemID() != 401) {
                    selectedTool.useTool(tileMap[tileY][tileX]);
                    updateToolbar();
                }
                else{
                    controller.plantPlant(selectedTool, tileMap[tileY][tileX]);
                }
            }

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    updateTools();
                }
            }, 0.1f);

        } catch (Exception e) {
            Gdx.app.error("ToolUse", e.getMessage());
        }
    }
    private void drawGround() {
        Texture grassTexture = tileTextures.get(TileType.EMPTY);
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (isInBounds(x, y)) {
                    batch.draw(grassTexture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    private void drawTiles() {
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (isInBounds(x, y)) {
                    Tile tile = tileMap[y][x];
                    if (tile.type != TileType.EMPTY) {
                        batch.draw(getTextureForTileType(tile.type), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
    }
    private void drawCottages() {
        for (int i = 0; i < 4; i++) {
            int offsetX = App.farmStart[i].x;
            int offsetY = App.farmStart[i].y;

            Cottage cottage = App.currentGame.map.farms[i].getCottage();
            Ground bounds = cottage.getGround();

            int groupX = bounds.startPoint.x + offsetX;
            int groupY = bounds.startPoint.y + offsetY;
            int groupWidth = bounds.endPoint.x - bounds.startPoint.x + 1;
            int groupHeight = bounds.endPoint.y - bounds.startPoint.y + 1;

            Texture cottageTex = cottage.isPlayerNearDoor(playerPosition) ? GameAssetManager.COTTAGEIn : GameAssetManager.COTTAGEOut;
            batch.draw(cottageTex,
                    groupY * TILE_SIZE,
                    groupX * TILE_SIZE,
                    groupWidth * TILE_SIZE,
                    groupHeight * TILE_SIZE);
        }
    }
    private void drawPlants() {
        try {
            for (Plant plant : currentGame.getPlants()) {
                batch.draw((plant.getCurrentStage() != -1 ? plant.getGrowStageImage() :
                                new Texture(plant.imagePath.replace("1", "4"))) ,
                        plant.getPoint().getY() * TILE_SIZE,
                        plant.getPoint().getX() * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE);
            }
        }catch (Exception e) {
            Gdx.app.error("Plants", e.getMessage());
        }
    }
    private void drawGreenhouses() {
        for (int i = 0; i < 4; i++) {
            int offsetX = App.farmStart[i].x;
            int offsetY = App.farmStart[i].y;

            greenHouse greenhouse = App.currentGame.map.farms[i].getGreenHouses();
            Ground bounds = greenhouse.getGround();

            int groupX = bounds.startPoint.x + offsetX;
            int groupY = bounds.startPoint.y + offsetY;
            int groupWidth = bounds.endPoint.x - bounds.startPoint.x + 1;
            int groupHeight = bounds.endPoint.y - bounds.startPoint.y + 1;

            boolean inside = playerPosition.x >= groupX * TILE_SIZE &&
                    playerPosition.x <= (groupX + groupWidth) * TILE_SIZE &&
                    playerPosition.y >= groupY * TILE_SIZE &&
                    playerPosition.y <= (groupY + groupHeight) * TILE_SIZE;

            Texture greenhouseTex = greenhouse.hasRepeare ?  GameAssetManager.GREENHOUSE : GameAssetManager.BROKEN_GREENHOUSE;
            batch.draw(greenhouseTex,
                    groupY * TILE_SIZE,
                    groupX * TILE_SIZE,
                    groupWidth * TILE_SIZE,
                    groupHeight * TILE_SIZE);
        }
    }


    private void drawNPCsAndForaging() {
        for (int y = startY(); y <= endY(); y++) {
            for (int x = startX(); x <= endX(); x++) {
                if (!isInBounds(x, y)) continue;
                Tile tile = tileMap[y][x];

                if (isNpcTile(tile.type)) {
                    batch.draw(GameAssetManager.NPCHOUSE, (x - 2) * TILE_SIZE, (y + 1) * TILE_SIZE, TILE_SIZE * 5, TILE_SIZE * 5);

                    Vector2 npcPos = new Vector2(x * TILE_SIZE, y * TILE_SIZE);
                    if (playerPosition.dst(npcPos) <= TILE_SIZE * 3) {
                        float iconX = x * TILE_SIZE;
                        float iconY = (y + 2) * TILE_SIZE;
                        batch.draw(GameAssetManager.DIALOG, iconX, iconY, TILE_SIZE, TILE_SIZE);
                        currentDialogIconBounds = new Rectangle(iconX, iconY, TILE_SIZE, TILE_SIZE);
                    }
                }

                if (tile.type == TileType.FORAGING) {
                    try {
//                        batch.draw(AllTheItemsInTheGame.allPlants.get(tile.id).image, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } catch (NullPointerException e) {
                        Gdx.app.log("error", e.getMessage());
                    }
                }
                if (tile.type == TileType.STONE) {
                    try {
//                        batch.draw(AllTheItemsInTheGame.allItems.get(tile.id).getImage(), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } catch (NullPointerException e) {
                        Gdx.app.log("error", e.getMessage());
                    }
                }
            }
        }
    }
    private void drawUI() {
        if (energyBarTexture != null && App.currentGame.currentPlayer != null) {
            batch.draw(energyBarTexture,
                    camera.position.x - viewport.getWorldWidth() / 2 + 20,
                    camera.position.y - viewport.getWorldHeight() / 2 + 20,
                    energyBarTexture.getWidth(),
                    energyBarTexture.getHeight());
        }
    }
    private void drawPlayer() {
        TextureRegion currentFrame = currentGame.currentPlayer.playerAnimations[moveDirection].getKeyFrame(stateTime, true);

        if (isFainted) {
            Sprite faintedSprite = new Sprite(currentFrame);
            faintedSprite.setSize(TILE_SIZE, TILE_SIZE * 2);
            faintedSprite.setOriginCenter();
            faintedSprite.setRotation(90);

            faintedSprite.setPosition(playerPosition.x, playerPosition.y);
            faintedSprite.draw(batch);
        } else {
            batch.draw(currentFrame, playerPosition.x, playerPosition.y, TILE_SIZE, TILE_SIZE * 2);
        }
    }

    private void drawTool() {
        Texture toolTex = toolTextures.get(selectedToolIndex);
        if (toolTex == null) return;

        boolean flipX = false;
        float toolX = playerPosition.x;
        float toolY = playerPosition.y;

        float offset = isToolAnimating ? 5f * (float)Math.sin(Math.min(toolAnimTime += Gdx.graphics.getDeltaTime(), toolAnimDuration) / toolAnimDuration * Math.PI) : 0;

        switch (moveDirection) {
            case 0: toolY -= TILE_SIZE * 0.4f - offset; toolX += TILE_SIZE * 0.2f; break;
            case 1: toolX += TILE_SIZE * 0.6f + offset; toolY += TILE_SIZE * 0.3f; break;
            case 2: toolY += TILE_SIZE * 1.1f + offset; toolX += TILE_SIZE * 0.2f; break;
            case 3: flipX = true; toolX -= TILE_SIZE * 0.6f - offset; toolY += TILE_SIZE * 0.3f; break;
        }

        batch.draw(toolTex, toolX, toolY, TILE_SIZE, TILE_SIZE,
                0, 0, toolTex.getWidth(), toolTex.getHeight(), flipX, false);
    }

    private void drawWeather() {
        WeatherRenderer.handleWeather(batch, camera, viewport);
    }

    private void handlePlayerEnergy() {
        Energy energy = App.currentGame.currentPlayer.getEnergy();
        if (energy.getCurrentEnergy() <= 0 && !isFainted) {
            isFainted = true;
            playerRotation = 90f;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Cottage cottage = currentGame.map.farms[currentGame.turn].getCottage();
                    int offsetX = App.farmStart[currentGame.turn].x;
                    int offsetY = App.farmStart[currentGame.turn].y;

                    Ground ground = cottage.getGround();

                    int cottageX = ground.startPoint.x + offsetX;
                    int cottageY = ground.endPoint.y + offsetY;

                    playerPosition.set((cottageY - 2) * TILE_SIZE + TILE_SIZE * 2,
                            (cottageX + 1) * TILE_SIZE + TILE_SIZE);

                    playerRotation = 0f;
                    isFainted = false;
                    energy.setCurrentEnergy(energy.getEnergyCap() / 2);
                }
            }, 2f);
        }

    }


    private void renderShops(float delta) {
        for (Shop shop : App.getCurrentGame().getShops()) {
            shop.update(playerPosition, delta);
            shop.render(batch);
//            if (shop.isMenuOpen())
//            {
//                updateToolbar();
//            }
        }
    }
    @Override
    public void render(float delta) {
        try {
            TextureRegion clockTexture = TimeAndDate.renderClockToTexture();

            if (!isOutOfRealGame || dialogOpen) {
                handleInput(delta);
                handleTouchInteraction();
            }

            updateState(delta);
            clearScreen();

            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            drawGround();
            drawTiles();
            batch.end();

            batch.begin();
            drawGreenhouses();
            drawCottages();
            drawPlants();
            Cottage cottage = App.returnCurrentFarm().getCottage();
            cottage.update(playerPosition, delta);
            cottage.render(batch);

            drawNPCsAndForaging();
            batch.end();
            batch.begin();
            renderShops(delta);
            batch.end();
            if (!isOutOfRealGame) {
                batch.begin();
                drawUI();
                drawPlayer();
                drawTool();
                batch.draw(clockTexture,
                        playerPosition.x + 220,
                        playerPosition.y + 58,
                        TILE_SIZE * 30,
                        15 * TILE_SIZE);
                batch.end();
                drawWeather();
            }

            handlePlayerEnergy();
            mainStage.act(delta);
            mainStage.draw();
            dialogStage.act(delta);
            dialogStage.draw();
            
            // Render chat stage for multiplayer
            if (isMultiplayerMode && chatStage != null) {
                chatStage.act(delta);
                chatStage.draw();
                
                // Check connection status and update UI
                checkConnectionStatus();
                
                // Handle input focus for chat
                if (isChatVisible) {
                    // Ensure chat stage gets input priority when visible
                    if (!chatInputField.hasKeyboardFocus()) {
                        chatStage.setKeyboardFocus(chatInputField);
                    }
                    
                    // When chat is visible, ensure the chat stage handles all input
                    // This prevents game input from interfering with chat input
                    if (Gdx.input.getInputProcessor() != chatStage) {
                        Gdx.input.setInputProcessor(chatStage);
                    }
                } else {
                    // When chat is not visible, restore the game input processor
                    if (Gdx.input.getInputProcessor() == chatStage) {
                        Gdx.input.setInputProcessor(new InputAdapter() {
                            @Override
                            public boolean scrolled(float amountX, float amountY) {
                                scrollDelta += amountY;
                                return true;
                            }
                        });
                    }
                }
            }

        } catch (Exception e) {
            Gdx.app.log("GameScreen", "Error in render: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        if (mainStage != null) mainStage.getViewport().update(width, height, true);
        if (dialogStage != null) dialogStage.getViewport().update(width, height, true);
        
        // Update chat stage viewport if it exists
        if (chatStage != null) {
            chatStage.getViewport().update(width, height, true);
            // Reposition chat table for new screen size
            if (chatTable != null) {
                chatTable.setPosition(width - 320, height - 250);
            }
        }
        
        // Reinitialize energy bar Pixmap on resize
        if (energyBarPixmap != null) {
            energyBarPixmap.dispose();
        }
        energyHelper.initEnergyBar();
    }

    @Override
    public void show() {
        mainStage = new Stage(new ScreenViewport(), batch);
        dialogStage = new Stage(new ScreenViewport());
        
        // Create a custom input processor that handles both game and chat input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                scrollDelta += amountY;
                return true;
            }
            
            @Override
            public boolean keyDown(int keycode) {
                // If chat is visible and focused, let the chat stage handle all input
                if (isChatVisible && chatInputField.hasKeyboardFocus()) {
                    return false; // Let the chat stage handle it
                }
                return false; // Let the game handle it
            }
        });
        
        // Initialize chat system if in multiplayer mode
        if (isMultiplayerMode) {
            // Ensure chat messages list is initialized
            if (chatMessages == null) {
                chatMessages = new ArrayList<>();
            }
            
            // Add a welcome message
            addChatMessage("System", "Multiplayer mode active. Press C for chat.");
        }
        
        updateTools();
    }
    public static void updateTools(){
        Table toolbar = new Table();
        toolbar.bottom().center().padTop(800);
        toolbar.setFillParent(true);

        for (int i = 0; i < TOOLBAR_SIZE; i++) {
            Item item = (i < toolbarItems.size()) ? toolbarItems.get(i) : null;
            int count = (item != null) ? item.getAmount() : 0;

            Texture tex = (item != null && item.getImage() != null) ? item.getImage() : emptySlotTexture;
            Image icon = new Image(new TextureRegion(tex));
            icon.setSize(36, 36);

            Container<Image> container = new Container<>(icon);
            container.background(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
            container.setColor(i == selectedToolIndex ? Color.GOLD : Color.DARK_GRAY);
            container.size(44, 44);

            Label countLabel = new Label(count > 0 ? String.valueOf(count) : "", skin);
            countLabel.setAlignment(Align.bottomRight);
            countLabel.setFontScale(0.7f);
            countLabel.setColor(Color.WHITE);

            Table labelTable = new Table();
            labelTable.setFillParent(true);
            labelTable.bottom().right();
            labelTable.add(countLabel).padRight(5).padBottom(5);

            Stack stack = new Stack();
            stack.add(container);
            stack.add(labelTable);
            toolbarSlots[i] = stack;

            toolbar.add(stack).pad(5);
        }

        mainStage.addActor(toolbar);
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        // Clean up multiplayer resources
        if (isMultiplayerMode) {
            try {
                if (gameOut != null) gameOut.close();
                if (gameIn != null) gameIn.close();
                if (gameSocket != null) gameSocket.close();
                if (gameMessageThread != null && gameMessageThread.isAlive()) {
                    gameMessageThread.interrupt();
                }
                if (chatStage != null) chatStage.dispose();
            } catch (IOException e) {
                System.err.println("Error cleaning up multiplayer resources: " + e.getMessage());
            }
        }
        
        batch.dispose();
        for (Entry<TileType, Texture> entry : tileTextures.entrySet()) entry.getValue().dispose();
        for (Texture tex : toolTextures) if (tex != null) tex.dispose();
        currentGame.currentPlayer.playerAtlas.dispose();
        emptySlotTexture.dispose();
        if (mainStage != null) mainStage.dispose();
        if (dialogStage != null) dialogStage.dispose();
        skin.dispose();
        if (energyBarTexture != null) energyBarTexture.dispose();
        if (energyBarPixmap != null) energyBarPixmap.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (fillTexture != null) fillTexture.dispose();
    }
}