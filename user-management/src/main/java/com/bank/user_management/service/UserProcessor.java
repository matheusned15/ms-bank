package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserProcessor {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User process(User user) {

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }

        return user;
    }

}


