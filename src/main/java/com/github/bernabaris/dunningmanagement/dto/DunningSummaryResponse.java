package com.github.bernabaris.dunningmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DunningSummaryResponse {

    private Long customerId;
    private Integer unpaidInvoiceCount;
    private BigDecimal totalOutstandingAmount;
}