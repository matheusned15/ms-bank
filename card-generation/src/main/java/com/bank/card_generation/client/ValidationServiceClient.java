package com.bank.card_generation.client;

import com.bank.card_generation.config.FeignConfig;
import com.bank.card_generation.entities.CardValidationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "validation-service", url = "localhost:8001", configuration = FeignConfig.class)
public interface ValidationServiceClient {

    @GetMapping("/api/card-validation/validate")
    @ResponseBody
    boolean validateCard(@RequestBody CardValidationRequestDTO requestDTO);
}
