package com.bank.card_transaction.service;


import com.bank.card_transaction.config.CardServiceClient;
import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.TransactionType;
import com.bank.card_transaction.exception.InsufficientFundsException;
import com.bank.card_transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardServiceClient cardServiceClient;

    @Transactional
    public void processTransaction(Transaction transaction) throws InsufficientFundsException {
        // Chamar o serviço de cartão para obter o saldo atual
        double currentBalance = cardServiceClient.getCardBalance(transaction.getCardNumber());

        if (transaction.getTransactionType() == TransactionType.DEBIT) {
            if (currentBalance >= transaction.getAmount()) {
                cardServiceClient.updateCardBalance(transaction.getCardNumber(), currentBalance - transaction.getAmount());
                transactionRepository.save(transaction);
            } else {
                throw new InsufficientFundsException("Saldo insuficiente para a transação.");
            }
        } else if (transaction.getTransactionType() == TransactionType.CREDIT) {
            cardServiceClient.updateCardBalance(transaction.getCardNumber(), currentBalance + transaction.getAmount());
            transactionRepository.save(transaction);
        }
    }
}
