package com.StardewValley.View;

import com.StardewValley.model.GameAssetManager;
import com.StardewValley.network.NetworkManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class ConnectionDialog {
    private Dialog dialog;
    private TextField serverAddressField;
    private TextField serverPortField;
    private TextField usernameField;
    private TextField passwordField;
    private Label statusLabel;
    private boolean connected = false;
    
    public ConnectionDialog(Stage stage) {
        this.dialog = new Dialog("Connect to Server", GameAssetManager.getGameAssetManager().getSkin());
        this.dialog.setModal(true);
        
        initializeUI();
        setupEventHandlers();
        
        dialog.show(stage);
    }
    
    private void initializeUI() {
        // Server address field
        serverAddressField = new TextField("localhost", GameAssetManager.getGameAssetManager().getSkin());
        serverAddressField.setMessageText("Server Address");
        
        // Server port field
        serverPortField = new TextField("8080", GameAssetManager.getGameAssetManager().getSkin());
        serverPortField.setMessageText("Port");
        
        // Username field
        usernameField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        usernameField.setMessageText("Username");
        
        // Password field
        passwordField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        
        // Status label
        statusLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());
        statusLabel.setAlignment(Align.center);
        
        // Add fields to dialog
        dialog.getContentTable().add(new Label("Server Address:", GameAssetManager.getGameAssetManager().getSkin())).pad(10);
        dialog.getContentTable().add(serverAddressField).pad(10);
        dialog.getContentTable().row();
        
        dialog.getContentTable().add(new Label("Port:", GameAssetManager.getGameAssetManager().getSkin())).pad(10);
        dialog.getContentTable().add(serverPortField).pad(10);
        dialog.getContentTable().row();
        
        dialog.getContentTable().add(new Label("Username:", GameAssetManager.getGameAssetManager().getSkin())).pad(10);
        dialog.getContentTable().add(usernameField).pad(10);
        dialog.getContentTable().row();
        
        dialog.getContentTable().add(new Label("Password:", GameAssetManager.getGameAssetManager().getSkin())).pad(10);
        dialog.getContentTable().add(passwordField).pad(10);
        dialog.getContentTable().row();
        
        dialog.getContentTable().add(statusLabel).colspan(2).pad(10);
        
        // Add buttons
        dialog.button("Connect", true);
        dialog.button("Cancel", false);
    }
    
    private void setupEventHandlers() {
        // Handle connect button
        dialog.getButtonTable().getCells().get(0).getActor().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                connectToServer();
            }
        });
    }
    
    private void connectToServer() {
        String address = serverAddressField.getText().trim();
        String portText = serverPortField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (address.isEmpty() || portText.isEmpty() || username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            return;
        }
        
        try {
            int port = Integer.parseInt(portText);
            
            statusLabel.setText("Connecting...");
            
            // Connect to server
            NetworkManager networkManager = NetworkManager.getInstance();
            boolean success = networkManager.connectToServer(address, port);
            
            if (success) {
                // Send login message
                networkManager.sendMessage(new com.StardewValley.network.messages.LoginMessage(username, password));
                statusLabel.setText("Connected! Logging in...");
                connected = true;
            } else {
                statusLabel.setText("Failed to connect to server");
            }
            
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid port number");
        }
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public String getUsername() {
        return usernameField.getText().trim();
    }
    
    public void hide() {
        dialog.hide();
    }
} 