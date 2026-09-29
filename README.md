# Users and Phone Numbers

Spring Boot + PostgreSQL. Users can have several phone numbers, the mobile operator is detected automatically from the number prefix. Includes a simple web UI with full CRUD.

## Run

1. Install Java 17+ and PostgreSQL.
2. Create database: `CREATE DATABASE demo_db;`
3. Set environment variable `DB_PASSWORD` (and optionally `DB_USER`, default `postgres`).
4. Start: `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
5. Open http://localhost:8080/

## API

| Method | URL | Description |
|---|---|---|
| GET | /users | all users with their phones |
| POST | /users | create user, body `{"name": "..."}` |
| PUT | /users/{id} | rename user |
| DELETE | /users/{id} | delete user and all their phones |
| GET | /users/{id}/phones | phones of one user |
| POST | /users/{id}/phones | add phone, body `{"number": "998971234567"}` |
| PUT | /users/{id}/phones/{phoneId} | change phone number |
| DELETE | /users/{id}/phones/{phoneId} | delete phone |