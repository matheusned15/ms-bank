package com.bank.card_transaction.client;

import com.bank.card_generation.entities.dto.CardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "card-generation-service", url = "http://localhost:8087", path = "/api/cards")
public interface CardServiceClient {

    @GetMapping("/{cardNumber}")
    CardResponseDTO getCardByNumber(@PathVariable("cardNumber") String cardNumber);

    @GetMapping("/cards/balance")
    double getCardBalance(@RequestParam("cardNumber") String cardNumber);

    @PutMapping("/cards/update-balance")
    void updateCardBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("newBalance") double newBalance);
}
