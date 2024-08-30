package com.bank.card_validation.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-generation", url = "${card.generation.service.url}")
public interface CardGenerationClient {

    @GetMapping("/api/cards/by-number")
    Object getCardByNumber(@RequestParam("cardNumber") String cardNumber);
}
