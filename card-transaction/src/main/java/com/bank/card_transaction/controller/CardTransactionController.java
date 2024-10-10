package com.bank.card_transaction.controller;



import com.bank.card_transaction.entity.dto.TransactionRequestDTO;
import com.bank.card_transaction.entity.dto.TransactionResponseDTO;
import com.bank.card_transaction.service.CardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/card-transactions")
public class CardTransactionController {

    @Autowired
    private CardTransactionService cardTransactionService;

    public CardTransactionController(CardTransactionService cardTransactionService) {
        this.cardTransactionService = cardTransactionService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transferBetweenCards(@RequestBody TransactionRequestDTO dto) {
        TransactionResponseDTO transaction = cardTransactionService.processTransaction(dto);
        return ResponseEntity.ok(transaction);
    }


    @GetMapping("/user/{cardId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUserId(@PathVariable Long cardId) {
        List<TransactionResponseDTO> transactions = cardTransactionService.getTransactionsByUserId(cardId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionResponseDTO> transactions = cardTransactionService.getAllTransactionsByUserId(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }
}
