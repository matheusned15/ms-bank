package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "notification-service", url = "http://localhost:8086" , path = "/api/notifications")
public interface NotificationClient {

    @PostMapping
    void sendNotification(@RequestBody NotificationDTO notificationDTO);
}
