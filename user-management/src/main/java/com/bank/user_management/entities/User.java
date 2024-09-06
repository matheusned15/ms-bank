package com.bank.user_management.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password_hash;
    private Date created_at;
    private Date updated_at;
    private boolean isActive;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public User(Long id, String username, String email, String passwordHash, boolean active) {
    }
}
