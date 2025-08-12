@echo off
echo ========================================
echo    GAME SERVER LAUNCHER
echo ========================================
echo.
echo Starting Game Server on port 8081...
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java and try again
    pause
    exit /b 1
)

REM Navigate to the core directory where GameServer.java is located
cd core\src\main\java

REM Compile the GameServer if needed
echo Compiling GameServer...
javac -cp . com\StardewValley\View\GameServer.java
if errorlevel 1 (
    echo ERROR: Failed to compile GameServer
    pause
    exit /b 1
)

echo.
echo GameServer compiled successfully!
echo.
echo Starting server...
echo.

REM Run the GameServer
java -cp . com.StardewValley.View.GameServer

echo.
echo Game Server has stopped.
pause
