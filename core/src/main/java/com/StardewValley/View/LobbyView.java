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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
    private Table connectionPanel;
    private Table chatPanel;
    private Table serverPanel;
    
    // Connection UI
    private TextField serverAddressField;
    private TextField serverPortField;
    private TextField playerNameField;
    private TextButton connectButton;
    private TextButton createServerButton;
    private Label statusLabel;
    
    // Chat UI
    private TextArea chatArea;
    private TextField chatInputField;
    private TextButton sendChatButton;
    private ScrollPane chatScrollPane;
    
    // Server List UI
    private Table serverListTable;
    private ScrollPane serverListScrollPane;
    
    // Player List UI
    private Table playerListTable;
    private ScrollPane playerListScrollPane;
    private Table playerListPanel;
    
    // Navigation
    private TextButton backButton;
    
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
        mainTable.pad(15);
        mainTable.setBackground(skin.newDrawable("white", new Color(0.15f, 0.15f, 0.2f, 0.95f)));
        
        // Header
        createHeader();
        
        // Main content area
        Table contentTable = new Table();
        contentTable.pad(10);
        
        // Left side - Connection and Server List
        Table leftSide = new Table();
        leftSide.pad(5);
        
        // Connection Panel
        createConnectionPanel();
        leftSide.add(connectionPanel).expandX().fillX().height(140).row();
        
        // Server List Panel
        createServerPanel();
        leftSide.add(serverPanel).expandX().fillX().height(200).padTop(10).row();
        
        // Right side - Chat and Players
        Table rightSide = new Table();
        rightSide.pad(5);
        
        // Chat Panel
        createChatPanel();
        rightSide.add(chatPanel).expandX().fillX().height(280).row();
        
        // Player List Panel
        createPlayerPanel();
        rightSide.add(playerListPanel).expandX().fillX().height(60).padTop(10).row();
        
        // Add sides to content
        contentTable.add(leftSide).width(280).expandY().fillY().padRight(10);
        contentTable.add(rightSide).expand().fill();
        
        mainTable.add(contentTable).expand().fill().row();
        
        // Footer
        createFooter();
        
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
        
        // Initialize server list
        refreshServerList();
    }
    
    private void createHeader() {
        Table headerTable = new Table();
        headerTable.pad(5);
        headerTable.setBackground(skin.newDrawable("white", new Color(0.2f, 0.2f, 0.3f, 0.9f)));
        
        Label titleLabel = new Label("MULTIPLAYER LOBBY", skin, "title");
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(1.1f);
        titleLabel.setColor(Color.WHITE);
        
        headerTable.add(titleLabel).expandX().fillX();
        mainTable.add(headerTable).expandX().fillX().height(40).padBottom(10).row();
    }
    
    private void createConnectionPanel() {
        connectionPanel = new Table();
        connectionPanel.pad(8);
        connectionPanel.setBackground(skin.newDrawable("white", new Color(0.25f, 0.25f, 0.35f, 0.8f)));
        
        // Title
        Label titleLabel = new Label("CONNECTION", skin, "subtitle");
        titleLabel.setFontScale(0.9f);
        titleLabel.setColor(Color.CYAN);
        connectionPanel.add(titleLabel).colspan(2).expandX().fillX().padBottom(5).row();
        
        // Server Address
        connectionPanel.add(new Label("Address:", skin)).left().width(60).padRight(5);
        serverAddressField = new TextField("localhost", skin);
        serverAddressField.setMaxLength(20);
        connectionPanel.add(serverAddressField).expandX().fillX().padBottom(3).row();
        
        // Port
        connectionPanel.add(new Label("Port:", skin)).left().width(60).padRight(5);
        serverPortField = new TextField("8080", skin);
        serverPortField.setMaxLength(5);
        connectionPanel.add(serverPortField).expandX().fillX().padBottom(3).row();
        
        // Player Name
        connectionPanel.add(new Label("Name:", skin)).left().width(60).padRight(5);
        playerNameField = new TextField("Player", skin);
        playerNameField.setMaxLength(15);
        connectionPanel.add(playerNameField).expandX().fillX().padBottom(5).row();
        
        // Buttons
        Table buttonTable = new Table();
        buttonTable.pad(2);
        
        connectButton = new TextButton("Connect", skin);
        connectButton.setWidth(70);
        createServerButton = new TextButton("Host", skin);
        createServerButton.setWidth(50);
        
        buttonTable.add(connectButton).padRight(5);
        buttonTable.add(createServerButton);
        
        connectionPanel.add(buttonTable).colspan(2).expandX().fillX().row();
        
        // Status
        statusLabel = new Label("Ready to connect", skin);
        statusLabel.setColor(Color.GREEN);
        statusLabel.setFontScale(0.7f);
        connectionPanel.add(statusLabel).colspan(2).expandX().fillX().padTop(3);
    }
    
    private void createServerPanel() {
        serverPanel = new Table();
        serverPanel.pad(8);
        serverPanel.setBackground(skin.newDrawable("white", new Color(0.25f, 0.25f, 0.35f, 0.8f)));
        
        // Title
        Label titleLabel = new Label("AVAILABLE SERVERS", skin, "subtitle");
        titleLabel.setFontScale(0.9f);
        titleLabel.setColor(Color.YELLOW);
        serverPanel.add(titleLabel).expandX().fillX().padBottom(5).row();
        
        // Server list
        serverListTable = new Table();
        serverListScrollPane = new ScrollPane(serverListTable, skin);
        serverListScrollPane.setFadeScrollBars(false);
        serverListScrollPane.setScrollingDisabled(false, false);
        
        serverPanel.add(serverListScrollPane).expand().fill();
    }
    
    private void createChatPanel() {
        chatPanel = new Table();
        chatPanel.pad(8);
        chatPanel.setBackground(skin.newDrawable("white", new Color(0.25f, 0.25f, 0.35f, 0.8f)));
        
        // Title
        Label titleLabel = new Label("CHAT", skin, "subtitle");
        titleLabel.setFontScale(0.9f);
        titleLabel.setColor(Color.LIME);
        chatPanel.add(titleLabel).expandX().fillX().padBottom(5).row();
        
        // Chat area
        chatArea = new TextArea("", skin);
        chatArea.setDisabled(true);
        chatArea.setPrefRows(10);
        chatScrollPane = new ScrollPane(chatArea, skin);
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(false, false);
        chatPanel.add(chatScrollPane).expand().fill().padBottom(5).row();
        
        // Chat input
        Table inputTable = new Table();
        inputTable.pad(2);
        
        chatInputField = new TextField("", skin);
        chatInputField.setMaxLength(100);
        sendChatButton = new TextButton("Send", skin);
        sendChatButton.setWidth(60);
        
        inputTable.add(chatInputField).expandX().fillX().padRight(5);
        inputTable.add(sendChatButton);
        
        chatPanel.add(inputTable).expandX().fillX();
    }
    
    private void createPlayerPanel() {
        playerListPanel = new Table();
        playerListPanel.pad(8);
        playerListPanel.setBackground(skin.newDrawable("white", new Color(0.25f, 0.25f, 0.35f, 0.8f)));
        
        // Title
        Label titleLabel = new Label("PLAYERS ONLINE", skin, "subtitle");
        titleLabel.setFontScale(0.9f);
        titleLabel.setColor(Color.ORANGE);
        playerListPanel.add(titleLabel).expandX().fillX().padBottom(5).row();
        
        // Player list
        playerListTable = new Table();
        playerListScrollPane = new ScrollPane(playerListTable, skin);
        playerListScrollPane.setFadeScrollBars(false);
        playerListScrollPane.setScrollingDisabled(false, false);
        
        playerListPanel.add(playerListScrollPane).expand().fill();
    }
    
    private void createFooter() {
        Table footerTable = new Table();
        footerTable.pad(5);
        footerTable.setBackground(skin.newDrawable("white", new Color(0.2f, 0.2f, 0.3f, 0.9f)));
        
        backButton = new TextButton("Back to Main Menu", skin);
        backButton.setWidth(120);
        
        footerTable.add(backButton).expandX().center();
        mainTable.add(footerTable).expandX().fillX().height(35).padTop(10);
    }
    
    private void setupEventHandlers() {
        connectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    controller.connectToServer(
                        serverAddressField.getText(),
                        Integer.parseInt(serverPortField.getText()),
                        playerNameField.getText()
                    );
                } catch (NumberFormatException e) {
                    updateStatus("Invalid port number", Color.RED);
                }
            }
        });
        
        createServerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    controller.createServer(Integer.parseInt(serverPortField.getText()));
                } catch (NumberFormatException e) {
                    updateStatus("Invalid port number", Color.RED);
                }
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
        serverListTable.clear();
        
        // Add sample servers
        addServerToList("Local Server", "localhost:8080", "2/4");
        addServerToList("Test Server", "192.168.1.100:8080", "1/4");
        addServerToList("Public Server", "stardew.example.com:8080", "3/4");
    }
    
    private void addServerToList(String name, String address, String players) {
        Table serverRow = new Table();
        serverRow.pad(3);
        serverRow.setBackground(skin.newDrawable("white", new Color(0.3f, 0.3f, 0.4f, 0.6f)));
        
        Label nameLabel = new Label(name, skin);
        nameLabel.setFontScale(0.8f);
        nameLabel.setColor(Color.WHITE);
        
        Label addressLabel = new Label(address, skin);
        addressLabel.setFontScale(0.7f);
        addressLabel.setColor(Color.LIGHT_GRAY);
        
        Label playersLabel = new Label(players, skin);
        playersLabel.setFontScale(0.7f);
        playersLabel.setColor(Color.YELLOW);
        
        TextButton joinButton = new TextButton("Join", skin);
        joinButton.setWidth(40);
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] parts = address.split(":");
                if (parts.length == 2) {
                    try {
                        controller.connectToServer(parts[0], Integer.parseInt(parts[1]), playerNameField.getText());
                    } catch (NumberFormatException e) {
                        updateStatus("Invalid server address", Color.RED);
                    }
                }
            }
        });
        
        serverRow.add(nameLabel).expandX().fillX().padRight(5);
        serverRow.add(addressLabel).expandX().fillX().padRight(5);
        serverRow.add(playersLabel).width(30).padRight(5);
        serverRow.add(joinButton).width(40);
        
        serverListTable.add(serverRow).expandX().fillX().padBottom(2).row();
    }
    
    public void updatePlayerList(String[] players) {
        playerListTable.clear();
        
        for (String player : players) {
            Label playerLabel = new Label("• " + player, skin);
            playerLabel.setFontScale(0.8f);
            playerLabel.setColor(Color.WHITE);
            playerListTable.add(playerLabel).left().pad(2).row();
        }
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
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