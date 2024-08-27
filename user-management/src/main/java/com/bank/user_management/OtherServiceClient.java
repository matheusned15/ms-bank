package com.bank.user_management;

import com.bank.user_management.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "other-service", url = "localhost:8001", path = "/workers")
public interface OtherServiceClient {

    @GetMapping("/other-service-path/{id}")
    Object getOtherServiceData(@PathVariable("id") String id);

    @PostMapping("/api/validate")
    String validateUser(@RequestBody User user);
}



