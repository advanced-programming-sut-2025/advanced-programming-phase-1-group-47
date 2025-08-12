@echo off
echo Testing Multiplayer Game with Chat
echo ==================================
echo.
echo This test verifies the new functionality:
echo - Single-player game for each player (same as main menu button)
echo - Chat opens when pressing 'C' key
echo - Players can communicate while playing independently
echo.
echo Step 1: Starting the server...
echo.
java -cp ".;libs/*" SimpleMultiplayerServer
echo.
echo Server stopped. Press any key to exit.
pause >nul
