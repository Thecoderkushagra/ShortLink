# ShortLink

A Spring Boot application that provides URL shortening services. Convert long URLs into short, manageable links and handle redirections seamlessly.

## Features

- User authentication and authorization using JWT
- URL shortening with Base62 encoding
- Redis caching for performance
- PostgreSQL database for persistence
- Swagger UI for API documentation
- Scheduled tasks for counter flushing

## Prerequisites

- Java 21
- Maven 3.6+
- PostgreSQL
- Redis

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/Thecoderkushagra/ShortLink
   cd ShortLink
   ```

2. Configure the database and Redis in `src/main/resources/application.properties`.

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage

Once the application is running, you can:

- Register and login users via `/auth` endpoints.
- Shorten URLs via `/api/urls` endpoints.
- Access shortened URLs for redirection via `/r/{shortCode}`.

## API Endpoints

### Authentication
- POST /auth/register - Register a new user
- POST /auth/login - Login and get JWT token

### URL Management
- POST /api/urls - Create a short URL
- GET /api/urls - Get user's URLs
- DELETE /api/urls/{id} - Delete a URL

### Redirection
- GET /r/{shortCode} - Redirect to original URL

For detailed API documentation, visit `/swagger-ui.html` when the application is running.

## Configuration

Update `application.properties` with your database and Redis settings:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/shortlink
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.redis.host=localhost
spring.redis.port=6379
jwt.secret=your_jwt_secret
```

## Contributing

Contributions are welcome. Please fork the repository and submit a pull request.
