package com.example.bank.audit_service.entities;

public class NotificationDTO {

    private String recipientEmail;
    private String subject;
    private String message;

    // Construtores
    public NotificationDTO() {}

    public NotificationDTO(String recipientEmail, String subject, String message) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.message = message;
    }

    // Getters e Setters
    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "recipientEmail='" + recipientEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
