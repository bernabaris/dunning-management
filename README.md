# Dunning Management System

A Spring Boot application for managing customers, invoices, and dunning
processes.

The project demonstrates a simplified telecom dunning workflow where
overdue invoices are evaluated according to dunning levels and
corresponding collection actions.

The application includes:

-   Customer and invoice management
-   Dunning level calculation based on overdue days
-   Dunning action processing
-   Duplicate dunning action prevention
-   REST API error handling
-   Oracle database integration
-   PL/SQL stored procedures
-   JDBC `CallableStatement` integration
-   Unit and integration tests
-   AI-assisted spec-to-code workflow

## Tech Stack

-   Java 17
-   Spring Boot
-   Spring Data JPA
-   Oracle XE 21c
-   PL/SQL
-   JDBC / `CallableStatement`
-   Docker & Docker Compose
-   Maven
-   JUnit 5
-   Mockito
-   Postman

## Architecture

The main application flow follows a layered architecture:

``` text
Controller
    ↓
Service
    ↓
Repository
    ↓
Oracle Database
```

PL/SQL procedures are accessed from Spring Boot through JDBC:

``` text
Spring Boot
    ↓
DunningProcedureRepository
    ↓
CallableStatement
    ↓
Oracle PL/SQL Procedure
```
## Dunning Rules

| Overdue Days | Dunning Level | Action |
|--------------|---------------|---------------------|
| 0–7          | NONE          | No action           |
| 8–15         | LEVEL_1       | SEND_SMS            |
| 16–30        | LEVEL_2       | SEND_EMAIL          |
| 31–60        | LEVEL_3       | PHONE_CALL          |
| 61+          | LEVEL_4       | SERVICE_RESTRICTION |

Paid invoices are not processed by the dunning flow.

## API Endpoints

### Customer

-   `POST /api/customers` --- Create a customer
-   `GET /api/customers` --- List all customers
-   `GET /api/customers/{customerId}/invoices` --- List invoices for a
    customer

### Invoice

-   `POST /api/invoices` --- Create an invoice

### Dunning

-   `POST /api/dunning/process/{invoiceId}` --- Process an unpaid
    invoice according to the dunning rules
-   `GET /api/dunning/procedure/{invoiceId}` --- Retrieve a dunning
    level through the Oracle PL/SQL procedure
-   `GET /api/dunning/summary/{customerId}` --- Retrieve the customer's
    unpaid invoice summary through PL/SQL

## PL/SQL Integration

The project uses Oracle PL/SQL procedures to demonstrate database-side
processing and Java-to-Oracle procedure integration.

### `GET_DUNNING_LEVEL`

Accepts an invoice ID and returns its calculated dunning level. This
procedure is retained as an integration example; the core dunning
business rules are implemented in the Java service layer.

### `GET_CUSTOMER_DUNNING_SUMMARY`

Accepts a customer ID and returns:

-   Number of unpaid invoices
-   Total outstanding amount

The Spring Boot application calls the procedures using JDBC
`CallableStatement` with `IN` and `OUT` parameters.

## Error Handling

The REST API uses centralized exception handling for key dunning
scenarios:

-   `400 Bad Request` --- A paid invoice cannot be processed
-   `404 Not Found` --- Invoice does not exist
-   `409 Conflict` --- A dunning action already exists for the same
    invoice and dunning level

## Testing

The project includes unit and integration testing with JUnit 5 and
Mockito.

Unit tests cover dunning boundary rules and controller behavior.
Boundary cases include 7/8, 15/16, 30/31, and 60/61 overdue days.

Integration tests connect to the real Oracle database and verify the
PL/SQL procedure calls through `DunningProcedureRepository`.

## AI-Assisted Spec-to-Code Workflow

The repository contains `SPEC.md`, which defines the Customer Dunning
Summary feature before implementation.

The intended workflow is:

``` text
Requirement
    ↓
SPEC.md
    ↓
AI-assisted code generation
    ↓
Developer review
    ↓
Unit / Integration tests
    ↓
Verified implementation
```

AI-generated suggestions are reviewed against the existing architecture,
business requirements, data types, procedure parameters, and automated
tests rather than being accepted without validation.

## Running the Project

### Prerequisites

-   Java 17
-   Maven
-   Docker / Docker Compose

### Start Oracle

``` bash
docker compose up -d
```

The current local configuration uses Oracle XE with port `1521`.

### Run the Application

``` bash
mvn spring-boot:run
```

The API is configured locally on port `8085`.

### Run Tests

Ensure the Oracle Docker container is running before executing
integration tests:

``` bash
mvn test
```

## Specification

See [`SPEC.md`](SPEC.md) for the technical specification used in the
AI-assisted spec-to-code workflow.
