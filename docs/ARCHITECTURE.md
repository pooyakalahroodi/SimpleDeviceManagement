# Architecture

Simple Device Management is split into Maven modules with clear responsibilities. The current `dev-002` layout keeps the runnable application, the core business/API module, database scripts, and asynchronous order processing separate.

## Module Responsibilities

| Module | Responsibility |
| --- | --- |
| `SimpleDeviceManagementApp` | Spring Boot application entrypoint, component scanning, profiles, SQL resource wiring, and integration/BDD test resources. |
| `SimpleDeviceManagementCore` | REST controllers, service interfaces and implementations, transfer objects, mappers, JPA entities, repositories, validation, domain exceptions, and security configuration. |
| `SdmOrderProcessing` | JMS listener and asynchronous device-registration order processing. |
| `SimpleDeviceManagementDb` | PostgreSQL and Oracle schema, drop, seed-data, and Oracle setup scripts. |

## Runtime Flow

1. HTTP clients call the REST controllers in `SimpleDeviceManagementCore`.
2. Controllers validate incoming request data and delegate to service interfaces.
3. Core services apply business rules and call Spring Data repositories.
4. Repositories persist JPA entities to PostgreSQL or Oracle depending on the active Spring profile.
5. MapStruct mappers translate between persistence entities and transfer objects.

## Asynchronous Device Registration

`SdmOrderProcessing` listens on the `device-user-registration-queue` queue. Incoming `SdmDeviceRegistrationOrderTo` messages are converted from JSON and delegated to `SdmDeviceRegistrationService`, which coordinates the device-registration workflow.

The repository already contains PlantUML documentation for the REST and JMS device registration flows:

- `SimpleDeviceManagement/device-registration-flow-rest.puml`
- `SimpleDeviceManagement/device-registration-flow-jms.puml`

## Persistence Profiles

The application supports separate database configurations:

- PostgreSQL: `SimpleDeviceManagementApp/src/main/resources/application-postgres.properties`
- Oracle: `SimpleDeviceManagementApp/src/main/resources/application-oracle.properties`
- Development defaults: `SimpleDeviceManagementApp/src/main/resources/application-dev.properties`

SQL scripts are packaged from `SimpleDeviceManagementDb` into the app module so runtime initialization can use database-specific schema and data files.

## Security

The current API security model uses Spring Security with HTTP Basic authentication and an in-memory development user. CSRF is disabled for API/tooling convenience in the current configuration.

Before production use, replace development credentials with an externalized identity source and configure least-privilege roles for each API area.
