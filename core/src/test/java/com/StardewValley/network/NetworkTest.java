package com.StardewValley.network;

import com.StardewValley.network.messages.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class NetworkTest {
    
    @Test
    public void testGameLobbyCreation() {
        GameLobby lobby = new GameLobby("Test Lobby", "testUser");
        
        assertEquals("Test Lobby", lobby.getName());
        assertEquals("testUser", lobby.getCreatorUsername());
        assertEquals(0, lobby.getCurrentPlayerCount());
        assertEquals(4, lobby.getMaxPlayers());
        assertFalse(lobby.isGameStarted());
        assertTrue(lobby.canJoin());
    }
    
    @Test
    public void testGameLobbyPlayerManagement() {
        GameLobby lobby = new GameLobby("Test Lobby", "creator");
        
        // Add players
        lobby.addPlayer("player1");
        lobby.addPlayer("player2");
        
        assertEquals(2, lobby.getCurrentPlayerCount());
        assertTrue(lobby.getPlayers().contains("player1"));
        assertTrue(lobby.getPlayers().contains("player2"));
        
        // Remove player
        lobby.removePlayer("player1");
        assertEquals(1, lobby.getCurrentPlayerCount());
        assertFalse(lobby.getPlayers().contains("player1"));
        assertTrue(lobby.getPlayers().contains("player2"));
    }
    
    @Test
    public void testGameLobbyCapacity() {
        GameLobby lobby = new GameLobby("Test Lobby", "creator");
        
        // Fill lobby
        lobby.addPlayer("player1");
        lobby.addPlayer("player2");
        lobby.addPlayer("player3");
        lobby.addPlayer("player4");
        
        assertEquals(4, lobby.getCurrentPlayerCount());
        assertFalse(lobby.canJoin()); // Should be full
        
        // Try to add another player
        lobby.addPlayer("player5");
        assertEquals(4, lobby.getCurrentPlayerCount()); // Should not add
        assertFalse(lobby.getPlayers().contains("player5"));
    }
    
    @Test
    public void testGameLobbyGameStart() {
        GameLobby lobby = new GameLobby("Test Lobby", "creator");
        lobby.addPlayer("player1");
        
        assertFalse(lobby.isGameStarted());
        assertTrue(lobby.canJoin());
        
        lobby.startGame("game123");
        
        assertTrue(lobby.isGameStarted());
        assertFalse(lobby.canJoin());
        assertEquals("game123", lobby.getGameId());
    }
    
    @Test
    public void testMessageCreation() {
        LoginMessage loginMsg = new LoginMessage("testUser", "password123");
        assertEquals(NetworkMessage.MessageType.LOGIN, loginMsg.getType());
        assertEquals("testUser", loginMsg.getUsername());
        assertEquals("password123", loginMsg.getPassword());
        
        ChatMessage chatMsg = new ChatMessage("Hello World", "sender", false, "recipient");
        assertEquals(NetworkMessage.MessageType.CHAT_MESSAGE, chatMsg.getType());
        assertEquals("Hello World", chatMsg.getContent());
        assertEquals("sender", chatMsg.getSender());
        assertFalse(chatMsg.isPrivate());
        assertEquals("recipient", chatMsg.getRecipient());
    }
    
    @Test
    public void testNetworkManagerSingleton() {
        NetworkManager instance1 = NetworkManager.getInstance();
        NetworkManager instance2 = NetworkManager.getInstance();
        
        assertSame(instance1, instance2);
    }
    
    @Test
    public void testLobbyEmptyState() {
        GameLobby lobby = new GameLobby("Empty Lobby", "creator");
        
        assertTrue(lobby.isEmpty());
        
        lobby.addPlayer("player1");
        assertFalse(lobby.isEmpty());
        
        lobby.removePlayer("player1");
        assertTrue(lobby.isEmpty());
    }
} 