package com.example.bank.audit_service.service;

import com.example.bank.audit_service.client.UserManagementClient;
import com.example.bank.audit_service.entities.AuditLog;
import com.example.bank.audit_service.entities.UserDTO;
import com.example.bank.audit_service.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditLogRepository;

    @Autowired
    private UserManagementClient userManagementClient;

    public AuditService(AuditRepository auditLogRepository, UserManagementClient userManagementClient) {
        this.auditLogRepository = auditLogRepository;
        this.userManagementClient = userManagementClient;
    }

    public void logEvent(AuditLog log) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEventType(log.getEventType());
        auditLog.setDescription(log.getDescription());
        auditLog.setEventTime(log.getEventTime());
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

}
