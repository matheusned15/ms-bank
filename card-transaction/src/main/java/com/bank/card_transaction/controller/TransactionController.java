package com.bank.card_transaction.controller;


import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.TransactionRequestDTO;
import com.bank.card_transaction.entity.TransactionResponseDTO;
import com.bank.card_transaction.exception.InsufficientFundsException;
import com.bank.card_transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponseDTO createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) throws InsufficientFundsException {
        return transactionService.processTransaction(transactionRequestDTO);
    }

    @GetMapping("/card/{cardId}")
    public List<Transaction> getTransactionsByCardId(@PathVariable Long cardId) {
        return transactionService.getTransactionsByCardId(cardId);
    }
}
