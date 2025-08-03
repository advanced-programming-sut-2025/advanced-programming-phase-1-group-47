@echo off
echo Manual Test - Stardew Valley Multiplayer Server
echo ===============================================
echo.

echo This script will help you test the server manually.
echo.

echo Step 1: Compiling Java files...
javac SimpleGameServer.java SimpleClientTest.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Compilation failed!
    echo Please make sure:
    echo 1. Java is installed and in your PATH
    echo 2. You're running this from the project directory
    echo 3. SimpleGameServer.java and SimpleClientTest.java exist
    echo.
    pause
    exit /b 1
)

echo SUCCESS: Files compiled successfully!
echo.

echo Step 2: Starting server in a new window...
echo The server will start in a separate window.
echo Keep this window open to run the client test.
echo.
start "Game Server" java SimpleGameServer 8080

echo Step 3: Waiting for server to start...
timeout /t 3 /nobreak > nul

echo Step 4: Testing client connection...
echo.
java SimpleClientTest localhost 8080

echo.
echo Test completed!
echo The server is still running in the other window.
echo You can close it manually or press any key to stop it.
pause

echo Stopping server...
taskkill /f /im java.exe > nul 2>&1

echo Done!
pause 