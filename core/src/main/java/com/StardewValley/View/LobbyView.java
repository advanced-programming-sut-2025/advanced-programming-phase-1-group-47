package com.StardewValley.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.StardewValley.Main;
import com.StardewValley.controllers.GameModeSelectionController;
import com.StardewValley.controllers.InitPageController;
import com.StardewValley.controllers.LobbyController;
import com.StardewValley.model.App;
import com.StardewValley.model.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class LobbyView implements Screen {

    // Core fields
    private Stage stage;
    private Skin skin;
    private LobbyController controller;

    // UI components
    private Table mainLayout, lobbyTable, chatTable;
    private TextField lobbyNameField, maxPlayersField, passwordField, chatInputField, joinByNameField;
    private TextArea chatArea;
    private ScrollPane chatScrollPane, lobbyScrollPane;
    private CheckBox invisibleLobbyCheckBox;
    private TextButton deleteLobbyButton;
    private TextButton startGameButton;

    // Connection data
    private boolean isConnected;
    private String playerNickname;
    private String currentLobbyId;
    
    // Network connection
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread messageListener;
    private Thread joinTimeoutThread; // For managing join timeout

    // Data
    private List<SimpleLobbyInfo> lobbies = new ArrayList<>();
    
    // Player list dialog
    private Dialog playerListDialog;
    private Label playerListLabel; // Store direct reference to the label
    private boolean isPlayerListVisible = false;
    


    public LobbyView() {
        skin = GameAssetManager.getGameAssetManager().getSkin();
        controller = new LobbyController(this);
        stage = new Stage();

        createUI();
        promptForNickname();
    }

    /** Prompts player for a nickname before connecting to the server */
    private void promptForNickname() {
        Dialog dialog = new Dialog("Choose Nickname", skin);
        String defaultNick = (App.loggedUser != null && !App.loggedUser.getNickname().isEmpty())
                ? App.loggedUser.getNickname()
                : "Guest" + (int) (Math.random() * 1000);

        TextField nicknameField = new TextField(defaultNick, skin);
        nicknameField.setMessageText("Enter nickname...");

        dialog.text("Please enter a nickname to join the multiplayer lobby:")
              .getContentTable().row();
        dialog.getContentTable().add(nicknameField).width(300).padTop(5);

        // Use proper ChangeListener instead of lambda
        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String nick = nicknameField.getText().trim();
                if (!nick.isEmpty()) {
                    playerNickname = nick;
                    dialog.hide();
                    connectToServer();
                }
            }
        });
        
        dialog.getButtonTable().add(confirmButton);

        dialog.show(stage);
    }

    /** Creates main UI layout with lobby list on the left and chat on the right */
    private void createUI() {
        mainLayout = new Table();
        mainLayout.setFillParent(true);
        mainLayout.pad(20);

        // Title
        Label title = new Label("Multiplayer Lobby", skin);
        title.setFontScale(2);
        mainLayout.add(title).colspan(2).center().padBottom(20).row();

        // Join by name section at top left
        Table joinByNameTable = createJoinByNameSection();
        mainLayout.add(joinByNameTable).colspan(2).expandX().fillX().padBottom(10).row();

        // Left: lobby controls
        lobbyTable = createLobbySection();
        mainLayout.add(lobbyTable).expand().fill().padRight(10);

        // Right: chat section
        chatTable = createChatSection();
        mainLayout.add(chatTable).expand().fill().padLeft(10);

        stage.addActor(mainLayout);
        Gdx.input.setInputProcessor(stage);
    }

    /** Builds the lobby creation & list section */
    private Table createLobbySection() {
        Table table = new Table();

        // Create lobby UI - moved to right side and made smaller
        table.add(new Label("Create Lobby", skin)).right().padBottom(5).row();
        
        // Lobby name field - right-aligned and smaller
        table.add(new Label("Lobby Name:", skin)).right().padRight(5);
        lobbyNameField = new TextField("My Lobby", skin);
        lobbyNameField.setWidth(150); // Make field smaller
        table.add(lobbyNameField).width(150).right().row();
        
        // Max players field - right-aligned and smaller
        table.add(new Label("Max Players:", skin)).right().padRight(5);
        maxPlayersField = new TextField("4", skin);
        maxPlayersField.setWidth(150); // Make field smaller
        table.add(maxPlayersField).width(150).right().row();
        
        // Password field - right-aligned and smaller (optional for private lobbies)
        table.add(new Label("Password (Optional):", skin)).right().padRight(5);
        passwordField = new TextField("", skin);
        passwordField.setMessageText("Leave empty for public lobby");
        passwordField.setWidth(150); // Make field smaller
        table.add(passwordField).width(150).right().row();
        
        // Invisible lobby checkbox - right-aligned
        table.add(new Label("Invisible Lobby:", skin)).right().padRight(5);
        invisibleLobbyCheckBox = new CheckBox("", skin);
        invisibleLobbyCheckBox.setChecked(false);
        table.add(invisibleLobbyCheckBox).width(150).right().row();
        
        // Create button - right-aligned and smaller
        TextButton createBtn = new TextButton("Create", skin);
        createBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onCreateLobby();
            }
        });
        table.add(createBtn).width(150).right().padTop(5).row();
        
        // Lobby list UI
        table.add(new Label("Available Lobbies", skin)).left().padTop(10).row();
        TextButton refreshBtn = new TextButton("Refresh", skin);
        refreshBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onRefreshLobbies();
            }
        });
        table.add(refreshBtn).colspan(2).fillX().row();
        
        lobbyScrollPane = new ScrollPane(new Table(), skin);
        table.add(lobbyScrollPane).colspan(2).expand().fill().padTop(5).row();
        
        // Back button
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onBack();
            }
        });
        table.add(backBtn).colspan(2).fillX().padTop(10);
        
        // Leave lobby button (only visible when in a lobby)
        TextButton leaveBtn = new TextButton("Leave Lobby", skin);
        leaveBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onLeaveLobby();
            }
        });
        table.add(leaveBtn).colspan(2).fillX().padTop(5);
        
        // Test connection button
        TextButton testConnBtn = new TextButton("Test Connection", skin);
        testConnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                testServerConnection();
            }
        });
        table.add(testConnBtn).colspan(2).fillX().padTop(5);
        
        return table;
    }
    
    /** Builds the join by name section */
    private Table createJoinByNameSection() {
        Table table = new Table();
        
        // Join by name field and button
        table.add(new Label("Join Lobby by Name:", skin)).left().padRight(5);
        joinByNameField = new TextField("", skin);
        joinByNameField.setMessageText("Enter lobby name...");
        table.add(joinByNameField).width(200).left().padRight(5);
        
        TextButton joinByNameBtn = new TextButton("Join", skin);
        joinByNameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onJoinByName();
            }
        });
        table.add(joinByNameBtn).width(80).left();
        
        return table;
    }

    /** Builds the chat UI */
    private Table createChatSection() {
        Table table = new Table();

        // Start game button (only visible when in a lobby and as host)
        startGameButton = new TextButton("Start Game", skin);
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onStartGame();
            }
        });
        startGameButton.setVisible(false); // Hidden by default
        table.add(startGameButton).expandX().fillX().padBottom(10).row();
        
        // Delete lobby button (only visible when in a lobby and as host)
        deleteLobbyButton = new TextButton("Delete Lobby", skin);
        deleteLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onDeleteLobby();
            }
        });
        deleteLobbyButton.setVisible(false); // Hidden by default
        table.add(deleteLobbyButton).expandX().fillX().padBottom(10).row();

        table.add(new Label("Chat", skin)).left().padBottom(5).row();

        chatArea = new TextArea("", skin);
        chatArea.setDisabled(true);
        chatScrollPane = new ScrollPane(chatArea, skin);
        table.add(chatScrollPane).expand().fill().row();

        Table inputRow = new Table();
        chatInputField = new TextField("", skin);
        chatInputField.setMessageText("Type a message...");
        TextButton sendBtn = new TextButton("Send", skin);
        sendBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onSendChat();
            }
        });

        inputRow.add(chatInputField).expandX().fillX();
        inputRow.add(sendBtn).width(80);
        table.add(inputRow).expandX().fillX().padTop(5).row();

        return table;
    }
    
    // Simple lobby info class
    private static class SimpleLobbyInfo {
        String lobbyId, lobbyName, hostPlayerId;
        int currentPlayers, maxPlayers;
        boolean isGameStarted;
        boolean isPrivate;
        boolean isInvisible;
        List<String> connectedPlayers;
        List<String> disconnectedPlayers;
        
        SimpleLobbyInfo(String data) {
            String[] parts = data.split(",", -1); // Use -1 to preserve trailing empty strings
            
            System.out.println("=== DEBUG: PARSING LOBBY DATA ===");
            System.out.println("Raw data: [" + data + "]");
            System.out.println("Data length: " + data.length());
            System.out.println("First char: '" + (data.length() > 0 ? data.charAt(0) : "empty") + "'");
            System.out.println("Split into " + parts.length + " parts");
            for (int i = 0; i < parts.length; i++) {
                System.out.println("Part " + i + ": [" + parts[i] + "] (length: " + parts[i].length() + ")");
            }
            
            if (parts.length >= 10) {
                lobbyId = parts[0];
                lobbyName = parts[1];
                hostPlayerId = parts[2];
                
                try {
                    currentPlayers = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse currentPlayers from [" + parts[3] + "], using default 0");
                    currentPlayers = 0;
                }
                
                try {
                    maxPlayers = Integer.parseInt(parts[4]);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse maxPlayers from [" + parts[4] + "], using default 4");
                    maxPlayers = 4;
                }
                
                try {
                    isGameStarted = Boolean.parseBoolean(parts[5]);
                } catch (Exception e) {
                    System.err.println("Failed to parse isGameStarted from [" + parts[5] + "], using default false");
                    isGameStarted = false;
                }
                
                try {
                    isPrivate = Boolean.parseBoolean(parts[6]);
                } catch (Exception e) {
                    System.err.println("Failed to parse isPrivate from [" + parts[6] + "], using default false");
                    isPrivate = false;
                }
                
                try {
                    isInvisible = Boolean.parseBoolean(parts[7]);
                } catch (Exception e) {
                    System.err.println("Failed to parse isInvisible from [" + parts[7] + "], using default false");
                    isInvisible = false;
                }
                
                // Parse connected players
                String connectedPlayersStr = parts[8];
                connectedPlayers = new ArrayList<>();
                if (!connectedPlayersStr.isEmpty()) {
                    String[] playerArray = connectedPlayersStr.split(";");
                    for (String player : playerArray) {
                        if (!player.trim().isEmpty()) {
                            connectedPlayers.add(player.trim());
                        }
                    }
                }
                
                // Parse disconnected players
                String disconnectedPlayersStr = parts[9];
                disconnectedPlayers = new ArrayList<>();
                if (!disconnectedPlayersStr.isEmpty()) {
                    String[] playerArray = disconnectedPlayersStr.split(";");
                    for (String player : playerArray) {
                        if (!player.trim().isEmpty()) {
                            disconnectedPlayers.add(player.trim());
                        }
                    }
                }
                
                System.out.println("Successfully parsed lobby - ID: [" + lobbyId + "], Name: [" + lobbyName + "], Players: " + currentPlayers + "/" + maxPlayers + ", isPrivate: " + isPrivate + ", isInvisible: " + isInvisible);
            } else {
                System.err.println("Invalid lobby data format. Expected at least 10 parts, got " + parts.length + ": [" + data + "]");
                // Set default values
                lobbyId = "";
                lobbyName = "";
                hostPlayerId = "";
                currentPlayers = 0;
                maxPlayers = 4;
                isGameStarted = false;
                isPrivate = false;
                isInvisible = false;
                connectedPlayers = new ArrayList<>();
                disconnectedPlayers = new ArrayList<>();
            }
            System.out.println("=== END DEBUG ===");
        }
        
        @Override
        public String toString() {
            String privacyIndicator = isPrivate ? " [PRIVATE]" : "";
            String visibilityIndicator = isInvisible ? " [INVISIBLE]" : "";
            return lobbyName + " (" + currentPlayers + "/" + maxPlayers + ")" + privacyIndicator + visibilityIndicator;
        }
    }
    
    // Event handlers
    private void onCreateLobby() {
        String lobbyName = lobbyNameField.getText().trim();
        int maxPlayers = 4;
        try {
            maxPlayers = Integer.parseInt(maxPlayersField.getText());
        } catch (NumberFormatException e) {
            // Use default value
        }
        
        if (lobbyName.isEmpty()) {
            addChatMessage("System", "Please enter a lobby name!");
            return;
        }
        
        if (maxPlayers < 1 || maxPlayers > 8) {
            addChatMessage("System", "Max players must be between 1 and 8!");
            return;
        }
        
        // Get password (optional)
        String password = passwordField.getText().trim();
        
        // Get invisible setting
        boolean isInvisible = invisibleLobbyCheckBox.isChecked();
        
        // Send create lobby message
        String createMessage = "CREATE_LOBBY:" + lobbyName + ":" + maxPlayers + ":" + playerNickname + ":" + password + ":" + isInvisible;
        sendMessage(createMessage);
        addChatMessage("System", "Creating lobby: " + lobbyName + (password.isEmpty() ? " (Public)" : " (Private)") + (isInvisible ? " (Invisible)" : " (Visible)"));
    }
    
    private void onJoinByName() {
        String lobbyName = joinByNameField.getText().trim();
        if (lobbyName.isEmpty()) {
            addChatMessage("System", "Please enter a lobby name!");
            return;
        }
        
        // Check if we're already in a lobby
        if (currentLobbyId != null) {
            addChatMessage("System", "You are already in a lobby! Leave the current lobby first.");
            return;
        }
        
        // Send join by name message
        String joinMessage = "JOIN_BY_NAME:" + lobbyName + ":";
        sendMessage(joinMessage);
        addChatMessage("System", "Attempting to join lobby by name: " + lobbyName);
        
        // Set a timeout for the join operation
        startJoinTimeout();
        
        // Clear the input field
        joinByNameField.setText("");
    }
    
    private void onRefreshLobbies() {
        sendMessage("LOBBY_LIST_REQUEST");
        addChatMessage("System", "Refreshing lobby list...");
    }
    
    private void onBack() {
        // Disconnect from server and go back to game mode selection
        if (isConnected) {
            try {
                isConnected = false;
                if (messageListener != null && messageListener.isAlive()) {
                    messageListener.interrupt();
                }
                
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error during disconnect: " + e.getMessage());
            }
        }
        
        // Navigate back to game mode selection
        try {
            Main.getMain().setScreen(new GameModeSelectionView(new GameModeSelectionController(), GameAssetManager.getGameAssetManager().getSkin()));
        } catch (Exception e) {
            System.err.println("Error navigating to GameModeSelectionView: " + e.getMessage());
            // Fallback: try to go back to main menu
            try {
                Main.getMain().setScreen(new InitPageView(new InitPageController(), GameAssetManager.getGameAssetManager().getSkin()));
            } catch (Exception fallbackError) {
                System.err.println("Fallback navigation also failed: " + fallbackError.getMessage());
                dispose();
            }
        }
    }
    
    private void onSendChat() {
        String message = chatInputField.getText().trim();
        if (message.isEmpty()) return;
        
        if (currentLobbyId == null) {
            addChatMessage("System", "You must be in a lobby to chat!");
            return;
        }
        
        // Send chat message
        String chatMessage = "CHAT:" + currentLobbyId + ":" + message;
        sendMessage(chatMessage);
        
        // Add message to local chat
        addChatMessage(playerNickname, message);
        
        // Clear input field
        chatInputField.setText("");
    }
    
    private void onLeaveLobby() {
        if (currentLobbyId == null) {
            addChatMessage("System", "You are not in a lobby!");
            return;
        }
        
        // Send leave lobby message
        String leaveMessage = "LEAVE_LOBBY:" + currentLobbyId;
        sendMessage(leaveMessage);
        addChatMessage("System", "Leaving lobby...");
        
        // Clear current lobby ID immediately for better UX
        currentLobbyId = null;
    }
    
    private void onStartGame() {
        if (currentLobbyId == null) {
            addChatMessage("System", "You are not in a lobby!");
            return;
        }
        
        // Show confirmation dialog
        Dialog confirmDialog = new Dialog("Confirm Start Game", skin);
        confirmDialog.text("Are you sure you want to start the game?\nAll players will be moved to the game.");
        
        // Use proper ClickListener objects instead of lambda expressions
        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirmDialog.hide();
            }
        });
        
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirmDialog.hide();
                sendMessage("START_GAME:" + currentLobbyId);
                addChatMessage("System", "Starting game...");
            }
        });
        
        confirmDialog.getButtonTable().add(cancelButton);
        confirmDialog.getButtonTable().add(startButton);
        confirmDialog.show(stage);
    }
    
    private void onDeleteLobby() {
        if (currentLobbyId == null) {
            addChatMessage("System", "You are not in a lobby!");
            return;
        }
        
        // Show confirmation dialog
        Dialog confirmDialog = new Dialog("Confirm Delete", skin);
        confirmDialog.text("Are you sure you want to delete this lobby?\nAll players will be kicked out.");
        
        // Use proper ClickListener objects instead of lambda expressions
        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirmDialog.hide();
            }
        });
        
        TextButton deleteButton = new TextButton("Delete", skin);
        deleteButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirmDialog.hide();
                sendMessage("DELETE_LOBBY:" + currentLobbyId);
                addChatMessage("System", "Deleting lobby...");
            }
        });
        
        confirmDialog.getButtonTable().add(cancelButton);
        confirmDialog.getButtonTable().add(deleteButton);
        confirmDialog.show(stage);
    }
    
    // Network methods
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Send player ID to server
            out.println(playerNickname);
            
            // No need to wait for welcome message since server doesn't send one anymore
            isConnected = true;
            
            // Start message listener
            startMessageListener();
            
            // Request lobby list
            onRefreshLobbies();
            
            // Add welcome messages
            addChatMessage("System", "Connected to lobby server successfully!");
            addChatMessage("System", "Use the buttons above to create or join lobbies.");
            addChatMessage("System", "Type messages in the chat box below to communicate with other players.");
            addChatMessage("System", "Press P to view all connected players and their lobby assignments.");
            
        } catch (IOException e) {
            String errorMsg = "Failed to connect to server: " + e.getMessage();
            System.err.println(errorMsg);
            addChatMessage("System", errorMsg);
        } catch (Exception e) {
            String errorMsg = "Unexpected error during connection: " + e.getMessage();
            System.err.println(errorMsg);
            addChatMessage("System", errorMsg);
        }
    }
    
    private void startMessageListener() {
        messageListener = new Thread(() -> {
            try {
                String message;
                while (isConnected && (message = in.readLine()) != null) {
                    final String finalMessage = message;
                    Gdx.app.postRunnable(() -> handleServerMessage(finalMessage));
                }
            } catch (IOException e) {
                if (isConnected) {
                    Gdx.app.postRunnable(() -> {
                        addChatMessage("System", "Connection lost: " + e.getMessage());
                        isConnected = false;
                    });
                }
            }
        });
        messageListener.setDaemon(true);
        messageListener.start();
    }
    
    private void handleServerMessage(String message) {
        if (message == null || message.isEmpty()) return;
        
        try {
            if (message.startsWith("LOBBY_CREATED:")) {
                if (message.length() > 14) {
                    String lobbyId = message.substring(14);
                    currentLobbyId = lobbyId;
                    addChatMessage("System", "Lobby created successfully! Lobby ID: " + lobbyId);
                    
                    // Add a small delay to ensure server has finished processing the lobby creation
                    // before requesting the lobby list
                    Gdx.app.postRunnable(() -> {
                        try {
                            Thread.sleep(100); // 100ms delay
                        } catch (InterruptedException e) {
                            // Ignore interruption
                        }
                        onRefreshLobbies();
                    });
                }
            } else if (message.startsWith("LOBBY_JOINED:")) {
                String lobbyId = message.substring(13);
                currentLobbyId = lobbyId;
                addChatMessage("System", "Joined lobby: " + lobbyId);
                updateLobbyUI();
                onRefreshLobbies();
            } else if (message.startsWith("LOBBY_CREATE_FAILED:")) {
                String errorReason = "Unknown error";
                if (message.length() > 20) {
                    errorReason = message.substring(20);
                }
                addChatMessage("System", "Failed to create lobby: " + errorReason);
                currentLobbyId = null; // Ensure we're not in a lobby
            } else if (message.startsWith("LOBBY_JOIN_FAILED:")) {
                String errorReason = "Unknown error";
                if (message.length() > 19) {
                    errorReason = message.substring(19);
                }
                addChatMessage("System", "Failed to join lobby: " + errorReason);
                currentLobbyId = null; // Ensure we're not in a lobby
                // Cancel join timeout since we got a response
                if (joinTimeoutThread != null && joinTimeoutThread.isAlive()) {
                    joinTimeoutThread.interrupt();
                }
            } else if (message.startsWith("JOIN_SUCCESS:")) {
                // Alternative join success message
                if (message.length() > 13) {
                    String lobbyId = message.substring(13);
                    currentLobbyId = lobbyId;
                    addChatMessage("System", "Successfully joined lobby! Lobby ID: " + lobbyId);
                    // Cancel join timeout since we successfully joined
                    if (joinTimeoutThread != null && joinTimeoutThread.isAlive()) {
                        joinTimeoutThread.interrupt();
                    }
                    // Update the lobby list to reflect the new player count
                    onRefreshLobbies();
                }
            } else if (message.startsWith("JOIN_FAILED:")) {
                // Alternative join failed message
                String errorReason = "Unknown error";
                if (message.length() > 12) {
                    errorReason = message.substring(12);
                }
                addChatMessage("System", "Failed to join lobby: " + errorReason);
                currentLobbyId = null; // Ensure we're not in a lobby
                // Cancel join timeout since we got a response
                if (joinTimeoutThread != null && joinTimeoutThread.isAlive()) {
                    joinTimeoutThread.interrupt();
                }
            } else if (message.startsWith("LOBBY_LEFT:")) {
                currentLobbyId = null;
                addChatMessage("System", "Left lobby successfully");
                // Refresh lobby list to show updated player counts
                onRefreshLobbies();
            } else if (message.startsWith("LOBBY_DELETED_SUCCESS:")) {
                String lobbyId = message.substring(23);
                addChatMessage("System", "Lobby deleted successfully");
                currentLobbyId = null;
                updateLobbyUI();
                onRefreshLobbies();
            } else if (message.startsWith("LOBBY_DELETE_FAILED:")) {
                String errorReason = message.length() > 20 ? message.substring(20) : "Unknown error";
                addChatMessage("System", "Failed to delete lobby: " + errorReason);
            } else if (message.startsWith("LOBBY_DELETED:")) {
                // This message is sent to all players when a lobby is deleted by the host
                String[] parts = message.split(":", 3);
                if (parts.length >= 3) {
                    String lobbyId = parts[1];
                    String reason = parts[2];
                    
                    if (currentLobbyId != null && currentLobbyId.equals(lobbyId)) {
                        addChatMessage("System", "Lobby was deleted by the host: " + reason);
                        currentLobbyId = null;
                        updateLobbyUI();
                        onRefreshLobbies();
                    }
                }
            } else if (message.startsWith("RECONNECTED:")) {
                // Handle successful reconnection
                String[] parts = message.split(":", 3);
                if (parts.length >= 3) {
                    String playerId = parts[1];
                    String lobbyId = parts[2];
                    
                    if (!"unknown".equals(lobbyId)) {
                        // Restore lobby state
                        currentLobbyId = lobbyId;
                        addChatMessage("System", "Successfully reconnected to lobby: " + lobbyId);
                        
                        // Update UI to show we're in a lobby
                        updateLobbyUI();
                    } else {
                        addChatMessage("System", "Reconnected but lobby information unavailable");
                    }
                } else {
                    addChatMessage("System", "Successfully reconnected to lobby");
                }
                
                // Refresh lobby list to show updated player counts
                onRefreshLobbies();
            } else if (message.startsWith("RECONNECT_FAILED:")) {
                // Handle reconnection failure
                String reason = message.length() > 17 ? message.substring(17) : "Unknown reason";
                addChatMessage("System", "Failed to reconnect: " + reason);
                currentLobbyId = null; // Reset lobby state
                // Refresh lobby list to show updated player counts
                onRefreshLobbies();
            } else if (message.startsWith("LOBBY_UPDATE:")) {
                // Handle lobby update messages silently - don't show in chat
                // These are sent after chat messages to update lobby info
                return;
            } else if (message.startsWith("ALL_PLAYERS_RESPONSE:")) {
                // Handle response with all players and their lobby assignments

                
                if (message.length() > 22) {
                    // Extract data after "ALL_PLAYERS_RESPONSE:"
                    String playerData = message.substring(23);
                    
                    // Format the data for better display by adding line breaks
                    String formattedData = playerData.replace("Connected Players and Their Lobbies:", "Connected Players and Their Lobbies:\n")
                                                   .replace("==============", "==============\n")
                                                   .replace("• ", "\n• ")
                                                   .replace("Lobby Details:", "\n\nLobby Details:\n")
                                                   .replace("  Players:", "\n  Players:")
                                                   .replace("  Host:", "\n  Host:")
                                                   .replace("  Status:", "\n  Status:");
                    

                    
                    updatePlayerListDialog(formattedData);
                    addChatMessage("System", "Player information updated successfully!");
                }

            } else if (message.startsWith("LOBBY_LIST:")) {
                if (message.length() > 12) {
                    // Fix: Use indexOf to find the colon and extract everything after it
                    int colonIndex = message.indexOf(':');
                    if (colonIndex != -1 && colonIndex + 1 < message.length()) {
                        String lobbyData = message.substring(colonIndex + 1);
                        updateLobbyListFromServer(lobbyData);
                    } else {
                        System.err.println("Invalid LOBBY_LIST message format - no colon found");
                    }
                } else {
                    System.err.println("LOBBY_LIST message too short: [" + message + "]");
                }
                // Don't show LOBBY_LIST messages in chat - they're just for updating the lobby list
                return;
            } else if (message.startsWith("CHAT:")) {
                // Handle chat messages from other players
                String[] parts = message.split(":", 4);
                if (parts.length == 4) {
                    String playerName = parts[1]; // Player who sent the message
                    String lobbyId = parts[2];
                    String chatMessage = parts[3];
                    
                    // Only show chat messages for the current lobby
                    if (currentLobbyId != null && currentLobbyId.equals(lobbyId)) {
                        // Don't show your own messages twice (they're already shown locally)
                        if (!playerName.equals(playerNickname)) {
                            addChatMessage(playerName, chatMessage);
                        }
                    }
                }
            } else if (message.startsWith("PLAYER_JOINED:")) {
                // Handle player joined lobby notification
                String[] parts = message.split(":", 2);
                if (parts.length == 2) {
                    String playerName = parts[1];
                    addChatMessage("System", playerName + " joined the lobby");
                }
            } else if (message.startsWith("PLAYER_LEFT:")) {
                // Handle player left lobby notification
                String[] parts = message.split(":", 2);
                if (parts.length == 2) {
                    String playerName = parts[1];
                    addChatMessage("System", playerName + " left the lobby");
                }
            } else if (message.startsWith("ERROR:")) {
                // Handle server errors
                String errorMsg = message.length() > 6 ? message.substring(6) : "Unknown server error";
                addChatMessage("System", "Server Error: " + errorMsg);
            } else if (message.startsWith("START_GAME:")) {
                // Handle game start message
                String lobbyId = message.substring(11);
                if (currentLobbyId != null && currentLobbyId.equals(lobbyId)) {
                    addChatMessage("System", "Game is starting! Transitioning to game...");
                    // Launch the regular game screen (singleplayer with chat)
                    Gdx.app.postRunnable(() -> {
                        try {
                            // Create and show the regular game screen
                            GameScreen gameScreen = new GameScreen();
                            // Set multiplayer info for chat functionality
                            gameScreen.setMultiplayerInfo(playerNickname, currentLobbyId);
                            
                            // Game started - transition to GameScreen
                            // GameScreen will connect to the game server on port 8081
                            Main.getMain().setScreen(gameScreen);
                            
                            System.out.println("LobbyView: Game started - transitioning to GameScreen");
                            System.out.println("LobbyView: GameScreen will connect to game server on port 8081");
                            
                        } catch (Exception e) {
                            addChatMessage("System", "Failed to start game: " + e.getMessage());
                            System.err.println("Error starting game: " + e.getMessage());
                        }
                    });
                }
            } else if (message.startsWith("INFO:")) {
                // Handle server info messages
                String infoMsg = message.length() > 5 ? message.substring(5) : "Server info";
                addChatMessage("System", infoMsg);
            } else if (message.startsWith("GAME_CHAT:")) {
                // Handle in-game chat messages (these will be handled by game server now)
                String[] parts = message.split(":", 3);
                if (parts.length == 3) {
                    String playerName = parts[1];
                    String chatMessage = parts[2];
                    System.out.println("LobbyView: Received GAME_CHAT from " + playerName + ": " + chatMessage);
                    
                    // Show in lobby chat for debugging
                    addChatMessage(playerName, chatMessage);
                }
            } else {
                // Only show unknown messages in chat if they're not server system messages
                // Make this very restrictive to prevent server messages from appearing
                if (!message.contains("connected to the server") && 
                    !message.contains("LOBBY_LIST:") &&
                    !message.startsWith("Welcome ") &&
                    !message.contains("ECHO:") &&
                    !message.contains("PONG") &&
                    !message.startsWith("LOBBY_UPDATE:") &&
                    !message.startsWith("PLAYER_") &&
                    !message.startsWith("CHAT:")) {
                    addChatMessage("Server", message);
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling server message: " + e.getMessage());
            addChatMessage("System", "Error processing server message: " + e.getMessage());
        }
    }
    
    private void updateLobbyListFromServer(String lobbyData) {
        System.out.println("=== DEBUG: UPDATE LOBBY LIST ===");
        System.out.println("Input lobbyData: [" + lobbyData + "]");
        System.out.println("Input data length: " + lobbyData.length());
        System.out.println("First char: '" + (lobbyData.length() > 0 ? lobbyData.charAt(0) : "empty") + "'");
        
        lobbies.clear();
        if (!lobbyData.isEmpty()) {
            String[] lobbies = lobbyData.split("\\|");
            System.out.println("Split into " + lobbies.length + " parts");
            for (int i = 0; i < lobbies.length; i++) {
                System.out.println("Part " + i + ": [" + lobbies[i] + "] (length: " + lobbies[i].length() + ")");
            }
            
            for (int i = 0; i < lobbies.length; i++) {
                String lobbyStr = lobbies[i];
                if (!lobbyStr.isEmpty()) {
                    System.out.println("Processing lobby string: [" + lobbyStr + "]");
                    this.lobbies.add(new SimpleLobbyInfo(lobbyStr));
                }
            }
        }
        System.out.println("=== END DEBUG ===");
        updateLobbyList();
    }
    
    private void updateLobbyList() {
        System.out.println("=== DEBUG: UPDATE LOBBY LIST ===");
        System.out.println("Number of lobbies: " + lobbies.size());
        for (int i = 0; i < lobbies.size(); i++) {
            SimpleLobbyInfo lobby = lobbies.get(i);
            System.out.println("Lobby " + i + ": ID=[" + lobby.lobbyId + "], Name=[" + lobby.lobbyName + "], Players=" + lobby.currentPlayers + "/" + lobby.maxPlayers + ", isPrivate=" + lobby.isPrivate + ", isInvisible=" + lobby.isInvisible);
        }
        System.out.println("=== END DEBUG ===");
        
        Table lobbyListTable = new Table();
        
        if (lobbies.isEmpty()) {
            Label noLobbiesLabel = new Label("No lobbies available", skin);
            noLobbiesLabel.setColor(Color.GRAY);
            lobbyListTable.add(noLobbiesLabel).expandX().fillX().row();
        } else {
            for (SimpleLobbyInfo lobby : lobbies) {
                Table lobbyRow = new Table();
                
                // Lobby info - show lobby name and player count with proper spacing
                Label lobbyLabel = new Label(lobby.lobbyName + " (" + lobby.currentPlayers + "/" + lobby.maxPlayers + ")", skin);
                lobbyLabel.setAlignment(Align.left);
                lobbyRow.add(lobbyLabel).width(300).left().padRight(10); // Fixed width and left alignment
                
                // Join button
                TextButton joinButton = new TextButton("Join", skin);
                joinButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        onJoinLobby(lobby.lobbyId);
                    }
                });
                
                // Disable join button if lobby is full or game started
                if (lobby.currentPlayers >= lobby.maxPlayers || lobby.isGameStarted) {
                    joinButton.setDisabled(true);
                }
                
                lobbyRow.add(joinButton).width(80).right(); // Fixed width and right alignment
                lobbyListTable.add(lobbyRow).expandX().fillX().padTop(2).padBottom(2).row();
            }
        }
        
        lobbyScrollPane.setActor(lobbyListTable);
        
        // Update start game and delete button visibility after lobby list update
        if (currentLobbyId != null) {
            boolean isHost = checkIfCurrentPlayerIsHost();
            startGameButton.setVisible(isHost);
            deleteLobbyButton.setVisible(isHost);
        }
    }
    
    private void onJoinLobby(String lobbyId) {
        if (lobbyId == null || lobbyId.trim().isEmpty()) {
            addChatMessage("System", "Invalid lobby ID!");
            return;
        }
        
        // Check if we're already in a lobby
        if (currentLobbyId != null) {
            addChatMessage("System", "You are already in a lobby! Leave the current lobby first.");
            return;
        }
        
        // Find the lobby info to validate it
        SimpleLobbyInfo targetLobby = null;
        for (SimpleLobbyInfo lobby : lobbies) {
            if (lobby.lobbyId.equals(lobbyId)) {
                targetLobby = lobby;
                break;
            }
        }
        
        if (targetLobby == null) {
            addChatMessage("System", "Lobby not found! Please refresh the lobby list.");
            return;
        }
        
        // Check if lobby is full
        if (targetLobby.currentPlayers >= targetLobby.maxPlayers) {
            addChatMessage("System", "Cannot join lobby: " + targetLobby.lobbyName + " - Lobby is full!");
            return;
        }
        
        // Check if game has already started
        if (targetLobby.isGameStarted) {
            addChatMessage("System", "Cannot join lobby: " + targetLobby.lobbyName + " - Game has already started!");
            return;
        }
        
        // Check if lobby is private and prompt for password
        if (targetLobby.isPrivate) {
            promptForPassword(lobbyId, targetLobby.lobbyName);
        } else {
            // Send join lobby message for public lobby
            String joinMessage = "JOIN_LOBBY:" + lobbyId + ":" + "";
            sendMessage(joinMessage);
            addChatMessage("System", "Attempting to join public lobby: " + targetLobby.lobbyName);
            
            // Set a timeout for the join operation
            startJoinTimeout();
        }
    }
    
    private void promptForPassword(String lobbyId, String lobbyName) {
        Dialog dialog = new Dialog("Join Private Lobby", skin);
        dialog.text("Enter password for lobby: " + lobbyName);
        
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter password...");
        passwordField.setPasswordMode(true); // Hide password input
        
        dialog.getContentTable().row();
        dialog.getContentTable().add(passwordField).width(300).padTop(5);
        
        TextButton confirmButton = new TextButton("Join", skin);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = passwordField.getText().trim();
                if (!password.isEmpty()) {
                    dialog.hide();
                    
                    // Send join lobby message with password
                    String joinMessage = "JOIN_LOBBY:" + lobbyId + ":" + password;
                    sendMessage(joinMessage);
                    addChatMessage("System", "Attempting to join private lobby: " + lobbyName);
                    
                    // Set a timeout for the join operation
                    startJoinTimeout();
                } else {
                    addChatMessage("System", "Password cannot be empty!");
                }
            }
        });
        
        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });
        
        dialog.getButtonTable().add(confirmButton);
        dialog.getButtonTable().add(cancelButton);
        
        dialog.show(stage);
    }
    
    private void startJoinTimeout() {
        // Cancel any existing timeout
        if (joinTimeoutThread != null && joinTimeoutThread.isAlive()) {
            joinTimeoutThread.interrupt();
        }
        
        // Start a new timeout thread
        joinTimeoutThread = new Thread(() -> {
            try {
                Thread.sleep(15000); // 15 second timeout (increased from 10)
                if (currentLobbyId == null) {
                    Gdx.app.postRunnable(() -> {
                        addChatMessage("System", "Join lobby timed out. Please try again or check server connection.");
                        // Try to refresh lobby list to get updated information
                        onRefreshLobbies();
                    });
                }
            } catch (InterruptedException e) {
                // Thread was interrupted, ignore
            }
        });
        joinTimeoutThread.setDaemon(true);
        joinTimeoutThread.start();
    }
    
    private void sendMessage(String message) {
        if (isConnected && out != null) {
            out.println(message);
            // Flush to ensure message is sent immediately
            out.flush();
        } else {
            System.err.println("Cannot send message: not connected to server");
            addChatMessage("System", "Error: Not connected to server");
        }
    }
    
    private void testServerConnection() {
        if (!isConnected) {
            addChatMessage("System", "Not connected to server. Attempting to reconnect...");
            connectToServer();
            return;
        }
        
        // Send a simple ping message to test connection
        sendMessage("PING");
        addChatMessage("System", "Testing server connection...");
    }
    
    private void updateLobbyUI() {
        if (currentLobbyId != null) {
            // Show lobby chat interface
            chatInputField.setVisible(true);
            chatInputField.setDisabled(false);
            
            // Update UI to show we're in a lobby
            addChatMessage("System", "You are now in lobby: " + currentLobbyId);
            
            // Enable chat functionality by ensuring the input field is properly configured
            chatInputField.setMessageText("Type your message...");
            
            // Make sure the chat area is visible and scrollable
            chatScrollPane.setVisible(true);
            chatArea.setVisible(true);
            
            // Check if current player is the host and show/hide start game and delete lobby buttons accordingly
            boolean isHost = checkIfCurrentPlayerIsHost();
            startGameButton.setVisible(isHost);
            deleteLobbyButton.setVisible(isHost);
            
            // Refresh the lobby list to show current state
            onRefreshLobbies();
        } else {
            // Not in a lobby, hide start game and delete buttons
            startGameButton.setVisible(false);
            deleteLobbyButton.setVisible(false);
        }
    }
    
    private boolean checkIfCurrentPlayerIsHost() {
        if (currentLobbyId == null || playerNickname == null) {
            return false;
        }
        
        // Find the current lobby in our lobby list
        for (SimpleLobbyInfo lobby : lobbies) {
            if (lobby.lobbyId.equals(currentLobbyId)) {
                return lobby.hostPlayerId.equals(playerNickname);
            }
        }
        
        return false;
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
    }
    

    

    
    /**
     * Hides the lobby UI elements when transitioning to game mode
     */
    private void hideLobbyUI() {
        // Hide all lobby-related UI elements
        if (mainLayout != null) mainLayout.setVisible(false);
        if (lobbyTable != null) lobbyTable.setVisible(false);
        if (chatTable != null) chatTable.setVisible(false);
        
        // Show a simple "Game in Progress" message
        showGameInProgressMessage();
        
        System.out.println("LobbyView: Lobby UI hidden, connection maintained");
    }
    
    /**
     * Shows a simple message indicating the game is in progress
     */
    private void showGameInProgressMessage() {
        // Clear the stage and show a simple message
        stage.clear();
        
        Table gameTable = new Table();
        gameTable.setFillParent(true);
        
        Label titleLabel = new Label("Game in Progress", skin);
        titleLabel.setFontScale(2);
        titleLabel.setColor(Color.GREEN);
        
        Label statusLabel = new Label("Connection maintained for chat functionality", skin);
        statusLabel.setColor(Color.WHITE);
        
        Label instructionLabel = new Label("Press C to toggle chat", skin);
        instructionLabel.setColor(Color.YELLOW);
        
        gameTable.add(titleLabel).center().padBottom(20).row();
        gameTable.add(statusLabel).center().padBottom(10).row();
        gameTable.add(instructionLabel).center().padBottom(20).row();
        
        // Add a simple chat display
        if (chatArea != null) {
            chatArea.setVisible(true);
            gameTable.add(chatArea).width(400).height(200).padTop(20).row();
        }
        
        stage.addActor(gameTable);
        
        System.out.println("LobbyView: Game in progress message displayed");
    }
    
    // Screen implementations
    @Override
    public void render(float delta) {
        // Handle input for keybinds
        handleInput();
        
        stage.act(delta);
        stage.draw();
    }
    
    /**
     * Handles keyboard input for keybinds
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            togglePlayerList();
        }
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
     * Shows the player list dialog
     */
    private void showPlayerList() {

        
        if (playerListDialog == null) {

            createPlayerListDialog();
        }
        
        // Request player list from server
        if (isConnected) {

            sendMessage("ALL_PLAYERS_REQUEST");
            addChatMessage("System", "Requesting player information...");
        } else {

            addChatMessage("System", "Not connected to server!");
            return;
        }
        

        
        if (stage != null && playerListDialog != null) {
            playerListDialog.show(stage);
            isPlayerListVisible = true;

            System.out.println("LobbyView: Dialog visible after show: " + playerListDialog.isVisible());
            System.out.println("LobbyView: Dialog modal: " + playerListDialog.isModal());
        } else {
            System.out.println("LobbyView: ERROR - Cannot show dialog - stage: " + (stage != null) + ", dialog: " + (playerListDialog != null));
        }
    }
    
    /**
     * Hides the player list dialog
     */
    private void hidePlayerList() {
        if (playerListDialog != null) {
            playerListDialog.hide();
        }
        isPlayerListVisible = false;
    }
    
    /**
     * Creates the player list dialog
     */
    private void createPlayerListDialog() {
        playerListDialog = new Dialog("All Players & Lobbies", skin);
        playerListDialog.setModal(false);
        playerListDialog.setResizable(true);
        
        // Create content table
        Table contentTable = new Table();
        contentTable.pad(10);
        
        // Title
        Label titleLabel = new Label("Connected Players and Their Lobbies", skin);
        titleLabel.setFontScale(1.2f);
        titleLabel.setColor(Color.BLUE);
        contentTable.add(titleLabel).center().padBottom(10).row();
        
        // Instructions
        Label instructionLabel = new Label("Press P again to close this window", skin);
        instructionLabel.setColor(Color.GRAY);
        contentTable.add(instructionLabel).center().padBottom(15).row();
        
        // Player list area (will be populated when data arrives)
        this.playerListLabel = new Label("Loading player information...", skin);
        this.playerListLabel.setWrap(true);
        this.playerListLabel.setAlignment(Align.left);
        contentTable.add(this.playerListLabel).width(400).height(300).padTop(10).row();
        
        // Test button to manually update the label
        TextButton testButton = new TextButton("Test Update", skin);
        testButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerListLabel != null) {
                    playerListLabel.setText("TEST UPDATE - Manual button click worked!");
                    System.out.println("LobbyView: Test button clicked - label updated manually");
                } else {
                    System.out.println("LobbyView: Test button clicked - but playerListLabel is null!");
                }
            }
        });
        contentTable.add(testButton).center().padTop(5).row();
        
        // Test button to show server data
        TextButton showDataButton = new TextButton("Show Server Data", skin);
        showDataButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerListLabel != null) {
                    String testData = "Connected Players and Their Lobbies:\n" +
                                    "================================\n\n" +
                                    "• emdies → LOBBY_1754967636785_844\n" +
                                    "• thewhat → No Lobby\n\n" +
                                    "Lobby Details:\n" +
                                    "==============\n" +
                                    "Lobby: My Lobby (LOBBY_1754967636785_844)\n" +
                                    "  Players: 1/4\n" +
                                    "  Host: emdies\n" +
                                    "  Status: Waiting for Players";
                    
                    playerListLabel.setText(testData);
                    System.out.println("LobbyView: Show Server Data button clicked - showing test data");
                } else {
                    System.out.println("LobbyView: Show Server Data button clicked - but playerListLabel is null!");
                }
            }
        });
        contentTable.add(showDataButton).center().padTop(5).row();
        
        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hidePlayerList();
            }
        });
        contentTable.add(closeButton).center().padTop(10);
        
        playerListDialog.getContentTable().add(contentTable);
        
        System.out.println("LobbyView: Player list dialog created successfully");
    }
    
    /**
     * Updates the player list dialog with data from server
     */
    private void updatePlayerListDialog(String playerData) {
        System.out.println("LobbyView: updatePlayerListDialog called with data: " + playerData);
        System.out.println("LobbyView: Data length: " + playerData.length());
        System.out.println("LobbyView: First 100 chars: " + playerData.substring(0, Math.min(100, playerData.length())));
        
        if (playerListDialog != null && isPlayerListVisible && playerListLabel != null) {
            System.out.println("LobbyView: All conditions met, updating label");
            System.out.println("LobbyView: Dialog visible: " + playerListDialog.isVisible());
            System.out.println("LobbyView: Label text before update: " + playerListLabel.getText());
            
            playerListLabel.setText(playerData);
            
            System.out.println("LobbyView: Label text after update: " + playerListLabel.getText());
            
            // Force a redraw of the dialog
            if (playerListDialog.isVisible()) {
                playerListDialog.pack();
                System.out.println("LobbyView: Dialog packed after update");
            }
            
            // Force stage to redraw
            if (stage != null) {
                stage.act(0);
                System.out.println("LobbyView: Stage acted after update");
            }
        } else {
            System.out.println("LobbyView: Cannot update player list - dialog: " + (playerListDialog != null) + 
                             ", visible: " + isPlayerListVisible + ", label: " + (playerListLabel != null));
            if (playerListDialog != null) {
                System.out.println("LobbyView: Dialog visible: " + playerListDialog.isVisible());
                System.out.println("LobbyView: Dialog modal: " + playerListDialog.isModal());
            }
        }
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose() {
        // Close the lobby server connection
        if (isConnected) {
            try {
                System.out.println("LobbyView: Closing lobby server connection during dispose");
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error during dispose: " + e.getMessage());
            }
        }
        
        if (stage != null) {
            stage.dispose();
        }
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
}
