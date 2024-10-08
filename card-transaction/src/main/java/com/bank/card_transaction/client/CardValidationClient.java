package com.bank.card_transaction.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-validation")
public interface CardValidationClient {

    @GetMapping("/api/card-validation/validate")
    boolean validateCard(@RequestParam Long cardId);
}

