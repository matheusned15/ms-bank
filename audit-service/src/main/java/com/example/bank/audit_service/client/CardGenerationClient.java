package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.CardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "card-generation-service", url = "http://localhost:8087", path = "/api/cards")
public interface CardGenerationClient {

    @GetMapping("/{cardNumber}")
    CardResponseDTO getCardDetails(@PathVariable("cardNumber") String cardNumber);

}
