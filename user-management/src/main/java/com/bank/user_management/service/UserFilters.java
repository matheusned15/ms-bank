package com.bank.user_management.service;

import com.bank.user_management.entities.User;

import java.util.function.Predicate;

public class UserFilters {

    public static Predicate<User> hasValidEmail = user -> user.getEmail() != null && user.getEmail().contains("@");

}
