# Stage 1: Build the Quarkus application
FROM gradle:8.14-jdk17 AS builder

# Set working directory
WORKDIR /app

# Copy Gradle configuration files
COPY --chown=gradle:gradle build.gradle settings.gradle gradle.properties /app/

# Copy Gradle wrapper
COPY --chown=gradle:gradle gradlew /app/
COPY --chown=gradle:gradle gradle /app/gradle

# Fix cache permissions
RUN chown -R gradle:gradle /home/gradle

# Download dependencies
RUN ./gradlew dependencies --no-daemon --stacktrace || true

# Copy source code
COPY --chown=gradle:gradle src /app/src

# Build with memory limits and debug
RUN ./gradlew build -x test --no-daemon --stacktrace

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy Quarkus artifacts
COPY --from=builder /app/build/quarkus-app /app/quarkus-app

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/quarkus-app/quarkus-run.jar"]