package com.sdm.invoicing.service;

import com.sdm.invoicing.entity.PasswordResetToken;
import com.sdm.invoicing.entity.User;
import com.sdm.invoicing.repository.PasswordResetTokenRepository;
import com.sdm.invoicing.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public ForgotPasswordService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email not found"));

        if(user.isActivated()) {
            String token = UUID.randomUUID().toString();

            Date expiryDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour from now

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setEmail(email);
            resetToken.setExpiryDate(expiryDate);
            passwordResetTokenRepository.save(resetToken);

            emailService.sendPasswordResetEmail(user, token);
            return "Password reset link has been sent to your email.";
        }

        return "Your Account is Not Activated.";
    }

    public boolean validateResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Token has expired");
        }

        return true;
    }

    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        User user = userRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}