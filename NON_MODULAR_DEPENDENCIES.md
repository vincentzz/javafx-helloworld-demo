# Handling Non-Modular Dependencies with jlink

This document explains how to use non-modular JAR dependencies in a modular JavaFX application that uses jlink for creating self-contained executables.

## The Problem

When you add a non-modular dependency to a modular Java application, jlink will fail with an error because it can only work with modular JARs (JARs that contain a `module-info.class` file).

## The Solution: ModiTect Maven Plugin

We use the ModiTect Maven Plugin to automatically add module descriptors to non-modular JARs during the build process.

### How It Works

1. **Dependency Addition**: Add your non-modular dependency normally to `pom.xml`
2. **ModiTect Configuration**: Configure the ModiTect plugin to add module descriptors
3. **Module Info Update**: Update your `module-info.java` to require the new module
4. **Build Process**: ModiTect creates modularized versions of the JARs
5. **jlink Success**: jlink can now work with the modularized dependencies

## Example Configuration

### 1. Add Non-Modular Dependency

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.14.0</version>
</dependency>
```

### 2. Configure ModiTect Plugin

```xml
<plugin>
    <groupId>org.moditect</groupId>
    <artifactId>moditect-maven-plugin</artifactId>
    <version>1.0.0.RC2</version>
    <executions>
        <execution>
            <id>add-module-infos</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>add-module-info</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.directory}/modules</outputDirectory>
                <modules>
                    <module>
                        <artifact>
                            <groupId>org.apache.commons</groupId>
                            <artifactId>commons-lang3</artifactId>
                        </artifact>
                        <moduleInfoSource>
                            module org.apache.commons.lang3 {
                                exports org.apache.commons.lang3;
                                exports org.apache.commons.lang3.builder;
                                exports org.apache.commons.lang3.concurrent;
                                exports org.apache.commons.lang3.event;
                                exports org.apache.commons.lang3.exception;
                                exports org.apache.commons.lang3.function;
                                exports org.apache.commons.lang3.math;
                                exports org.apache.commons.lang3.mutable;
                                exports org.apache.commons.lang3.reflect;
                                exports org.apache.commons.lang3.stream;
                                exports org.apache.commons.lang3.text;
                                exports org.apache.commons.lang3.time;
                                exports org.apache.commons.lang3.tuple;
                                exports org.apache.commons.lang3.util;
                            }
                        </moduleInfoSource>
                    </module>
                </modules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 3. Update module-info.java

```java
module javafx.helloworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.commons.lang3;  // Add this line
    
    exports com.example.helloworld;
}
```

## How to Determine Module Exports

To determine what packages to export in the module descriptor:

1. **Check JAR Contents**: Use `jar -tf commons-lang3-3.14.0.jar | grep "\.class$" | head -20`
2. **Use jdeps**: Run `jdeps --module-path path/to/jar commons-lang3-3.14.0.jar`
3. **Library Documentation**: Check the library's documentation for package structure
4. **IDE Inspection**: Use your IDE to browse the JAR contents

## Multiple Non-Modular Dependencies

For multiple dependencies, add multiple `<module>` blocks:

```xml
<modules>
    <module>
        <artifact>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </artifact>
        <moduleInfoSource>
            module org.apache.commons.lang3 {
                exports org.apache.commons.lang3;
                // ... other exports
            }
        </moduleInfoSource>
    </module>
    <module>
        <artifact>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </artifact>
        <moduleInfoSource>
            module com.google.common {
                exports com.google.common.base;
                exports com.google.common.collect;
                // ... other exports
            }
        </moduleInfoSource>
    </module>
</modules>
```

## Alternative Solutions

### 1. jpackage Instead of jlink
- Use jpackage for final packaging
- Works with both modular and non-modular dependencies
- Creates native installers but larger file sizes

### 2. Fat JAR + Custom JRE
- Create an uber-jar with all dependencies
- Use jlink to create a custom JRE separately
- Combine them for distribution

### 3. Automatic Modules
- Use `--add-modules ALL-MODULE-PATH`
- Less control over module boundaries
- May include unnecessary dependencies

## Troubleshooting

### Common Issues

1. **Missing Exports**: If you get `ClassNotFoundException`, add the missing package to exports
2. **Transitive Dependencies**: ModiTect handles transitive dependencies automatically
3. **Service Loading**: For libraries using ServiceLoader, add `uses` clauses to module descriptor

### Debug Commands

```bash
# Check modularized JAR
jar --describe-module --file=target/modules/commons-lang3-3.14.0.jar

# List modules in jlink image
./target/javafx-helloworld/bin/java --list-modules

# Verify module dependencies
jdeps --module-path target/modules target/javafx-helloworld-1.0.0.jar
```

## Benefits of This Approach

1. **Maintains jlink Benefits**: Small, optimized runtime images
2. **Full Module System Support**: Proper module boundaries and dependencies
3. **Build-Time Verification**: Catches module issues during compilation
4. **No Runtime Dependencies**: Self-contained executables
5. **Cross-Platform**: Works on Windows, Mac, and Linux

## Example Usage in Code

```java
// Using the non-modular dependency
import org.apache.commons.lang3.StringUtils;

public class Example {
    public void example() {
        String text = StringUtils.capitalize("hello world");
        System.out.println(text); // Output: Hello world
    }
}
```

This solution allows you to use any non-modular library with jlink while maintaining all the benefits of the module system and jlink's optimized runtime images.
