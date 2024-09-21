package com.bank.card_generation.repository;


import com.bank.card_generation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
