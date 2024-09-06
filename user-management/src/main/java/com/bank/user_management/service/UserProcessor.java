package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class UserProcessor {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User process(User user) {
        // Adicione aqui qualquer lógica de processamento necessária.
        // Exemplo: Encriptar senha, validar dados, etc.

        String hashedPassword = passwordEncoder.encode(user.getPassword_hash());

        // Define a senha criptografada no objeto User
        user.setPassword_hash(hashedPassword);
        // Apenas retornando o usuário sem alterações neste exemplo
        return user;
    }

}


