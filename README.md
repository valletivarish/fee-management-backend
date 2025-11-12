# Backend Service

Spring Boot service providing REST APIs for the Fee Management System.

## Tech Stack
- Java 17
- Spring Boot 3.3.11 (Web, Security, Data MongoDB, Validation)
- MongoDB with Atlas connection support
- JWT-based authentication
- Springdoc OpenAPI for Swagger UI

## Prerequisites
- Java 17+
- Maven 3.9+
- MongoDB URI (local or Atlas cluster) with database name

## Configuration
Set environment variables or edit `src/main/resources/application.properties`:

```
SPRING_DATA_MONGODB_URI=mongodb+srv://<user>:<pass>@cluster.mongodb.net/organization?retryWrites=true&w=majority&tls=true
APP_ENVIRONMENT_MODE=Production
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

## Running Locally

```bash
mvn spring-boot:run
```

Application starts on `http://localhost:8080`.

## Swagger UI
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Useful Endpoints
- `GET /api/db/stats` – MongoDB usage summary
- `GET /actuator/health` – health check
- `POST /api/auth/login` – JWT authentication
- CRUD endpoints under `/api/students`, `/api/fee-plans`, `/api/student-fees`, `/api/payments`

## Seed Data
On startup, `DataInitializer` creates:
- Admin user: `admin@example.com / Admin@123`
- Sample student: `john.doe@example.com`

## Testing

```bash
mvn test
```

## Deployment Notes
- Ensure Jenkins/servers use Java 17+ and have outbound access to MongoDB Atlas.
- Allowlist build/server IPs in Atlas if using SRV connection.
- Monitor `/api/db/stats` to track the 512 MB free-tier usage.
