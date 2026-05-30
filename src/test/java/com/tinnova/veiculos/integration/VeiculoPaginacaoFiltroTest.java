package com.tinnova.veiculos.integration;

import com.tinnova.veiculos.entity.VeiculoEntity;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class VeiculoPaginacaoFiltroTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VeiculoRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();

        repository.saveAll(List.of(
                VeiculoEntity.builder()
                        .marca("Ford")
                        .modelo("Fiesta")
                        .ano(2018)
                        .cor("Prata")
                        .placa("AAA1111")
                        .precoEmDolar(new BigDecimal("10000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build(),

                VeiculoEntity.builder()
                        .marca("Ford")
                        .modelo("Focus")
                        .ano(2020)
                        .cor("Preto")
                        .placa("BBB2222")
                        .precoEmDolar(new BigDecimal("15000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build(),

                VeiculoEntity.builder()
                        .marca("Honda")
                        .modelo("Civic")
                        .ano(2019)
                        .cor("Preto")
                        .placa("CCC3333")
                        .precoEmDolar(new BigDecimal("20000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));
    }

    @Test
    void deveFiltrarPorMarcaCorEAnoComPaginacao() throws Exception {
        mvc.perform(get("/api/veiculos")
                        .param("marca", "ford")
                        .param("cor", "preto")
                        .param("ano", "2020")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "modelo,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].modelo").value("Focus"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }

    @Test
    void deveRetornarPaginaComDoisItens() throws Exception {
        mvc.perform(get("/api/veiculos")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "marca,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
}
