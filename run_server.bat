@echo off
echo Starting Stardew Valley Multiplayer Server...
echo.

REM Check if port argument is provided
if "%1"=="" (
    set PORT=8080
    echo Using default port: %PORT%
) else (
    set PORT=%1
    echo Using port: %PORT%
)

echo.
echo Compiling SimpleGameServer...
javac SimpleGameServer.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Starting server on port %PORT%...
echo Press Ctrl+C to stop the server
echo.

java SimpleGameServer %PORT%

pause 