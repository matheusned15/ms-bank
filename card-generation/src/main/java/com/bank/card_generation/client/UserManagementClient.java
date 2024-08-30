package com.bank.card_generation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management", url = "localhost:8001")
public interface UserManagementClient {

    @GetMapping("/api/users/{id}")
    Object getUserById(@PathVariable("id") Long id);
}