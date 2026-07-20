# Itaú Backend Challenge — Transactions & Statistics API

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-gray?style=for-the-badge&logo=docker)

## 🎯 Goal

This is my solution to **Itaú Unibanco's backend programming challenge**. The goal was to build a high-performance REST API that records financial transactions and returns real-time statistics, with all data stored exclusively in memory.

The application follows industry best practices for code quality, software architecture, and maintainability, aiming for a robust, production-grade solution rather than a minimal pass on the requirements.

The original challenge brief (in Portuguese) is kept as [`desafio.md`](desafio.md) for reference.

## ✨ Implemented Features

### ✔️ Core Requirements
- **`POST /transacao`**: receives and validates new transactions.
  - Returns **201 Created** on success.
  - Returns **422 Unprocessable Entity** for business-rule violations (negative amount, future date).
  - Returns **400 Bad Request** for malformed JSON.
- **`DELETE /transacao`**: clears all stored transactions.
  - Returns **200 OK** on success.
- **`GET /estatistica`**: computes and returns statistics (`count`, `sum`, `avg`, `min`, `max`) for all transactions from the last 60 seconds.
  - Optimized to stay performant even under a high volume of transactions.
- **100% in-memory storage**: no database or external cache dependency — thread-safe data structures guarantee integrity under concurrent requests.

### 🚀 Extras
- **🧪 Automated tests**: unit tests (Mockito) for business logic and integration tests (MockMvc) for the API endpoints.
- **🐳 Dockerized**: fully containerized, with an optimized `Dockerfile` for build and run, ready to deploy anywhere.
- **📜 Structured logging**: SLF4J logging across the API for easier monitoring and debugging.
- **❤️ Observability**: Spring Boot Actuator exposes a `/actuator/health` endpoint.
- **🛡️ Centralized error handling**: a `GlobalExceptionHandler` standardizes error responses and returns the correct HTTP status without leaking internal details.
- **📚 API documentation**: self-documented via OpenAPI (Swagger), with an interactive UI to explore and test endpoints.
- **⚙️ Externalized configuration**: the statistics time window (default: 60 seconds) is configurable via `application.properties`, no recompilation needed.

## 🛠️ Tech Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3
- **Build tool**: Maven
- **Testing**: JUnit 5, Mockito
- **Docs**: Springdoc OpenAPI (Swagger)
- **Container**: Docker
- **Other**: Lombok, Spring Web, Spring Validation, Spring Actuator

## 🚀 Running the Project

### Prerequisites
- JDK 17
- Maven 3.8+
- Docker (optional)

### 1. Clone the repository
```bash
git clone https://github.com/JoseOtavioJunqueira/desafio-itau-backend.git
cd desafio-itau-backend
```

### 2. Build with Maven
```bash
./mvnw clean package
```

### 3. Run the application

**Option A: Locally via Java**
```bash
java -jar target/transacao-estatistica-api-0.0.1-SNAPSHOT.jar
```

**Option B: Via Docker**
```bash
# Build the image
docker build -t itau-desafio-api .

# Run the container
docker run -p 8080:8080 itau-desafio-api
```

## 🔗 Accessing the API

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Health check**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
