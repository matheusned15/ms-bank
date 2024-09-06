package com.bank.user_management;

import com.bank.user_management.entities.CardValidationRequestDTO;
import com.bank.user_management.entities.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "card-validation-service", url = "http://localhost:8002", path = "/api")
public interface CardValidationClient {

    @PostMapping("/validate")
    boolean validateCard(@RequestBody CardValidationRequestDTO requestDTO);
}



