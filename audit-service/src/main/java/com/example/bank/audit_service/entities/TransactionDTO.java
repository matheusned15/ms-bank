package com.example.bank.audit_service.entities;

import java.time.LocalDateTime;

public class TransactionDTO {

    private String transactionId;
    private String cardNumber;
    private Double amount;
    private LocalDateTime transactionDate;
    private String transactionStatus;

    // Construtores
    public TransactionDTO() {}

    public TransactionDTO(String transactionId, String cardNumber, Double amount, LocalDateTime transactionDate, String transactionStatus) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
    }

    // Getters e Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId='" + transactionId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }
}
