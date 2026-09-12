package com.github.bernabaris.dunningmanagement.impl;

import com.github.bernabaris.dunningmanagement.entity.DunningAction;
import com.github.bernabaris.dunningmanagement.entity.Invoice;
import com.github.bernabaris.dunningmanagement.enums.ActionType;
import com.github.bernabaris.dunningmanagement.enums.DunningLevel;
import com.github.bernabaris.dunningmanagement.enums.InvoiceStatus;
import com.github.bernabaris.dunningmanagement.repository.DunningActionRepository;
import com.github.bernabaris.dunningmanagement.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class DunningServiceImplTest {

    private DunningServiceImpl dunningService;
    private InvoiceRepository invoiceRepository;
    private DunningActionRepository dunningActionRepository;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        dunningActionRepository = mock(DunningActionRepository.class);

        dunningService = new DunningServiceImpl(
                invoiceRepository,
                dunningActionRepository
        );
    }

    @Test
    void shouldReturnNoneWhenInvoiceIs7DaysOverdue() {
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(7));

        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        assertEquals(DunningLevel.NONE, result);
    }

    @Test
    void shouldReturnLevel1WhenInvoiceIs8DaysOverdue() {

        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(8));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_1, result);
    }

    @Test
    void shouldReturnLevel1WhenInvoiceIs15DaysOverdue() {

        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(15));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_1, result);
    }

    @Test
    void shouldReturnLevel2WhenInvoiceIs16DaysOverdue() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(16));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_2, result);
    }

    @Test
    void shouldReturnLevel2WhenInvoiceIs30DaysOverdue() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(30));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_2, result);
    }

    @Test
    void shouldReturnLevel3WhenInvoiceIs31DaysOverdue() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(31));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_3, result);
    }

    @Test
    void shouldReturnLevel3WhenInvoiceIs60DaysOverdue() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(60));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_3, result);
    }

    @Test
    void shouldReturnLevel4WhenInvoiceIs61DaysOverdue() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setDueDate(LocalDate.now().minusDays(61));

        // Act
        DunningLevel result = dunningService.calculateDunningLevel(invoice);

        // Assert
        assertEquals(DunningLevel.LEVEL_4, result);
    }

    @Test
    void shouldCreateSendSmsActionWhenInvoiceIs8DaysOverdue() {

        // Arrange
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setDueDate(LocalDate.now().minusDays(8));

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        when(dunningActionRepository.save(any(DunningAction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        DunningAction result = dunningService.processInvoice(1L);

        // Assert
        assertEquals(DunningLevel.LEVEL_1, result.getDunningLevel());
        assertEquals(ActionType.SEND_SMS, result.getActionType());
        assertEquals(invoice, result.getInvoice());
    }

    @Test
    void shouldThrowExceptionWhenInvoiceIsPaid() {

        // Arrange
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setDueDate(LocalDate.now().minusDays(20));

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dunningService.processInvoice(1L)
        );

        assertEquals(
                "Paid invoice cannot be processed",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenInvoiceNotFound() {

        // Arrange
        when(invoiceRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dunningService.processInvoice(999L)
        );

        assertEquals(
                "Invoice not found",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenDunningActionAlreadyExists() {

        // Arrange
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setDueDate(LocalDate.now().minusDays(8));

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        when(dunningActionRepository.existsByInvoiceIdAndDunningLevel(
                1L,
                DunningLevel.LEVEL_1
        )).thenReturn(true);

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> dunningService.processInvoice(1L)
        );

        assertEquals(
                "Dunning action already exists for this level",
                exception.getMessage()
        );
    }

}
