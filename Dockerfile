# Use Eclipse Temurin on Alpine as the base image
FROM eclipse-temurin:8-jre-alpine

# Copy the target/bin directory to /app in the Docker image
COPY target/bin /app

# Set the working directory to /app
WORKDIR /app

# Set the entry point to run.sh
ENTRYPOINT ["sh", "./run.sh"]
