FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
# 添加 JVM 參數以避免在 WSL2 環境下崩潰
ENV MAVEN_OPTS="-XX:-TieredCompilation -XX:+UseSerialGC"
RUN mvn clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar app.jar

RUN groupadd -r appuser && \
    useradd -r -g appuser appuser && \
    chown -R appuser:appuser /app

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=90s --retries=5 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM 參數：避免 JIT 編譯器問題，使用 Serial GC
ENV JAVA_OPTS="-XX:-TieredCompilation -XX:+UseSerialGC -Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
