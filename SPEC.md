# Dunning Management - Technical Specification

## Feature: Customer Dunning Summary

### Goal

Provide an API endpoint that returns a customer's unpaid invoice
summary.

### Endpoint

`GET /api/dunning/summary/{customerId}`

### Input

Path variable:

-   `customerId`: Long

Example:

`GET /api/dunning/summary/2`

### Business Requirements

The system must:

1.  Find all invoices belonging to the given customer.
2.  Only include invoices with status `UNPAID`.
3.  Return the total number of unpaid invoices.
4.  Return the total outstanding amount.
5.  Paid invoices must not be included in the calculation.

### Database Requirement

The aggregation must be performed by an Oracle PL/SQL procedure.

Procedure name:

`GET_CUSTOMER_DUNNING_SUMMARY`

Input parameter:

-   `p_customer_id IN NUMBER`

Output parameters:

-   `p_unpaid_invoice_count OUT NUMBER`
-   `p_total_outstanding_amount OUT NUMBER`

### Expected API Response

``` json
{
  "customerId": 2,
  "unpaidInvoiceCount": 4,
  "totalOutstandingAmount": 5450
}
```

### Implementation Requirements

The Spring Boot application must contain:

-   `DunningSummaryResponse` DTO
-   `DunningProcedureRepository`
-   `DunningProcedureRepositoryImpl`
-   `CallableStatement` based PL/SQL integration
-   `DunningController` endpoint

### Testing Requirements

The implementation must include:

-   Integration test for the Oracle PL/SQL procedure
-   Validation of unpaid invoice count
-   Validation of total outstanding amount

### Acceptance Criteria

Given customer 2 has:

-   4 `UNPAID` invoices
-   1 `PAID` invoice

When:

`GET /api/dunning/summary/2`

Then the API must return:

-   `unpaidInvoiceCount = 4`
-   `totalOutstandingAmount = 5450`

The `PAID` invoice must not be included.
