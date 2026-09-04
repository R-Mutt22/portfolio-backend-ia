# 🧠 Portafolio Backend IA (RAG)

Backend inteligente desarrollado con **Java 21 y Spring Boot 4** para mi portafolio interactivo. Este servicio expone una API REST con arquitectura RAG (*Retrieval-Augmented Generation*) integrando un modelo de embeddings local y modelos LLM para responder preguntas sobre mi perfil profesional, proyectos y experiencia.

## 🚀 Sobre el proyecto

Como Junior Full Stack Developer y QA, diseñé este backend para servir como un asistente virtual que interactúa de forma directa con los visitantes de mi portafolio web. La API procesa el contexto cargado en un vector store para responder consultas precisas sobre mis contribuciones en proyectos Web3, accesibilidad web, pruebas funcionales y desarrollo de software.

## 🛠️ Stack Tecnológico

* **Core Backend:** Java 21, Spring Boot 4, Spring AI.
* **Procesamiento Vectorial (RAG):** `SimpleVectorStore` local con embeddings `all-MiniLM-L6-v2` (ONNX / DJL).
* **Integración LLM:** OpenRouter API Gateway (`openrouter/free`).
* **Seguridad y Resiliencia:** Filtro custom de *Rate Limiting* (`RateLimitingFilter`) y manejo global de excepciones (`GlobalExceptionHandler`).
* **Monitoreo:** Spring Boot Actuator (`/actuator/health`).
* **Contenerización y Despliegue:** Docker, Eclipse Temurin JRE 21 Alpine.
* **Infraestructura:** Oracle Cloud Infrastructure (OCI) / Cloud Hosting.

## 📂 Experiencia y Proyectos Modelados

El contexto de la Inteligencia Artificial está entrenado para responder sobre:
* **CILSA:** Testing funcional, reporte de bugs y auditorías de accesibilidad web para plataformas como Airbnb y Pinterest.
* **ArtBlink:** Desarrollo e incubación de una red social descentralizada para artistas en la blockchain de Solana (Web3).
* **Stack Dev:** Desarrollo con Java (Spring Boot), Node.js (Express), React, Astro, Angular y gestión en Oracle Cloud Infrastructure (OCI).

## ⚙️ Configuración y Ejecución Local

### Prerrequisitos
* Java 21 instalado.
* Maven 3.8+ o `./mvnw`.

### 1. Variables de Entorno
Configura las variables de entorno necesarias para la conexión con la API de IA:

```bash
$env:OPENROUTER_API_KEY="tu_api_key_aqui"
$env:OPENROUTER_BASE_URL="https://openrouter.ai/api/v1"
$env:OPENROUTER_MODEL="openrouter/free"
```
### 2. Compilar y Ejecutar

```bash
# Compilar el proyecto omitiendo los tests
mvn clean package -DskipTests

# Ejecutar el archivo ejecutable JAR
java -jar target/portfolio-backend-ia-0.0.1-SNAPSHOT.jar
```
## 🐳 Ejecución con Docker
Si prefieres ejecutar el contenedor empaquetado:

```bash
# Crear la imagen Docker
docker build -t portfolio-backend-ia .

# Ejecutar el contenedor pasando las variables de entorno
docker run -p 8080:8080 \
  -e OPENROUTER_API_KEY="tu_api_key_aqui" \
  -e OPENROUTER_BASE_URL="https://openrouter.ai/api/v1" \
  -e OPENROUTER_MODEL="openrouter/free" \
  portfolio-backend-ia
```
## 📡 Documentación de la API

### 1. Estado de Salud del Servicio

* **URL:** /actuator/health
* **Método:** GET
* **Respuesta Esperada (200 OK):**

```json
{
   "status": "UP",
   "groups": ["liveness", "readiness"]
}
```
### 2. Enviar Consulta al Asistente

* **URL:** /api/chat
* **Método:** POST
* **Headers:** Content-Type: application/json

**Request Body:**
```json
{
   "pregunta": "¿Qué experiencia laboral tiene Matías?"
}
```
**Response (200 OK):**
```json
{
   "respuesta": "Matías Zelarayán cuenta con experiencia en..."
}
```

## 📐 Prácticas de Arquitectura y Calidad

* **Arquitectura Limpia:** Separación estricta de responsabilidades en capas (controller, service, config, security).
* **Protección de API:** Control de tasa de peticiones mediante middleware custom.
* **Resiliencia:** Manejo centralizado de fallos de parsing de JSON y llamadas externas.
