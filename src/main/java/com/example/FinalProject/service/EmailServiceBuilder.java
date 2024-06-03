package com.example.FinalProject.service;

import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceBuilder {
    private JavaMailSender javaMailSender;

    public EmailServiceBuilder setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        return this;
    }

    public EmailService createEmailService() {
        return new EmailService(javaMailSender);
    }
}