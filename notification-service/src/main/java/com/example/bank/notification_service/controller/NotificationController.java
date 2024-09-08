package com.example.bank.notification_service.controller;

import com.example.bank.notification_service.entities.NotificationRequestDTO;
import com.example.bank.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDTO notificationRequest) {
        notificationService.sendNotification(notificationRequest);
        return ResponseEntity.ok("Notification sent");
    }
}
