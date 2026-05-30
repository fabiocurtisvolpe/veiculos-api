# 🚗 Veículos API

API REST para gerenciamento de veículos, construída com Spring Boot 3, Spring Security (JWT), Spring Data JPA, Flyway, PostgreSQL, Feign Client e testes completos (unitários + integração).

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Flyway
- Feign Client
- JUnit 5 + MockMvc + TestRestTemplate
- H2 (para testes)

---

## 🚀 Como Executar

### 1. Configurar Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=veiculosdb
DB_USER=veiculos
DB_PASS=veiculos123

JWT_SECRET=uma_chave_segura_aqui
JWT_EXPIRATION=86400000
```

### 2. Subir o PostgreSQL (opcional via Docker)

```bash
docker run --name veiculos-db \
  -e POSTGRES_PASSWORD=veiculos123 \
  -e POSTGRES_USER=veiculos \
  -e POSTGRES_DB=veiculosdb \
  -p 5432:5432 -d postgres:15
```

### 3. Rodar a Aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em: `http://localhost:8080`

---

## 🧪 Executar Testes

```bash
./mvnw test
```

**O que é testado:**
- Controllers
- Services
- Repositories
- Specifications
- Paginação + filtros
- Integração com banco H2
- Segurança (roles `USER` / `ADMIN`)

> O profile `test` usa banco H2, Flyway desabilitado e Security desabilitado ou mockado.

---

## 📁 Estrutura do Projeto

```
src/main/java/com/tinnova/veiculos
│
├── config          # Configurações: Security, JWT, Feign, CORS
├── controller      # Endpoints REST
├── dto             # DTOs
├── entity          # Entidades JPA
├── exception       # Handler global
├── repository      # Repositórios JPA
├── security        # JWT, filtros, autenticação
├── service         # Regras de negócio
├── spec            # Specifications
└── VeiculosApiApplication.java
```

---

## 🔐 Autenticação (JWT)

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123"
}
```

### Resposta

```json
{
  "token": "jwt_aqui"
}
```

### Uso do Token

```http
Authorization: Bearer <token>
```

---

## 🔑 Regras de Acesso

| Rota | Permissão |
|------|-----------|
| `/api/admin/**` | `ADMIN` |
| `/api/veiculos/**` | `USER` ou `ADMIN` |
| `/swagger-ui/**` | Público |

---

## 📡 Endpoints Principais

### Listar veículos com filtros + paginação

```http
GET /api/veiculos?marca=ford&cor=preto&ano=2020&page=0&size=10&sort=modelo,asc
```

### Criar veículo

```http
POST /api/veiculos
```

### Atualizar veículo

```http
PUT /api/veiculos/{id}
```

### Deletar veículo *(ADMIN)*

```http
DELETE /api/admin/veiculos/{id}
```

---

## 🗄️ Migrações Flyway

```bash
./mvnw flyway:migrate
```

---

## 🐳 Docker Compose

```yaml
version: "3.8"

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: veiculosdb
      POSTGRES_USER: veiculos
      POSTGRES_PASSWORD: veiculos123
    ports:
      - "5432:5432"

  api:
    build: .
    environment:
      DB_HOST: postgres
      DB_PORT: 5432
      DB_NAME: veiculosdb
      DB_USER: veiculos
      DB_PASS: veiculos123
      JWT_SECRET: uma_chave_segura_aqui
    ports:
      - "8080:8080"
    depends_on:
      - postgres
```

```bash
docker compose up --build
```

---

## ✅ Status do Projeto

- [x] API funcional
- [x] Segurança com JWT
- [x] Testes completos
- [x] Paginação + filtros dinâmicos
- [x] Cobertura alta
- [x] Pronto para produção

---

## 👨‍💻 Autor

**Fabio** — Desenvolvedor Backend Java
