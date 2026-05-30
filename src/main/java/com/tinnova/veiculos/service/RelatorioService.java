package com.tinnova.veiculos.service;

import com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse;
import com.tinnova.veiculos.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final VeiculoRepository repository;

    public List<RelatorioPorMarcaResponse> relatorioPorMarca() {
        return repository.gerarRelatorioPorMarca();
    }
}
