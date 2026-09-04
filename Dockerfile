# Etapa 1: Compilación del proyecto con Maven y Java 21
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final basada en Debian con límites de memoria de la JVM
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Ajuste de memoria para la capa gratuita de Render (512 MB RAM)
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70.0", "-Xms128m", "-Xmx360m", "-jar", "app.jar"]