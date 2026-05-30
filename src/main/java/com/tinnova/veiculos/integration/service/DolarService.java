package com.tinnova.veiculos.integration.service;

import com.tinnova.veiculos.dto.AwesomeApiUsdBrl;
import com.tinnova.veiculos.dto.FrankfurterResponse;
import com.tinnova.veiculos.exception.BusinessException;
import com.tinnova.veiculos.exception.ErrorMessage;
import com.tinnova.veiculos.integration.AwesomeApiClient;
import com.tinnova.veiculos.integration.FrankfurterClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
public class DolarService {

    private static final String CACHE_KEY = "cotacao_dolar";

    private final AwesomeApiClient awesomeApi;
    private final FrankfurterClient frankfurterApi;

    private final StringRedisTemplate redis;

    public DolarService(AwesomeApiClient awesomeApi,
                        FrankfurterClient frankfurterApi,
                        StringRedisTemplate redis) {

        this.awesomeApi = awesomeApi;
        this.frankfurterApi = frankfurterApi;
        this.redis = redis;
    }

    public BigDecimal obterCotacaoDolar() {

        String cache = redis.opsForValue().get(CACHE_KEY);
        if (cache != null) {
            return new BigDecimal(cache);
        }

        try {
            var response = awesomeApi.getCotacaoDolar();
            AwesomeApiUsdBrl usdbrl = response.get("USDBRL");

            if (usdbrl != null && usdbrl.high() != null) {
                BigDecimal valor = new BigDecimal(usdbrl.high());
                redis.opsForValue().set(CACHE_KEY, valor.toString(), Duration.ofMinutes(10));
                return valor;
            }
        } catch (Exception ignored) {}

        try {
            FrankfurterResponse resp = frankfurterApi.getRate("USD", "BRL");
            Double valor = resp.rates().get("BRL");

            if (valor != null) {
                redis.opsForValue().set(CACHE_KEY, valor.toString(), Duration.ofMinutes(10));
                return BigDecimal.valueOf(valor);
            }
        } catch (Exception ignored) {}

        throw new BusinessException(ErrorMessage.FALHA_CONSULTA_DOLAR.get());
    }
}
