package com.bank.card_transaction.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "expiration_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yy")
    private String expirationDate;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "balance")
    private double balance;

    @OneToOne(mappedBy = "card")
    @JsonBackReference
    private User user;

    public Card(Long id, String cardNumber, String cardHolderName, String cvv, String expirationDate, Integer age, double balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.balance = balance;
    }

}
