package com.bank.card_generation.entities.dto;

public class BalanceRequestDTO {
    private String cardNumber;

    public BalanceRequestDTO() {}

    public BalanceRequestDTO(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}