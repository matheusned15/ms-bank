package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management", url = "http://localhost:8080/api/users")
public interface UserManagementClient {

    @GetMapping("/{userId}")
    UserDTO getUserDetails(@PathVariable("userId") String userId);
}
