package com.bank.card_transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-generation-service")
public interface CardServiceClient {

    @GetMapping("/cards/balance")
    double getCardBalance(@RequestParam("cardNumber") String cardNumber);

    @PutMapping("/cards/update-balance")
    void updateCardBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("newBalance") double newBalance);
}
