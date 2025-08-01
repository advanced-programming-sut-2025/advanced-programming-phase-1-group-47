package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class LoginResponseMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final boolean success;
    private final String message;
    
    public LoginResponseMessage(boolean success, String message) {
        super(MessageType.LOGIN_RESPONSE);
        this.success = success;
        this.message = message;
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
} 