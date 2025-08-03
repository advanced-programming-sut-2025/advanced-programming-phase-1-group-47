package com.StardewValley.View;

import java.util.concurrent.ConcurrentHashMap;

import com.StardewValley.Main;
import com.StardewValley.controllers.LobbyController;
import com.StardewValley.model.Game;
import com.StardewValley.model.GameAssetManager;
import com.StardewValley.model.Player;
import com.StardewValley.network.GameAction;
import com.StardewValley.network.NetworkManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MultiplayerGameScreen implements Screen, Disposable {
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;
    
    // Multiplayer components
    private NetworkManager networkManager;
    private Player localPlayer;
    private LobbyController lobbyController;
    private MultiplayerChatView chatView;
    
    // Game state
    private Game game;
    private boolean isConnected = false;
    
    // UI Components
    private Table mainTable;
    private Table gameInfoTable;
    private Table playerListTable;
    private Table controlsTable;
    
    // Game info UI
    private Label statusLabel;
    private Label playerCountLabel;
    private Label serverInfoLabel;
    
    // Player list UI
    private ScrollPane playerListScrollPane;
    private Table playerListContent;
    
    // Controls UI
    private TextButton disconnectButton;
    private TextButton toggleChatButton;
    private TextButton backToLobbyButton;
    
    // Chat state
    private boolean chatVisible = false;
    
    public MultiplayerGameScreen(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
        this.networkManager = lobbyController.getNetworkManager();
        this.localPlayer = lobbyController.getLocalPlayer();
        this.isConnected = lobbyController.isConnected();
        
        stage = new Stage(new ScreenViewport());
        skin = GameAssetManager.getGameAssetManager().getSkin();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        
        // Initialize game
        this.game = new Game();
        
        createUI();
        setupEventHandlers();
        
        // Set up chat view
        chatView = new MultiplayerChatView(100, 100, 400, 300);
        chatView.setMessageListener(message -> {
            if (isConnected) {
                networkManager.sendChatMessage(message);
            }
        });
        
        // Set up network listener
        networkManager.setMessageListener(new NetworkManager.NetworkMessageListener() {
            @Override
            public void onPlayerJoined(Player player) {
                Gdx.app.postRunnable(() -> {
                    updatePlayerList();
                    updateStatus("Player " + player.getNickname() + " joined", Color.GREEN);
                });
            }
            
            @Override
            public void onPlayerLeft(String playerId) {
                Gdx.app.postRunnable(() -> {
                    updatePlayerList();
                    updateStatus("A player left", Color.ORANGE);
                });
            }
            
            @Override
            public void onGameStateUpdate(Game game) {
                Gdx.app.postRunnable(() -> {
                    MultiplayerGameScreen.this.game = game;
                    updateGameInfo();
                });
            }
            
            @Override
            public void onChatMessage(String playerId, String message) {
                Gdx.app.postRunnable(() -> {
                    String playerName = "Unknown";
                    ConcurrentHashMap<String, Player> players = networkManager.getConnectedPlayers();
                    for (Player player : players.values()) {
                        if (String.valueOf(player.getId()).equals(playerId)) {
                            playerName = player.getNickname();
                            break;
                        }
                    }
                    chatView.addPlayerMessage(playerName, message);
                });
            }
            
            @Override
            public void onPlayerPositionUpdate(String playerId, float x, float y) {
                // Handle position updates
                Gdx.app.postRunnable(() -> {
                    // Update other players' positions in the game
                });
            }
            
            @Override
            public void onError(String error) {
                Gdx.app.postRunnable(() -> {
                    updateStatus("Error: " + error, Color.RED);
                });
            }
        });
        
        // Initial updates
        updatePlayerList();
        updateGameInfo();
        updateStatus("Connected to multiplayer game", Color.GREEN);
    }
    
    private void createUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(10);
        
        // Title
        Label titleLabel = new Label("Multiplayer Game", skin, "title");
        titleLabel.setAlignment(Align.center);
        mainTable.add(titleLabel).colspan(2).expandX().fillX().padBottom(10).row();
        
        // Main content area
        Table contentTable = new Table();
        
        // Left side - Game info and controls
        Table leftPanel = new Table();
        leftPanel.pad(5);
        
        // Game info section
        gameInfoTable = new Table();
        gameInfoTable.pad(5);
        
        Label gameInfoLabel = new Label("Game Information", skin, "subtitle");
        gameInfoTable.add(gameInfoLabel).expandX().fillX().padBottom(5).row();
        
        statusLabel = new Label("Status: Connected", skin);
        gameInfoTable.add(statusLabel).left().padBottom(2).row();
        
        playerCountLabel = new Label("Players: 1", skin);
        gameInfoTable.add(playerCountLabel).left().padBottom(2).row();
        
        serverInfoLabel = new Label("Server: localhost:8080", skin);
        gameInfoTable.add(serverInfoLabel).left().padBottom(5).row();
        
        leftPanel.add(gameInfoTable).expandX().fillX().row();
        
        // Controls section
        controlsTable = new Table();
        controlsTable.pad(5);
        
        Label controlsLabel = new Label("Controls", skin, "subtitle");
        controlsTable.add(controlsLabel).expandX().fillX().padBottom(5).row();
        
        disconnectButton = new TextButton("Disconnect", skin);
        toggleChatButton = new TextButton("Toggle Chat", skin);
        backToLobbyButton = new TextButton("Back to Lobby", skin);
        
        controlsTable.add(disconnectButton).expandX().fillX().padBottom(2).row();
        controlsTable.add(toggleChatButton).expandX().fillX().padBottom(2).row();
        controlsTable.add(backToLobbyButton).expandX().fillX().row();
        
        leftPanel.add(controlsTable).expandX().fillX().padTop(10).row();
        
        // Right side - Player list
        Table rightPanel = new Table();
        rightPanel.pad(5);
        
        Label playerListLabel = new Label("Connected Players", skin, "subtitle");
        rightPanel.add(playerListLabel).expandX().fillX().padBottom(5).row();
        
        playerListContent = new Table();
        playerListScrollPane = new ScrollPane(playerListContent, skin);
        playerListScrollPane.setFadeScrollBars(false);
        rightPanel.add(playerListScrollPane).expand().fill().row();
        
        // Add panels to content
        contentTable.add(leftPanel).width(300).expandY().fillY().padRight(10);
        contentTable.add(rightPanel).width(250).expandY().fillY();
        
        mainTable.add(contentTable).expand().fill().row();
        
        // Game area placeholder
        Table gameAreaTable = new Table();
        gameAreaTable.pad(10);
        
        Label gameAreaLabel = new Label("Game Area - Multiplayer functionality integrated", skin);
        gameAreaLabel.setAlignment(Align.center);
        gameAreaTable.add(gameAreaLabel).expand().fill();
        
        mainTable.add(gameAreaTable).expand().fill().padTop(10);
        
        stage.addActor(mainTable);
        
        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }
    
    private void setupEventHandlers() {
        disconnectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disconnectFromGame();
            }
        });
        
        toggleChatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleChat();
            }
        });
        
        backToLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBackToLobby();
            }
        });
    }
    
    private void updateStatus(String status, Color color) {
        statusLabel.setText("Status: " + status);
        statusLabel.setColor(color);
    }
    
    private void updatePlayerList() {
        playerListContent.clear();
        
        // Add local player
        Label localPlayerLabel = new Label("• " + localPlayer.getNickname() + " (You)", skin);
        localPlayerLabel.setColor(Color.GREEN);
        playerListContent.add(localPlayerLabel).left().pad(2).row();
        
        // Add other players
        ConcurrentHashMap<String, Player> players = networkManager.getConnectedPlayers();
        for (Player player : players.values()) {
            if (!String.valueOf(player.getId()).equals(String.valueOf(localPlayer.getId()))) {
                Label playerLabel = new Label("• " + player.getNickname(), skin);
                playerListContent.add(playerLabel).left().pad(2).row();
            }
        }
        
        // Update player count
        int totalPlayers = players.size() + 1;
        playerCountLabel.setText("Players: " + totalPlayers);
    }
    
    private void updateGameInfo() {
        if (game != null) {
            // Update game-specific information
            // This would show game state, time, weather, etc.
        }
    }
    
    private void disconnectFromGame() {
        if (isConnected) {
            networkManager.sendPlayerLeave();
            networkManager.disconnect();
            isConnected = false;
            updateStatus("Disconnected", Color.RED);
        }
    }
    
    private void toggleChat() {
        chatVisible = !chatVisible;
        chatView.setVisible(chatVisible);
        toggleChatButton.setText(chatVisible ? "Hide Chat" : "Show Chat");
    }
    
    private void goBackToLobby() {
        if (isConnected) {
            disconnectFromGame();
        }
        
        // Return to lobby
        Main.getMain().setScreen(new LobbyView(lobbyController));
    }
    
    public void sendGameAction(GameAction action) {
        if (isConnected) {
            networkManager.sendGameAction(action);
        }
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
        
        // Render chat if visible
        if (chatVisible) {
            chatView.render(batch);
        }
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        if (chatView != null) {
            chatView.resize(width, height);
        }
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (font != null) {
            font.dispose();
        }
        if (chatView != null) {
            chatView.dispose();
        }
    }
} 