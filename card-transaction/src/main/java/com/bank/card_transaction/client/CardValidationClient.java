package com.bank.card_transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "card-validation-service", url = "${card.validation.service.url}")
public interface CardValidationClient {

    @GetMapping("/api/validate")
    boolean validateCard(@RequestParam("cardNumber") String cardNumber, @RequestParam("cvv") String cvv);

    @GetMapping("/api/card/balance")
    BigDecimal getCardBalance(@RequestParam("cardNumber") String cardNumber);

    @GetMapping("/api/card/updateBalance")
    void updateCardBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("newBalance") BigDecimal newBalance);
}
