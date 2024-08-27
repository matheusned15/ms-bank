package com.bank.user_management.service;

import com.bank.user_management.entities.User;

import java.util.function.Consumer;

public class UserProcessor {

    public static Consumer<User> emailNotifier = user -> System.out.println("Sending email to: " + user.getEmail());

    public static Consumer<User> logger = user -> System.out.println("User logged: " + user);

}
