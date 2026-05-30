package com.tinnova.veiculos.service;

import com.tinnova.veiculos.dto.request.VeiculoRequest;
import com.tinnova.veiculos.dto.response.VeiculoResponse;
import com.tinnova.veiculos.entity.VeiculoEntity;
import com.tinnova.veiculos.exception.ConflictException;
import com.tinnova.veiculos.exception.ErrorMessage;
import com.tinnova.veiculos.exception.NotFoundException;
import com.tinnova.veiculos.integration.service.DolarService;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.tinnova.veiculos.spec.VeiculoSpecification.*;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final DolarService dolarService;

    public VeiculoService(VeiculoRepository repository, DolarService dolarService) {
        this.repository = repository;
        this.dolarService = dolarService;
    }

    public Page<VeiculoResponse> listar(String marca, Integer ano, String cor,
                                        BigDecimal minPreco, BigDecimal maxPreco,
                                        Pageable pageable) {

        var spec = Specification.where(marcaContains(marca))
                .and(anoEquals(ano))
                .and(corContains(cor))
                .and(precoMin(minPreco))
                .and(precoMax(maxPreco));

        return repository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    public VeiculoResponse buscarPorId(Long id) {
        VeiculoEntity veiculo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USUARIO_NAO_ENCONTRADO.get()));
        return toResponse(veiculo);
    }

    public VeiculoResponse criar(VeiculoRequest dto) {

        if (repository.existsByPlaca(dto.getPlaca())) {
            throw new ConflictException(ErrorMessage.PLACA_JA_CADASTRADA.get());
        }

        BigDecimal precoEmDolar = converterParaDolar(dto.getPrecoEmReais());

        VeiculoEntity veiculo = VeiculoEntity.builder()
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .ano(dto.getAno())
                .cor(dto.getCor())
                .placa(dto.getPlaca())
                .precoEmDolar(precoEmDolar)
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(veiculo);

        return toResponse(veiculo);
    }

    public VeiculoResponse atualizar(Long id, VeiculoRequest dto) {

        VeiculoEntity veiculo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VEICULO_NAO_ENCONTRADO.get()));

        if (!veiculo.getPlaca().equals(dto.getPlaca()) &&
                repository.existsByPlaca(dto.getPlaca())) {
            throw new ConflictException(ErrorMessage.PLACA_JA_CADASTRADA.get());
        }

        BigDecimal precoEmDolar = converterParaDolar(dto.getPrecoEmReais());

        VeiculoEntity atualizado = VeiculoEntity.builder()
                .id(veiculo.getId())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .ano(dto.getAno())
                .cor(dto.getCor())
                .placa(dto.getPlaca())
                .precoEmDolar(precoEmDolar)
                .ativo(veiculo.getAtivo())
                .build();

        repository.save(atualizado);

        return toResponse(atualizado);
    }

    public VeiculoResponse atualizarParcial(Long id, VeiculoRequest dto) {

        VeiculoEntity veiculo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VEICULO_NAO_ENCONTRADO.get()));

        if (dto.getPlaca() != null &&
                !dto.getPlaca().equals(veiculo.getPlaca()) &&
                repository.existsByPlaca(dto.getPlaca())) {
            throw new ConflictException(ErrorMessage.PLACA_JA_CADASTRADA.get());
        }

        String marca = dto.getMarca() != null ? dto.getMarca() : veiculo.getMarca();
        String modelo = dto.getModelo() != null ? dto.getModelo() : veiculo.getModelo();
        Integer ano = dto.getAno() != null ? dto.getAno() : veiculo.getAno();
        String cor = dto.getCor() != null ? dto.getCor() : veiculo.getCor();
        String placa = dto.getPlaca() != null ? dto.getPlaca() : veiculo.getPlaca();

        BigDecimal precoEmDolar = dto.getPrecoEmReais() != null
                ? converterParaDolar(dto.getPrecoEmReais())
                : veiculo.getPrecoEmDolar();

        VeiculoEntity atualizado = VeiculoEntity.builder()
                .id(veiculo.getId())
                .marca(marca)
                .modelo(modelo)
                .ano(ano)
                .cor(cor)
                .placa(placa)
                .precoEmDolar(precoEmDolar)
                .ativo(veiculo.getAtivo())
                .createdAt(veiculo.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(atualizado);

        return toResponse(atualizado);
    }

    public void remover(Long id) {
        VeiculoEntity veiculo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.VEICULO_NAO_ENCONTRADO.get()));

        VeiculoEntity atualizado = VeiculoEntity.builder()
                .id(veiculo.getId())
                .marca(veiculo.getMarca())
                .modelo(veiculo.getModelo())
                .ano(veiculo.getAno())
                .cor(veiculo.getCor())
                .placa(veiculo.getPlaca())
                .precoEmDolar(veiculo.getPrecoEmDolar())
                .ativo(false)
                .build();

        repository.save(atualizado);
    }

    private VeiculoResponse toResponse(VeiculoEntity entity) {
        return VeiculoResponse.builder()
                .id(entity.getId())
                .marca(entity.getMarca())
                .modelo(entity.getModelo())
                .ano(entity.getAno())
                .cor(entity.getCor())
                .precoEmDolar(entity.getPrecoEmDolar())
                .placa(entity.getPlaca())
                .ativo(entity.getAtivo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private BigDecimal converterParaDolar(BigDecimal precoEmReais) {
        BigDecimal dolar = dolarService.obterCotacaoDolar();
        return precoEmReais.divide(dolar, 2, RoundingMode.HALF_UP);
    }

}
