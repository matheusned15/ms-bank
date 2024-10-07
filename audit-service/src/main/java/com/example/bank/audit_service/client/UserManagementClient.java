package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definindo um Feign Client para se comunicar com o user-management-service
@FeignClient(name = "user-management-service")
public interface UserManagementClient {

    // Endpoint para buscar detalhes do usuário pelo ID
    @GetMapping("/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);

}
