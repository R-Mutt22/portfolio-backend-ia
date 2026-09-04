FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Heap incrementado a 220M con restricciones agresivas en el resto de la JVM
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xms64m", "-Xmx220m", "-XX:MaxMetaspaceSize=110m", "-XX:ReservedCodeCacheSize=24m", "-Xss256k", "-jar", "app.jar"]