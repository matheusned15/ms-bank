package com.bank.card_transaction.client;

import com.bank.card_transaction.entity.dto.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-generation", url = "localhost:8087", path = "/api/cards")
public interface CardGenerationClient {

    @GetMapping("/{cardId}")
    CardDTO getCardById(@PathVariable("cardId") Long cardId);

    @PutMapping("{cardId}/balance")
    void updateCardBalance(@PathVariable Long cardId, @RequestParam double newBalance);
}
