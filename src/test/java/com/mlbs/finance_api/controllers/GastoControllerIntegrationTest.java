package com.mlbs.finance_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlbs.finance_api.entities.dto.GastoSaveRequestDTO;
import com.mlbs.finance_api.entities.enums.Moeda;
import com.mlbs.finance_api.repositories.GastoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GastoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        gastoRepository.deleteAll();
    }

    @Test
    void deveCriarGastoComSucesso() throws Exception {

        GastoSaveRequestDTO dto = new GastoSaveRequestDTO(
                new BigDecimal("150.00"),
                "Supermercado",
                LocalDate.of(2025, 1, 1),
                Moeda.REAL
        );

        mockMvc.perform(
                post("/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.descricao").value("Supermercado"))
        .andExpect(jsonPath("$.valor").value(150.00))
        .andExpect(jsonPath("$.moeda").value(Moeda.REAL.name()))
        .andExpect(jsonPath("$.data").value("2025-01-01"));
        
        assertEquals(1, gastoRepository.count());
    }
}
