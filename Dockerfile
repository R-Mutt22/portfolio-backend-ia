# Etapa 1: Compilación con Maven
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución ultra liviana en producción
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Configuración optimizada para Render Free Tier (512MB RAM)
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xms64m", "-Xmx256m", "-jar", "app.jar"]