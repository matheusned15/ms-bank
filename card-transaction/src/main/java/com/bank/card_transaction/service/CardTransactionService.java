package com.bank.card_transaction.service;




import com.bank.card_transaction.client.CardValidationClient;
import com.bank.card_transaction.entity.Card;
import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.TransactionRequestDTO;
import com.bank.card_transaction.entity.TransactionResponseDTO;
import com.bank.card_transaction.repository.CardRepository;
import com.bank.card_transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CardTransactionService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardValidationClient cardValidationClient; // Cliente para validação de cartões

    public CardTransactionService(CardRepository cardRepository, TransactionRepository transactionRepository, CardValidationClient cardValidationClient) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.cardValidationClient = cardValidationClient;
    }

    @Transactional
    public TransactionResponseDTO processTransaction(TransactionRequestDTO request) {
        // Validar o cartão do pagador e do recebedor usando o client de validação
        if (!cardValidationClient.validateCard(request.getPayerCardId()) ||
                !cardValidationClient.validateCard(request.getRecipientCardId())) {
            return createFailedTransactionResponse("Um ou mais cartões são inválidos");
        }

        // Buscar o cartão do pagador
        Card payerCard = cardRepository.findById(request.getPayerCardId())
                .orElseThrow(() -> new RuntimeException("Cartão do pagador não encontrado"));

        // Buscar o cartão do recebedor
        Card recipientCard = cardRepository.findById(request.getRecipientCardId())
                .orElseThrow(() -> new RuntimeException("Cartão do recebedor não encontrado"));

        // Verificar saldo do pagador
        if (payerCard.getBalance() < request.getAmount()) {
            return createFailedTransactionResponse("Saldo insuficiente no cartão do pagador");
        }

        // Atualizar saldo do pagador e do recebedor
        double payerNewBalance = payerCard.getBalance() - request.getAmount();
        double recipientNewBalance = recipientCard.getBalance() + request.getAmount();

        payerCard.setBalance(payerNewBalance);
        recipientCard.setBalance(recipientNewBalance);

        // Salvar as alterações nos cartões
        cardRepository.save(payerCard);
        cardRepository.save(recipientCard);

        // Registrar a transação
        Transaction transaction = new Transaction();
        transaction.setPayerCard(payerCard);
        transaction.setRecipientCard(recipientCard);
        transaction.setTransactionAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transactionRepository.save(transaction);

        // Retornar resposta de sucesso
        return createSuccessTransactionResponse(payerNewBalance, recipientNewBalance);
    }

    // Método para criar uma resposta de sucesso
    private TransactionResponseDTO createSuccessTransactionResponse(double payerNewBalance, double recipientNewBalance) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setStatus("SUCCESS");
        response.setMessage("Transação realizada com sucesso");
        response.setPayerNewBalance(payerNewBalance);
        response.setRecipientNewBalance(recipientNewBalance);
        return response;
    }

    // Método para criar uma resposta de falha
    private TransactionResponseDTO createFailedTransactionResponse(String message) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setStatus("FAILED");
        response.setMessage(message);
        return response;
    }


    public List<Transaction> getTransactionsByCardId(Long cardId) {
        List<Transaction> transactionList = transactionRepository.findAllById(Collections.singleton(cardId));
        return transactionList;
    }
}
