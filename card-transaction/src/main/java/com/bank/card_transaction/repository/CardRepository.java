package com.bank.card_transaction.repository;


import com.bank.card_transaction.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}

