package com.bank.card_transaction.controller;




import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.TransactionRequestDTO;
import com.bank.card_transaction.entity.TransactionResponseDTO;
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

    // Endpoint para realizar uma transação entre dois cartões
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transferBetweenCards(@RequestParam TransactionRequestDTO dto) {
        TransactionResponseDTO transaction = cardTransactionService.processTransaction(dto);
        return ResponseEntity.ok(transaction);
    }

    // Endpoint para listar transações de um cartão específico
    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCardId(@PathVariable Long cardId) {
        List<Transaction> transactions = cardTransactionService.getTransactionsByCardId(cardId);
        return ResponseEntity.ok(transactions);
    }
}
