package com.bank.card_generation.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {
    private Long id;
    private String cardHolderName;
    private Integer age;
}
