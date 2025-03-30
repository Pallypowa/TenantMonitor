package com.bigfoot.tenantmonitor.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String SUBJECT = "Verification code";
    private static final String TEXT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\" />\n" +
            "    <title>TenantMonitor Verification Code</title>\n" +
            "  </head>\n" +
            "  <body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px;\">\n" +
            "    <table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);\">\n" +
            "      <tr>\n" +
            "        <td style=\"padding: 30px;\">\n" +
            "          <h2 style=\"color: #2c3e50;\">Welcome to <span style=\"color: #4CAF50;\">TenantMonitor</span> \uD83D\uDC4B</h2>\n" +
            "          <p style=\"font-size: 16px; color: #333;\">\n" +
            "            We're excited to have you on board! To complete your sign-up, please use the verification code below:\n" +
            "          </p>\n" +
            "\n" +
            "          <div style=\"margin: 30px 0; text-align: center;\">\n" +
            "            <span style=\"display: inline-block; background-color: #f0f0f0; padding: 15px 25px; font-size: 24px; font-weight: bold; border-radius: 6px; color: #2c3e50;\">\n" +
            "              %s\n" +
            "            </span>\n" +
            "          </div>\n" +
            "\n" +
            "          <p style=\"font-size: 14px; color: #666;\">\n" +
            "            This code is valid for a limited time. If you didn’t request this, you can safely ignore this email.\n" +
            "          </p>\n" +
            "\n" +
            "          <p style=\"font-size: 14px; color: #999; margin-top: 40px;\">\n" +
            "            – The TenantMonitor Team\n" +
            "          </p>\n" +
            "        </td>\n" +
            "      </tr>\n" +
            "    </table>\n" +
            "  </body>\n" +
            "</html>\n";

    private final JavaMailSender javaMailSender;

    public void sendVerificationEmail(final String email, final String verificationCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(SUBJECT);
        helper.setText(TEXT.formatted(verificationCode), true);

        javaMailSender.send(message);
    }
}
