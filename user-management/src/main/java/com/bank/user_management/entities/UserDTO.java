package com.bank.user_management.entities;

import lombok.*;

import java.util.Date;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String password_hash;
    private Date created_at;
    private Date updated_at;

    public UserDTO(String email, String username) {
    }
}
