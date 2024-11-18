package com.bank.card_transaction.controller;


import com.bank.card_transaction.entity.dto.TransactionRequestDTO;
import com.bank.card_transaction.entity.dto.TransactionResponseDTO;
import com.bank.card_transaction.service.CardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card-transactions")
public class CardTransactionController {

    @Autowired
    private CardTransactionService cardTransactionService;

    public CardTransactionController(CardTransactionService cardTransactionService) {
        this.cardTransactionService = cardTransactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferBetweenCards(@RequestBody TransactionRequestDTO dto) {
        try {
            TransactionResponseDTO transaction = cardTransactionService.processTransaction(dto);
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "timestamp", LocalDateTime.now(),
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "error", "Invalid Argument",
                            "message", ex.getMessage(),
                            "path", "/card-transactions/transfer"
                    ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "timestamp", LocalDateTime.now(),
                            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "error", "Internal Server Error",
                            "message", "An unexpected error occurred. Please try again.",
                            "path", "/card-transactions/transfer"
                    ));
        }
    }

    @GetMapping("card/{cardId}")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactionsByUserId(@PathVariable Long cardId) {
        List<TransactionResponseDTO> transactions = cardTransactionService.getAllTransactionsByUserId(cardId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getTransactionsByUserId/{userId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionsByUserId(@PathVariable Long userId) {
        TransactionResponseDTO transactions = cardTransactionService.getTransactionById(userId);
        return ResponseEntity.ok(transactions);
    }
}
