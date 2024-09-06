package com.bank.card_validation.client;

import com.bank.card_validation.entity.dto.CardValidationRequestDTO;
import com.bank.card_validation.entity.dto.CardValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;


import org.springframework.web.bind.annotation.*;

@FeignClient(name = "card-generation-service", url = "http://localhost:8082")
public interface CardServiceClient {

    @GetMapping("/api/cards/{cardNumber}")
    CardValidationResponseDTO getCardDetails(@PathVariable("cardNumber") String cardNumber);

    @PostMapping("/api/cards/updateBalance")
    void updateBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("newBalance") double newBalance);
}
