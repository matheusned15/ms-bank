package com.example.bank.notification_service.service;

import com.example.bank.notification_service.entities.NotificationRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(NotificationRequestDTO notificationRequest) {
        switch (notificationRequest.getType().toLowerCase()) {
            case "email":
                sendEmailNotification(notificationRequest.getRecipient(), notificationRequest.getMessage());
                break;
            case "sms":
                sendSmsNotification(notificationRequest.getRecipient(), notificationRequest.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Invalid notification type: " + notificationRequest.getType());
        }
    }

    private void sendEmailNotification(String recipient, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject("Notificação Importante");
            email.setText(message);
            email.setFrom("noreply@bancoimobiliario.com");

            mailSender.send(email);
            System.out.println("E-mail enviado para: " + recipient);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    private void sendSmsNotification(String recipient, String message) {
        // Aqui você poderia integrar com um serviço de SMS (Ex: Twilio)
        System.out.println("SMS enviado para: " + recipient);
        System.out.println("Mensagem: " + message);
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotificationEvent(String eventMessage) {
        // Aqui você pode processar a mensagem do evento e criar a notificação
        NotificationRequestDTO notificationRequest = parseEventToNotification(eventMessage);

        sendNotification(notificationRequest);
    }

    private NotificationRequestDTO parseEventToNotification(String eventMessage) {
        // Transformar o evento recebido em um DTO para notificação
        // Exemplo simples de mapeamento (isso pode ser mais complexo dependendo do seu evento)
        NotificationRequestDTO notification = new NotificationRequestDTO();
        notification.setRecipient("user@example.com"); // Ajuste o recipient conforme o evento
        notification.setMessage("Novo evento: " + eventMessage);
        notification.setType("email");  // Pode ser "email" ou "sms" dependendo do evento

        return notification;
    }
}
