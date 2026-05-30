package com.tinnova.veiculos.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class VeiculoResponse {

    private final Long id;
    private final String marca;
    private final String modelo;
    private final Integer ano;
    private final String cor;
    private final BigDecimal precoEmDolar;
    private final String placa;
    private final Boolean ativo;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
