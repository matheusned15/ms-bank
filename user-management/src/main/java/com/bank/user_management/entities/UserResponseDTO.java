package com.bank.user_management.entities;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Date created_at;
    private Date updated_at;
    private boolean isActive;

    public UserResponseDTO(String email, String username) {
    }

    public UserResponseDTO(Long id, String username, String email, String passwordHash, boolean active) {
    }
}
