package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class ChatMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    private final String content;
    private final String sender;
    private final boolean isPrivate;
    private final String recipient;
    private final String lobbyId;
    
    public ChatMessage(String content, String sender, boolean isPrivate, String recipient) {
        super(MessageType.CHAT_MESSAGE);
        this.content = content;
        this.sender = sender;
        this.isPrivate = isPrivate;
        this.recipient = recipient;
        this.lobbyId = null;
    }
    
    public ChatMessage(String content, String sender, String lobbyId) {
        super(MessageType.CHAT_MESSAGE);
        this.content = content;
        this.sender = sender;
        this.isPrivate = false;
        this.recipient = null;
        this.lobbyId = lobbyId;
    }
    
    public String getContent() { return content; }
    public String getSender() { return sender; }
    public boolean isPrivate() { return isPrivate; }
    public String getRecipient() { return recipient; }
    public String getLobbyId() { return lobbyId; }
} 