package com.bank.card_generation.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO {
    private String eventType;
    private String description;
    private LocalDateTime eventTime;
}
