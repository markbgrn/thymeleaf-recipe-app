package com.champstart.recipeapp.user.service.impl;

import com.champstart.recipeapp.user.dto.UserDto;
import com.champstart.recipeapp.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.InvalidParameterException;

@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    @Autowired
    EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom("noreply@recipehub.com");
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setText(body);

            String htmlContent = body;
            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public String contructVerificationHtml(String verificationId) {
        if(verificationId.equals("") || verificationId == null) {
            throw new IllegalArgumentException("verificationId is null or empty");
        }
        String url = "http://localhost:8080/verify?verificationId=" + verificationId;
        return "<h1>Please verify your RecipeHub Account</h1>" +
                "<p>Click the link to verify your account: </p></br>" + "<a href=\"" + url + "\">" + url + "</a>";
    }

    public String contructResetPasswordHtml(String verificationId) {
        if(verificationId.equals("") || verificationId == null) {
            throw new IllegalArgumentException("verificationId is null or empty");
        }
        String url = "http://localhost:8080/new-password?verificationId=" + verificationId;
        return "<h1>Reset your RecipeHub password</h1>" +
                "<p>Click the link to set a new password: </p></br>" + "<a href=\"" + url + "\">" + url + "</a>";
    }
}
