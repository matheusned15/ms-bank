package com.bank.card_transaction.entity;

import java.time.LocalDateTime;

public class TransactionRequestDTO {
    private Long payerId; // ID do pagador
    private Long payerCardId; // ID do cartão do pagador
    private Long recipientId; // ID do recebedor
    private Long recipientCardId; // ID do cartão do recebedor (opcional)
    private double amount; // Valor da transação
    private String description; // Descrição da transação
    private LocalDateTime transactionDate; // Data da transação

    // Getters e Setters
    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getPayerCardId() {
        return payerCardId;
    }

    public void setPayerCardId(Long payerCardId) {
        this.payerCardId = payerCardId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getRecipientCardId() {
        return recipientCardId;
    }

    public void setRecipientCardId(Long recipientCardId) {
        this.recipientCardId = recipientCardId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}