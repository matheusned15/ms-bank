package com.bank.card_transaction.service;



import com.bank.card_transaction.client.AuditClient;
import com.bank.card_transaction.client.CardValidationClient;
import com.bank.card_transaction.entity.AuditDTO;
import com.bank.card_transaction.entity.Transaction;
import com.bank.card_transaction.entity.TransactionRequestDTO;
import com.bank.card_transaction.entity.TransactionResponseDTO;
import com.bank.card_transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardValidationClient cardValidationClient;

    @Autowired
    private AuditClient auditClient;

    public TransactionService(TransactionRepository transactionRepository, CardValidationClient cardValidationClient) {
        this.transactionRepository = transactionRepository;
        this.cardValidationClient = cardValidationClient;
    }

    public TransactionResponseDTO processTransaction(TransactionRequestDTO transactionRequestDTO) {
        // 1. Validar o cartão
        boolean isCardValid = cardValidationClient.validateCard(transactionRequestDTO.getCardNumber(), transactionRequestDTO.getCvv());

        if (!isCardValid) {
            return createErrorResponse(transactionRequestDTO, "Invalid card details");
        }

        // 2. Verificar o saldo (simulando a lógica de validação de saldo)
        BigDecimal currentBalance = cardValidationClient.getCardBalance(transactionRequestDTO.getCardNumber());
        if (currentBalance.compareTo(transactionRequestDTO.getTransactionAmount()) < 0) {
            return createErrorResponse(transactionRequestDTO, "Insufficient balance");
        }

        // 3. Atualizar o saldo (simulação de uma operação de débito)
        BigDecimal updatedBalance = currentBalance.subtract(transactionRequestDTO.getTransactionAmount());
        cardValidationClient.updateCardBalance(transactionRequestDTO.getCardNumber(), updatedBalance);

        // 4. Registrar a transação no banco de dados
        Transaction transaction = new Transaction();
        transaction.setId(Long.valueOf(UUID.randomUUID().toString()));
        transaction.setCardNumber(transactionRequestDTO.getCardNumber());
        transaction.setTransactionAmount(transactionRequestDTO.getTransactionAmount());
        transaction.setStatus("SUCCESS");

        transactionRepository.save(transaction);

        AuditDTO auditDTO = new AuditDTO(
                "TRANSACTION",
                "Transaction processed for card: " + transactionRequestDTO.getCardNumber(),
                LocalDateTime.now()
        );
        auditClient.sendAuditEvent(auditDTO);

        // 5. Retornar a resposta
        return createSuccessResponse(transaction);
    }

    private TransactionResponseDTO createSuccessResponse(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId().toString(),
                transaction.getCardNumber(),
                transaction.getTransactionAmount(),
                transaction.getStatus()
        );
    }

    private TransactionResponseDTO createErrorResponse(TransactionRequestDTO transactionRequestDTO, String status) {
        return new TransactionResponseDTO(
                null,
                transactionRequestDTO.getCardNumber(),
                transactionRequestDTO.getTransactionAmount(),
                status
        );
    }

    public List<Transaction> getTransactionsByCardId(Long cardId) {
        List<Transaction> transactionList = transactionRepository.findByCardId(cardId);
        if (!transactionList.isEmpty()) {
            return transactionList;
        }
        return null;
    }
}
