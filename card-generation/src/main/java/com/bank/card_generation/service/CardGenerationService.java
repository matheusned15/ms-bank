package com.bank.card_generation.service;


import com.bank.card_generation.client.ValidationServiceClient;
import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.CardValidationRequestDTO;
import com.bank.card_generation.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardGenerationService {

    private final ValidationServiceClient validationServiceClient;

    @Autowired
    public CardGenerationService(ValidationServiceClient validationServiceClient, CardRepository cardRepository) {
        this.validationServiceClient = validationServiceClient;
        this.cardRepository = cardRepository;
    }

    private final CardRepository cardRepository;


    public Card createCard(Card card) {
        // Lógica para gerar número de cartão fictício, data de validade e CVV
        card.setCardNumber(generateCardNumber());
        card.setExpirationDate(generateExpirationDate());
        card.setCvv(generateCVV());
        return cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    // Métodos auxiliares para geração de dados fictícios

    private String generateCardNumber() {
        // Lógica de geração de número de cartão fictício
        return "1234-5678-9101-1121";
    }

    private String generateExpirationDate() {
        // Lógica de geração de data de validade fictícia
        return "12/27";
    }

    private String generateCVV() {
        // Lógica de geração de CVV fictício
        return "123";
    }

    public boolean generateAndValidateCard(String cardHolderName, String cardNumber, String expirationDate, String cvv) {
        // Lógica para gerar o cartão (pode incluir geração de números, etc.)

        // Criar o DTO de requisição de validação
        CardValidationRequestDTO validationRequest = new CardValidationRequestDTO(cardNumber, cardHolderName, expirationDate, cvv);

        // Chamar o serviço de validação de cartões usando Feign Client
        return validationServiceClient.validateCard(validationRequest);
    }

}
