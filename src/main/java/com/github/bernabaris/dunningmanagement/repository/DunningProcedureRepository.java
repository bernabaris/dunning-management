package com.github.bernabaris.dunningmanagement.repository;

public interface DunningProcedureRepository {
    String getDunningLevel(Long invoiceId);
}
