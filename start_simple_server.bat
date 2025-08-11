@echo off
echo Compiling Simple Multiplayer Server...
echo.

REM Compile the server
javac SimpleMultiplayerServer.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed! Please check for errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting Simple Multiplayer Server on port 8080...
echo.
echo This server provides:
echo - Lobby creation and management
echo - Player joining/leaving
echo - Real-time chat
echo - Basic multiplayer functionality
echo.
echo Press Ctrl+C to stop the server
echo.

REM Run the server
java SimpleMultiplayerServer

echo.
echo Server stopped. Press any key to exit...
pause > nul
