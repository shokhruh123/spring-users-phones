
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
| GET | /users/{id} | one user |
| POST | /users | create user, body `{"name": "..."}` |
| PUT | /users/{id} | rename user |
| DELETE | /users/{id} | delete user and all their phones |
| GET | /users/{id}/phones | phones of one user |
| POST | /users/{id}/phones | add phone, body `{"number": "998971234567"}` |
| PUT | /users/{id}/phones/{phoneId} | change phone number |
| DELETE | /users/{id}/phones/{phoneId} | delete phone |
| GET/POST/PUT/DELETE | /messages | messages CRUD |

## Errors

All errors have a single format:

```json
{ "status": 404, "message": "Пользователь с id 999 не найден" }
```

`400` for validation errors, `404` when a resource is not found.