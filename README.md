# Ride-Sharing Backend (Spring Boot)

A minimal Uber-style backend with JWT auth, riders/drivers, ride lifecycle, and simple earnings analytics on MongoDB.

## Stack
- Java 17, Spring Boot 3
- Spring Security with JWT
- Spring Data MongoDB
- Springdoc OpenAPI (Swagger UI)

## Features
- Register/login users with roles `ROLE_USER` (passenger) and `ROLE_DRIVER`
- Create ride requests (passenger)
- Accept and complete rides (driver)
- Driver earnings aggregation via MongoDB pipeline
- **Interactive Swagger UI** for API testing

## Project Structure
```
src/main/java/org/example/rideshare
├── config/        # Security, JWT filter, OpenAPI config
├── controller/    # REST endpoints
├── exception/     # Global exception handler
├── model/         # User, Ride, AuthRequest
├── repository/    # Mongo repositories
├── service/       # User, Ride, Analytics services
└── util/          # JwtUtil
```

## Configuration
All sensitive values are env-backed. Set these before running:
- `MONGODB_URI` (e.g., `mongodb://user:pass@host:27017/db`)
- `MONGODB_DB` (optional, defaults to `rideshare`)
- `JWT_SECRET` (strong random string)

See `src/main/resources/application-example.properties` for a template. Do not commit real creds.

## Run
```sh
# Ensure Maven PATH is set (if not installed globally)
$env:M2_HOME = "C:\tools\maven\apache-maven-3.9.6"
$env:PATH = "$env:M2_HOME\bin;" + $env:PATH

mvn spring-boot:run
# or
mvn -DskipTests package
java -jar target/rideshare-0.0.1-SNAPSHOT.jar
```

## Swagger UI
**Start the app** and visit:
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/api-docs
- **OpenAPI YAML**: http://localhost:8081/api-docs.yaml

The UI includes:
- ✅ API groups (Authentication, Rides, Analytics)
- ✅ Full endpoint documentation
- ✅ Try it out - test endpoints directly from browser
- ✅ Request/response schemas

## API Quick Reference

### Authentication
- `POST /api/auth/register` — Register new user/driver. Body: `{username, password, role}`
  - Roles: `ROLE_USER` (passenger), `ROLE_DRIVER` (driver)
- `POST /api/auth/login` — Login and get JWT. Body: `{username, password}` → Returns `{token}`

### Rides
- `POST /api/rides` — Create ride (passenger JWT). Body: `{pickupLocation, dropLocation, fare}`
- `POST /api/rides/accept/{id}` — Accept ride (driver JWT)
- `POST /api/rides/complete/{id}` — Complete ride

### Analytics
- `GET /api/analytics/driver/{driver}/earnings` — Total earnings for driver (Bearer JWT)

### Ride Status Flow
`REQUESTED` → `ACCEPTED` → `COMPLETED`

## Notes
- Mongo must be running and reachable via `MONGODB_URI`.
- JWT secret is not in the repo; supply via `JWT_SECRET` env var.
- Swagger paths are allowed in Security config (no auth required).
- Artifacts and local config are gitignored for safe public publishing.

