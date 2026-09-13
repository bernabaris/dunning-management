package com.github.bernabaris.dunningmanagement.controller;

import com.github.bernabaris.dunningmanagement.dto.DunningLevelResponse;
import com.github.bernabaris.dunningmanagement.dto.DunningSummaryResponse;
import com.github.bernabaris.dunningmanagement.entity.DunningAction;
import com.github.bernabaris.dunningmanagement.repository.DunningProcedureRepository;
import com.github.bernabaris.dunningmanagement.service.DunningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dunning")
public class DunningController {

    private final DunningService dunningService;

    private final DunningProcedureRepository dunningProcedureRepository;

    public DunningController(DunningService dunningService, DunningProcedureRepository dunningProcedureRepository) {
        this.dunningService = dunningService;
        this.dunningProcedureRepository = dunningProcedureRepository;
    }

    @PostMapping("/process/{invoiceId}")
    public DunningAction processInvoice(@PathVariable Long invoiceId) {
        return dunningService.processInvoice(invoiceId);
    }

    @GetMapping("/procedure/{invoiceId}")
    public DunningLevelResponse getDunningLevelFromProcedure(@PathVariable Long invoiceId) {
        String level = dunningProcedureRepository.getDunningLevel(invoiceId);
        return new DunningLevelResponse(invoiceId,level);
    }

    @GetMapping("/summary/{customerId}")
    public DunningSummaryResponse getCustomerDunningSummary(
            @PathVariable Long customerId) {

        return dunningProcedureRepository
                .getCustomerDunningSummary(customerId);
    }
}
