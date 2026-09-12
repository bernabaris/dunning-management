package com.github.bernabaris.dunningmanagement.controller;

import com.github.bernabaris.dunningmanagement.entity.Customer;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.service.CustomerService;
import com.github.bernabaris.dunningmanagement.service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final InvoiceService invoiceService;

    public CustomerController(CustomerService customerService, InvoiceService invoiceService) {
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping("/{customerId}/invoices")
    public List<Invoice> getInvoicesByCustomerId(@PathVariable Long customerId) {
        return invoiceService.getInvoicesByCustomerId(customerId);
    }
}
