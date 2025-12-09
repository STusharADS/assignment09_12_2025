# Ride-Sharing Backend (Spring Boot)

A minimal Uber-style backend with JWT auth, riders/drivers, ride lifecycle, and simple earnings analytics on MongoDB.

## Stack
- Java 17, Spring Boot 3
- Spring Security with JWT
- Spring Data MongoDB

## Features
- Register/login users with roles `ROLE_USER` (passenger) and `ROLE_DRIVER`
- Create ride requests (passenger)
- Accept and complete rides (driver)
- Driver earnings aggregation via MongoDB pipeline

## Project Structure
```
src/main/java/org/example/rideshare
├── config/        # Security + JWT filter
├── controller/    # REST endpoints
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
mvn spring-boot:run
# or
mvn -DskipTests package
java -jar target/rideshare-0.0.1-SNAPSHOT.jar
```

## API Quick Reference
- `POST /api/auth/register` — body: `{username, password, role}`
- `POST /api/auth/login` — body: `{username, password}` -> returns JWT
- `POST /api/rides` — create ride (passenger JWT)
- `POST /api/rides/accept/{id}` — accept ride (driver JWT)
- `POST /api/rides/complete/{id}` — complete ride
- `GET /api/analytics/driver/{driver}/earnings` — total earnings for driver

## Roles and Status
- Roles: `ROLE_USER` (passenger), `ROLE_DRIVER` (driver)
- Ride status flow: `REQUESTED` -> `ACCEPTED` -> `COMPLETED`

## Notes
- Mongo must be running and reachable via `MONGODB_URI`.
- JWT secret is not in the repo; supply via env.
- Artifacts and local config are gitignored for safe public publishing.
