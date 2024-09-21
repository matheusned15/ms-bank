package com.bank.card_validation.client;

import com.bank.card_validation.entity.dto.CardValidationRequestDTO;
import com.bank.card_validation.entity.dto.CardValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "card-generation-service", url = "http://localhost:8087", path = "/api/cards")
public interface CardServiceClient {

    @GetMapping("/api/cards/{cardNumber}")
    CardValidationResponseDTO getCardDetails(@PathVariable("cardNumber") String cardNumber);

    @PostMapping("/api/cards/updateBalance")
    void updateBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("newBalance") double newBalance);
}
