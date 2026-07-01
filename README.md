# Simple Device Management

Simple Device Management is a Spring Boot based backend for managing company devices, departments, users, and device handover protocols. It exposes REST APIs for the core workflows, stores data through JPA, supports PostgreSQL and Oracle profiles, and includes asynchronous device registration through JMS.

The project is organized as a multi-module Maven application so API contracts, persistence, domain logic, service endpoints, the runnable starter, and order-processing concerns can evolve independently.

## Highlights

- Device inventory management with serial-number based lookup and status updates.
- User and department management with assignment workflows.
- Device handover protocol creation, lookup, latest-protocol retrieval, and confirmation.
- Asynchronous device-registration order processing over JMS.
- PostgreSQL and Oracle SQL resources for schema and initial data.
- Spring Security with HTTP Basic authentication for API access.
- OpenAPI annotations for generated API documentation.
- Unit, controller, service, and Cucumber-style BDD test coverage.
- Docker and Docker Compose support for local infrastructure.
- GitHub Actions backend CI for every push and pull request.

## Technology Stack

| Area | Technology |
| --- | --- |
| Runtime | Java 21, Spring Boot 3.5 |
| Build | Maven multi-module project |
| Web/API | Spring Web, Spring Validation, SpringDoc OpenAPI |
| Persistence | Spring Data JPA, PostgreSQL, Oracle JDBC |
| Mapping | MapStruct, Lombok |
| Security | Spring Security, HTTP Basic |
| Messaging | Spring JMS, ActiveMQ |
| Tests | JUnit 5, Spring Boot Test, Cucumber, Testcontainers |
| Packaging | Docker multi-stage build |

## Project Structure

```text
SimpleDeviceManagement/
  SdmCoreApi/          Shared transfer objects and enums
  SdmCorePersistent/   JPA entities and repositories
  SdmCore/             Core domain services, mappers, validation, security
  SdmServices/         REST controllers and service facade layer
  SdmStarter/          Spring Boot application entrypoint and runtime config
  SdmOrderProcessing/  JMS listener and asynchronous registration processing
  SdmDataBase/         PostgreSQL and Oracle schema/data scripts
  Dockerfile           Production-style application image build
  docker-compose.yml   Local database, Oracle, and application services
```

Additional documentation lives in [`docs/`](docs/):

- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) explains the module responsibilities and request flows.
- [`docs/GITHUB_WORKFLOW.md`](docs/GITHUB_WORKFLOW.md) defines the protected-branch and pull-request workflow.

## Prerequisites

- Java 21
- Maven 3.9 or newer
- Docker and Docker Compose, when running local database services
- GitHub CLI, when working with pull requests from the command line

## Getting Started

Clone the repository and enter the Maven project directory:

```bash
git clone https://github.com/pooyakalahroodi/SimpleDeviceManagement.git
cd SimpleDeviceManagement/SimpleDeviceManagement
```

Build and test the project:

```bash
mvn clean verify
```

Start the local PostgreSQL database:

```bash
docker compose up -d postgres
```

Run the application with the PostgreSQL profile:

```bash
mvn -pl SdmStarter spring-boot:run -Dspring-boot.run.profiles=dev,postgres
```

The development server uses the configured Spring Boot port from `SdmStarter/src/main/resources/application-dev.properties`.

## Docker

Build and run the application stack with PostgreSQL:

```bash
cd SimpleDeviceManagement
docker compose up --build app
```

Run with Oracle support:

```bash
docker compose --profile oracle up --build app-oracle
```

## API Overview

The service exposes the main REST resources under `/api`:

| Resource | Base Path | Purpose |
| --- | --- | --- |
| Devices | `/api/devices` | Create devices, list devices, update device status |
| Users | `/api/users` | Create users, query users, assign devices and departments |
| Departments | `/api/departments` | Create and list departments |
| Handover protocols | `/api/handover-protocols` | Create, query, and confirm handover protocols |

The API is protected by HTTP Basic authentication. The current development credentials are defined in `SdmCore/src/main/java/.../SecurityConfig.java`.

## Testing

Run the full Maven verification lifecycle:

```bash
cd SimpleDeviceManagement
mvn clean verify
```

Run a faster package build without tests when building a local image or checking packaging only:

```bash
mvn clean package -DskipTests
```

BDD tests are located in `SdmStarter/src/test/resources/features`.

## Continuous Integration

GitHub Actions runs the `backend` workflow on pushes and pull requests. The required check is:

```text
backend
```

The workflow validates the Java backend with Maven and is intended to be required by the `main` branch protection rule. See [`docs/GITHUB_WORKFLOW.md`](docs/GITHUB_WORKFLOW.md) for the full repository workflow.

## Contribution Workflow

All changes should go through pull requests:

```bash
git checkout main
git pull
git checkout -b dev/name-of-change
git push -u origin dev/name-of-change
```

Open a pull request into `main`, wait for the `backend` check to pass, resolve all review conversations, and merge only after approval.

Direct pushes to `main` should remain disabled through GitHub branch protection.
