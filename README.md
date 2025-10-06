# Desafio Backend Itaú - API de Transações e Estatísticas

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-gray?style=for-the-badge&logo=docker)

## 🎯 Objetivo

Este projeto é a minha solução para o **Desafio de Programação para a área de Backend do Itaú Unibanco**. O objetivo foi construir uma API REST de alta performance para registrar transações financeiras e retornar estatísticas em tempo real, com todos os dados armazenados exclusivamente em memória.

A aplicação foi desenvolvida seguindo as melhores práticas de mercado para qualidade de código, arquitetura de software e manutenibilidade, visando criar uma solução robusta e profissional.

## ✨ Features Implementadas

### ✔️ Requisitos Obrigatórios
- **`POST /transacao`**: Endpoint para receber e validar novas transações.
  - Retorna **201 Created** para sucesso.
  - Retorna **422 Unprocessable Entity** para transações com regras de negócio inválidas (valor negativo, data futura).
  - Retorna **400 Bad Request** para JSONs malformados.
- **`DELETE /transacao`**: Endpoint que apaga todas as transações armazenadas.
  - Retorna **200 OK** para sucesso.
- **`GET /estatistica`**: Endpoint que calcula e retorna as estatísticas (`count`, `sum`, `avg`, `min`, `max`) de todas as transações ocorridas nos últimos 60 segundos.
  - A lógica é otimizada para ser performática, mesmo com um grande volume de dados.
- **Armazenamento 100% em Memória**: Nenhuma dependência de banco de dados ou cache externo, utilizando estruturas de dados thread-safe para garantir a integridade em um ambiente com múltiplas requisições simultâneas.

### 🚀 Recursos Adicionais (Extras)
- **[x] 🧪 Testes Automatizados**: Cobertura de testes unitários (com Mockito) para a lógica de negócio e testes de integração (com MockMvc) para os endpoints da API, garantindo a confiabilidade da aplicação.
- **[x] 🐳 Containerização com Docker**: A aplicação está totalmente containerizada, incluindo um `Dockerfile` otimizado para build e execução, facilitando o deploy em qualquer ambiente.
- **[x] 📜 Logs Detalhados**: Implementação de logs com SLF4J para rastrear as operações da API, facilitando o monitoramento e a depuração.
- **[x] ❤️ Observabilidade (Health Check)**: Uso do Spring Boot Actuator para expor um endpoint `/actuator/health`, permitindo a verificação da saúde da aplicação.
- **[x]  elegantly Tratamento de Erros**: Criação de um `GlobalExceptionHandler` para padronizar e tratar erros de forma elegante, retornando os status HTTP corretos sem expor detalhes internos do sistema.
- **[x] 📚 Documentação da API (Swagger)**: A API é autodocumentada utilizando o padrão OpenAPI (Swagger), fornecendo uma interface interativa para explorar e testar os endpoints.
- **[x] ⚙️ Configuração Externalizada**: O intervalo de tempo para o cálculo das estatísticas (padrão de 60 segundos) pode ser facilmente alterado através do arquivo `application.properties`, sem a necessidade de recompilar o código.

## 🛠️ Tecnologias Utilizadas
- **Linguagem**: Java 17
- **Framework**: Spring Boot 3
- **Build Tool**: Maven
- **Testes**: JUnit 5, Mockito
- **Documentação**: Springdoc OpenAPI (Swagger)
- **Container**: Docker
- **Outros**: Lombok, Spring Web, Spring Validation, Spring Actuator

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 17
- Maven 3.8+
- Docker (Opcional)

### 1. Clone o repositório
```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>
```

### 2. Construa o projeto com Maven
```bash
./mvnw clean package
```

### 3. Execute a aplicação

**Opção A: Localmente via Java**
```bash
java -jar target/transacao-estatistica-api-0.0.1-SNAPSHOT.jar
```

**Opção B: Via Docker**
```bash
# Construa a imagem Docker
docker build -t itau-desafio-api .

# Rode o container
docker run -p 8080:8080 itau-desafio-api
```

## 🔗 Acessando a API

- **Swagger UI (Documentação Interativa)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Health Check**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)