package com.github.bernabaris.dunningmanagement.repository;

import com.github.bernabaris.dunningmanagement.entity.DunningAction;
import com.github.bernabaris.dunningmanagement.enums.DunningLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DunningActionRepository extends JpaRepository<DunningAction,Long> {
    boolean existsByInvoiceIdAndDunningLevel(
            Long invoiceId,
            DunningLevel dunningLevel
    );
}
