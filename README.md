# URL Shortener Spring Boot Project

This is a Spring Boot application that provides a simple URL shortening service.

## Features
- Shorten long URLs via a REST API
- Redirect short URLs to the original long URLs

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

## License
MIT
