package com.tinnova.veiculos.controller;

import com.tinnova.veiculos.dto.request.VeiculoRequest;
import com.tinnova.veiculos.dto.response.VeiculoResponse;
import com.tinnova.veiculos.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<VeiculoResponse> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) BigDecimal minPreco,
            @RequestParam(required = false) BigDecimal maxPreco,
            Pageable pageable
    ) {
        return service.listar(marca, ano, cor, minPreco, maxPreco, pageable);
    }

    @GetMapping("/{id}")
    public VeiculoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public VeiculoResponse criar(@Valid @RequestBody VeiculoRequest dto) {
        return service.criar(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public VeiculoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequest dto
    ) {
        return service.atualizar(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public VeiculoResponse atualizarParcial(
            @PathVariable Long id,
            @RequestBody VeiculoRequest dto
    ) {
        return service.atualizarParcial(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
