# Multiplayer Implementation - Phase 1

## Overview
This document describes the initial implementation of the multiplayer structure for the Stardew Valley game.

## Current Implementation

### 1. Main Menu Flow
The game now starts with a main menu (`InitPageView`) instead of going directly to the game. The flow is:

```
Main Menu (InitPageView)
├── Login → Game Mode Selection
├── Sign Up → Game Mode Selection
└── Exit
```

### 2. Game Mode Selection
After successful authentication (login or signup), users are presented with a game mode selection screen (`GameModeSelectionView`) that offers:

- **Singleplayer**: Starts the game as before (current implementation)
- **Multiplayer**: Placeholder for future implementation
- **Back to Main Menu**: Returns to the main menu

### 3. File Structure
New files created:
- `core/src/main/java/com/StardewValley/View/GameModeSelectionView.java`
- `core/src/main/java/com/StardewValley/controllers/GameModeSelectionController.java`

Modified files:
- `core/src/main/java/com/StardewValley/Main.java` - Now starts with main menu
- `core/src/main/java/com/StardewValley/controllers/LoginMenuController.java` - Redirects to game mode selection
- `core/src/main/java/com/StardewValley/controllers/SignUpMenuController.java` - Redirects to game mode selection

## How It Works

### Authentication Flow
1. User clicks Login or Sign Up from main menu
2. User completes authentication
3. Upon success, user is automatically redirected to game mode selection
4. User chooses between singleplayer or multiplayer

### Singleplayer Mode
- Works exactly as the game did before
- Starts `GameScreen` directly
- No changes to existing game logic

### Multiplayer Mode (Future)
- Currently shows "Coming Soon" message
- Will be implemented in future phases
- Placeholder for server connection, player synchronization, etc.

## Next Steps for Multiplayer Implementation

### Phase 2: Basic Networking
- Implement client-server communication
- Add player position synchronization
- Basic multiplayer game state management

### Phase 3: Game Features
- Shared world state
- Player interaction systems
- Multiplayer-specific game mechanics

### Phase 4: Advanced Features
- Chat system
- Player permissions
- Mod support

## Testing
To test the current implementation:
1. Run the game
2. You should see the main menu instead of going directly to the game
3. Try logging in or signing up
4. You should be redirected to the game mode selection screen
5. Singleplayer should work as before
6. Multiplayer should show a placeholder message

## Notes
- The current implementation maintains backward compatibility
- Singleplayer mode is unchanged
- Multiplayer is a placeholder that can be expanded incrementally
- All existing game functionality is preserved
