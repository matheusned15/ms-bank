package com.bank.card_validation.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "card-generation", url = "${card.generation.service.url}")
public class CardServiceClient {
    public double getCardBalance(String cardNumber) {
    }
}
