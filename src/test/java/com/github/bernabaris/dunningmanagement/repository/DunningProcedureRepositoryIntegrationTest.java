package com.github.bernabaris.dunningmanagement.repository;

import com.github.bernabaris.dunningmanagement.dto.DunningSummaryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DunningProcedureRepositoryIntegrationTest {

    @Autowired
    private DunningProcedureRepository dunningProcedureRepository;

    @Test
    void shouldReturnLevel2FromOracleProcedure() {

        // Act
        String result = dunningProcedureRepository.getDunningLevel(3L);

        // Assert
        assertEquals("LEVEL_2", result);
    }

    @Test
    void shouldReturnCustomerDunningSummaryFromOracleProcedure() {

        DunningSummaryResponse result =
                dunningProcedureRepository.getCustomerDunningSummary(2L);

        assertEquals(2L, result.getCustomerId());
        assertEquals(4, result.getUnpaidInvoiceCount());
        assertEquals(new BigDecimal("5450"), result.getTotalOutstandingAmount());
    }
}