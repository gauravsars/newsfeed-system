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
SPRING_DATASOURCE_USERNAME=newsfeed \
SPRING_DATASOURCE_PASSWORD=secret \
mvn spring-boot:run
```

## REST API Reference

All endpoints are rooted at the Spring Boot default base URL `http://localhost:8080`.

| Method | URL | Description |
| --- | --- | --- |
| `POST` | `http://localhost:8080/api/users` | Create a user. |
| `POST` | `http://localhost:8080/api/users/{userId}/follow/{followeeId}` | Follow another user (path variables only, no request body). |
| `POST` | `http://localhost:8080/api/users/{userId}/interests/{categoryId}` | Add a category to a user's interests (path variables only). |
| `POST` | `http://localhost:8080/api/categories` | Create a category. |
| `GET` | `http://localhost:8080/api/categories` | List all categories. |
| `GET` | `http://localhost:8080/api/categories/{categoryId}` | Get a single category. |
| `POST` | `http://localhost:8080/api/posts` | Create a post that belongs to one or more categories. |
| `POST` | `http://localhost:8080/api/posts/{postId}/comments` | Comment on a post. |
| `POST` | `http://localhost:8080/api/posts/{postId}/likes?userId={userId}` | Like a post (user supplied as query parameter). |
| `POST` | `http://localhost:8080/api/posts/{postId}/shares?userId={userId}` | Share a post (user supplied as query parameter). |
| `GET` | `http://localhost:8080/api/feed/{userId}` | Retrieve the personalized feed for a user. |

### JSON payloads for POST requests

#### Create a user

```json
{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "password": "p@ssw0rd"
}
```

#### Create a category

```json
{
  "name": "Technology"
}
```

#### Create a post

```json
{
  "userId": 1,
  "content": "Exploring the latest in AI research.",
  "mediaUrl": "https://cdn.example.com/posts/ai-update.png",
  "categoryIds": [1, 3]
}
```

`mediaUrl` is optional—omit it if the post contains only text.

#### Add a comment to a post

```json
{
  "userId": 2,
  "content": "Great insights!"
}
```

### Example requests without JSON bodies

- **Follow a user:** `POST http://localhost:8080/api/users/1/follow/2`
- **Add an interest:** `POST http://localhost:8080/api/users/1/interests/5`
- **Like a post:** `POST http://localhost:8080/api/posts/10/likes?userId=3`
- **Share a post:** `POST http://localhost:8080/api/posts/10/shares?userId=3`

Request payloads are validated and return HTTP 400 if constraints fail. Standard JPA exceptions (such as referencing missing IDs) surface as HTTP 404 errors.

### Postman collection

Import [`postman/newsfeed-system.postman_collection.json`](postman/newsfeed-system.postman_collection.json) into Postman to get preconfigured requests for every endpoint. The collection uses a `baseUrl` variable that defaults to `http://localhost:8080`; override it in Postman if your server runs on a different host or port.

## Database Schema

The project ships with JPA entities that map directly to the schema shared in the requirements. Hibernate will automatically generate the required tables (including the `post_categories` join table) when the datasource is configured with `spring.jpa.hibernate.ddl-auto=update` against PostgreSQL. Adjust the setting as needed for schema management in other environments.

## Testing

The project currently relies on manual or integration testing via API clients such as Postman or cURL. Tests can be added under `src/test/java` as needed.
