package com.example.bank.audit_service.controller;

import com.example.bank.audit_service.entities.AuditLog;
import com.example.bank.audit_service.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/audit")
    public ResponseEntity<String> auditEvent(@RequestBody AuditLog auditDTO) {
        auditService.logEvent(auditDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Audit event recorded.");
    }
}
