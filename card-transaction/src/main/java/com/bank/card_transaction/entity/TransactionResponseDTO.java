package com.bank.card_transaction.entity;

import java.math.BigDecimal;

public class TransactionResponseDTO {
    private String transactionId;
    private String cardNumber;
    private BigDecimal transactionAmount;
    private String status;

    public TransactionResponseDTO() {}

    public TransactionResponseDTO(String transactionId, String cardNumber, BigDecimal transactionAmount, String status) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.transactionAmount = transactionAmount;
        this.status = status;
    }

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

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
