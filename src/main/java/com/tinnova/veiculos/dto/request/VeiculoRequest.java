package com.tinnova.veiculos.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class VeiculoRequest {

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer ano;

    @NotBlank
    private String cor;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal precoEmReais;

    @NotBlank
    @Size(min = 7, max = 20)
    private String placa;
}
