package com.tinnova.veiculos.repository;

import com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse;
import com.tinnova.veiculos.entity.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long>, JpaSpecificationExecutor<VeiculoEntity> {

    Optional<VeiculoEntity> findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    @Query("""
       SELECT new com.tinnova.veiculos.dto.response.RelatorioPorMarcaResponse(
           v.marca,
           COUNT(v)
       )
       FROM VeiculoEntity v
       GROUP BY v.marca
       ORDER BY COUNT(v) DESC
       """)
    List<RelatorioPorMarcaResponse> gerarRelatorioPorMarca();
}
