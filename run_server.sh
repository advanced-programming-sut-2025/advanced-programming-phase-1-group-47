#!/bin/bash

echo "Starting Stardew Valley Multiplayer Server..."
echo

# Check if port argument is provided
if [ -z "$1" ]; then
    PORT=8080
    echo "Using default port: $PORT"
else
    PORT=$1
    echo "Using port: $PORT"
fi

echo
echo "Compiling project..."
./gradlew build

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo
echo "Starting server on port $PORT..."
echo "Press Ctrl+C to stop the server"
echo

java -cp "build/libs/*:libs/*" ServerLauncher $PORT 