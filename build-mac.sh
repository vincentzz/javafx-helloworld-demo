#!/bin/bash

# JavaFX Hello World - Mac Build Script
# This script builds a self-contained executable for macOS using jlink

echo "========================================="
echo "JavaFX Hello World - Mac Build Script"
echo "========================================="

# Check if Java 21 is available
echo "Checking Java version..."
java -version 2>&1 | head -n 1

# Check if Maven is available
echo "Checking Maven version..."
mvn -version | head -n 1

echo ""
echo "Step 1: Cleaning previous builds..."
mvn clean

echo ""
echo "Step 2: Compiling the application..."
mvn compile

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed. Please check your code."
    exit 1
fi

echo ""
echo "Step 3: Creating jlink executable for Mac..."
mvn package -Pjlink

if [ $? -ne 0 ]; then
    echo "❌ jlink packaging failed."
    exit 1
fi

echo ""
echo "✅ Build completed successfully!"
echo ""
echo "Generated files:"
echo "📁 Executable location: target/javafx-helloworld/"
echo "🚀 Run the app: ./target/javafx-helloworld/bin/JavaFXHelloWorld"
echo ""
echo "To distribute this app:"
echo "1. Copy the entire 'target/javafx-helloworld' folder to the target Mac"
echo "2. Run: ./javafx-helloworld/bin/JavaFXHelloWorld"
echo ""

# Make the executable file executable (in case it's not)
chmod +x target/javafx-helloworld/bin/JavaFXHelloWorld

echo ""
echo "Step 4: Creating versioned distribution package..."

# Get version from pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
APP_NAME=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)

# Create distribution directory
DIST_DIR="target/dist"
mkdir -p "$DIST_DIR"

# Create versioned zip package
ZIP_NAME="${APP_NAME}-${VERSION}-mac-aarch64.zip"
cd target
zip -r "dist/${ZIP_NAME}" javafx-helloworld/
cd ..

echo "📦 Created distribution package: target/dist/${ZIP_NAME}"
echo ""
echo "✨ Mac executable is ready!"
