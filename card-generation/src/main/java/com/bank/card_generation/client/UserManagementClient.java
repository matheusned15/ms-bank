package com.bank.card_generation.client;

import com.bank.card_generation.entities.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "user-management-service", url = "localhost:8081", path = "/api/users")
public interface UserManagementClient {

    @GetMapping("{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

    @PutMapping("/{id}")
    void updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO);
}