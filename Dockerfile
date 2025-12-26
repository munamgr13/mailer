# Java 17 runtime (official replacement for OpenJDK)
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/mailer-0.0.1-SNAPSHOT.jar app.jar

# Expose port used by Spring Boot
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
