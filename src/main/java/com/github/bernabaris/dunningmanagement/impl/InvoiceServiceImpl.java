package com.github.bernabaris.dunningmanagement.impl;

import com.github.bernabaris.dunningmanagement.dto.InvoiceRequest;
import com.github.bernabaris.dunningmanagement.entity.Customer;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.repository.CustomerRepository;
import com.github.bernabaris.dunningmanagement.repository.InvoiceRepository;
import com.github.bernabaris.dunningmanagement.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final CustomerRepository customerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository){
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Invoice createInvoice(InvoiceRequest request){
        Invoice invoice = new Invoice();

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        invoice.setAmount(request.getAmount());
        invoice.setDueDate(request.getDueDate());
        invoice.setStatus(request.getStatus());
        invoice.setCustomer(customer);
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }
}
