package com.bank.card_validation.client;

import com.bank.card_validation.entity.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "audit-service", url = "${audit-service.url}", path = "/api")
public interface AuditClient {

    @PostMapping("/audit")
    void sendAuditEvent(@RequestBody AuditDTO auditDTO);
}
