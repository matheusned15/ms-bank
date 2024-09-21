package com.bank.user_management.entities;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String username;
    private String email;
    private String password_hash;
    private boolean isActive;

    public UserRequestDTO(String johnDoe, String mail, String password123) {
    }
}


