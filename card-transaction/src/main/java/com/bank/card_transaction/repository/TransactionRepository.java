package com.bank.card_transaction.repository;

import com.bank.card_transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByPayerCard_IdOrRecipientCard_Id(Long payerCardId, Long recipientCardId);

    List<Transaction> findByPayerCard_UserIdOrRecipientCard_UserId(Long payerUserId, Long recipientUserId);
}


