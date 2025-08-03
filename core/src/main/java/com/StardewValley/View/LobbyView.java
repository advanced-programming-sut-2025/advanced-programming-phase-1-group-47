package com.StardewValley.View;

import com.StardewValley.controllers.LobbyController;
import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LobbyView implements Screen, Disposable {
    private Stage stage;
    private LobbyController controller;
    private Skin skin;
    private SpriteBatch batch;
    private BitmapFont font;
    
    // UI Components
    private Table mainTable;
    private Table serverListTable;
    private Table connectionTable;
    private Table chatTable;
    
    // Connection UI
    private TextField serverAddressField;
    private TextField serverPortField;
    private TextField playerNameField;
    private TextButton connectButton;
    private TextButton createServerButton;
    private TextButton refreshButton;
    private TextButton backButton;
    
    // Server List UI
    private ScrollPane serverListScrollPane;
    private Table serverListContent;
    private Label statusLabel;
    
    // Chat UI
    private TextArea chatArea;
    private TextField chatInputField;
    private TextButton sendChatButton;
    private ScrollPane chatScrollPane;
    
    // Player List UI
    private Table playerListTable;
    private ScrollPane playerListScrollPane;
    
    public LobbyView(LobbyController controller) {
        this.controller = controller;
        this.controller.setView(this);
        
        stage = new Stage(new ScreenViewport());
        skin = GameAssetManager.getGameAssetManager().getSkin();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        
        createUI();
        setupEventHandlers();
    }
    
    private void createUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        
        // Title
        Label titleLabel = new Label("Multiplayer Lobby", skin, "title");
        titleLabel.setAlignment(Align.center);
        mainTable.add(titleLabel).colspan(3).expandX().fillX().padBottom(20).row();
        
        // Main content area
        Table contentTable = new Table();
        
        // Left side - Connection and Server List
        Table leftPanel = new Table();
        leftPanel.pad(10);
        
        // Connection section
        connectionTable = new Table();
        connectionTable.pad(10);
        
        Label connectionLabel = new Label("Connect to Server", skin, "subtitle");
        connectionTable.add(connectionLabel).colspan(2).expandX().fillX().padBottom(10).row();
        
        connectionTable.add(new Label("Server Address:", skin)).left().padRight(10);
        serverAddressField = new TextField("localhost", skin);
        connectionTable.add(serverAddressField).expandX().fillX().padBottom(5).row();
        
        connectionTable.add(new Label("Port:", skin)).left().padRight(10);
        serverPortField = new TextField("8080", skin);
        connectionTable.add(serverPortField).expandX().fillX().padBottom(5).row();
        
        connectionTable.add(new Label("Player Name:", skin)).left().padRight(10);
        playerNameField = new TextField("Player", skin);
        connectionTable.add(playerNameField).expandX().fillX().padBottom(10).row();
        
        Table buttonTable = new Table();
        connectButton = new TextButton("Connect", skin);
        createServerButton = new TextButton("Create Server", skin);
        refreshButton = new TextButton("Refresh", skin);
        
        buttonTable.add(connectButton).padRight(10);
        buttonTable.add(createServerButton).padRight(10);
        buttonTable.add(refreshButton);
        
        connectionTable.add(buttonTable).colspan(2).expandX().fillX().row();
        
        // Status label
        statusLabel = new Label("Ready to connect", skin);
        statusLabel.setColor(Color.GREEN);
        connectionTable.add(statusLabel).colspan(2).expandX().fillX().padTop(10).row();
        
        leftPanel.add(connectionTable).expandX().fillX().row();
        
        // Server list section
        Table serverListSection = new Table();
        serverListSection.pad(10);
        
        Label serverListLabel = new Label("Available Servers", skin, "subtitle");
        serverListSection.add(serverListLabel).expandX().fillX().padBottom(10).row();
        
        serverListContent = new Table();
        serverListScrollPane = new ScrollPane(serverListContent, skin);
        serverListScrollPane.setFadeScrollBars(false);
        serverListSection.add(serverListScrollPane).expand().fill().row();
        
        leftPanel.add(serverListSection).expand().fill().padTop(10).row();
        
        // Right side - Chat and Player List
        Table rightPanel = new Table();
        rightPanel.pad(10);
        
        // Chat section
        chatTable = new Table();
        chatTable.pad(10);
        
        Label chatLabel = new Label("Chat", skin, "subtitle");
        chatTable.add(chatLabel).expandX().fillX().padBottom(10).row();
        
        chatArea = new TextArea("", skin);
        chatArea.setDisabled(true);
        chatScrollPane = new ScrollPane(chatArea, skin);
        chatScrollPane.setFadeScrollBars(false);
        chatTable.add(chatScrollPane).expand().fill().padBottom(5).row();
        
        Table chatInputTable = new Table();
        chatInputField = new TextField("", skin);
        sendChatButton = new TextButton("Send", skin);
        
        chatInputTable.add(chatInputField).expandX().fillX().padRight(5);
        chatInputTable.add(sendChatButton).width(80);
        
        chatTable.add(chatInputTable).expandX().fillX().row();
        
        rightPanel.add(chatTable).expand().fill().row();
        
        // Player list section
        Table playerListSection = new Table();
        playerListSection.pad(10);
        
        Label playerListLabel = new Label("Connected Players", skin, "subtitle");
        playerListSection.add(playerListLabel).expandX().fillX().padBottom(10).row();
        
        playerListTable = new Table();
        playerListScrollPane = new ScrollPane(playerListTable, skin);
        playerListScrollPane.setFadeScrollBars(false);
        playerListSection.add(playerListScrollPane).expand().fill().row();
        
        rightPanel.add(playerListSection).expand().fill().padTop(10).row();
        
        // Add panels to content
        contentTable.add(leftPanel).width(400).expandY().fillY().padRight(10);
        contentTable.add(rightPanel).expand().fill().padLeft(10);
        
        mainTable.add(contentTable).expand().fill().row();
        
        // Bottom buttons
        Table bottomTable = new Table();
        backButton = new TextButton("Back to Main Menu", skin);
        bottomTable.add(backButton).padRight(10);
        
        mainTable.add(bottomTable).expandX().fillX().padTop(20);
        
        stage.addActor(mainTable);
        
        // Set input processor
        Gdx.input.setInputProcessor(stage);
        
        // Initialize server list
        refreshServerList();
    }
    
    private void setupEventHandlers() {
        connectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.connectToServer(
                    serverAddressField.getText(),
                    Integer.parseInt(serverPortField.getText()),
                    playerNameField.getText()
                );
            }
        });
        
        createServerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.createServer(Integer.parseInt(serverPortField.getText()));
            }
        });
        
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.refreshServerList();
            }
        });
        
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goBackToMainMenu();
            }
        });
        
        sendChatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String message = chatInputField.getText().trim();
                if (!message.isEmpty()) {
                    controller.sendChatMessage(message);
                    chatInputField.setText("");
                }
            }
        });
        
        // Enter key for chat
        chatInputField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                    String message = chatInputField.getText().trim();
                    if (!message.isEmpty()) {
                        controller.sendChatMessage(message);
                        chatInputField.setText("");
                    }
                }
            }
        });
    }
    
    public void updateStatus(String status, Color color) {
        statusLabel.setText(status);
        statusLabel.setColor(color);
    }
    
    public void addChatMessage(String message) {
        String currentText = chatArea.getText();
        String newText = currentText + message + "\n";
        chatArea.setText(newText);
        
        // Scroll to bottom
        chatScrollPane.setScrollY(chatScrollPane.getMaxY());
    }
    
    public void refreshServerList() {
        serverListContent.clear();
        
        // Add some sample servers (in real implementation, this would come from network discovery)
        addServerToList("Local Server", "localhost:8080", "2/4 players");
        addServerToList("Test Server", "192.168.1.100:8080", "1/4 players");
        
        // Add refresh button
        TextButton refreshListButton = new TextButton("Refresh List", skin);
        refreshListButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.refreshServerList();
            }
        });
        serverListContent.add(refreshListButton).expandX().fillX().padTop(10).row();
    }
    
    private void addServerToList(String name, String address, String players) {
        Table serverRow = new Table();
        serverRow.pad(5);
        
        Label nameLabel = new Label(name, skin);
        Label addressLabel = new Label(address, skin);
        Label playersLabel = new Label(players, skin);
        
        TextButton joinButton = new TextButton("Join", skin);
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] parts = address.split(":");
                if (parts.length == 2) {
                    controller.connectToServer(parts[0], Integer.parseInt(parts[1]), playerNameField.getText());
                }
            }
        });
        
        serverRow.add(nameLabel).expandX().fillX().padRight(10);
        serverRow.add(addressLabel).expandX().fillX().padRight(10);
        serverRow.add(playersLabel).expandX().fillX().padRight(10);
        serverRow.add(joinButton).width(60);
        
        serverListContent.add(serverRow).expandX().fillX().row();
    }
    
    public void updatePlayerList(String[] players) {
        playerListTable.clear();
        
        for (String player : players) {
            Label playerLabel = new Label("• " + player, skin);
            playerListTable.add(playerLabel).left().pad(2).row();
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
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
} 