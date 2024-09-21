package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "user-management-service", url = "$localhost:8081", path = "/api/users")
public interface UserManagementClient {

    @GetMapping("/{userId}")
    UserDTO getUserDetails(@PathVariable("userId") String userId);
}
