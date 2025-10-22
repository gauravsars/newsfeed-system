# Newsfeed System

This project is a Spring Boot application that models a social newsfeed with support for posts, likes, comments, shares, following relationships, and personalized feeds based on user interests.

## Features

- Create users with hashed passwords and manage their interests.
- Create categories and associate posts with one or more categories.
- Create posts that can include rich text and optional media URLs.
- Like, comment on, and share posts.
- Follow other users while preventing duplicate or self-follow operations.
- Generate a personalized feed that surfaces posts from followed users and posts that match a user's interest categories.

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA with Hibernate
- PostgreSQL 14+ (configurable via Spring Boot datasource properties)
- Maven for build management

## Building and Running

```bash
mvn spring-boot:run
```

The application exposes a REST API on port `8080`.

### Database configuration

By default the app expects a PostgreSQL instance reachable at `jdbc:postgresql://localhost:5432/newsfeed` with a `newsfeed` user/password. Override these settings using environment variables when starting the app:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/newsfeed \
SPRING_DATASOURCE_USERNAME=postgres \
SPRING_DATASOURCE_PASSWORD=java \
mvn spring-boot:run
```

## Key API Endpoints

| Endpoint | Method | Description |
| --- | --- | --- |
| `/api/users](http://localhost:8080/api/users` | `POST` | Create a user. |
| `[/api/users/{userId}/follow/{followeeId}](http://localhost:8080/api/users/{userId}/follow/{followeeId})` | `POST` | Follow another user. |
| `[/api/users/{userId}/interests/{categoryId}](http://localhost:8080/api/users/{userId}/interests/{categoryId})` | `POST` | Add an interest category to a user. |
| `[/api/categories](http://localhost:8080/api/categories)` | `POST` | Create a new category. |
| `[/api/categories](http://localhost:8080/api/categories)` | `GET` | List all categories. |
| `[/api/posts](http://localhost:8080/api/posts)` | `POST` | Create a new post for a user with categories. |
| `[/api/posts/{postId}/comments](http://localhost:8080/api/posts/{postId}/comments)` | `POST` | Comment on a post. |
| `[/api/posts/{postId}/likes](http://localhost:8080/api/posts/{postId}/likes?userId={userId})` | `POST` | Like a post. |
| `[/api/posts/{postId}/shares](http://localhost:8080/api/posts/{postId}/shares?userId={userId})` | `POST` | Share a post. |
| `[/api/feed/{userId}](http://localhost:8080/api/feed/{userId})` | `GET` | Retrieve the personalized feed for a user. |

Request payloads are validated and will return HTTP 400 responses if validation fails. Standard JPA exceptions (such as missing IDs) surface as HTTP 404 errors.

## Database Schema

The project ships with JPA entities that map directly to the schema shared in the requirements. Hibernate will automatically generate the required tables (including the `post_categories` join table) when the datasource is configured with `spring.jpa.hibernate.ddl-auto=update` against PostgreSQL. Adjust the setting as needed for schema management in other environments.

## Testing

The project currently relies on manual or integration testing via API clients such as Postman or cURL. Tests can be added under `src/test/java` as needed.
