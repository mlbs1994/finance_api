package com.mlbs.finance_api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlbs.finance_api.entities.Gasto;
import com.mlbs.finance_api.entities.dto.GastoSaveRequestDTO;
import com.mlbs.finance_api.entities.dto.GastoUpdateRequestDTO;
import com.mlbs.finance_api.entities.enums.Moeda;
import com.mlbs.finance_api.repositories.GastoRepository;
import com.mlbs.finance_api.services.GastoService;
import com.mlbs.finance_api.utils.TestEntityFactory;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GastoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GastoRepository repository;
    
    @Autowired
    private GastoService service;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TestEntityFactory testEntityFactory;
    
    private static final GastoSaveRequestDTO DTO_PADRAO = new GastoSaveRequestDTO(
            new BigDecimal("150.00"),
            "Supermercado",
            LocalDate.of(2025, 1, 1),
            Moeda.REAL
    );

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void deveCriarGastoComSucesso() throws Exception {

        mockMvc.perform(
                post("/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DTO_PADRAO))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.descricao").value("Supermercado"))
        .andExpect(jsonPath("$.valor").value(150.00))
        .andExpect(jsonPath("$.moeda").value(Moeda.REAL.name()))
        .andExpect(jsonPath("$.data").value("2025-01-01"));
        
        assertEquals(1, repository.count());
    }
    
    @Test
    void deveConsultarGastoComSucesso() throws Exception {
    	
    	var gastoSalvo = testEntityFactory.createEntity(DTO_PADRAO, service::save);

        mockMvc.perform(
                get("/gastos/{id}", gastoSalvo.id())
                        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.descricao").value("Supermercado"))
        .andExpect(jsonPath("$.valor").value(150.00))
        .andExpect(jsonPath("$.moeda").value(Moeda.REAL.name()))
        .andExpect(jsonPath("$.data").value("2025-01-01"));
        
        assertEquals(1, repository.count());
    }
    
    @Test
    void deveListarGastosComSucesso() throws Exception {

        for (int i = 1; i <= 5; i++) {
            testEntityFactory.createEntity(DTO_PADRAO, service::save);
        }

        mockMvc.perform(get("/gastos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.length()").value(5))
            .andExpect(jsonPath("$.content[0].descricao").value("Supermercado"))
            .andExpect(jsonPath("$.content[0].valor").value(150.00))
            .andExpect(jsonPath("$.content[0].moeda").value(Moeda.REAL.name()))
            .andExpect(jsonPath("$.content[0].data").value("2025-01-01"));

        assertEquals(5, repository.count());
    }
    
    @Test
    void deveEditarGastoComSucesso() throws Exception {

        var gastoAntes = testEntityFactory.createEntity(DTO_PADRAO, service::save);

        GastoUpdateRequestDTO request = new GastoUpdateRequestDTO(
                new BigDecimal("200.00"),
                "Farmácia",
                null,
                null
        );

        String json = mockMvc.perform(
                        put("/gastos/{id}", gastoAntes.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Farmácia"))
                .andExpect(jsonPath("$.valor").value(200.00))
                .andExpect(jsonPath("$.moeda").value(Moeda.REAL.name()))
                .andExpect(jsonPath("$.data").value("2025-01-01"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Gasto gastoDepois = objectMapper.readValue(json, Gasto.class);

        assertEquals(gastoAntes.id(), gastoDepois.getId());
        assertNotEquals(gastoAntes.valor(), gastoDepois.getValor());
        assertNotEquals(gastoAntes.descricao(), gastoDepois.getDescricao());
        assertEquals(gastoAntes.data(), gastoDepois.getData());
        assertEquals(gastoAntes.moeda(), gastoDepois.getMoeda());

        assertEquals(1, repository.count());
    }
    
    @Test
    void deveDeletarGastoComSucesso() throws Exception {
    	
    	var gastoSalvo = testEntityFactory.createEntity(DTO_PADRAO, service::save);
    	
    	assertEquals(1, repository.count());

        mockMvc.perform(
                delete("/gastos/{id}", gastoSalvo.id()))
        .andExpect(status().isNoContent());
        
        assertEquals(0, repository.count());
        
        mockMvc.perform(get("/gastos/{id}", gastoSalvo.id()))
        	.andExpect(status().isNotFound());
        
    }


}
