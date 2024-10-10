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

    @ManyToOne
    private Card payerCard;

    @ManyToOne
    private Card recipientCard;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    @Column(nullable = false)
    private double transactionAmount;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;


    @Column(nullable = false)
    private LocalDateTime transactionDate;


    @Column(nullable = false)
    private String merchantName;


    @Column(nullable = false)
    private String status;


    @Column(name = "description")
    private String description;


    @Column(name = "payerNewBalance", nullable = false)
    private BigDecimal payerNewBalance;


    @Column(name = "recipientNewBalance", nullable = false)
    private BigDecimal recipientNewBalance;
}
