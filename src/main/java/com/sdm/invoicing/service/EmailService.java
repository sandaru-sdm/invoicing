package com.sdm.invoicing.service;

import com.sdm.invoicing.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.base.url}")
    private String BASE_URL = "";

    @Value("${app.client.url}")
    private String FRONTEND_URL = "";

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

//    public void sendActivationEmail(String toEmail, String activationCode) {
//        String activationLink = BASE_URL + "auth/activate?code=" + activationCode;
//        String subject = "Account Activation";
//        String text = "Please activate your account using the following link: " + activationLink;
//
//        sendEmail(toEmail, subject, text);
//    }

    public void sendActivationEmail(String toEmail, String activationCode) {
        String activationLink = BASE_URL + "auth/activate?code=" + activationCode;
        String subject = "Account Activation";

        String htmlContent = "<h2>Account Activation</h2>"
                + "<p>Thank you for signing up! Please activate your account by clicking the button below:</p>"
                + "<a href='" + activationLink + "' style='background-color:#007bff; color:white; padding:10px 20px; text-decoration:none; border-radius:5px;'>Activate Account</a>"
                + "<p>If the button above doesn't work, click the following link:</p>"
                + "<p><a href='" + activationLink + "'>" + activationLink + "</a></p>";

        sendHtmlEmail(toEmail, subject, htmlContent);
    }


    public void sendPasswordResetEmail(User user, String token) {
        String resetUrl = FRONTEND_URL + "reset-password/" + token;
        String subject = "Password Reset Request";

        String htmlContent = "<h2>Password Reset Request</h2>"
                + "<p>Click the link below to reset your password:</p>"
                + "<a href='" + resetUrl + "' style='background-color:#007bff; color:white; padding:10px 20px; text-decoration:none; border-radius:5px;'>Reset Password</a>";

        sendHtmlEmail(user.getEmail(), subject, htmlContent);
    }

    public void sendHtmlEmail(String email, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Failed to send HTML email to {}", email, e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error while sending email to {}", email, e);
        }
    }

    public void sendEmail(String toEmail, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Failed to send email to {}", toEmail, e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error while sending email to {}", toEmail, e);
        }
    }
}
