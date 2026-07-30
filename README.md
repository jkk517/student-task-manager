# Student Task Manager - Backend

A REST API for a task management app, built with Spring Boot. Users can register, log in, and manage their own tasks — create, update, delete, mark complete, and search by title. Built to practice Spring Boot, Spring Security with JWT, and deploying a real Java app instead of just running it on localhost.

🔗 **Live:** [https://student-task-manager-eqin.onrender.com](https://student-task-manager-eqin.onrender.com)
*(root URL shows "Not Found" — that's expected, there's no homepage route, only API endpoints under `/api`)*

## Tech Stack

- Java 21, Spring Boot 3
- Spring Data JPA, Spring Security
- JWT authentication (jjwt)
- MySQL (hosted on Aiven)
- Maven
- Deployed with Docker on Render

## Features

- Register / login with JWT
- Passwords hashed with BCrypt
- Create, view, update, delete tasks
- Mark tasks as completed
- Search tasks by title
- Each user only sees their own tasks

## Project Structure

```
backend/
├── Dockerfile
└── src/main/java/com/studentapp/taskmanager/
    ├── controller/   # REST endpoints
    ├── service/      # business logic
    ├── repository/   # Spring Data JPA repos
    ├── entity/       # User, Task
    ├── dto/          # request/response objects
    ├── security/     # JWT util + filter
    ├── config/       # Spring Security config
    └── exception/    # global exception handling
```

## API Endpoints

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/register` | No | Create an account |
| POST | `/api/auth/login` | No | Log in, get a JWT |
| GET | `/api/tasks` | Yes | Get all your tasks |
| POST | `/api/tasks` | Yes | Create a task |
| GET | `/api/tasks/{id}` | Yes | Get one task |
| PUT | `/api/tasks/{id}` | Yes | Update a task |
| DELETE | `/api/tasks/{id}` | Yes | Delete a task |
| PATCH | `/api/tasks/{id}/complete` | Yes | Mark a task completed |
| GET | `/api/tasks/search?title=` | Yes | Search tasks by title |

Protected routes need `Authorization: Bearer <token>` in the header.

## Running Locally

**1. Database**
```sql
CREATE DATABASE student_task_manager;
```
Tables are auto-created on startup (`ddl-auto=update`), so this is all you need.

**2. Run the app**
```bash
mvn spring-boot:run
```
Runs on `http://localhost:8080`. Default local DB credentials are `root` / `root` — change them in `application.properties` if yours differ.

## Config

DB and JWT settings are read from environment variables with local fallbacks, so the same code runs locally and in production:
```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/student_task_manager?useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
jwt.secret=${JWT_SECRET:studentTaskManagerSecretKeyForJwtSigning123456}
```

## Testing

Tested with a Postman collection that includes actual assertions (status codes, response shape, token capture between requests) — see `postman/StudentTaskManager.postman_collection.json`.

## Author

**Jaswanth K Kumaran**
GitHub: [@jkk517](https://github.com/jkk517)
Email: jaswanthkk517@gmail.com
