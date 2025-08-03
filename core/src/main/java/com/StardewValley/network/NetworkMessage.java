package com.StardewValley.network;

public class NetworkMessage {
    private MessageType type;
    private String playerId;
    private String data;
    private long timestamp;

    public NetworkMessage() {
        this.timestamp = System.currentTimeMillis();
    }

    public NetworkMessage(MessageType type, String playerId, String data) {
        this.type = type;
        this.playerId = playerId;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
} 