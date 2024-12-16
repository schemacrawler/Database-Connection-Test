FROM eclipse-temurin:8-jre-noble

COPY target/bin /app

WORKDIR /app

ENTRYPOINT ["sh", "./run.sh"]
