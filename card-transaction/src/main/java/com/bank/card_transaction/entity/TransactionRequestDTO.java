package com.bank.card_transaction.entity;

import java.math.BigDecimal;

public class TransactionRequestDTO {
    private String cardNumber;
    private String cvv;
    private BigDecimal transactionAmount;

    public TransactionRequestDTO() {}

    public TransactionRequestDTO(String cardNumber, String cvv, BigDecimal transactionAmount) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.transactionAmount = transactionAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}