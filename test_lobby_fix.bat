@echo off
echo Testing Fixed Lobby System...
echo.

echo This will test the lobby system with better error handling.
echo Make sure the server is running first!
echo.

REM Test the integration
javac SimpleGameLauncher.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Testing server connection with improved error handling...
java SimpleGameLauncher

echo.
echo Test completed. Press any key to exit...
pause > nul
