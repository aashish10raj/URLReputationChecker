# Stage 1: Build Stage
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy Maven configuration file
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application and create JAR file
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage
# Use Ubuntu-based image - has better ARM64 support
FROM eclipse-temurin:17-jre

WORKDIR /app

# Create non-root user (Ubuntu syntax)
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose port 8080
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
