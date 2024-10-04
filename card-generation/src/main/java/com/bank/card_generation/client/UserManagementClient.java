package com.bank.card_generation.client;

import com.bank.card_generation.entities.dto.CardRequestDTO;
import com.bank.card_generation.entities.dto.UserResponseDTO;
import com.bank.card_generation.entities.dto.UserValidationRequestDTO;
import com.bank.card_generation.entities.dto.ValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "user-management-service", url = "localhost:8081", path = "/api/users")
public interface UserManagementClient {

    @GetMapping("{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

    @PostMapping("/api/users/validate")
    ValidationResponseDTO validateUser(@RequestBody UserValidationRequestDTO requestDTO);

    @PutMapping("/api/users/{userId}/add-card")
    void addCardToUser(@PathVariable("userId") Long userId, @RequestBody CardRequestDTO card);
}