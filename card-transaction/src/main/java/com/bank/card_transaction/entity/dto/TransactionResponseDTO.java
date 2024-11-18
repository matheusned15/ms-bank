package com.bank.card_transaction.entity.dto;


public class TransactionResponseDTO {

    private Long transactionId;
    private String status;
    private String message;
    private double payerNewBalance;
    private double recipientNewBalance;
    private Long payerId;
    private Long recipientId;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

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
