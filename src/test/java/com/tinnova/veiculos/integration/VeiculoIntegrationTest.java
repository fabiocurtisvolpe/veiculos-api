package com.tinnova.veiculos.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.veiculos.dto.request.VeiculoRequest;
import com.tinnova.veiculos.dto.response.VeiculoResponse;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VeiculoIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VeiculoRepository repository;

    private RestTemplate rest;

    @BeforeEach
    void setUp() {
        rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    private String url(String path) {
        return "http://localhost:" + port + "/api/veiculos" + path;
    }

    @Test
    void fluxoCompletoDeVeiculo() {

        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmReais(new BigDecimal("50000"))
                .placa("ABC1234")
                .build();

        ResponseEntity<VeiculoResponse> createResp =
                rest.postForEntity(url(""), req, VeiculoResponse.class);

        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        VeiculoResponse criado = createResp.getBody();
        assertThat(criado).isNotNull();
        assertThat(criado.getId()).isNotNull();

        Long id = criado.getId();

        // 2️⃣ Buscar por ID
        ResponseEntity<VeiculoResponse> getResp =
                rest.getForEntity(url("/" + id), VeiculoResponse.class);

        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(getResp.getBody());
        assertThat(getResp.getBody().getModelo()).isEqualTo("Fiesta");

        // 3️⃣ Atualizar veículo
        VeiculoRequest updateReq = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Focus")
                .ano(2020)
                .cor("Preto")
                .precoEmReais(new BigDecimal("60000"))
                .placa("ABC1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> updateEntity =
                new HttpEntity<>(toJson(updateReq), headers);

        ResponseEntity<VeiculoResponse> updateResp =
                rest.exchange(url("/" + id), HttpMethod.PUT, updateEntity, VeiculoResponse.class);

        assertThat(updateResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(updateResp.getBody());
        assertThat(updateResp.getBody().getModelo()).isEqualTo("Focus");

        // 4️⃣ Atualizar parcial (PATCH)
        String patchJson = """
                { "cor": "Azul" }
                """;

        HttpEntity<String> patchEntity = new HttpEntity<>(patchJson, headers);

        ResponseEntity<VeiculoResponse> patchResp =
                rest.exchange(url("/" + id), HttpMethod.PATCH, patchEntity, VeiculoResponse.class);

        assertThat(patchResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(patchResp.getBody());
        assertThat(patchResp.getBody().getCor()).isEqualTo("Azul");

        // 5️⃣ Remover (desativar)
        rest.delete(url("/" + id));

        var desativado = repository.findById(id).orElseThrow();
        assertThat(desativado.getAtivo()).isFalse();
    }

    private String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
