# Spring Security with JWT

A hands-on Spring Boot REST API showing stateless authentication with Spring Security and JSON Web Tokens: sign up, sign in to receive an access token and a refresh token, and role-based access to a protected endpoint.

## Tech Stack

- Java 17, Spring Boot 3.1
- Spring Security 6 (stateless, JWT filter, `DaoAuthenticationProvider`, BCrypt)
- JJWT 0.12
- Spring Data JPA (Hibernate) + MySQL
- Lombok

## Features

- User sign-up (new users get the `USER` role, passwords hashed with BCrypt)
- Sign-in with email and password, returning an access token and a refresh token
- Refresh endpoint that issues a new access token from a valid refresh token
- JWT filter that authenticates requests carrying `Authorization: Bearer <token>`
- `ADMIN`-only endpoint
- A default admin account is created at startup if none exists: `admin@gmail.com` / `admin`

## Getting Started

### Prerequisites

- JDK 17
- MySQL running locally

### Configuration

Settings are in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_security?createDatabaseIfNotExist=true
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
jwt.secret=${JWT_SECRET:...}
```

| Environment variable | Default | Description |
|----------------------|---------|-------------|
| `DB_USERNAME` | `root` | MySQL user |
| `DB_PASSWORD` | `root` | MySQL password |
| `JWT_SECRET` | development key | Base64-encoded HMAC key (at least 256 bits) used to sign tokens |

Set your own `JWT_SECRET` outside local development, e.g. `openssl rand -base64 32`.

### Run

```bash
git clone https://github.com/Hussein-Fouani/Hands_ON_Spring_Security_and_JWT_Tokens.git
cd Hands_ON_Spring_Security_and_JWT_Tokens
./mvnw spring-boot:run
```

The API runs on `http://localhost:8080`.

## API Endpoints

| Method | Path | Auth | Body | Description |
|--------|------|------|------|-------------|
| POST | `/api/v1/auth/signup` | Public | `{ "firstName", "lastName", "email", "password" }` | Register a user |
| POST | `/api/v1/auth/signin` | Public | `{ "email", "password" }` | Returns `{ "token", "refreshToken" }` |
| POST | `/api/v1/auth/refresh` | Public | `{ "token": "<refresh token>" }` | Returns a new access token |
| GET | `/api/v1/admin` | `ADMIN` | | Test endpoint for admins |

Example:

```bash
curl -X POST http://localhost:8080/api/v1/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@gmail.com","password":"admin"}'

curl http://localhost:8080/api/v1/admin -H "Authorization: Bearer <token>"
```
