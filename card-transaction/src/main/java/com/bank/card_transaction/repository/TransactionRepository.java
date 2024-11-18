package com.bank.card_transaction.repository;

import com.bank.card_transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.id = :id")
    Transaction getByTransaction(Long id);

    List<Transaction> getAllTransactionsByUserId(Long userId);
}


