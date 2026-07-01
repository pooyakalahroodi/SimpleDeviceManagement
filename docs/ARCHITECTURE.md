# Architecture

Simple Device Management is split into Maven modules with clear responsibilities. The current layout keeps transport models, persistence, domain behavior, REST APIs, runtime bootstrapping, and asynchronous processing separate.

## Module Responsibilities

| Module | Responsibility |
| --- | --- |
| `SdmCoreApi` | Shared transfer objects, domain-facing models, enums, and validation annotations used across modules. |
| `SdmCorePersistent` | JPA entities and Spring Data repositories for devices, users, departments, and handover protocols. |
| `SdmCore` | Core service interfaces and implementations, mapping with MapStruct, validation, domain exceptions, and security configuration. |
| `SdmServices` | REST controllers and service facade layer exposed under `/api`. |
| `SdmStarter` | Spring Boot application entrypoint, component scanning, profiles, SQL resource wiring, and integration/BDD test resources. |
| `SdmOrderProcessing` | JMS listener and asynchronous device-registration order processing. |
| `SdmDataBase` | PostgreSQL and Oracle schema, drop, seed-data, and Oracle setup scripts. |

## Runtime Flow

1. HTTP clients call the REST controllers in `SdmServices`.
2. Controllers validate incoming request data and delegate to service interfaces.
3. Core services in `SdmCore` apply business rules and call repositories from `SdmCorePersistent`.
4. Repositories persist JPA entities to PostgreSQL or Oracle depending on the active Spring profile.
5. MapStruct mappers translate between persistence entities and transfer objects from `SdmCoreApi`.

## Asynchronous Device Registration

`SdmOrderProcessing` listens on the `device-user-registration-queue` queue. Incoming `SdmDeviceRegistrationOrderTo` messages are converted from JSON and delegated to `SdmDeviceRegistrationService`, which coordinates the device-registration workflow.

The repository already contains PlantUML documentation for the REST and JMS device registration flows:

- `SimpleDeviceManagement/device-registration-flow-rest.puml`
- `SimpleDeviceManagement/device-registration-flow-jms.puml`

## Persistence Profiles

The application supports separate database configurations:

- PostgreSQL: `SdmStarter/src/main/resources/application-postgres.properties`
- Oracle: `SdmStarter/src/main/resources/application-oracle.properties`
- Development defaults: `SdmStarter/src/main/resources/application-dev.properties`

SQL scripts are packaged from `SdmDataBase` into the starter module so runtime initialization can use database-specific schema and data files.

## Security

The current API security model uses Spring Security with HTTP Basic authentication and an in-memory development user. CSRF is disabled for API/tooling convenience in the current configuration.

Before production use, replace development credentials with an externalized identity source and configure least-privilege roles for each API area.
