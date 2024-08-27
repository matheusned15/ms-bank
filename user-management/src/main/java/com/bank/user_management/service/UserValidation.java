package com.bank.user_management.service;

import com.bank.user_management.entities.User;

@FunctionalInterface
public interface UserValidation {
    boolean validate(User user);
}
