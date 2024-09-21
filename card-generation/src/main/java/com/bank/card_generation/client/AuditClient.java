package com.bank.card_generation.client;


import com.bank.card_generation.entities.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service", url = "http://localhost:8081")  // Substitua pela URL do audit-service
public interface AuditClient {

    @PostMapping("/audit")
    void sendAuditEvent(@RequestBody AuditDTO auditDTO);
}
