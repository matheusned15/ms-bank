package com.bank.card_transaction.service;

import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.dto.TransactionResponseDTO;
import com.bank.card_transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CardTransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUserId(5L);
        transaction.setRecipientId(3L);
        transaction.setTransactionAmount(500.0);
        transaction.setTransactionDate(LocalDateTime.now());
    }

    @Test
    void testGetTransactionById() {
        when(transactionRepository.getByTransaction(any(Long.class))).thenReturn(transaction);

        TransactionResponseDTO result = transactionService.getTransactionById(1L);

        assertEquals(transaction.getId(), result.getTransactionId());
        assertEquals(transaction.getTransactionAmount(), result.getAmount());
    }
}


