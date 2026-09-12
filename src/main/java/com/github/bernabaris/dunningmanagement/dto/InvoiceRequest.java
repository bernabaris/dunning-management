package com.github.bernabaris.dunningmanagement.dto;

import com.github.bernabaris.dunningmanagement.enums.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {
    private BigDecimal amount;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private Long customerId;
}
