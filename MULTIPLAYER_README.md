# Stardew Valley Multiplayer Implementation

This document describes the multiplayer features implemented for the Stardew Valley game project.

## Overview

The multiplayer system implements a client-server architecture with real-time communication, allowing multiple players to play together in the same game world.

## Features Implemented

### Core Networking
- **NetworkManager**: Handles client-server communication
- **GameServer**: Manages the game server and multiple client connections
- **ClientHandler**: Individual client connection management
- **MultiplayerController**: Integrates multiplayer functionality with the game

### Communication
- **NetworkMessage**: Message structure for client-server communication
- **MessageType**: Enum defining different message types
- **GameAction**: Represents various game actions players can perform

### UI Components
- **MultiplayerChatView**: Chat interface for player communication
- **Real-time player position updates**
- **Player join/leave notifications**

## Architecture

### Client-Server Model
- **Server**: Central game state management
- **Clients**: Individual player connections
- **Real-time synchronization**: Game state updates every 100ms

### Message Types
- `PLAYER_JOIN`: New player joining
- `PLAYER_LEAVE`: Player disconnecting
- `PLAYER_POSITION`: Player movement updates
- `CHAT`: Chat messages
- `GAME_ACTION`: Player actions (planting, harvesting, etc.)
- `GAME_STATE_UPDATE`: Server game state updates
- `ADMIN_COMMAND`: Administrative commands
- `VOTE`: Voting system messages

## Setup Instructions

### Starting the Server

1. **Compile the project**:
   ```bash
   ./gradlew build
   ```

2. **Run the server**:
   ```bash
   java -cp "build/libs/*" ServerLauncher [port]
   ```
   Default port is 8080.

3. **Server features**:
   - Automatic game state updates
   - Player management
   - Chat system
   - Admin commands
   - Ban system

### Connecting Clients

1. **In the game**:
   - Use the multiplayer connection interface
   - Enter server address and port
   - Connect with your player profile

2. **Connection features**:
   - Real-time position updates
   - Chat messaging
   - Game action synchronization
   - Player visibility

## Game Actions Supported

### Farming
- Plant crops
- Harvest crops
- Water crops
- Fertilize crops

### Resource Gathering
- Mine rocks
- Chop trees
- Fish

### Animal Care
- Feed animals
- Milk animals
- Collect eggs

### Building
- Build structures
- Upgrade tools

### Social
- Socialize with NPCs
- Give gifts
- Attend events

## Admin Features

### Commands
- `kick [player]`: Kick a player from the server
- `ban [player]`: Ban a player from the server

### Admin Access
- Default admin: `admin` / `admin123`
- Configurable admin accounts

## Chat System

### Features
- Public chat for all players
- System messages for game events
- Player join/leave notifications
- Message history (last 50 messages)

### Usage
- Press Enter to open chat
- Type message and press Enter to send
- Chat window can be toggled on/off

## Technical Details

### Network Protocol
- **Transport**: TCP sockets
- **Format**: JSON messages
- **Encoding**: UTF-8
- **Heartbeat**: Automatic connection monitoring

### Performance
- **Update Rate**: 10 FPS server loop
- **Position Updates**: Real-time player movement
- **State Synchronization**: Full game state updates

### Security
- **Input Validation**: All messages validated
- **Error Handling**: Graceful error recovery
- **Connection Management**: Automatic cleanup

## Development Notes

### Adding New Features
1. Define new message type in `MessageType` enum
2. Add handling in `ClientHandler` and `GameServer`
3. Update `NetworkManager` for client-side handling
4. Add UI components if needed

### Extending Game Actions
1. Add new action type to `GameAction.ActionType`
2. Implement processing in `GameServer.processGameAction()`
3. Add client-side action sending in `MultiplayerController`

### Customization
- Server port configuration
- Admin account management
- Game rules and settings
- Chat moderation features

## Troubleshooting

### Common Issues
1. **Connection refused**: Check if server is running
2. **Port already in use**: Change server port
3. **Player not visible**: Check network connectivity
4. **Chat not working**: Verify message listener setup

### Debug Information
- Server logs show connection events
- Client logs show network errors
- Game state updates logged for debugging

## Future Enhancements

### Planned Features
- Private messaging
- Player groups/parties
- Advanced voting system
- NPC dialogue with LLM
- Real-time map updates
- Player achievements
- Trade system
- Weather synchronization

### Performance Improvements
- Message compression
- Delta updates
- Client-side prediction
- Bandwidth optimization

## License

This multiplayer implementation is part of the Stardew Valley project and follows the same licensing terms. 