package com.tinnova.veiculos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.veiculos.config.JwtConfig;
import com.tinnova.veiculos.dto.request.VeiculoRequest;
import com.tinnova.veiculos.dto.response.VeiculoResponse;
import com.tinnova.veiculos.security.JwtAuthenticationFilter;
import com.tinnova.veiculos.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(VeiculoController.class)
@AutoConfigureMockMvc(addFilters = false)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VeiculoService service;

    @MockBean
    private JwtConfig jwtConfig;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deveCriarVeiculo() throws Exception {
        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmReais(new BigDecimal("50000"))
                .placa("ABC1234")
                .build();

        VeiculoResponse resp = VeiculoResponse.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmDolar(new BigDecimal("10000"))
                .placa("ABC1234")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(service.criar(any())).thenReturn(resp);

        mvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.modelo").value("Fiesta"));
    }

    @Test
    void deveBuscarPorId() throws Exception {
        VeiculoResponse resp = VeiculoResponse.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmDolar(new BigDecimal("10000"))
                .placa("ABC1234")
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(service.buscarPorId(1L)).thenReturn(resp);

        mvc.perform(get("/api/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Fiesta"));
    }

    @Test
    void deveAtualizarVeiculo() throws Exception {
        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Focus")
                .ano(2020)
                .cor("Preto")
                .precoEmReais(new BigDecimal("60000"))
                .placa("ABC1234")
                .build();

        VeiculoResponse resp = VeiculoResponse.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Focus")
                .ano(2020)
                .cor("Preto")
                .precoEmDolar(new BigDecimal("12000"))
                .placa("ABC1234")
                .ativo(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(service.atualizar(eq(1L), any())).thenReturn(resp);

        mvc.perform(put("/api/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Focus"));
    }

    @Test
    void deveAtualizarParcial() throws Exception {
        String patchJson = """
                { "cor": "Azul" }
                """;

        VeiculoResponse resp = VeiculoResponse.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Azul")
                .precoEmDolar(new BigDecimal("10000"))
                .placa("ABC1234")
                .ativo(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(service.atualizarParcial(eq(1L), any())).thenReturn(resp);

        mvc.perform(patch("/api/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cor").value("Azul"));
    }

    @Test
    void deveRemoverVeiculo() throws Exception {
        mvc.perform(delete("/api/veiculos/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(service).remover(1L);
    }
}
