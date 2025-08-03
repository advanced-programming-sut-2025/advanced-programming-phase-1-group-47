# Troubleshooting Guide - Stardew Valley Multiplayer

## Quick Fixes

### 1. If `run_server.bat` fails:

**Problem**: Gradle build fails or Java not found

**Solution**: Use the standalone server instead:
```bash
# Option 1: Use the fixed run_server.bat (now uses javac instead of gradle)
run_server.bat

# Option 2: Use manual test
manual_test.bat

# Option 3: Compile and run manually
javac SimpleGameServer.java
java SimpleGameServer 8080
```

### 2. If Java is not found:

**Problem**: `'javac' is not recognized as an internal or external command`

**Solution**: 
1. Install Java JDK (not just JRE)
2. Add Java to your PATH environment variable
3. Restart your command prompt

**Check Java installation**:
```bash
java -version
javac -version
```

### 3. If port is already in use:

**Problem**: `Address already in use` error

**Solution**: Use a different port:
```bash
java SimpleGameServer 8081
java SimpleClientTest localhost 8081
```

### 4. If compilation fails:

**Problem**: Missing files or syntax errors

**Solution**: 
1. Make sure you're in the correct directory
2. Check that these files exist:
   - `SimpleGameServer.java`
   - `SimpleClientTest.java`
3. Run the manual test script for detailed error messages

## Step-by-Step Testing

### Method 1: Manual Testing
```bash
# 1. Open Command Prompt in project directory
# 2. Compile the server
javac SimpleGameServer.java

# 3. Start server in one terminal
java SimpleGameServer 8080

# 4. Open another terminal and test client
javac SimpleClientTest.java
java SimpleClientTest localhost 8080
```

### Method 2: Using Scripts
```bash
# Use the manual test script
manual_test.bat

# Or use the quick test
quick_test.bat
```

## Expected Output

### Successful Server Start:
```
Simple Game server started on port 8080
```

### Successful Client Connection:
```
Connecting to server: localhost:8080
Connected to server!
Sent: Hello from client!
Received: Server received: Hello from client!
```

## Common Error Messages

### 1. "Compilation failed!"
- Check Java installation
- Verify file locations
- Check for syntax errors

### 2. "Connection refused"
- Server not running
- Wrong port number
- Firewall blocking connection

### 3. "Address already in use"
- Port 8080 is busy
- Use different port
- Kill existing Java processes

### 4. "Class not found"
- Missing .class files
- Wrong directory
- Compilation failed

## Alternative Solutions

### If Gradle is required:
```bash
# Use the original approach with Gradle
./gradlew build
java -cp "build/libs/*;libs/*" ServerLauncher 8080
```

### If you need the full game integration:
1. Fix the Game class methods first
2. Use the GameServer instead of SimpleGameServer
3. Ensure all dependencies are properly imported

## Getting Help

1. **Check Java installation**: `java -version`
2. **Verify file locations**: `dir *.java`
3. **Test basic compilation**: `javac SimpleGameServer.java`
4. **Check port availability**: `netstat -an | findstr 8080`

## Quick Commands

```bash
# Check Java
java -version

# Check files
dir *.java

# Compile
javac SimpleGameServer.java SimpleClientTest.java

# Run server
java SimpleGameServer 8080

# Test client (in another terminal)
java SimpleClientTest localhost 8080
``` 