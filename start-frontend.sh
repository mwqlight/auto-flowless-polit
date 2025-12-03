#!/bin/bash

# Frontend startup script
# This script checks if the frontend application is already running, stops it if necessary, and then starts it

# Configuration
PORT=3000
FRONTEND_DIR="/usr/local/share/data-01-space/auto-flowless-polit/auto-flowless-polit-frontend"
LOG_FILE="/usr/local/share/data-01-space/auto-flowless-polit/frontend.log"
NODE_VERSION="20.10.0"

# Check if the application is already running
PID=$(lsof -t -i:$PORT 2>/dev/null)

if [ -n "$PID" ]; then
    echo "Frontend application is already running on port $PORT with PID $PID. Stopping it..."
    kill -15 $PID
    sleep 5
    
    # Check if the process is still running
    PID=$(lsof -t -i:$PORT 2>/dev/null)
    if [ -n "$PID" ]; then
        echo "Failed to stop the application gracefully. Forcing shutdown..."
        kill -9 $PID
        sleep 2
    fi
    
    echo "Frontend application stopped successfully"
fi

# Navigate to the frontend directory
cd $FRONTEND_DIR

# Load nvm and switch to the required node version
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion

# Install the required node version if not already installed
nvm install $NODE_VERSION 2>/dev/null

# Switch to the required node version
nvm use $NODE_VERSION

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing frontend dependencies..."
    npm install
    if [ $? -ne 0 ]; then
        echo "Dependency installation failed. Exiting..."
        exit 1
    fi
fi

# Start the application
echo "Starting frontend application on port $PORT..."
nohup npm run dev -- --port $PORT > $LOG_FILE 2>&1 &

# Wait for the application to start
sleep 10

# Check if the application is running
PID=$(lsof -t -i:$PORT 2>/dev/null)
if [ -n "$PID" ]; then
    echo "Frontend application started successfully on port $PORT with PID $PID"
    echo "Logs can be found at: $LOG_FILE"
    echo "Access the application at: http://localhost:$PORT"
else
    echo "Failed to start frontend application. Check logs at: $LOG_FILE"
    exit 1
fi