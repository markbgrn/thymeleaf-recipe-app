package com.champstart.recipeapp.user.service;

import com.champstart.recipeapp.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public interface EmailService {

    public void sendEmail(String to, String subject, String body);

    public String contructVerificationHtml(String verificationId);
    public String contructResetPasswordHtml(String verificationId);
}
