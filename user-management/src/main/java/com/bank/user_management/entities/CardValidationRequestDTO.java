package com.bank.user_management.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardValidationRequestDTO {

    private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expirationDate;
}
