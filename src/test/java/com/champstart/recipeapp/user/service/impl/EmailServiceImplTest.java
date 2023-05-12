package com.champstart.recipeapp.user.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class EmailServiceImplTest {
    @Mock
    JavaMailSender mailSender;

    @Spy
    @InjectMocks
    EmailServiceImpl emailService;

    String testVerificationId = "0bd6def6d7a93ac3ee3f803b416bd2c7";

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail() {
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doNothing().when(mailSender).send(mock(MimeMessage.class));
        emailService.sendEmail(anyString(), anyString(), anyString());
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void contructVerificationHtml() {
        String verificationHtml = emailService.contructVerificationHtml(testVerificationId);
        assertTrue(verificationHtml.contains(testVerificationId));
    }
}