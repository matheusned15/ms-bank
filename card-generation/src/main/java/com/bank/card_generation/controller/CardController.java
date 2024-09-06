package com.bank.card_generation.controller;


import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.dto.CardRequestDTO;
import com.bank.card_generation.entities.dto.CardResponseDTO;
import com.bank.card_generation.service.CardGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardGenerationService cardService;

    @Autowired
    public CardController(CardGenerationService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public CardResponseDTO createCard(@RequestBody CardRequestDTO request) {
        return cardService.createCard(request);
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public Optional<Card> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }
}
