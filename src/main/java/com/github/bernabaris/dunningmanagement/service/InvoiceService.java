package com.github.bernabaris.dunningmanagement.service;

import com.github.bernabaris.dunningmanagement.dto.InvoiceRequest;
import com.github.bernabaris.dunningmanagement.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(InvoiceRequest invoice);

    List<Invoice> getInvoicesByCustomerId(Long customerId);
}
