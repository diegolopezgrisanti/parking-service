
# Parking Service

This project is a parking management service built with Spring Boot and PostgreSQL.

## Prerequisites

Ensure you have the following installed before starting:

- JDK 21 or higher
- Docker and Docker Compose
- Gradle 8.9 or higher (optional if you prefer using `./gradlew`)

## Local Environment Setup

### 1. Database Setup with Docker Compose

This project uses PostgreSQL as the database. You can spin up a local instance using Docker Compose.

To start the PostgreSQL database with Docker Compose:

```bash
docker-compose up -d
```

### 2. Running the Service

To run the service locally:

1. Ensure the database is running.
2. Start the service with the following command:

```bash
./gradlew bootRun
```

The service will be available at [http://localhost:8080/](http://localhost:8080/)

### 3. Database Migrations with Flyway

Database migrations are managed by Flyway. Make sure to follow the naming conventions below when creating new migration scripts:

- **Prefix:** Use `V` for versioned migrations and `R` for repeatable migrations.
- **Version Number:** Follow the prefix with a version number (e.g., `V001`).
- **Separator:** Use double underscores `__` to separate the version from the description.
- **Description:** Provide a brief description of the migration in lowercase, using underscores to separate words.
- **Suffix:** End with `.sql` for SQL migrations.

Example:

```text
V001__create_users_table.sql
R__populate_initial_data.sql
```

### 4. Running Tests

The project includes various levels of testing: unit, integration, contract, and component tests. You can run all tests using Gradle.

1. To run unit tests:

```bash
./gradlew test
```

2. To run integration tests:

```bash
./gradlew integrationTest
```

3. To run contract tests:

```bash
./gradlew contractTest
```

4. To run component tests:

```bash
./gradlew componentTest
```

5. To run all tests:

```bash
./gradlew check
```

## Key Dependencies

The project uses the following key dependencies:

- **Spring Boot:** The main framework for building the service.
- **PostgreSQL:** Relational database.
- **Flyway:** Tool for managing database migrations.


## Additional Documentation

For more information, refer to the following resources:

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#build-image)

---
