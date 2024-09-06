package com.bank.user_management.entities;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password_hash;
    private Date created_at;
    private Date updated_at;
    private boolean isActive;

    public UserDTO(String email, String username) {
    }

    public UserDTO(Long id, String username, String email, String passwordHash, boolean active) {
    }
}
