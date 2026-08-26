# 🧠 Portafolio Backend IA (RAG)

Backend inteligente desarrollado con **Java 21 y Spring Boot 4** para mi portafolio interactivo. Este proyecto expone una API REST que integra Inteligencia Artificial mediante arquitectura RAG (Retrieval-Augmented Generation) para responder preguntas sobre mi perfil profesional, proyectos y experiencia.

## 🚀 Sobre el proyecto

Como Trainee Jr enfocado en el desarrollo y la calidad del software (QA), diseñé este backend para que actúe como un asistente virtual. La API procesa el contexto de mis experiencias, permitiendo a los usuarios consultar detalles específicos de manera conversacional, como mis aportes en accesibilidad web, creación de casos de prueba, y desarrollo de aplicaciones descentralizadas. 

## 🛠️ Stack Tecnológico

La arquitectura de este servicio se integra dentro de mi ecosistema general de trabajo:

* **Core Backend:** Java 21, Spring Boot 4, Spring AI.
* **Procesamiento de Datos:** RAG In-Memory (`SimpleVectorStore`).
* **DevOps e Infraestructura:** Oracle Cloud Infrastructure (OCI).
* **Gestión y Calidad:** Jira, Trello, Figma.
* **Ecosistema Adicional:** Preparado para interoperar con mis otros servicios backend (Node.js / Express) y clientes frontend (React, Astro, Angular, Bootstrap).

## 📂 Experiencia y Proyectos Modelados en la IA

El contexto de la Inteligencia Artificial está entrenado con mis participaciones en:
* **CILSA:** Testing funcional, reporte de bugs y auditorías de accesibilidad web para plataformas globales como Airbnb y Pinterest.
* **ArtBlink:** Desarrollo e incubación de red social descentralizada orientada a artistas en la blockchain de Solana (Web3).

## ⚙️ Configuración y Uso Local

1. Clonar este repositorio:
   ```bash
   git clone https://github.com/R-Mutt22/portfolio-backend-ia.git

2. Configurar la clave de la API en las variables de entorno. Nunca exponer la API Key en el código:
   ```bash
   export GEMINI_API_KEY="tu_clave_aqui"
3. Ejecutar el proyecto usando Maven:
   ```bash
   ./mvnw spring-boot:run

## 📐 Prácticas de Arquitectura y QA
* Diseño de aplicación estructurado en múltiples capas (Controlador, Servicio, Configuración).
* Principios SOLID aplicados para facilitar futuras integraciones de Tool Calling.
* Testing funcional orientado a la estabilidad de la API REST.
