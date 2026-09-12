package com.github.bernabaris.dunningmanagement.service;

import com.github.bernabaris.dunningmanagement.entity.DunningAction;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.enums.DunningLevel;

public interface DunningService {

    DunningLevel calculateDunningLevel(Invoice invoice);

    DunningAction processInvoice(Long invoiceId);
}
