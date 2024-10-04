package com.bank.card_generation.client;

import com.bank.card_generation.config.FeignConfig;
import com.bank.card_generation.entities.dto.CardValidationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "validation-service", url = "localhost:8003", path = "/api/card-validation")
public interface ValidationServiceClient {

    @GetMapping("/validateCard")
    boolean validateCard(@RequestBody CardValidationRequestDTO requestDTO);
}
