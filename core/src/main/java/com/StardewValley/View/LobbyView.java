package com.StardewValley.View;

import com.StardewValley.network.GameLobby;
import com.StardewValley.network.NetworkManager;
import com.StardewValley.controllers.LobbyController;
import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public class LobbyView implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private LobbyController controller;
    private Skin skin;
    
    // UI Components
    private Table mainTable;
    private Table lobbyListTable;
    private Table chatTable;
    private Table playerListTable;
    private TextField chatInput;
    private TextArea chatArea;
    private List<GameLobby> lobbies;
    private String currentUsername;
    private GameLobby currentLobby;
    
    public LobbyView(LobbyController controller, String username) {
        this.controller = controller;
        this.currentUsername = username;
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport(), batch);
        
        initializeUI();
        setupEventHandlers();
    }
    
    private void initializeUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20);
        
        // Create lobby list section
        createLobbyListSection();
        
        // Create chat section
        createChatSection();
        
        // Create player list section
        createPlayerListSection();
        
        // Add sections to main table
        mainTable.add(lobbyListTable).width(400).height(400).pad(10);
        mainTable.add(chatTable).width(300).height(400).pad(10);
        mainTable.add(playerListTable).width(200).height(400).pad(10);
        
        stage.addActor(mainTable);
    }
    
    private void createLobbyListSection() {
        lobbyListTable = new Table();
        lobbyListTable.setBackground(skin.getDrawable("default-pane"));
        
        Label titleLabel = new Label("Available Lobbies", skin, "title");
        titleLabel.setAlignment(Align.center);
        
        ScrollPane lobbyScrollPane = new ScrollPane(createLobbyList(), skin);
        lobbyScrollPane.setFadeScrollBars(false);
        
        Button createLobbyButton = new TextButton("Create Lobby", skin);
        createLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCreateLobbyDialog();
            }
        });
        
        Button refreshButton = new TextButton("Refresh", skin);
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                NetworkManager.getInstance().requestLobbyList();
            }
        });
        
        lobbyListTable.add(titleLabel).colspan(2).pad(10);
        lobbyListTable.row();
        lobbyListTable.add(lobbyScrollPane).colspan(2).expand().fill().pad(10);
        lobbyListTable.row();
        lobbyListTable.add(createLobbyButton).pad(5);
        lobbyListTable.add(refreshButton).pad(5);
    }
    
    private Table createLobbyList() {
        Table lobbyTable = new Table();
        
        if (lobbies != null) {
            for (GameLobby lobby : lobbies) {
                Table lobbyRow = createLobbyRow(lobby);
                lobbyTable.add(lobbyRow).expandX().fillX().pad(5);
                lobbyTable.row();
            }
        }
        
        return lobbyTable;
    }
    
    private Table createLobbyRow(GameLobby lobby) {
        Table row = new Table();
        row.setBackground(skin.getDrawable("default-pane"));
        
        Label nameLabel = new Label(lobby.getName(), skin);
        Label playerCountLabel = new Label(lobby.getCurrentPlayerCount() + "/" + lobby.getMaxPlayers(), skin);
        Label statusLabel = new Label(lobby.isGameStarted() ? "In Game" : "Waiting", skin);
        
        TextButton joinButton = new TextButton("Join", skin);
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                joinLobby(lobby);
            }
        });
        
        joinButton.setDisabled(lobby.isGameStarted() || !lobby.canJoin());
        
        row.add(nameLabel).expandX().left().pad(5);
        row.add(playerCountLabel).pad(5);
        row.add(statusLabel).pad(5);
        row.add(joinButton).pad(5);
        
        return row;
    }
    
    private void createChatSection() {
        chatTable = new Table();
        chatTable.setBackground(skin.getDrawable("default-pane"));
        
        Label titleLabel = new Label("Chat", skin, "title");
        titleLabel.setAlignment(Align.center);
        
        chatArea = new TextArea("", skin);
        chatArea.setDisabled(true);
        chatArea.setPrefRows(15);
        
        ScrollPane chatScrollPane = new ScrollPane(chatArea, skin);
        chatScrollPane.setFadeScrollBars(false);
        
        chatInput = new TextField("", skin);
        chatInput.setMessageText("Type your message...");
        
        Button sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendChatMessage();
            }
        });
        
        chatTable.add(titleLabel).pad(10);
        chatTable.row();
        chatTable.add(chatScrollPane).expand().fill().pad(10);
        chatTable.row();
        chatTable.add(chatInput).expandX().fillX().pad(5);
        chatTable.add(sendButton).pad(5);
    }
    
    private void createPlayerListSection() {
        playerListTable = new Table();
        playerListTable.setBackground(skin.getDrawable("default-pane"));
        
        Label titleLabel = new Label("Players Online", skin, "title");
        titleLabel.setAlignment(Align.center);
        
        ScrollPane playerScrollPane = new ScrollPane(createPlayerList(), skin);
        playerScrollPane.setFadeScrollBars(false);
        
        playerListTable.add(titleLabel).pad(10);
        playerListTable.row();
        playerListTable.add(playerScrollPane).expand().fill().pad(10);
    }
    
    private Table createPlayerList() {
        Table playerTable = new Table();
        
        // This would be populated with online players
        Label placeholderLabel = new Label("No players online", skin);
        playerTable.add(placeholderLabel).pad(10);
        
        return playerTable;
    }
    
    private void setupEventHandlers() {
        // Handle Enter key in chat input
        chatInput.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\n' || c == '\r') {
                    sendChatMessage();
                }
            }
        });
    }
    
    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin);
        
        TextField lobbyNameField = new TextField("", skin);
        lobbyNameField.setMessageText("Enter lobby name");
        
        CheckBox privateCheckBox = new CheckBox("Private Lobby", skin);
        
        dialog.getContentTable().add(new Label("Lobby Name:", skin)).pad(10);
        dialog.getContentTable().add(lobbyNameField).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(privateCheckBox).colspan(2).pad(10);
        
        dialog.button("Create", true);
        dialog.button("Cancel", false);
        
        dialog.result(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((Boolean) dialog.getResult()) {
                    String lobbyName = lobbyNameField.getText().trim();
                    if (!lobbyName.isEmpty()) {
                        NetworkManager.getInstance().createLobby(lobbyName, currentUsername);
                    }
                }
            }
        });
        
        dialog.show(stage);
    }
    
    private void joinLobby(GameLobby lobby) {
        NetworkManager.getInstance().joinLobby(lobby.getId(), currentUsername);
        currentLobby = lobby;
        updateChatTitle("Lobby: " + lobby.getName());
    }
    
    private void sendChatMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            if (currentLobby != null) {
                NetworkManager.getInstance().sendChatMessage(message, currentUsername, false, null);
            } else {
                NetworkManager.getInstance().sendChatMessage(message, currentUsername, false, null);
            }
            chatInput.setText("");
        }
    }
    
    private void updateChatTitle(String title) {
        // Update chat section title
        chatTable.clear();
        createChatSection();
    }
    
    public void updateLobbyList(List<GameLobby> newLobbies) {
        this.lobbies = newLobbies;
        // Refresh the lobby list UI
        lobbyListTable.clear();
        createLobbyListSection();
    }
    
    public void addChatMessage(String sender, String message) {
        chatArea.appendText(sender + ": " + message + "\n");
        // Auto-scroll to bottom
        chatArea.setCursorPosition(chatArea.getText().length());
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        NetworkManager.getInstance().requestLobbyList();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
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
        stage.dispose();
        batch.dispose();
    }
} 