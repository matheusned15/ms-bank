package com.bank.card_generation.client;

import com.bank.card_generation.entities.dto.UserDTO;
import com.bank.card_generation.entities.dto.UserValidationRequestDTO;
import com.bank.card_generation.entities.dto.ValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-management-service", url = "${user.management.service.url}")
public interface UserManagementClient {

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);

    @PostMapping("/api/users/validate")
    ValidationResponseDTO validateUser(@RequestBody UserValidationRequestDTO requestDTO);
}