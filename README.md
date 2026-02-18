# URL Shortener Spring Boot Project

This is a Spring Boot application that provides a simple URL shortening service.

## Features
- Shorten long URLs via a REST API
- Redirect short URLs to the original long URLs
- Unique 7-character alphanumeric short codes
- CRUD operations for short URLs (create, read, update expiry, delete)
- Optional expiry for each short URL (valid forever by default, can be set/updated)
- In-memory H2 database for development/testing
- Swagger UI for API documentation at `/swagger-ui.html`
- Global exception handling with `@RestControllerAdvice`
- Unit tests for controller, service, and model classes

## Logic
- **Short Code Generation:** Random 7-character string from [a-zA-Z0-9], checked for uniqueness in the database.
- **Expiry:** Each `ShortUrl` entity has an optional `expiry` field (in seconds). If not set, the URL is valid forever.
- **CRUD Endpoints:**
  - `POST /shorten` — Shorten a URL
  - `GET /{shortURL}` — Redirect to the original URL
  - `PUT /{id}/expiry` — Update expiry for a short URL
  - `DELETE /{id}` — Delete a short URL mapping

## Getting Started

### Prerequisites
- Java 17 or later
- Maven

### Build and Run

```
./mvnw spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).

## API Endpoints
- `POST /shorten` - Shorten a URL
- `GET /{shortCode}` - Redirect to the original URL
- `PUT /{id}/expiry` - Update expiry for a short URL
- `DELETE /{id}` - Delete a short URL mapping
- Swagger UI: `/swagger-ui.html`

## Example API Usage

- **Shorten a URL:**
  ```json
  POST /shorten
  { "url": "https://example.com" }
  ```
- **Redirect:**
  ```
  GET /AbC1234
  ```
- **Update Expiry:**
  ```json
  PUT /1/expiry
  { "expiry": 3600 }
  ```
- **Delete:**
  ```
  DELETE /1
  ```

## License
MIT
