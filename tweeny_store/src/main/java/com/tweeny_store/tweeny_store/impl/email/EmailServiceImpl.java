package com.tweeny_store.tweeny_store.impl.email;

import com.tweeny_store.tweeny_store.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.tweeny_store.tweeny_store.model.email.EmailTemplate.ACTIVATE_ACCOUNT;
import static com.tweeny_store.tweeny_store.ultils.email.EmailUtils.getEmailMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(ACTIVATE_ACCOUNT.name());
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setText(getEmailMessage(name, host, token));
        emailSender.send(message);
    }
}
