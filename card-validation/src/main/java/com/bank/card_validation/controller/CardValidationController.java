package com.bank.card_validation.controller;

import com.bank.card_validation.entity.dto.CardValidationRequestDTO;
import com.bank.card_validation.entity.dto.CardValidationResponseDTO;
import com.bank.card_validation.service.CardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card-validation")
public class CardValidationController {

    private final CardValidationService cardValidationService;

    @Autowired
    public CardValidationController(CardValidationService cardValidationService) {
        this.cardValidationService = cardValidationService;
    }

    @PostMapping
    public boolean validateCard(@RequestBody CardValidationRequestDTO requestDTO) {
        return cardValidationService.validateCardAndProcessTransaction(requestDTO);
    }
}
