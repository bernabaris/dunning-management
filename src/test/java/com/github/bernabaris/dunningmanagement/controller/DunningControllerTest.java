package com.github.bernabaris.dunningmanagement.controller;

import com.github.bernabaris.dunningmanagement.repository.DunningProcedureRepository;
import com.github.bernabaris.dunningmanagement.service.DunningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DunningControllerTest {

    private DunningController dunningController;
    private DunningService dunningService;
    private DunningProcedureRepository dunningProcedureRepository;

    @BeforeEach
    void setUp() {
        dunningService = mock(DunningService.class);
        dunningProcedureRepository = mock(DunningProcedureRepository.class);

        dunningController = new DunningController(
                dunningService,
                dunningProcedureRepository
        );
    }

    @Test
    void shouldReturnDunningLevelFromProcedure() {

        when(dunningProcedureRepository.getDunningLevel(3L))
                .thenReturn("LEVEL_2");

        var response = dunningController
                .getDunningLevelFromProcedure(3L);

        assertEquals(3L, response.getInvoiceId());
        assertEquals("LEVEL_2", response.getDunningLevel());
    }
}