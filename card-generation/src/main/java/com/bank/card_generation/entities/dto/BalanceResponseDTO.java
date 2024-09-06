package com.bank.card_generation.entities.dto;

import java.math.BigDecimal;

public class BalanceResponseDTO {
    private String cardNumber;
    private BigDecimal currentBalance;

    public BalanceResponseDTO() {}

    public BalanceResponseDTO(String cardNumber, BigDecimal currentBalance) {
        this.cardNumber = cardNumber;
        this.currentBalance = currentBalance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
