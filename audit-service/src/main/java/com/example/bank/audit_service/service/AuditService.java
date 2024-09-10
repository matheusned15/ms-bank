package com.example.bank.audit_service.service;

import com.example.bank.audit_service.entities.AuditLog;
import com.example.bank.audit_service.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditLogRepository;

    public void logEvent(String eventType, String eventDescription) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEventType(eventType);
        auditLog.setEventDescription(eventDescription);
        auditLog.setCreatedAt(LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
