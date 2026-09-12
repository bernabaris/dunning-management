package com.github.bernabaris.dunningmanagement.impl;

import com.github.bernabaris.dunningmanagement.entity.DunningAction;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.enums.ActionType;
import com.github.bernabaris.dunningmanagement.enums.DunningLevel;
import com.github.bernabaris.dunningmanagement.enums.InvoiceStatus;
import com.github.bernabaris.dunningmanagement.exception.DunningAlreadyExistsException;
import com.github.bernabaris.dunningmanagement.exception.InvoiceNotFoundException;
import com.github.bernabaris.dunningmanagement.exception.PaidInvoiceException;
import com.github.bernabaris.dunningmanagement.repository.DunningActionRepository;
import com.github.bernabaris.dunningmanagement.repository.InvoiceRepository;
import com.github.bernabaris.dunningmanagement.service.DunningService;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class DunningServiceImpl implements DunningService {

    private final InvoiceRepository invoiceRepository;
    private final DunningActionRepository dunningActionRepository;

    public DunningServiceImpl(
            InvoiceRepository invoiceRepository,
            DunningActionRepository dunningActionRepository) {

        this.invoiceRepository = invoiceRepository;
        this.dunningActionRepository = dunningActionRepository;
    }
    @Override
    public DunningLevel calculateDunningLevel(Invoice invoice) {

        long overdueDays = ChronoUnit.DAYS.between(
                invoice.getDueDate(),
                LocalDate.now()
        );

        if (overdueDays <= 7) {
            return DunningLevel.NONE;
        }

        if (overdueDays <= 15) {
            return DunningLevel.LEVEL_1;
        }

        if (overdueDays <= 30) {
            return DunningLevel.LEVEL_2;
        }

        if (overdueDays <= 60) {
            return DunningLevel.LEVEL_3;
        }

        return DunningLevel.LEVEL_4;
    }

    @Override
    public DunningAction processInvoice(Long invoiceId) {

        Invoice invoice = invoiceRepository
                .findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found"));

        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new PaidInvoiceException("Paid invoice cannot be processed");
        }

        DunningLevel dunningLevel = calculateDunningLevel(invoice);

        if (dunningLevel == DunningLevel.NONE) {
            throw new RuntimeException("Invoice is not overdue enough for dunning");
        }

        boolean alreadyProcessed =
                dunningActionRepository.existsByInvoiceIdAndDunningLevel(
                        invoice.getId(),
                        dunningLevel
                );

        if (alreadyProcessed) {
            throw new DunningAlreadyExistsException(
                    "Dunning action already exists for this level"
            );
        }

        DunningAction dunningAction = new DunningAction();

        dunningAction.setInvoice(invoice);
        dunningAction.setDunningLevel(dunningLevel);
        dunningAction.setCreatedAt(LocalDateTime.now());

        if (dunningLevel == DunningLevel.LEVEL_1) {
            dunningAction.setActionType(ActionType.SEND_SMS);
        } else if (dunningLevel == DunningLevel.LEVEL_2) {
            dunningAction.setActionType(ActionType.SEND_EMAIL);
        } else if (dunningLevel == DunningLevel.LEVEL_3) {
            dunningAction.setActionType(ActionType.PHONE_CALL);
        } else if (dunningLevel == DunningLevel.LEVEL_4) {
            dunningAction.setActionType(ActionType.SERVICE_RESTRICTION);
        }

        return dunningActionRepository.save(dunningAction);
    }
}
