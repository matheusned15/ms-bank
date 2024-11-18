package com.bank.card_generation.client;


import com.bank.card_generation.entities.dto.CardValidationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "validation-service", url = "card-validation.url", path = "/api/card-validation")
public interface ValidationServiceClient {

    @GetMapping("/validateCard")
    boolean validateCard(@RequestBody CardValidationRequestDTO requestDTO);
}
