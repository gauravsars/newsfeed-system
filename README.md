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
- H2 in-memory database (can be swapped for Postgres or MySQL)
- Maven for build management

## Building and Running

```bash
mvn spring-boot:run
```

The application exposes a REST API on port `8080`. An in-memory H2 console is also available at `http://localhost:8080/h2-console` with the default credentials (`sa`/`sa`).

## Key API Endpoints

| Endpoint | Method | Description |
| --- | --- | --- |
| `/api/users` | `POST` | Create a user. |
| `/api/users/{userId}/follow/{followeeId}` | `POST` | Follow another user. |
| `/api/users/{userId}/interests/{categoryId}` | `POST` | Add an interest category to a user. |
| `/api/categories` | `POST` | Create a new category. |
| `/api/categories` | `GET` | List all categories. |
| `/api/posts` | `POST` | Create a new post for a user with categories. |
| `/api/posts/{postId}/comments` | `POST` | Comment on a post. |
| `/api/posts/{postId}/likes` | `POST` | Like a post. |
| `/api/posts/{postId}/shares` | `POST` | Share a post. |
| `/api/feed/{userId}` | `GET` | Retrieve the personalized feed for a user. |

Request payloads are validated and will return HTTP 400 responses if validation fails. Standard JPA exceptions (such as missing IDs) surface as HTTP 404 errors.

## Database Schema

The project ships with JPA entities that map directly to the schema shared in the requirements. Hibernate will automatically generate the required tables (including the `post_categories` join table) when running against the in-memory database. For production usage, you can point the datasource configuration to an external PostgreSQL or MySQL instance.

## Testing

The project currently relies on manual or integration testing via API clients such as Postman or cURL. Tests can be added under `src/test/java` as needed.
