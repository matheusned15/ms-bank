package com.bank.card_generation.entities.dto;

public class ValidationResponseDTO {

    private boolean isValid;
    private String message;

    // Getters and Setters

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
