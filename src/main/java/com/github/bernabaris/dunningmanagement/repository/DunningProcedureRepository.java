package com.github.bernabaris.dunningmanagement.repository;

import com.github.bernabaris.dunningmanagement.dto.DunningSummaryResponse;

public interface DunningProcedureRepository {
    String getDunningLevel(Long invoiceId);
    DunningSummaryResponse getCustomerDunningSummary(Long customerId);
}
