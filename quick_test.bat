@echo off
echo Quick Test - Stardew Valley Multiplayer Server
echo ==============================================
echo.

echo Step 1: Compiling Java files...
javac SimpleGameServer.java SimpleClientTest.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed! Check if Java is installed and in PATH.
    pause
    exit /b 1
)

echo Step 2: Starting server...
start "Game Server" java SimpleGameServer 8080

echo Step 3: Waiting for server to start...
timeout /t 2 /nobreak > nul

echo Step 4: Testing client connection...
java SimpleClientTest localhost 8080

echo.
echo Test completed! Press any key to stop the server...
pause

echo Stopping server...
taskkill /f /im java.exe > nul 2>&1

echo Test finished!
pause 