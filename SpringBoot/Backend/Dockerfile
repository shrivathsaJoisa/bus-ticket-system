# Use a small Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Work inside /app in the container
WORKDIR /app

# Copy the built JAR into the container
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
