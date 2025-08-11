# Multiplayer Setup Guide

This guide explains how to set up and use the multiplayer lobby system for the Stardew Valley game.

## Prerequisites

- Java 8 or higher
- Gradle build system
- The game project compiled

## Starting the Multiplayer Server

1. **Build the project first:**
   ```bash
   ./gradlew build
   ```

2. **Start the multiplayer server:**
   - On Windows: Double-click `start_multiplayer_server.bat`
   - On Linux/Mac: Run `java -cp "core/build/classes/java/main:core/build/resources/main:lwjgl3/build/classes/java/main:lwjgl3/build/resources/main" com.StardewValley.network.MultiplayerGameServer`

3. **Server will start on port 8080** - you should see:
   ```
   Multiplayer server started on port 8080
   ```

## Using the Multiplayer System

### 1. Launch the Game
- Start the game normally
- Click "Login as Guest" or login with your account
- Select "Multiplayer" from the game mode selection

### 2. Nickname Selection
- A dialog will appear asking you to choose a nickname
- Enter your desired nickname
- Click "Confirm" to proceed

### 3. Lobby Features
Once connected to the server, you can:

- **Create a Lobby:**
  - Enter a lobby name
  - Set maximum players (default: 4)
  - Click "Create Lobby"

- **Join a Lobby:**
  - View available lobbies in the list
  - Click "Join" next to any available lobby
  - Lobbies that are full or have started games will show disabled join buttons

- **Chat:**
  - Type messages in the chat input field
  - Press Enter or click "Send" to send messages
  - Chat messages are visible to all players in the same lobby

- **Refresh Lobbies:**
  - Click "Refresh" to update the lobby list

- **Leave Lobby:**
  - Use the "Back to Menu" button to return to the main menu

## Server Features

The multiplayer server provides:

- **Lobby Management:** Create, join, and leave game lobbies
- **Real-time Chat:** In-lobby communication between players
- **Player Synchronization:** Real-time position updates (for future game integration)
- **Connection Management:** Automatic handling of player connections/disconnections

## Troubleshooting

### Server Won't Start
- Ensure Java is installed and in your PATH
- Check that port 8080 is not already in use
- Verify the project has been built successfully

### Can't Connect to Server
- Ensure the server is running
- Check that the client is configured to connect to `localhost:8080`
- Verify firewall settings allow connections on port 8080

### Lobby Issues
- Refresh the lobby list if lobbies don't appear
- Ensure you have a valid nickname selected
- Check server console for error messages

## Technical Details

- **Server Port:** 8080 (configurable)
- **Protocol:** TCP sockets with Java serialization
- **Message Types:** Player join/leave, lobby management, chat, position updates
- **Max Players:** 8 concurrent players per server
- **Update Rate:** 60 position updates per second

## Future Enhancements

The current implementation includes:
- ✅ Lobby creation and management
- ✅ Real-time chat system
- ✅ Player connection handling
- ✅ Basic server infrastructure

Planned features:
- 🔄 Actual multiplayer game synchronization
- 🔄 Shared world state
- 🔄 Player interaction systems
- 🔄 Game save/load for multiplayer sessions

## Testing

To test the system:
1. Start the server
2. Launch multiple game instances
3. Create and join lobbies
4. Test chat functionality
5. Verify lobby management features

## Support

If you encounter issues:
1. Check the server console for error messages
2. Verify all prerequisites are met
3. Ensure the project builds successfully
4. Check network connectivity and firewall settings
