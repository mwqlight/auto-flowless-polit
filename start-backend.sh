#!/bin/bash

# Backend startup script
# This script checks if the backend application is already running, stops it if necessary, and then starts it

# Configuration
PORT=8080
BACKEND_DIR="/usr/local/share/data-01-space/auto-flowless-polit/auto-flowless-polit-backend"
JAR_NAME="web.jar"
LOG_FILE="/usr/local/share/data-01-space/auto-flowless-polit/backend.log"

# Check if the application is already running
PID=$(lsof -t -i:$PORT 2>/dev/null)

if [ -n "$PID" ]; then
    echo "Backend application is already running on port $PORT with PID $PID. Stopping it..."
    kill -15 $PID
    sleep 5
    
    # Check if the process is still running
    PID=$(lsof -t -i:$PORT 2>/dev/null)
    if [ -n "$PID" ]; then
        echo "Failed to stop the application gracefully. Forcing shutdown..."
        kill -9 $PID
        sleep 2
    fi
    
    echo "Backend application stopped successfully"
fi

# Navigate to the backend directory
cd $BACKEND_DIR

# Build the application if the JAR file doesn't exist
if ! ls web/target/$JAR_NAME 2>/dev/null; then
    echo "Building the backend application..."
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "Build failed. Exiting..."
        exit 1
    fi
fi

# Start the application
echo "Starting backend application on port $PORT..."
nohup java -jar web/target/$JAR_NAME --server.port=$PORT > $LOG_FILE 2>&1 &

# Wait for the application to start
sleep 10

# Check if the application is running
PID=$(lsof -t -i:$PORT 2>/dev/null)
if [ -n "$PID" ]; then
    echo "Backend application started successfully on port $PORT with PID $PID"
    echo "Logs can be found at: $LOG_FILE"
else
    echo "Failed to start backend application. Check logs at: $LOG_FILE"
    exit 1
fi