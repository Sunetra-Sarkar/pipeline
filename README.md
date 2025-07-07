# ArtisanTek Java Sample - Lightweight Edition

A beautiful and extremely lightweight Java 21 web application built with Javalin, designed for memory-constrained environments.

## Features

- ✨ Beautiful gradient background with animations
- 🎨 Modern glassmorphism design
- 📱 Responsive layout for mobile and desktop
- 🎯 Java 21, runs in a tiny footprint.

## Prerequisites

- Java 21 JDK
- Maven 3.6+

## Build Instructions

```bash
# Navigate to the project directory
cd jenkins/java-sample-21

# Clean and build the project to create a fat JAR
mvn clean package

# The JAR file will be created in the target directory
# File name: java-sample-21-1.0.0.jar
```

## Run Instructions

To run the application with a **strict memory limit** (e.g., 64MB of heap space), use the `-Xmx` flag. This is highly recommended for your EC2 instance.

```bash
# Run the application with a 64MB memory cap
java -jar target/java-sample-21-1.0.0.jar

# The application will start on port 5000
# Open your browser and go to: http://localhost:5000
```

This version uses significantly less memory than the Spring Boot application, making it ideal for your server.

Enjoy your beautiful and lightweight ArtisanTek web application! 🎉 
>>>>>>> 61b58a5 (added src file)
