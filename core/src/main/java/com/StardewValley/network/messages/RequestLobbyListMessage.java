package com.StardewValley.network.messages;

import com.StardewValley.network.NetworkMessage;

public class RequestLobbyListMessage extends NetworkMessage {
    private static final long serialVersionUID = 1L;
    
    public RequestLobbyListMessage() {
        super(MessageType.REQUEST_LOBBY_LIST);
    }
} 