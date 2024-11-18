package com.bank.card_generation.repository;


import com.bank.card_generation.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    String findByCardNumber(String number);

    Optional<Card> findById(Long id);
}