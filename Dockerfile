# Use a base image with Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the build output (JAR file) to the working directory in the container
COPY build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
