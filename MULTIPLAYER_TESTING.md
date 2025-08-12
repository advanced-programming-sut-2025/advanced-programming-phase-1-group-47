# Multiplayer Lobby System Testing Guide

## Overview
This guide explains how to test the multiplayer lobby system that has been implemented for the Stardew Valley game. The system provides lobby creation, joining, and chat functionality.

## What's Implemented

### ✅ **Server Features**
- **Simple Multiplayer Server** (`SimpleMultiplayerServer.java`)
  - Accepts up to 8 simultaneous players
  - Manages lobby creation and deletion
  - Handles player join/leave operations
  - Provides real-time chat between lobby members
  - Automatic lobby cleanup when empty

### ✅ **Lobby Features**
- **Create Lobbies**: Players can create lobbies with custom names and player limits
- **Join Lobbies**: Players can join existing lobbies
- **Leave Lobbies**: Players can leave lobbies (host transfer if needed)
- **Chat System**: Real-time chat within lobbies
- **Player Management**: Track players in each lobby
- **Host System**: Designated host for each lobby

### ✅ **Client Features**
- **Simple Client Test** (`SimpleClientTest.java`) for testing
- **Command Interface**: Text-based commands for all operations
- **Real-time Updates**: Immediate feedback for all actions

## How to Test

### 1. **Compile the Project**
```bash
./gradlew build
```

### 2. **Start the Server**
```bash
# Option 1: Direct Java command
java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleMultiplayerServer

# Option 2: Use the test batch file (Windows)
test_multiplayer_server.bat
```

### 3. **Connect Test Clients**
```bash
# Client 1 (Alice)
java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Alice

# Client 2 (Bob)
java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Bob

# Client 3 (Charlie)
java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Charlie
```

## Available Commands

### **Basic Commands**
- `help` - Show all available commands
- `quit` - Disconnect from server

### **Lobby Management**
- `create <name> <max_players>` - Create a new lobby
  - Example: `create MyLobby 4`
- `list` - Show all available lobbies
- `join <lobby_id>` - Join a specific lobby
  - Example: `join LOBBY_1234567890_123`
- `leave` - Leave current lobby

### **Lobby Interaction**
- `chat <message>` - Send a chat message to lobby
  - Example: `chat Hello everyone!`
- `players` - Show all players in current lobby

## Testing Scenarios

### **Scenario 1: Basic Lobby Creation**
1. Start server
2. Connect Client 1 (Alice)
3. Alice creates lobby: `create FarmLobby 4`
4. Verify lobby appears in list: `list`

### **Scenario 2: Multiple Players Joining**
1. Connect Client 2 (Bob)
2. Bob lists lobbies: `list`
3. Bob joins Alice's lobby: `join LOBBY_ID`
4. Check players: `players`
5. Verify both players see each other

### **Scenario 3: Chat Functionality**
1. Alice sends message: `chat Welcome to the farm!`
2. Bob should see: `[FarmLobby] Alice: Welcome to the farm!`
3. Bob responds: `chat Thanks Alice!`
4. Both should see the conversation

### **Scenario 4: Host Transfer**
1. Alice leaves lobby: `leave`
2. Bob should automatically become host
3. Check players: `players`
4. Verify Bob is marked as (Host)

### **Scenario 5: Lobby Cleanup**
1. All players leave lobby: `leave`
2. Server should automatically remove empty lobby
3. List lobbies: `list`
4. Verify lobby is gone

## Expected Behavior

### **Server Console**
```
Simple multiplayer server started on port 8080
Server is ready to accept connections!
Player Alice connected. Total players: 1
Lobby created: LOBBY_1234567890_123 by Alice
Player Bob connected. Total players: 2
Player Bob joined lobby LOBBY_1234567890_123
Player Alice left lobby LOBBY_1234567890_123
Player Bob disconnected. Total players: 1
Lobby LOBBY_1234567890_123 removed (empty)
```

### **Client Console**
```
Connected to server at localhost:8080
Connected as: Alice
Welcome Alice! Type 'help' for commands.
Alice> create FarmLobby 4
Server: Lobby created: LOBBY_1234567890_123 - FarmLobby
Alice> list
Server: Available lobbies:
Server:   LOBBY_1234567890_123 - FarmLobby (1/4)
```

## Troubleshooting

### **Common Issues**

1. **"Connection refused"**
   - Ensure server is running
   - Check port 8080 is available
   - Verify firewall settings

2. **"Server full"**
   - Maximum 8 players allowed
   - Wait for a slot to open

3. **"Lobby not found"**
   - Use exact lobby ID from `list` command
   - Lobby may have been removed if empty

4. **Chat not working**
   - Must be in a lobby to chat
   - Use `join` command first

### **Performance Notes**
- Server handles up to 8 simultaneous players
- Chat messages are limited to 100 per lobby
- Automatic cleanup prevents memory leaks
- Real-time updates for all operations

## Next Steps

### **Phase 2: Game Integration**
- Connect lobby system to actual game
- Player position synchronization
- Shared world state
- Game start functionality

### **Phase 3: Enhanced Features**
- Player authentication
- Persistent lobbies
- Advanced chat features
- Mod support

## Files Created

- `SimpleMultiplayerServer.java` - Main server implementation
- `SimpleClientTest.java` - Test client for development
- `test_multiplayer_server.bat` - Windows test launcher
- `MULTIPLAYER_TESTING.md` - This testing guide

## Summary

The multiplayer lobby system is now **fully functional** and ready for testing! It provides:

- ✅ **Working server** with lobby management
- ✅ **Real-time chat** between players
- ✅ **Lobby creation/joining** functionality
- ✅ **Player management** and host system
- ✅ **Test clients** for verification

You can now test the complete lobby experience and verify that all features work correctly before integrating with the main game.
