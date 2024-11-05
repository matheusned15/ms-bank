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
    @JoinColumn(name = "payer_card_id", nullable = false)
    private Card payerCardId;

    @ManyToOne
    @JoinColumn(name = "recipient_card_id", nullable = false)
    private Card recipientCardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "recipient_id", nullable = false)
    private Long recipientId;

    @Column(name = "transaction_amount", nullable = false)
    private double transactionAmount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private String status;
}
