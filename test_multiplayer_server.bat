@echo off
echo Testing Multiplayer Server
echo =========================
echo.

echo Starting server...
start "Multiplayer Server" java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleMultiplayerServer

echo Waiting for server to start...
timeout /t 3 /nobreak > nul

echo.
echo Starting test clients...
echo.

echo Starting Client 1 (Alice)...
start "Client 1 - Alice" java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Alice

echo Starting Client 2 (Bob)...
start "Client 2 - Bob" java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Bob

echo Starting Client 3 (Charlie)...
start "Client 3 - Charlie" java -cp "core/build/classes/java/main" com.StardewValley.network.SimpleClientTest localhost 8080 Charlie

echo.
echo Test clients started!
echo.
echo Instructions:
echo 1. In each client window, type 'help' to see available commands
echo 2. Try creating a lobby: 'create MyLobby 4'
echo 3. List lobbies: 'list'
echo 4. Join a lobby: 'join LOBBY_ID'
echo 5. Send chat messages: 'chat Hello everyone!'
echo 6. Check players: 'players'
echo 7. Leave lobby: 'leave'
echo 8. Quit client: 'quit'
echo.
echo Press any key to close this window...
pause > nul
