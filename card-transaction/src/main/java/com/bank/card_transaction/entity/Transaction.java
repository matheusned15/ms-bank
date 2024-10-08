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
    @Column(nullable = false)
    private Card payerCard; // Cartão do pagador

    @ManyToOne
    @Column(nullable = false)
    private Card recipientCard; // Cartão do recebedor

    // Valor da transação
    @Column(nullable = false)
    private double transactionAmount;

    // Tipo de transação (exemplo: débito ou crédito)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;

    // Data da transação
    @Column(nullable = false)
    private LocalDateTime transactionDate;

    // Nome do comerciante ou motivo da transação
    @Column(nullable = false)
    private String merchantName;

    // Status da transação (exemplo: SUCCESS, FAILED)
    @Column(nullable = false)
    private String status;

    // Descrição adicional (opcional)
    @Column(name = "description")
    private String description;

    // Saldo do pagador após a transação
    @Column(name = "payerNewBalance", nullable = false)
    private BigDecimal payerNewBalance;

    // Saldo do recebedor após a transação
    @Column(name = "recipientNewBalance", nullable = false)
    private BigDecimal recipientNewBalance;
}
