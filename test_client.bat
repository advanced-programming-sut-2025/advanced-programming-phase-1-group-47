@echo off
echo Compiling Test Client...
echo.

REM Compile the client
javac SimpleTestClient.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed! Please check for errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting Test Client...
echo Make sure the server is running first!
echo.

REM Run the client
java SimpleTestClient

echo.
echo Client stopped. Press any key to exit...
pause > nul
