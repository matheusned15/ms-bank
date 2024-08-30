package com.bank.card_transaction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cardId;

    @Column(name = "cardNumber")
    private String cardNumber;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private TransactionType transactionType;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private String merchantName;

    @Column(nullable = false)
    private String status;
}
