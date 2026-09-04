# Etapa 1: Compilación del proyecto con Maven y Java 21
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final basada en Debian con optimización nativa para 512 MB
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Ajuste de memoria JVM con Serial GC y límites estrictos off-heap
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-Xms64m", "-Xmx180m", "-XX:MaxMetaspaceSize=128m", "-XX:ReservedCodeCacheSize=32m", "-Xss256k", "-jar", "app.jar"]