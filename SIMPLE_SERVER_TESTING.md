# Simple Multiplayer Server Testing Guide

This guide explains how to test the basic multiplayer functionality using the standalone server and test client.

## Quick Start

### 1. Start the Server
```bash
# Double-click this file:
start_simple_server.bat
```

You should see:
```
Simple Multiplayer server started on port 8080
Ready to accept connections...
```

### 2. Test with Multiple Clients
Open multiple command prompt windows and run:
```bash
# In each window:
test_client.bat
```

## What You Can Test

### Basic Connection
- Multiple clients can connect simultaneously
- Each client gets a unique player ID
- Server shows connection status

### Lobby Management
- **Create Lobby**: Set name and max players
- **Join Lobby**: Join existing lobbies
- **Leave Lobby**: Leave current lobby
- **Lobby List**: See all available lobbies

### Chat System
- Send messages to specific lobbies
- Real-time message delivery
- Multiple players can chat simultaneously

## Testing Scenarios

### Scenario 1: Basic Lobby Creation
1. Start server
2. Start Client 1 (Player1)
3. Create a lobby named "Test Lobby" with max 4 players
4. Verify lobby appears in lobby list

### Scenario 2: Multiple Players Joining
1. Start Client 2 (Player2)
2. Request lobby list
3. Join the "Test Lobby"
4. Verify both players are in the lobby

### Scenario 3: Chat Testing
1. Both players in the same lobby
2. Send chat messages
3. Verify messages appear for both players

### Scenario 4: Lobby Management
1. Player2 leaves the lobby
2. Verify lobby still exists
3. Player1 can continue using the lobby

## Server Commands

The server understands these message formats:

- `CREATE_LOBBY:name:maxPlayers:hostId`
- `JOIN_LOBBY:lobbyId`
- `LEAVE_LOBBY:lobbyId`
- `LOBBY_LIST_REQUEST`
- `CHAT:lobbyId:message`

## Troubleshooting

### Server Won't Start
- Check if port 8080 is already in use
- Ensure Java is installed and in PATH
- Check firewall settings

### Client Can't Connect
- Verify server is running
- Check server console for connection messages
- Ensure no firewall blocking localhost:8080

### Messages Not Working
- Check server console for error messages
- Verify message format is correct
- Ensure client is connected to server

## Next Steps

Once you've verified the simple server works:

1. **Fix the main project compilation issues**
2. **Integrate the working network code into the game**
3. **Replace the simple server with the full MultiplayerGameServer**

## Files Created

- `SimpleMultiplayerServer.java` - Standalone server
- `SimpleTestClient.java` - Test client
- `start_simple_server.bat` - Server launcher
- `test_client.bat` - Client launcher

## Technical Details

- **Protocol**: Simple text-based messages over TCP
- **Port**: 8080 (configurable)
- **Max Players**: 8 concurrent connections
- **Lobby Limit**: Configurable per lobby
- **Message Format**: Colon-separated values

This simple server provides the foundation for testing multiplayer functionality without the complexity of the full game integration.
