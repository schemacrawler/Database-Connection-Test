#!/bin/bash

# Create a new shell script to build the Docker image using Docker Buildx
# Include commands to build the image for both ARM and AMD64 systems

# Set the image name
IMAGE_NAME="connecttest"

# Create a new builder instance
docker buildx create --name mybuilder --use

# Build the Docker image for both ARM and AMD64 architectures
docker buildx build --platform linux/amd64,linux/arm64 -t $IMAGE_NAME .

# Remove the builder instance
docker buildx rm mybuilder
