package com.tinnova.veiculos.spec;

import com.tinnova.veiculos.entity.VeiculoEntity;
import com.tinnova.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class VeiculoSpecificationTest {

    @Autowired
    private VeiculoRepository repository;

    @BeforeEach
    void setup() {
        repository.saveAll(List.of(
                VeiculoEntity.builder()
                        .marca("Ford")
                        .modelo("Fiesta")
                        .ano(2018)
                        .cor("Prata")
                        .placa("ABC1234")
                        .precoEmDolar(new BigDecimal("10000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build(),

                VeiculoEntity.builder()
                        .marca("Chevrolet")
                        .modelo("Onix")
                        .ano(2020)
                        .cor("Branco")
                        .placa("XYZ9876")
                        .precoEmDolar(new BigDecimal("15000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build(),

                VeiculoEntity.builder()
                        .marca("Honda")
                        .modelo("Civic")
                        .ano(2019)
                        .cor("Preto")
                        .placa("AAA1111")
                        .precoEmDolar(new BigDecimal("20000"))
                        .ativo(true)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));
    }

    @Test
    void deveFiltrarPorMarca() {
        Specification<VeiculoEntity> spec = VeiculoSpecification.marcaContains("ford");

        var result = repository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getMarca()).isEqualTo("Ford");
    }

    @Test
    void deveFiltrarPorAno() {
        Specification<VeiculoEntity> spec = VeiculoSpecification.anoEquals(2020);

        var result = repository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getModelo()).isEqualTo("Onix");
    }

    @Test
    void deveFiltrarPorCor() {
        Specification<VeiculoEntity> spec = VeiculoSpecification.corContains("preto");

        var result = repository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCor()).isEqualTo("Preto");
    }

    @Test
    void deveFiltrarPorPrecoMinimo() {
        Specification<VeiculoEntity> spec = VeiculoSpecification.precoMin(new BigDecimal("15000"));

        var result = repository.findAll(spec);

        assertThat(result).hasSize(2);
    }

    @Test
    void deveFiltrarPorPrecoMaximo() {
        Specification<VeiculoEntity> spec = VeiculoSpecification.precoMax(new BigDecimal("12000"));

        var result = repository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getModelo()).isEqualTo("Fiesta");
    }

    @Test
    void deveCombinarFiltros() {
        Specification<VeiculoEntity> spec =
                Specification.where(VeiculoSpecification.marcaContains("honda"))
                        .and(VeiculoSpecification.corContains("preto"))
                        .and(VeiculoSpecification.precoMin(new BigDecimal("15000")));

        var result = repository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getModelo()).isEqualTo("Civic");
    }
}
