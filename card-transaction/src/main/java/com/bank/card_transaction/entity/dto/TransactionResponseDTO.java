package com.bank.card_transaction.entity.dto;

import java.math.BigDecimal;

public class TransactionResponseDTO {
    private String status;
    private String message;
    private double payerNewBalance;
    private double recipientNewBalance;

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPayerNewBalance() {
        return payerNewBalance;
    }

    public void setPayerNewBalance(double payerNewBalance) {
        this.payerNewBalance = payerNewBalance;
    }

    public double getRecipientNewBalance() {
        return recipientNewBalance;
    }

    public void setRecipientNewBalance(double recipientNewBalance) {
        this.recipientNewBalance = recipientNewBalance;
    }
}
