package com.StardewValley.View;

import com.StardewValley.Main;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Player;
import com.StardewValley.model.Point;
import com.StardewValley.model.Tile;
import com.StardewValley.model.enums.TileType;
import com.StardewValley.model.things.Item;
import com.StardewValley.View.Helpers.EnergyHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiplayerGameScreen implements Screen {

    // Singleplayer game components (inherited from GameScreen)
    private int scrollDelta = 0;
    public static final int TILE_SIZE = 30;
    private Tile[][] tileMap;
    private Vector2 playerPosition;
    private float speed = 350f;
    private float stateTime;
    private int moveDirection = 2;
    private Rectangle currentDialogIconBounds;
    private HashMap<TileType, Texture> tileTextures;
    
    // UI Components
    public static Stage mainStage, dialogStage;
    private static Skin skin;
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
    
    // Game state
    private boolean isFainted = false;
    private float playerRotation = 0f;
    private Texture energyBarTexture;
    private Pixmap energyBarPixmap;
    private Texture backgroundTexture;
    private Texture fillTexture;
    public static boolean isOutOfRealGame = false;
    private boolean dialogOpen = false;
    
    // Multiplayer components (simplified - only chat)
    private Socket gameSocket;
    private PrintWriter gameOut;
    private BufferedReader gameIn;
    private Thread gameMessageThread;
    private String playerNickname;
    private String currentLobbyId;
    
    // Chat system
    private Stage chatStage;
    private Table chatTable;
    private TextArea chatArea;
    private TextField chatInputField;
    private ScrollPane chatScrollPane;
    private boolean isChatVisible = false;
    private List<String> chatMessages;
    
    // Camera and rendering
    public static OrthographicCamera camera;
    public static FitViewport viewport;
    public static SpriteBatch batch;
    
    public MultiplayerGameScreen(String playerNickname, String lobbyId) {
        this.playerNickname = playerNickname;
        this.currentLobbyId = lobbyId;
        this.chatMessages = new ArrayList<>();
        
        // Initialize game components
        initializeGameComponents();
        initializeChatSystem();
        
        // Connect to network
        connectToMultiplayerGame();
    }
    
    private void initializeGameComponents() {
        // Initialize camera and viewport (same as GameScreen)
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camera);
        batch = new SpriteBatch();
        skin = GameAssetManager.getGameAssetManager().getSkin();
        
        // Initialize stages
        mainStage = new Stage(viewport);
        dialogStage = new Stage(new ScreenViewport());
        chatStage = new Stage(new ScreenViewport());
        
        // Set input processor
        Gdx.input.setInputProcessor(mainStage);
        
        // Initialize other game components (you'll need to copy relevant methods from GameScreen)
        loadTextures();
        setupGame();
        setupToolbar();
    }
    
    private void initializeChatSystem() {
        chatTable = new Table();
        chatTable.setFillParent(true);
        chatTable.pad(20);
        
        // Chat area
        chatArea = new TextArea("", skin);
        chatArea.setDisabled(true);
        chatArea.setPrefRows(10);
        chatScrollPane = new ScrollPane(chatArea, skin);
        
        // Chat input
        chatInputField = new TextField("", skin);
        chatInputField.setMessageText("Type a message...");
        
        // Send button
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendChatMessage();
            }
        });
        
        // Layout chat components
        Table inputRow = new Table();
        inputRow.add(chatInputField).expandX().fillX();
        inputRow.add(sendButton).width(80);
        
        chatTable.add(chatScrollPane).expand().fill().row();
        chatTable.add(inputRow).expandX().fillX().padTop(5);
        
        // Initially hide chat
        chatTable.setVisible(false);
        chatStage.addActor(chatTable);
    }
    
    private void connectToMultiplayerGame() {
        try {
            // Connect to the server using the same connection as the lobby
            gameSocket = new Socket("localhost", 8080);
            gameOut = new PrintWriter(gameSocket.getOutputStream(), true);
            gameIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            
            // Send game client identification
            gameOut.println("GAME_CLIENT:" + playerNickname + ":" + currentLobbyId);
            
            // Start message listening thread
            gameMessageThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = gameIn.readLine()) != null) {
                        handleGameMessage(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from server: " + e.getMessage());
                }
            });
            gameMessageThread.start();
            
            // Set initial position
            playerPosition = new Vector2(400, 200);
            
            addChatMessage("System", "Connected to multiplayer game!");
            
        } catch (Exception e) {
            addChatMessage("System", "Failed to connect to multiplayer game: " + e.getMessage());
            System.err.println("Error connecting to game: " + e.getMessage());
        }
    }
    
    private void handleGameMessage(String message) {
        // Handle different types of game messages
        if (message.startsWith("GAME_CHAT:")) {
            // Handle in-game chat messages
            String[] parts = message.split(":", 3);
            if (parts.length == 3) {
                String playerId = parts[1];
                String chatMessage = parts[2];
                addChatMessage(playerId, chatMessage);
            }
        } else if (message.startsWith("GAME_STATE:IN_GAME:")) {
            // Game has started, we're now in-game
            addChatMessage("System", "Game has started! All players are now in-game.");
        } else if (message.startsWith("START_GAME:")) {
            // Game start confirmation
            addChatMessage("System", "Game is starting...");
        }
    }
    
    private void toggleChat() {
        isChatVisible = !isChatVisible;
        chatTable.setVisible(isChatVisible);
        
        if (isChatVisible) {
            // Switch input processor to chat stage
            Gdx.input.setInputProcessor(chatStage);
            chatInputField.setDisabled(false);
        } else {
            // Switch back to main stage
            Gdx.input.setInputProcessor(mainStage);
            chatInputField.setDisabled(true);
        }
    }
    
    private void sendChatMessage() {
        String message = chatInputField.getText().trim();
        if (!message.isEmpty()) {
            // Send message through simple text protocol
            if (gameOut != null) {
                gameOut.println("GAME_CHAT:" + currentLobbyId + ":" + message);
            }
            
            // Add to local chat
            addChatMessage(playerNickname, message);
            
            // Clear input field
            chatInputField.setText("");
        }
    }
    
    private void addChatMessage(String sender, String message) {
        String chatText = chatArea.getText();
        if (!chatText.isEmpty()) {
            chatText += "\n";
        }
        chatText += sender + ": " + message;
        chatArea.setText(chatText);
        
        // Scroll to bottom
        chatScrollPane.setScrollY(chatScrollPane.getMaxY());
        
        // Store message
        chatMessages.add(sender + ": " + message);
        
        // Limit chat history
        if (chatMessages.size() > 100) {
            chatMessages.remove(0);
        }
    }
    
    // Game input handling
    private void handleInput(float delta) {
        // Handle movement input
        boolean isMoving = false;
        Vector2 newPosition = new Vector2(playerPosition);
        
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newPosition.y += speed * delta;
            moveDirection = 0; // Up
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newPosition.y -= speed * delta;
            moveDirection = 2; // Down
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newPosition.x -= speed * delta;
            moveDirection = 3; // Left
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newPosition.x += speed * delta;
            moveDirection = 1; // Right
            isMoving = true;
        }
        
        // Update position if valid
        if (isValidPosition(newPosition)) {
            playerPosition.set(newPosition);
        }
        
        // Handle chat toggle with T key
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            toggleChat();
        }
        
        // Handle chat toggle with Escape key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (isChatVisible) {
                toggleChat();
            }
        }
    }
    
    private boolean isValidPosition(Vector2 position) {
        // Add boundary checking logic here
        return position.x >= 0 && position.x < 800 && position.y >= 0 && position.y < 400;
    }
    
    // Rendering methods
    private void renderGame() {
        // Clear screen
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        // Draw game world
        batch.begin();
        drawGround();
        drawTiles();
        drawCottages();
        drawPlants();
        drawGreenhouses();
        drawNPCsAndForaging();
        
        // Draw local player
        drawPlayer();
        drawTool();
        
        batch.end();
        
        // Draw UI
        drawUI();
        drawWeather();
    }
    
    // You'll need to implement these methods by copying from GameScreen
    private void loadTextures() {
        // Copy texture loading logic from GameScreen
    }
    
    private void setupGame() {
        // Copy game setup logic from GameScreen
    }
    
    private void setupToolbar() {
        // Copy toolbar setup logic from GameScreen
    }
    
    private void drawGround() {
        // Copy ground drawing logic from GameScreen
    }
    
    private void drawTiles() {
        // Copy tile drawing logic from GameScreen
    }
    
    private void drawCottages() {
        // Copy cottage drawing logic from GameScreen
    }
    
    private void drawPlants() {
        // Copy plant drawing logic from GameScreen
    }
    
    private void drawGreenhouses() {
        // Copy greenhouse drawing logic from GameScreen
    }
    
    private void drawNPCsAndForaging() {
        // Copy NPC drawing logic from GameScreen
    }
    
    private void drawPlayer() {
        // Copy player drawing logic from GameScreen
    }
    
    private void drawTool() {
        // Copy tool drawing logic from GameScreen
    }
    
    private void drawUI() {
        // Copy UI drawing logic from GameScreen
    }
    
    private void drawWeather() {
        // Copy weather drawing logic from GameScreen
    }
    
    // Screen interface methods
    @Override
    public void render(float delta) {
        // Handle input
        handleInput(delta);
        
        // Update game state
        updateState(delta);
        
        // Render game
        renderGame();
        
        // Render stages
        mainStage.act(delta);
        mainStage.draw();
        
        if (isChatVisible) {
            chatStage.act(delta);
            chatStage.draw();
        }
        
        if (dialogOpen) {
            dialogStage.act(delta);
            dialogStage.draw();
        }
    }
    
    private void updateState(float delta) {
        stateTime += delta;
        
        // Update toolbar animation
        if (isToolAnimating) {
            toolAnimTime += delta;
            if (toolAnimTime >= toolAnimDuration) {
                isToolAnimating = false;
                toolAnimTime = 0f;
            }
        }
        
        // Update other game state as needed
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        chatStage.getViewport().update(width, height);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(mainStage);
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void dispose() {
        // Disconnect from game server
        try {
            if (gameSocket != null && !gameSocket.isClosed()) {
                gameSocket.close();
            }
            if (gameMessageThread != null && gameMessageThread.isAlive()) {
                gameMessageThread.interrupt();
            }
        } catch (IOException e) {
            System.err.println("Error closing game connection: " + e.getMessage());
        }
        
        if (batch != null) {
            batch.dispose();
        }
        
        if (mainStage != null) {
            mainStage.dispose();
        }
        
        if (chatStage != null) {
            chatStage.dispose();
        }
        
        if (dialogStage != null) {
            dialogStage.dispose();
        }
        
        // Dispose other game resources
    }
}
