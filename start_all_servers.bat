@echo off
echo ========================================
echo    STARDEW VALLEY MULTIPLAYER SERVERS
echo ========================================
echo.
echo This script will start both servers needed for multiplayer:
echo - Lobby Server (Port 8080) - for lobby management
echo - Game Server (Port 8081) - for in-game features
echo.
echo Press any key to start both servers...
pause > nul

echo.
echo Starting Lobby Server on port 8080...
start "Lobby Server (Port 8080)" cmd /k "cd core\src\main\java && javac -cp . com\StardewValley\servers\SimpleMultiplayerServer.java && java -cp . com.StardewValley.servers.SimpleMultiplayerServer"

echo Waiting for Lobby Server to start...
timeout /t 3 /nobreak > nul

echo.
echo Starting Game Server on port 8081...
start "Game Server (Port 8081)" cmd /k "cd core\src\main\java && javac -cp . com\StardewValley\servers\GameServer.java && java -cp . com.StardewValley.servers.GameServer"

echo.
echo ========================================
echo Both servers are starting...
echo.
echo Lobby Server: http://localhost:8080
echo Game Server: http://localhost:8081
echo.
echo Keep both server windows open while playing!
echo.
echo To stop servers, close their respective windows
echo ========================================
echo.
pause
