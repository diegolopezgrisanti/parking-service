

# Read Me First
The following information was discovered during the project build process:

* The JVM level has been updated from '11' to '17'. For more details, review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) in the wiki.
* The original package name 'com.parkingapp.parking-service' is invalid. This project now employs 'com.parkingapp.parkingservice'.

# Getting Started

### Setting Up Locally

To run the project locally, follow these steps:

1. Start the Docker container housing the PostgreSQL database.
2. Run the project.

After completing these steps, the service will be accessible at the following URL: [http://localhost:8080/](http://localhost:8080/).

Certainly! Here's an addition to your README regarding the naming conventions for Flyway migrations:

---

### Running Migrations with Flyway

#### Migration Naming Conventions

Flyway follows a specific naming convention for migration files. Ensure your migration files adhere to the following naming format:

* **Prefix:** Start the filename with a prefix that describes the purpose or category of the migration. For example: `V` for versioned migrations, `R` for repeatable migrations, or any custom identifier.

* **Version Number:** Follow the prefix with a version number, separating elements with underscores. For versioned migrations, this number reflects the version the migration applies to, like `V001`.
* **Separator:** Use double underscore as a separator between version and description, like `V001__`.

* **Description:** Add a brief description of the migration, using underscores to separate words. For example, `add_users_table` or `populate_initial_data`.

* **Suffix (For Versioned Migrations):** End versioned migration filenames with `.sql` for SQL migrations, `.kt` for Kotlin, `.java` for Java, etc.

* **Example (Versioned Migration):** `V001__create_users_table.sql`

* **Example (Repeatable Migration):** `R__populate_initial_data.sql`

Ensure your migration filenames follow these conventions to allow Flyway to execute them correctly.

---



### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.1/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references might be helpful:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

---

Feel free to provide more details about the migration process or any other information you'd like to include!