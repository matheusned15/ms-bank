package com.bank.card_generation.service;


import com.bank.card_generation.client.AuditClient;
import com.bank.card_generation.client.UserManagementClient;
import com.bank.card_generation.client.ValidationServiceClient;
import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.User;
import com.bank.card_generation.entities.dto.*;
import com.bank.card_generation.repository.CardRepository;
import com.bank.card_generation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardGenerationService {

    private static double LIMITE_INICIAL;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManagementClient userManagementClient;

    @Autowired
    private AuditClient auditClient;

    @Autowired
    private final ValidationServiceClient validationServiceClient;

    @Autowired
    public CardGenerationService(UserRepository userRepository, UserManagementClient userManagementClient, AuditClient auditClient, ValidationServiceClient validationServiceClient, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.userManagementClient = userManagementClient;
        this.auditClient = auditClient;
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
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getCard() != null) {
            throw new IllegalStateException("User already has a card. Cannot create another.");
        }

        String cardNumber = generateCardNumber();
        String cvv = generateCVV();
        LocalDateTime expirationDate = LocalDateTime.now().plusYears(4);

        if (dto.getAge() <= 22) {
            LIMITE_INICIAL = 2000;
        } else {
            LIMITE_INICIAL = 5000;
        }

        CardValidationRequestDTO requestDTO = new CardValidationRequestDTO(cardNumber, dto.getCardHolderName(), expirationDate, cvv);
        boolean isValid = validationServiceClient.validateCard(requestDTO);

        if (isValid) {
            Card card = new Card(null, cardNumber, dto.getCardHolderName(), cvv, expirationDate, LIMITE_INICIAL);
            cardRepository.save(card);

            user.setCard(card);
            userRepository.save(user);

            AuditDTO auditDTO = new AuditDTO(
                    "CARD_CREATION",
                    "Card created for user: " + dto.getCardHolderName(),
                    LocalDateTime.now()
            );
            auditClient.sendAuditEvent(auditDTO);

            return new CardResponseDTO(cardNumber, dto.getCardHolderName(), cvv, expirationDate.toLocalDate(), LIMITE_INICIAL);
        } else {
            throw new IllegalArgumentException("Card validation failed");
        }
    }

    public void deleteCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getCard() == null) {
            throw new IllegalStateException("User does not have a card.");
        }

        cardRepository.delete(user.getCard());
        user.setCard(null);
        userRepository.save(user);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();

        // Geração dos primeiros 15 dígitos aleatórios
        for (int i = 0; i < 15; i++) {
            cardNumber.append(random.nextInt(10));  // Adiciona um número entre 0-9
        }

        // Geração do último dígito utilizando o algoritmo de Luhn
        cardNumber.append(generateLuhnCheckDigit(cardNumber.toString()));

        return cardNumber.toString();
    }

    // Método auxiliar para calcular o dígito verificador usando o algoritmo de Luhn
    private int generateLuhnCheckDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        // Percorre o número de cartão de trás para frente
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }

    private String generateCVV() {
        Random random = new Random();

        // Geração de um número de 3 dígitos entre 100 e 999
        int cvv = 100 + random.nextInt(900);  // Garante que o número fique entre 100 e 999

        return String.valueOf(cvv);
    }

}
