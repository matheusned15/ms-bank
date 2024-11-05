package com.bank.card_transaction.repository;

import com.bank.card_transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);

    List<Transaction> getAllTransactionsByUserId(Long userId);
}


