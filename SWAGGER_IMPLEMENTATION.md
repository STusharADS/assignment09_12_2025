# Swagger/OpenAPI Implementation Guide

## Overview
This project includes complete Swagger UI integration using Springdoc OpenAPI 2.7.0. The Swagger UI provides interactive API documentation and allows testing endpoints directly from a browser.

## What's Implemented

### 1. Dependencies (pom.xml)
- ✅ `springdoc-openapi-starter-webmvc-ui` v2.7.0

### 2. Configuration (application.yaml)
- ✅ API grouping: Authentication, Core Rides, Core Analytics
- ✅ Swagger UI path: `/swagger-ui.html`
- ✅ OpenAPI JSON path: `/api-docs`
- ✅ Method sorting and alphabetical tag sorting

### 3. Security (SecurityConfig.java)
- ✅ Swagger paths permitted without authentication
- ✅ Correct order: Swagger paths FIRST in `requestMatchers()`
- ✅ All required paths included:
  - `/swagger-ui/**`
  - `/swagger-ui.html`
  - `/api-docs/**`
  - `/v3/api-docs/**`
  - `/swagger-resources/**`
  - `/webjars/**`

### 4. Exception Handling (GlobalExceptionHandler.java)
- ✅ `@Hidden` annotation prevents Swagger from processing exception handlers
- ✅ Handles: NotFoundException, BadRequestException, validation errors, generic exceptions
- ✅ Returns structured error response with timestamp, error code, message

### 5. Controllers
All controllers decorated with:
- ✅ `@Tag` - Groups related endpoints
- ✅ `@Operation` - Describes individual endpoints

Controllers:
- AuthController - Authentication APIs (Register, Login)
- RideController - Core Ride APIs (Create, Accept, Complete)
- AnalyticsController - Core Analytics APIs (Driver Earnings)

### 6. Custom OpenAPI Config (OpenApiConfig.java)
- ✅ Sets API title, version, description
- ✅ Configures server URL (localhost:8081)
- ✅ Customizable for production deployment

## Access Swagger UI

### Local Development
1. Start application: `mvn spring-boot:run`
2. Open browser: **http://localhost:8081/swagger-ui.html**

### What You'll See
- **API Groups Dropdown** at top:
  - Authentication APIs
  - Core Ride APIs
  - Core Analytics APIs
  
- **Endpoints List** organized by tag
- **Expandable Details** - Click to see parameters, request body, response schema
- **Try It Out** button - Test API directly from browser
- **Response Examples** - See actual responses

## Available URLs

| Purpose | URL |
|---------|-----|
| **Swagger UI** | http://localhost:8081/swagger-ui.html |
| **OpenAPI JSON** | http://localhost:8081/api-docs |
| **OpenAPI JSON (Group)** | http://localhost:8081/api-docs/auth |
| **OpenAPI YAML** | http://localhost:8081/api-docs.yaml |
| **Swagger Resources** | http://localhost:8081/swagger-resources |

## Key Annotations Used

### `@Tag`
Organizes endpoints into groups in Swagger UI
```java
@Tag(name = "Authentication APIs", 
      description = "User registration and login endpoints")
```

### `@Operation`
Describes individual endpoints
```java
@Operation(summary = "Register a new user",
           description = "Register a new user or driver. Returns the created user with ID.")
```

### `@Hidden`
Excludes class from Swagger documentation
```java
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler { ... }
```

## API Groups Configuration

Defined in `application.yaml`:
```yaml
springdoc:
  group-configs:
    - group: 'auth'
      display-name: 'Authentication APIs'
      paths-to-match: '/api/auth/**'
    - group: 'core-rides'
      display-name: 'Core Ride APIs'
      paths-to-match: '/api/rides/**'
    - group: 'core-analytics'
      display-name: 'Core Analytics APIs'
      paths-to-match: '/api/analytics/**'
```

## Testing Endpoints via Swagger UI

### Example: Register User
1. Go to **Authentication APIs** group
2. Click **POST /api/auth/register**
3. Click **Try it out**
4. Enter JSON body:
   ```json
   {
     "username": "john_doe",
     "password": "password123",
     "role": "ROLE_USER"
   }
   ```
5. Click **Execute**
6. See response with user ID, username, and role

### Example: Login
1. Click **POST /api/auth/login**
2. Click **Try it out**
3. Enter:
   ```json
   {
     "username": "john_doe",
     "password": "password123"
   }
   ```
4. Click **Execute**
5. Copy the JWT token from response

### Example: Create Ride (with Authorization)
1. Click **POST /api/rides**
2. Click **Try it out**
3. In the **Authorize** section at top, enter:
   - **Bearer <your_jwt_token>**
4. Enter ride body:
   ```json
   {
     "pickupLocation": "Times Square",
     "dropLocation": "Central Park",
     "fare": 25.50
   }
   ```
5. Click **Execute**

## Troubleshooting

### Issue: 403 Forbidden on Swagger UI
**Solution:**
- Check SecurityConfig - Swagger paths must come FIRST in `requestMatchers()`
- Verify all paths are included: `/swagger-ui/**`, `/api-docs/**`, etc.

### Issue: No endpoints visible
**Solution:**
- Ensure controllers have `@RestController` annotation
- Verify controllers are in `org.example.rideshare.controller` package
- Check application logs for errors

### Issue: NoSuchMethodError
**Solution:**
- Using Springdoc v2.7.0 (compatible with Spring Boot 3.2.0)
- Add `@Hidden` to GlobalExceptionHandler
- Add `basePackages` to `@RestControllerAdvice`

## Files Modified/Created

### Modified
- `pom.xml` - Added Springdoc dependency
- `application.yaml` - Added Swagger configuration (created from properties)
- `SecurityConfig.java` - Added Swagger path matchers
- `AuthController.java` - Added @Tag and @Operation
- `RideController.java` - Added @Tag and @Operation
- `AnalyticsController.java` - Added @Tag and @Operation

### Created
- `OpenApiConfig.java` - Custom OpenAPI configuration
- `GlobalExceptionHandler.java` - Global exception handling
- `NotFoundException.java` - Custom exception
- `BadRequestException.java` - Custom exception

## Best Practices

1. ✅ **Security First**: Swagger paths are allowed in security config
2. ✅ **Documentation**: Every endpoint has @Operation summary and description
3. ✅ **Organization**: Endpoints grouped by feature (Auth, Rides, Analytics)
4. ✅ **Error Handling**: Consistent error responses via GlobalExceptionHandler
5. ✅ **Secrets Safe**: No credentials in Swagger config or docs

## Next Steps

To further enhance Swagger documentation:
1. Add `@Parameter` annotations to method parameters
2. Add `@ApiResponse` annotations for different HTTP status codes
3. Add example values using Swagger annotations
4. Generate Postman collection from OpenAPI spec
5. Deploy to production with appropriate server URLs

## References
- [Springdoc OpenAPI Documentation](https://springdoc.org/)
- [OpenAPI 3.0 Specification](https://spec.openapis.org/oas/v3.0.0)
- [Spring Boot Security with Swagger](https://www.baeldung.com/spring-boot-swagger-security)
