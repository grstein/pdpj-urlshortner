# URL Shortener Webservice
The URL Shortener Webservice is a RESTful API designed to shorten long URLs into smaller, more manageable versions while keeping track of the number of times each shortened URL is requested. This project uses Java EE with Spring Boot and Hibernate, and utilizes PostgreSQL for the database. The API also incorporates Keycloak for authentication and authorization.

## Scope
The scope of this project includes the development of a RESTful API that supports URL shortening, tracking of shortened URL requests, and secure user authentication and authorization.

## Functional Requirements
Users must be able to create a new account or log in using existing credentials via Keycloak authentication.
Authenticated users must be able to submit long URLs to the API, which will then generate and return a unique shortened URL.
Users should be able to view a list of their previously shortened URLs, along with the number of times each URL has been requested.
The system must track the number of times each shortened URL is requested, incrementing the count whenever the URL is accessed.
When a user accesses a shortened URL, the system should redirect them to the original long URL.
The API should allow for deletion of a user's own shortened URLs.
The system should support URL validation to ensure only valid URLs are shortened.
## Non-Functional Requirements
Performance: The API must be able to handle a large number of concurrent requests without any significant degradation in performance.
Scalability: The system should be designed to accommodate increasing numbers of users and shortened URLs over time.
Security: User data and sensitive information must be securely stored and transmitted. All communications between the client and the server must be encrypted using HTTPS.
Availability: The API should have high availability, ensuring that users can access and utilize the service with minimal downtime.
Maintainability: The codebase should be well-structured, modular, and easy to understand, enabling future developers to maintain and expand the API with ease.
Backup and Recovery: The system should have regular database backups to protect against data loss, and a recovery plan in place for unexpected outages.
Monitoring and Logging: The system should have monitoring and logging features to track performance, usage, and potential issues.
## Technologies Used
- Java EE
- Spring Boot
- Hibernate
- PostgreSQL
- Keycloak

## Installation and Setup

1. Install Java Development Kit (JDK) version 11 or higher.
2. Install PostgreSQL and create a new database for the project.
3. Set up Keycloak for user authentication and configure the Keycloak settings in the `application.properties` file.
4. Clone the repository and navigate to the project directory.
5. Update the `application.properties` file with the appropriate database connection settings.
6. Build the project using the following command: `./mvnw clean package -DskipTests`
7. Run the application using the following command: `java -jar target/urlshortener-0.0.1-SNAPSHOT.jar`

## API Endpoints

- `POST /api/shorten`: Shortens a given long URL.
  - Input: JSON object containing the long URL.
  - Output: JSON object containing the generated short URL.
  - Authentication: Required

- `GET /api/shorturls`: Retrieves a list of the authenticated user's shortened URLs.
  - Output: JSON array of the user's shortened URLs.
  - Authentication: Required

...

## Testing

To run the tests for the project, use the following command in the project directory:

```bash
./mvnw test```

## Usage
To use the URL Shortener Webservice API, users must first create an account and obtain an API key through Keycloak authentication. Once authenticated, users can submit long URLs to the API, which will then generate and return a unique shortened URL. Users can view a list of their previously shortened URLs, delete their own shortened URLs, and track the number of times each shortened URL is requested.

## Contributors
This project is maintained by GrStein. If you would like to contribute to this project, please submit a pull request.