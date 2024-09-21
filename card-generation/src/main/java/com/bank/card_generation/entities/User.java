package com.bank.card_generation.entities;


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
    private String password_hash;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public User(Long id, String username, String email, String passwordHash, boolean active) {
    }

    public User(Long id, String username, String email, String password_hash, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
