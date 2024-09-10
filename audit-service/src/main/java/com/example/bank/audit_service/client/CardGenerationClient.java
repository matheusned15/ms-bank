package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.CardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "card-generation", url = "http://localhost:8081/api/cards")
public interface CardGenerationClient {

    @GetMapping("/{cardNumber}")
    CardResponseDTO getCardDetails(@PathVariable("cardNumber") String cardNumber);
}
