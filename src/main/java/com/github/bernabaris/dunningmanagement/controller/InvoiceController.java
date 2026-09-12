package com.github.bernabaris.dunningmanagement.controller;

import com.github.bernabaris.dunningmanagement.dto.InvoiceRequest;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.service.InvoiceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceRequest invoice) {
        return invoiceService.createInvoice(invoice);
    }


}
