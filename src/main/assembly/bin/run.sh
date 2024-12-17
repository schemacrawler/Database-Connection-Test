#!/bin/bash

# Resolve the directory of the script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Set the classpath to include all jars in the lib directory
CLASSPATH="$DIR/lib/*"

# Run the main class
java -cp "$CLASSPATH" us.fatehi.database_connector_test.DatabaseConnectorMain "$@"
