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

        // Buscar o usuário pelo serviço UserManagement
        UserResponseDTO userDTO = userManagementClient.getUserById(dto.getId());

        // Converter o UserResponseDTO para a entidade User
        User user = convertToEntity(userDTO);

        // Verificar se o usuário já possui um cartão no sistema UserManagement
        if (user.getCard() != null) {
            throw new IllegalStateException("Usuário já possui um cartão. Não é possível criar outro.");
        }

        // Gerar número do cartão, CVV e data de expiração
        String cardNumber = generateCardNumber();
        String cvv = generateCVV();
        LocalDateTime expirationDate = LocalDateTime.now().plusYears(4);

        // Definir o limite inicial do cartão baseado na idade
        if (dto.getAge() <= 22) {
            LIMITE_INICIAL = 2000;
        } else {
            LIMITE_INICIAL = 5000;
        }

        // Criar um CardValidationRequestDTO para validar o cartão
        CardValidationRequestDTO requestDTO = new CardValidationRequestDTO(cardNumber, dto.getCardHolderName(), expirationDate, cvv);

        // Validar o cartão usando o serviço de validação
        boolean isValid = validationServiceClient.validateCard(requestDTO);

        if (isValid) {
            // Preencher os dados do cartão e associar ao usuário
            Card card = new Card();
            card.setCardHolderName(dto.getCardHolderName());
            card.setCardNumber(cardNumber);
            card.setCvv(cvv);
            card.setExpirationDate(expirationDate);
            card.setBalance(LIMITE_INICIAL);

            // Associar o usuário ao cartão, garantindo que o usuário tenha um ID
            if (user.getId() == null) {
                throw new IllegalStateException("O usuário deve possuir um ID antes de associar um cartão.");
            }
            card.setUser(user);

            // Salvar o cartão no repositório de cartões
            Card savedCard = cardRepository.save(card);

            // **Atualizar o usuário no UserManagement com o ID do cartão**
            user.setCard(savedCard);

            UserRequestDTO userRequestDTO = convertToRequestDTO(user);
            userRequestDTO.getCard().setId(savedCard.getId()); // define o ID do cartão no DTO de atualização

            userManagementClient.updateUser(user.getId(), userRequestDTO);

            // Enviar um evento de auditoria para registrar a criação do cartão
            AuditDTO auditDTO = new AuditDTO(
                    "CARD_CREATION",
                    "Cartão criado para o usuário: " + dto.getCardHolderName(),
                    LocalDateTime.now()
            );
            auditClient.sendAuditEvent(auditDTO);

            // Retornar a resposta contendo os detalhes do cartão criado
            return new CardResponseDTO(cardNumber, dto.getCardHolderName(), cvv, expirationDate.toLocalDate(), LIMITE_INICIAL);
        } else {
            throw new IllegalArgumentException("A validação do cartão falhou.");
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





    private Card fillUser(CardValidationRequestDTO requestDTO, User user, UserResponseDTO userDTO, double limiteInicial) {
        Card card = new Card();
        card.setId(user.getId());
        card.setCardHolderName(requestDTO.getCardHolderName());
        card.setCardNumber(requestDTO.getCardNumber());
        card.setUser(user);
        card.setBalance(limiteInicial);
        card.setCvv(requestDTO.getCvv());
        card.setExpirationDate(requestDTO.getExpirationDate());
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());
        return card;
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
        // Buscar o usuário pelo ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verificar se o usuário possui um cartão
        Card card = user.getCard();  // Captura o cartão antes de remover a referência
        if (card != null) {
            // Desassociar o cartão do usuário
            user.setCard(null);
            userRepository.save(user); // Atualizar o usuário para remover o vínculo

            // Remover o cartão da base usando o objeto 'card' capturado
            cardRepository.delete(card);
        } else {
            throw new IllegalStateException("Usuário não possui um cartão para remover.");
        }
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

    public void updateCardBalance(Long cardId, double newBalance) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));


        card.setBalance(newBalance);
        cardRepository.save(card);
    }
}
