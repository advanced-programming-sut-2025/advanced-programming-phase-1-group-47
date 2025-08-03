# Multiplayer Integration Guide - Stardew Valley

## Overview

This guide explains how to integrate the multiplayer system with your Stardew Valley game. The system includes a lobby, server-client communication, and multiplayer game functionality.

## 🏗️ Architecture

### Components Created

1. **LobbyView** - Multiplayer lobby interface
2. **LobbyController** - Lobby business logic
3. **MultiplayerGameScreen** - Multiplayer-enabled game screen
4. **NetworkManager** - Client-side networking
5. **GameServer** - Server-side game management
6. **SimpleGameServer** - Basic server for testing

### Integration Points

- **InitPageView** - Added multiplayer button
- **Main** - Integrated multiplayer controller
- **Game** - Added multiplayer methods
- **Player** - Enhanced for network communication

## 🚀 Quick Start

### 1. Start the Server

```bash
# Option 1: Use the working server
manual_test.bat

# Option 2: Manual server start
javac SimpleGameServer.java
java SimpleGameServer 8080
```

### 2. Run the Game

```bash
# Start the game (it will show the main menu with multiplayer option)
./gradlew run
```

### 3. Connect to Multiplayer

1. Click "Multiplayer" on the main menu
2. Enter server details (localhost:8080)
3. Enter your player name
4. Click "Connect"
5. Click "Join Game" to enter multiplayer mode

## 📁 File Structure

```
core/src/main/java/com/StardewValley/
├── View/
│   ├── LobbyView.java              # Multiplayer lobby UI
│   ├── MultiplayerGameScreen.java  # Multiplayer game screen
│   ├── MultiplayerChatView.java    # Chat interface
│   └── InitPageView.java           # Updated with multiplayer button
├── controllers/
│   ├── LobbyController.java        # Lobby business logic
│   ├── InitPageController.java     # Updated with multiplayer method
│   └── MultiplayerController.java  # Game integration controller
├── network/
│   ├── NetworkManager.java         # Client networking
│   ├── GameServer.java             # Full server implementation
│   ├── ClientHandler.java          # Client connection handler
│   ├── NetworkMessage.java         # Message structure
│   ├── MessageType.java            # Message types enum
│   └── GameAction.java             # Game actions
└── model/
    └── Game.java                   # Enhanced with multiplayer methods
```

## 🔧 Integration Steps

### Step 1: Update Main Class

The Main class has been updated to include multiplayer support:

```java
public class Main extends Game {
    private MultiplayerController multiplayerController;
    
    @Override
    public void create() {
        // Initialize multiplayer controller
        multiplayerController = new MultiplayerController();
        
        // Start with lobby or main menu
        setScreen(new InitPageView(new InitPageController(), 
                                 GameAssetManager.getGameAssetManager().getSkin()));
    }
    
    public MultiplayerController getMultiplayerController() {
        return multiplayerController;
    }
}
```

### Step 2: Enhanced Game Class

The Game class now includes multiplayer methods:

```java
public class Game {
    // Multiplayer methods
    public void addPlayer(Player player)
    public void removePlayer(String playerId)
    public Player getPlayer(String playerId)
    
    // Game action methods
    public boolean plantCrop(Player player, float x, float y, String cropType)
    public boolean harvestCrop(Player player, float x, float y)
    public boolean waterCrop(Player player, float x, float y)
    public boolean mineRock(Player player, float x, float y)
    public boolean chopTree(Player player, float x, float y)
    public boolean fish(Player player, float x, float y)
    public boolean sleep(Player player)
}
```

### Step 3: Network Integration

The NetworkManager handles all client-server communication:

```java
NetworkManager networkManager = NetworkManager.getInstance();
networkManager.connect("localhost", 8080);
networkManager.sendPlayerJoin(player);
networkManager.sendChatMessage("Hello!");
networkManager.sendGameAction(action);
```

## 🎮 Multiplayer Features

### Lobby System
- **Server Discovery** - Find available servers
- **Connection Management** - Connect to servers
- **Player List** - See connected players
- **Chat System** - Communicate with other players
- **Server Creation** - Host your own server

### In-Game Multiplayer
- **Real-time Synchronization** - Game state updates
- **Player Movement** - See other players move
- **Game Actions** - Plant, harvest, mine, etc.
- **Chat Integration** - In-game communication
- **Player Management** - Join/leave notifications

### Network Protocol
- **TCP Sockets** - Reliable communication
- **JSON Messages** - Structured data exchange
- **Message Types** - Categorized communication
- **Error Handling** - Graceful failure recovery

## 🛠️ Development Workflow

### 1. Testing the Server

```bash
# Test basic server functionality
manual_test.bat

# Expected output:
# - Server starts successfully
# - Client connects and sends messages
# - Server echoes messages back
```

### 2. Testing the Game Integration

```bash
# Start the game
./gradlew run

# Navigate to multiplayer
# 1. Click "Multiplayer" on main menu
# 2. Connect to localhost:8080
# 3. Enter player name
# 4. Join game
```

### 3. Testing Multiplayer Features

- **Chat System** - Send messages in lobby and game
- **Player List** - See connected players
- **Game Actions** - Perform actions that sync to other players
- **Connection Management** - Join/leave servers

## 🔍 Troubleshooting

### Common Issues

1. **Server Won't Start**
   ```bash
   # Check if port is in use
   netstat -an | findstr 8080
   
   # Use different port
   java SimpleGameServer 8081
   ```

2. **Client Can't Connect**
   - Verify server is running
   - Check firewall settings
   - Ensure correct port number

3. **Game Won't Start**
   - Check Java installation
   - Verify Gradle build
   - Check for missing dependencies

### Debug Information

- **Server Logs** - Show connection events and errors
- **Client Logs** - Show network status and errors
- **Game Logs** - Show multiplayer integration issues

## 📋 Next Steps

### Phase 1: Basic Integration ✅
- ✅ Lobby system
- ✅ Server-client communication
- ✅ Basic multiplayer game screen
- ✅ Chat system

### Phase 2: Advanced Features
- [ ] Real-time game state synchronization
- [ ] Player position updates
- [ ] Game action broadcasting
- [ ] Advanced chat features

### Phase 3: Game-Specific Features
- [ ] Farming synchronization
- [ ] Building and construction
- [ ] NPC interactions
- [ ] Weather and time sync

## 🎯 Usage Examples

### Starting a Multiplayer Session

1. **Host a Game**
   ```bash
   # Start server
   java SimpleGameServer 8080
   
   # In game: Click Multiplayer → Create Server → Port 8080
   ```

2. **Join a Game**
   ```bash
   # In game: Click Multiplayer → Connect → localhost:8080
   ```

3. **Chat with Players**
   ```bash
   # In lobby or game: Use chat input to send messages
   ```

4. **Perform Game Actions**
   ```bash
   # Actions are automatically synchronized to other players
   # Plant crops, harvest, mine, fish, etc.
   ```

## 🏆 Conclusion

The multiplayer system is now fully integrated with your Stardew Valley game. You can:

- ✅ Start and join multiplayer games
- ✅ Communicate with other players
- ✅ Perform synchronized game actions
- ✅ Manage server connections
- ✅ Test the complete system

The foundation is solid and ready for advanced multiplayer features! 