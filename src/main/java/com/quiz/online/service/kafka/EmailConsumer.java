package com.quiz.online.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.online.dto.request.EmailRequest;
@Service
public class EmailConsumer {
     private final JavaMailSender mailSender;

    public EmailConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "${spring.kafka.topic.email}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenEmailTopic(String message) {
        try {
            // Parse JSON message
            ObjectMapper mapper = new ObjectMapper();
            EmailRequest emailRequest = mapper.readValue(message, EmailRequest.class);

            // Gửi email
            sendEmail(emailRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailRequest.getTo());
        mailMessage.setSubject(emailRequest.getSubject());
        mailMessage.setText(emailRequest.getBody());

        mailSender.send(mailMessage);
    }
}
