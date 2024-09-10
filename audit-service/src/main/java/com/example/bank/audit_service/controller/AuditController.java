package com.example.bank.audit_service.controller;

import com.example.bank.audit_service.entities.AuditLog;
import com.example.bank.audit_service.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/logs")
    public List<AuditLog> getAuditLogs() {
        return auditService.getAllLogs();
    }
}
