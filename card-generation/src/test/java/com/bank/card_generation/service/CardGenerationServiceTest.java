package com.bank.card_generation.service;


import com.bank.card_generation.client.AuditClient;
import com.bank.card_generation.client.UserManagementClient;
import com.bank.card_generation.client.ValidationServiceClient;
import com.bank.card_generation.entities.Card;
import com.bank.card_generation.entities.dto.*;
import com.bank.card_generation.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CardGenerationServiceTest {

    @InjectMocks
    private CardGenerationService cardGenerationService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserManagementClient userManagementClient;

    @Mock
    private ValidationServiceClient validationServiceClient;

    @Mock
    private AuditClient auditClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCardForAdult() {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setId(5L);
        requestDTO.setCardHolderName("analuiza");
        requestDTO.setAge(25);

        Card card = new Card();
        card.setId(1L);
        card.setCardHolderName("analuiza");
        card.setBalance(700);
        card.setCardNumber("6206350681248618");
        card.setCvv("953");
        card.setExpirationDate(LocalDateTime.now().plusYears(3));

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(5L);
        userResponse.setUsername("analuiza");
        userResponse.setEmail("analuiza@gmail.com");

        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> {
            Card savedCard = invocation.getArgument(0);
            savedCard.setId(1L);
            savedCard.setCardNumber("6206350681248618");
            savedCard.setCvv("953");
            savedCard.setExpirationDate(LocalDateTime.now().plusYears(3));
            return savedCard;
        });

        when(userManagementClient.getUserById(any(Long.class))).thenReturn(userResponse);

        when(validationServiceClient.validateCard(any(CardValidationRequestDTO.class))).thenReturn(true);

        doNothing().when(auditClient).sendAuditEvent(any(AuditDTO.class));

        CardResponseDTO responseDTO = cardGenerationService.createCard(requestDTO);

        assertNotNull(responseDTO.getCardNumber());
        assertNotNull(responseDTO.getCvv());
        assertNotNull(responseDTO.getExpirationDate());
        assertEquals(5000.0, responseDTO.getCurrentBalance());
        assertEquals("analuiza", responseDTO.getCardHolderName());

        verify(auditClient, times(1)).sendAuditEvent(any(AuditDTO.class));
    }



    @Test
    void testGenerateCardForMinor() {
        CardRequestDTO requestDTO = new CardRequestDTO();
        requestDTO.setId(5L);
        requestDTO.setCardHolderName("analuiza");
        requestDTO.setAge(16);

        Card card = new Card();
        card.setId(1L);
        card.setCardHolderName("analuiza");
        card.setBalance(700);
        card.setCardNumber("6206350681248618");
        card.setCvv("953");
        card.setExpirationDate(LocalDateTime.now().plusYears(3));

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(5L);
        userResponse.setUsername("analuiza");
        userResponse.setEmail("analuiza@gmail.com");

        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> {
            Card savedCard = invocation.getArgument(0);
            savedCard.setId(1L);
            savedCard.setCardNumber("6206350681248618");
            savedCard.setCvv("953");
            savedCard.setExpirationDate(LocalDateTime.now().plusYears(3));
            return savedCard;
        });

        when(userManagementClient.getUserById(any(Long.class))).thenReturn(userResponse);

        when(validationServiceClient.validateCard(any(CardValidationRequestDTO.class))).thenReturn(true);

        doNothing().when(auditClient).sendAuditEvent(any(AuditDTO.class));

        CardResponseDTO responseDTO = cardGenerationService.createCard(requestDTO);

        assertNotNull(responseDTO.getCardNumber());
        assertNotNull(responseDTO.getCvv());
        assertNotNull(responseDTO.getExpirationDate());
        assertEquals(2000.0, responseDTO.getCurrentBalance());
        assertEquals("analuiza", responseDTO.getCardHolderName());

        verify(auditClient, times(1)).sendAuditEvent(any(AuditDTO.class));
    }
}
