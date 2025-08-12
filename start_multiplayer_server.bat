@echo off
echo Starting Multiplayer Game Server...
echo.
echo This will start the multiplayer server on port 8080
echo Make sure the game client is configured to connect to localhost:8080
echo.
echo Press any key to start the server...
pause > nul

cd /d "%~dp0"
java -cp "core/build/classes/java/main;core/build/resources/main;lwjgl3/build/classes/java/main;lwjgl3/build/resources/main" com.StardewValley.network.MultiplayerGameServer

echo.
echo Server stopped. Press any key to exit...
pause > nul
