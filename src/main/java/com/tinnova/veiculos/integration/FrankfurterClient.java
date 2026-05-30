package com.tinnova.veiculos.integration;

import com.tinnova.veiculos.dto.FrankfurterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "frankfurterClient",
        url = "https://api.frankfurter.app"
)
public interface FrankfurterClient {

    @GetMapping("/latest")
    FrankfurterResponse getRate(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );
}
