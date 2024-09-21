package com.bank.user_management.service;

import com.bank.user_management.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class UserFilters {

    public List<User> filterActiveUsers(List<User> users) {
        return users.stream()
                .filter(User::isActive) // Supondo que exista um método isActive() na entidade User
                .collect(Collectors.toList());
    }

}
