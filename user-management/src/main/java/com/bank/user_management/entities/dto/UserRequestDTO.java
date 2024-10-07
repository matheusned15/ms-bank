package com.bank.user_management.entities.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String username;
    private String email;
    private String password;
    private boolean isActive;

    public UserRequestDTO(String johnDoe, String mail, String password123) {
    }
}


