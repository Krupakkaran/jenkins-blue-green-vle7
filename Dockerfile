# Dockerfile
# Use a slim Java 17 runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Define the name of the JAR file created by Maven
# NOTE: Verify the exact name of the JAR file in your project's target/ directory
ARG JAR_FILE=target/sample-java-app-0.0.1-SNAPSHOT.jar 

# Copy the application JAR file from the build context
COPY ${JAR_FILE} app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
