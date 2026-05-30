package com.tinnova.veiculos.dto;

import java.util.Map;

public record FrankfurterResponse(
        String date,
        String base,
        Map<String, Double> rates
) {}