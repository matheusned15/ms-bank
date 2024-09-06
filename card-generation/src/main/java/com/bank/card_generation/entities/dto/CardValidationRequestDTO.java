package com.bank.card_generation.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardValidationRequestDTO {
    private String cardNumber;
    private String cardHolderName;
    private LocalDateTime expirationDate;
    private String cvv;
}
