@echo off
REM JavaFX Hello World - Windows Build Script
REM This script builds a self-contained executable for Windows using jlink

echo =========================================
echo JavaFX Hello World - Windows Build Script
echo =========================================

REM Check if Java 21 is available
echo Checking Java version...
java -version 2>&1 | findstr /C:"version"

REM Check if Maven is available
echo Checking Maven version...
mvn -version | findstr /C:"Apache Maven"

echo.
echo Step 1: Cleaning previous builds...
call mvn clean

echo.
echo Step 2: Compiling the application...
call mvn compile

if %ERRORLEVEL% neq 0 (
    echo ❌ Compilation failed. Please check your code.
    pause
    exit /b 1
)

echo.
echo Step 3: Creating jlink executable for Windows...
call mvn package -Pjlink

if %ERRORLEVEL% neq 0 (
    echo ❌ jlink packaging failed.
    pause
    exit /b 1
)

echo.
echo ✅ Build completed successfully!
echo.
echo Generated files:
echo 📁 Executable location: target\javafx-helloworld\
echo 🚀 Run the app: target\javafx-helloworld\bin\JavaFXHelloWorld.exe
echo.
echo To distribute this app:
echo 1. Copy the entire 'target\javafx-helloworld' folder to the target Windows machine
echo 2. Run: javafx-helloworld\bin\JavaFXHelloWorld.exe
echo.

echo Step 4: Creating versioned distribution package...

REM Get version and app name from Maven
for /f "delims=" %%i in ('mvn help:evaluate -Dexpression=project.version -q -DforceStdout') do set VERSION=%%i
for /f "delims=" %%i in ('mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout') do set APP_NAME=%%i

REM Create distribution directory
if not exist "target\dist" mkdir "target\dist"

REM Create versioned zip package
set ZIP_NAME=%APP_NAME%-%VERSION%-windows-x64.zip
cd target
powershell Compress-Archive -Path "javafx-helloworld" -DestinationPath "dist\%ZIP_NAME%" -Force
cd ..

echo 📦 Created distribution package: target\dist\%ZIP_NAME%
echo.
echo ✨ Windows executable is ready!
echo.
pause
