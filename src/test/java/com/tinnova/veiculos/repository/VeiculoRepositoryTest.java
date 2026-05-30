package com.tinnova.veiculos.repository;

import com.tinnova.veiculos.entity.VeiculoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository repository;

    @Test
    void deveSalvarVeiculo() {
        VeiculoEntity veiculo = VeiculoEntity.builder()
                .marca("Ford")
                .modelo("Fiesta")
                .ano(2018)
                .cor("Prata")
                .placa("ABC1234")
                .precoEmDolar(new BigDecimal("10000"))
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        VeiculoEntity salvo = repository.save(veiculo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getMarca()).isEqualTo("Ford");
        assertThat(salvo.getPlaca()).isEqualTo("ABC1234");
    }

    @Test
    void deveEncontrarPorId() {
        VeiculoEntity veiculo = VeiculoEntity.builder()
                .marca("Chevrolet")
                .modelo("Onix")
                .ano(2020)
                .cor("Branco")
                .placa("XYZ9876")
                .precoEmDolar(new BigDecimal("12000"))
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        VeiculoEntity salvo = repository.save(veiculo);

        var encontrado = repository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getModelo()).isEqualTo("Onix");
    }

    @Test
    void deveVerificarExistenciaDePlaca() {
        VeiculoEntity veiculo = VeiculoEntity.builder()
                .marca("Honda")
                .modelo("Civic")
                .ano(2019)
                .cor("Preto")
                .placa("AAA1111")
                .precoEmDolar(new BigDecimal("15000"))
                .ativo(true)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(veiculo);

        boolean existe = repository.existsByPlaca("AAA1111");

        assertThat(existe).isTrue();
    }
}