package com.tinnova.veiculos.service;

import com.tinnova.veiculos.dto.request.VeiculoRequest;
import com.tinnova.veiculos.entity.VeiculoEntity;
import com.tinnova.veiculos.exception.ConflictException;
import com.tinnova.veiculos.exception.NotFoundException;
import com.tinnova.veiculos.integration.service.DolarService;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @Mock
    private DolarService dolarService;

    @InjectMocks
    private VeiculoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarVeiculoComSucesso() {
        VeiculoEntity existente = VeiculoEntity.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .placa("ABC1234")
                .precoEmDolar(new BigDecimal("10000"))
                .ativo(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(null)
                .build();

        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Focus")
                .ano(2020)
                .cor("Preto")
                .precoEmReais(new BigDecimal("50000"))
                .placa("ABC1234")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.existsByPlaca("ABC1234")).thenReturn(false);

        when(dolarService.obterCotacaoDolar()).thenReturn(new BigDecimal("5.00"));

        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var resp = service.atualizar(1L, req);

        assertThat(resp.getModelo()).isEqualTo("Focus");
        assertThat(resp.getAno()).isEqualTo(2020);
        assertThat(resp.getCor()).isEqualTo("Preto");

        verify(repository).save(any(VeiculoEntity.class));
    }


    @Test
    void deveLancarNotFoundAoAtualizar() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmReais(new BigDecimal("50000"))
                .build();

        assertThatThrownBy(() -> service.atualizar(1L, req))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void deveLancarConflictQuandoPlacaJaExiste() {
        VeiculoEntity existente = VeiculoEntity.builder()
                .id(1L)
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .placa("ABC1234")
                .precoEmDolar(new BigDecimal("10000"))
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        VeiculoRequest req = VeiculoRequest.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .precoEmReais(new BigDecimal("50000"))
                .placa("XYZ9999")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.existsByPlaca("XYZ9999")).thenReturn(true);

        assertThatThrownBy(() -> service.atualizar(1L, req))
                .isInstanceOf(ConflictException.class);
    }
}
