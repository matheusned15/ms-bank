package com.bank.card_generation.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardValidationRequestDTO {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
}
