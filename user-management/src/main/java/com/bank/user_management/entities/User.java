package com.bank.user_management.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;



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
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updated_at;
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public User(Long id, String username, String email, String password_hash, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password_hash;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String username, String email, String passwordHash, boolean active, LocalDateTime created, LocalDateTime last_update) {
        this.username = username;
        this.email = email;
        this.password = passwordHash;
        this.isActive = active;
        this.created_at = created;
        this.updated_at = last_update;
    }
}
