package com.bank.card_generation.service;


import com.bank.card_generation.client.ValidationServiceClient;
import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.dto.CardRequestDTO;
import com.bank.card_generation.entities.dto.CardResponseDTO;
import com.bank.card_generation.entities.dto.CardValidationRequestDTO;
import com.bank.card_generation.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CardGenerationService {

    private static double LIMITE_INICIAL = 1000.00;

    private final ValidationServiceClient validationServiceClient;

    @Autowired
    public CardGenerationService(ValidationServiceClient validationServiceClient, CardRepository cardRepository) {
        this.validationServiceClient = validationServiceClient;
        this.cardRepository = cardRepository;
    }

    private final CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public CardResponseDTO createCard(CardRequestDTO dto) {
        String cardNumber = generateCardNumber();
        String cvv = generateCVV();
        LocalDateTime expirationDate = LocalDateTime.now().plusYears(4);
        CardValidationRequestDTO requestDTO = new CardValidationRequestDTO(cardNumber, dto.getCardHolderName(), expirationDate, cvv);

        boolean isValid = validationServiceClient.validateCard(requestDTO);

        if (isValid) {
            Card card = new Card(null, cardNumber, dto.getCardHolderName(), cvv, expirationDate, LIMITE_INICIAL);

            cardRepository.save(card);
            return new CardResponseDTO(cardNumber, dto.getCardHolderName(), cvv, LocalDate.now().plusYears(3), LIMITE_INICIAL);
        } else {
            throw new IllegalArgumentException("Card validation failed");
        }
    }

    private String generateCardNumber() {
        // Lógica para gerar o número do cartão
        return "1234567812345678";
    }

    private LocalDateTime generateExpirationDate() {
        // Lógica de geração de data de validade fictícia
        return LocalDateTime.parse("12/27");
    }

    private String generateCVV() {
        // Lógica de geração de CVV fictício
        return "123";
    }
}
