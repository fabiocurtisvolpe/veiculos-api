package com.tinnova.veiculos.spec;

import com.tinnova.veiculos.entity.VeiculoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class VeiculoSpecification {

    public static Specification<VeiculoEntity> marcaContains(String marca) {
        return (root, query, cb) ->
                marca == null ? null :
                        cb.like(cb.lower(root.get("marca")), "%" + marca.toLowerCase() + "%");
    }

    public static Specification<VeiculoEntity> anoEquals(Integer ano) {
        return (root, query, cb) ->
                ano == null ? null :
                        cb.equal(root.get("ano"), ano);
    }

    public static Specification<VeiculoEntity> corContains(String cor) {
        return (root, query, cb) ->
                cor == null ? null :
                        cb.like(cb.lower(root.get("cor")), "%" + cor.toLowerCase() + "%");
    }

    public static Specification<VeiculoEntity> precoMin(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null :
                        cb.greaterThanOrEqualTo(root.get("precoEmDolar"), min);
    }

    public static Specification<VeiculoEntity> precoMax(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null :
                        cb.lessThanOrEqualTo(root.get("precoEmDolar"), max);
    }
}
