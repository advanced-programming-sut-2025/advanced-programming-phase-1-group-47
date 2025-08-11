@echo off
echo Testing Multiplayer Integration...
echo.

REM Compile the test launcher
javac SimpleGameLauncher.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed! Please check for errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Testing server connection...
echo Make sure the server is running first!
echo.

REM Run the test
java SimpleGameLauncher

echo.
echo Test completed. Press any key to exit...
pause > nul
