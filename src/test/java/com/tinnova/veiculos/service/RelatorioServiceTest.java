package com.tinnova.veiculos.service;

import com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RelatorioServiceTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private RelatorioService service;

    @Test
    void deveGerarRelatorioAgrupadoPorMarca() {
        // Arrange
        List<RelatorioPorMarcaResponse> mockResultado = List.of(
                new RelatorioPorMarcaResponse("Ford", 5L),
                new RelatorioPorMarcaResponse("Honda", 3L),
                new RelatorioPorMarcaResponse("Toyota", 2L)
        );

        when(repository.gerarRelatorioPorMarca()).thenReturn(mockResultado);

        // Act
        List<RelatorioPorMarcaResponse> resultado = service.relatorioPorMarca();

        // Assert
        assertThat(resultado).hasSize(3);

        assertThat(resultado.get(0).marca()).isEqualTo("Ford");
        assertThat(resultado.get(0).quantidade()).isEqualTo(5L);

        assertThat(resultado.get(1).marca()).isEqualTo("Honda");
        assertThat(resultado.get(1).quantidade()).isEqualTo(3L);

        assertThat(resultado.get(2).marca()).isEqualTo("Toyota");
        assertThat(resultado.get(2).quantidade()).isEqualTo(2L);
    }
}
