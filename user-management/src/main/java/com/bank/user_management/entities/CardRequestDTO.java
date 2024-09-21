package com.bank.user_management.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {
    private Long id;
    private String cardHolderName;
    private Integer age;
    private String cardNumber;
    private String cvv;
    private LocalDateTime expirationDate;
    private double limit;
}
