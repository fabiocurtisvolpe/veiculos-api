package com.tinnova.veiculos.controller;

import com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse;
import com.tinnova.veiculos.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @GetMapping("/por-marca")
    public ResponseEntity<List<RelatorioPorMarcaResponse>> porMarca() {
        return ResponseEntity.ok(service.relatorioPorMarca());
    }
}
