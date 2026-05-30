package com.tinnova.veiculos.repository;

import com.tinnova.veiculos.entity.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long>, JpaSpecificationExecutor<VeiculoEntity> {

    Optional<VeiculoEntity> findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}
