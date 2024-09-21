package com.example.bank.audit_service.client;


import com.example.bank.audit_service.entities.AuditLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service", url = "http://localhost:8081")  // Substitua pela URL do audit-service
public interface AuditClient {

    @PostMapping("/audit")
    void sendAuditEvent(@RequestBody AuditLog auditDTO);
}
