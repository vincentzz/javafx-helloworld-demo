# JavaFX Hello World with jlink

A simple JavaFX Hello World application built with Java 21 and Maven, with support for creating native executables using jlink for both Windows and Mac.

## Prerequisites

- Java 21 (JDK)
- Maven 3.8 or later
- Platform-specific JDK for cross-compilation (if building for multiple platforms)

## Project Structure

```
javafx-helloworld/
├── src/
│   └── main/
│       └── java/
│           ├── module-info.java
│           └── com/example/helloworld/
│               └── HelloWorldApp.java
├── pom.xml
├── build-mac.sh
├── build-windows.bat
└── README.md
```

## Building and Running

### 1. Run the application directly
```bash
mvn clean javafx:run
```

### 2. Compile and package
```bash
mvn clean compile
```

### 3. Create jlink executables

#### For Mac (on Mac)
```bash
./build-mac.sh
```

#### For Windows (on Windows)
```batch
build-windows.bat
```

#### Manual jlink build
```bash
mvn clean package -Pjlink
```

## Generated Executables

After running the build scripts, you'll find the executables in:
- `target/javafx-helloworld/` - Contains the complete JRE with your application
- `target/javafx-helloworld/bin/JavaFXHelloWorld` (Mac/Linux) or `target/javafx-helloworld/bin/JavaFXHelloWorld.exe` (Windows)

## Distribution Packages

The build scripts automatically create versioned zip packages for distribution:
- **Mac**: `target/dist/javafx-helloworld-1.0.0-mac-aarch64.zip`
- **Windows**: `target/dist/javafx-helloworld-1.0.0-windows-x64.zip`
- **Maven**: `target/javafx-helloworld-1.0.0-distribution.zip` (platform-agnostic)

These zip files contain the complete self-contained application and can be distributed to end users.

## Features

- ✅ Java 21 support
- ✅ JavaFX 21 UI framework
- ✅ Maven build system
- ✅ Modular application (module-info.java)
- ✅ jlink packaging for reduced size executables
- ✅ Cross-platform support (Windows, Mac, Linux)
- ✅ Self-contained executables (no JRE installation required)

## Application Features

- Simple Hello World GUI
- Interactive button with feedback
- Responsive layout
- Modern styling

## Notes

- The generated executables are self-contained and don't require Java to be installed on the target system
- The jlink tool creates a custom JRE with only the modules your application needs, resulting in smaller distribution size
- For cross-platform builds, you'll need the target platform's JDK
