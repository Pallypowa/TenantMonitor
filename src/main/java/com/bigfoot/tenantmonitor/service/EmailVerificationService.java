package com.bigfoot.tenantmonitor.service;

import com.bigfoot.tenantmonitor.exception.UserNotFoundException;
import com.bigfoot.tenantmonitor.exception.VerificationTokenInvalidException;
import com.bigfoot.tenantmonitor.model.User;
import com.bigfoot.tenantmonitor.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final UserRepository userRepository;
    private final EmailService emailS;


    public void verify(final String email, final String verificationCode) {
        final User user = userRepository.findByUserNameOrEmail(null, email).orElseThrow(() -> new UserNotFoundException("User not found!"));
        if(!user.getVerificationCode().equals(verificationCode) || LocalDateTime.now().isAfter(user.getVerificationCodeExpires().plusMinutes(15))) {
            throw new VerificationTokenInvalidException("Email verification token invalid or expired!");
        }

        user.setVerified(true);
        userRepository.save(user);
    }

    @Transactional
    public void resend(final String email) {
        final User user = userRepository.findByUserNameOrEmail(null, email).orElseThrow(() -> new UserNotFoundException("User not found!"));
        if(user.isEnabled()) {
            throw new RuntimeException("User is already verified!");
        }
        final String verificationCode = generateVerificationCode();

        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpires(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        emailS.sendVerificationEmail(user.getEmail(), verificationCode);
    }

    public void sendNewVerification(final User user, final String email) {
        final String verificationCode = generateVerificationCode();
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpires(LocalDateTime.now().plusMinutes(15));
        user.setVerified(false);

        emailS.sendVerificationEmail(user.getEmail(), user.getVerificationCode());
    }


    private static String generateVerificationCode() {
        return RandomStringUtils.random(6, 0, 0, true, true, null, new SecureRandom());
    }
}
