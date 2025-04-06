package com.bigfoot.tenantmonitor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaMailSenderEmailService implements EmailService {
    private static final String SUBJECT = "Verification code";

    private final JavaMailSender javaMailSender;

    @Override
    public void sendVerificationEmail(final String email, final String verificationCode) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("${spring.mail.username}");
            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(TEXT.formatted(verificationCode), true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}
