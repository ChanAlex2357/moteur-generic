#!/bin/bash

# Paths
BIN_DIR="bin"
RES_DIR="dev/resources"
CONFIG_DIR="dev/config"
DEPLOY_DIR="build"
JAR_NAME="project.jar"

# Clean up previous deployment
echo "Cleaning up previous deployment..."
rm -f "$DEPLOY_DIR/$JAR_NAME"

# Copy resources and configuration files
echo "Copying resources and configuration files..."
cp -r "$RES_DIR"/* "$BIN_DIR"
cp -r "$CONFIG_DIR"/* "$BIN_DIR"

# Create JAR file
echo "Creating JAR file..."
jar cvf "$DEPLOY_DIR/$JAR_NAME" -C "$BIN_DIR" .

echo "Deployment completed successfully."
