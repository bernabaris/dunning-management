package com.github.bernabaris.dunningmanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}