package com.bank.card_generation.client;

import com.bank.card_generation.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validation-service", url = "localhost:8001", configuration = FeignConfig.class)
public interface ValidationServiceClient {

    @GetMapping("/api/card-validation/validate")
    boolean validateCard(@RequestParam("cardNumber") String cardNumber,
                         @RequestParam("expirationDate") String expirationDate,
                         @RequestParam("cvv") String cvv);
}
