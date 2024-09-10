package com.example.bank.audit_service.client;

import com.example.bank.audit_service.entities.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8084/api/notifications")
public interface NotificationClient {

    @PostMapping
    void sendNotification(@RequestBody NotificationDTO notificationDTO);
}
