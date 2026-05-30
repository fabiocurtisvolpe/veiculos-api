package com.tinnova.veiculos.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "veiculos")
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 150)
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 50)
    private String cor;

    @Column(name = "preco_em_dolar", nullable = false, precision = 15, scale = 2)
    private BigDecimal precoEmDolar;

    @Column(nullable = false, unique = true, length = 20)
    private String placa;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected VeiculoEntity() { }

    @Builder
    public VeiculoEntity(
            Long id,
            String marca,
            String modelo,
            Integer ano,
            String cor,
            BigDecimal precoEmDolar,
            String placa,
            Boolean ativo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.precoEmDolar = precoEmDolar;
        this.placa = placa;
        this.ativo = ativo != null ? ativo : true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
