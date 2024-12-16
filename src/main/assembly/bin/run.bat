@echo off
REM Set the classpath to include all jars in the lib directory
set CLASSPATH=lib/*

REM Execute the main class
java -cp %CLASSPATH% us.fatehi.DatabaseConnectorMain %*
