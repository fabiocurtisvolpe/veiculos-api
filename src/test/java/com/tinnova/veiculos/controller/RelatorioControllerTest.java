package com.tinnova.veiculos.controller;

import com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse;
import com.tinnova.veiculos.service.RelatorioService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class RelatorioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RelatorioService service;

    @Test
    void deveRetornarRelatorioPorMarca() throws Exception {
        when(service.relatorioPorMarca()).thenReturn(List.of(
                new RelatorioPorMarcaResponse("Ford", 5L),
                new RelatorioPorMarcaResponse("Honda", 3L)
        ));

        mvc.perform(get("/api/veiculos/relatorios/por-marca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Ford"))
                .andExpect(jsonPath("$[0].quantidade").value(5))
                .andExpect(jsonPath("$[1].marca").value("Honda"))
                .andExpect(jsonPath("$[1].quantidade").value(3));
    }
}
