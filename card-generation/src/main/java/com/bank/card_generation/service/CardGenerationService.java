package com.bank.card_generation.service;


import com.bank.card_generation.client.AuditClient;
import com.bank.card_generation.client.UserManagementClient;
import com.bank.card_generation.client.ValidationServiceClient;
import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.User;
import com.bank.card_generation.entities.dto.*;
import com.bank.card_generation.repository.CardRepository;
import com.bank.card_generation.repository.UserRepository;
import jakarta.transaction.Transactional;
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

        UserResponseDTO userDTO = userManagementClient.getUserById(dto.getId());

        User user = convertToEntity(userDTO);

        if (user.getCard() != null) {
            throw new IllegalStateException("Usuário já possui um cartão. Não é possível criar outro.");
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
            Card card = new Card();
            card.setCardHolderName(dto.getCardHolderName());
            card.setCardNumber(cardNumber);
            card.setCvv(cvv);
            card.setExpirationDate(expirationDate);
            card.setBalance(LIMITE_INICIAL);


            if (user.getId() == null) {
                throw new IllegalStateException("The user must have an ID before associating a card.");
            }
            card.setUser(user);

            Card savedCard = cardRepository.save(card);

            user.setCard(savedCard);

            UserRequestDTO userRequestDTO = convertToRequestDTO(user);
            userRequestDTO.getCard().setId(savedCard.getId());

            userManagementClient.updateUser(user.getId(), userRequestDTO);

            AuditDTO auditDTO = new AuditDTO(
                    "CARD_CREATION",
                    "Cartão criado para o usuário: " + dto.getCardHolderName(),

                    LocalDateTime.now()
            );
            auditClient.sendAuditEvent(auditDTO);

            return new CardResponseDTO(cardNumber, dto.getCardHolderName(), cvv, expirationDate.toLocalDate(), LIMITE_INICIAL);
        } else {
            throw new IllegalArgumentException("Card validation failed.");
        }
    }

    public UserRequestDTO convertToRequestDTO(User user) {
        CardDTO cardDTO = user.getCard() != null ? new CardDTO(
                user.getCard().getCardNumber(),
                user.getCard().getCardHolderName(),
                user.getCard().getExpirationDate(),
                user.getCard().getCvv(),
                user.getCard().getBalance()
        ) : null;

        return new UserRequestDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreated_at(),
                user.getUpdated_at(),
                user.isActive(),
                cardDTO
        );
    }

    public User convertToEntity(UserResponseDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getCreated_at(),
                userDTO.getUpdated_at(),
                userDTO.isActive()
        );
    }

    @Transactional
    public void deleteCard(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Card card = user.getCard();
        if (card != null) {
            user.setCard(null);
            userRepository.save(user);

            cardRepository.delete(card);
        } else {
            throw new IllegalStateException("User does not have a card to remove.");
        }
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            cardNumber.append(random.nextInt(10));
        }

        cardNumber.append(generateLuhnCheckDigit(cardNumber.toString()));

        return cardNumber.toString();
    }

    private int generateLuhnCheckDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

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
        int cvv = 100 + random.nextInt(900);

        return String.valueOf(cvv);
    }

    public void updateCardBalance(Long cardId, double newBalance) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found."));

        card.setBalance(newBalance);
        cardRepository.save(card);
    }
}
