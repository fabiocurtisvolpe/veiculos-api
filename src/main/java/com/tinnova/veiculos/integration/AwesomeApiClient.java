package com.tinnova.veiculos.integration;

import com.tinnova.veiculos.dto.AwesomeApiUsdBrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(
        name = "awesomeApiClient",
        url = "https://economia.awesomeapi.com.br"
)
public interface AwesomeApiClient {

    @GetMapping("/json/last/USD-BRL")
    Map<String, AwesomeApiUsdBrl> getCotacaoDolar();
}
