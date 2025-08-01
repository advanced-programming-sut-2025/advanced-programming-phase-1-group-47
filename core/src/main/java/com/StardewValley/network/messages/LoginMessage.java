package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class LoginMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String username;
    private final String password;
    
    public LoginMessage(String username, String password) {
        super(MessageType.LOGIN);
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
} 