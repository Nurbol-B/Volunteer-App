package com.example.FinalProject.service.impl;

import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.service.EmailService;
import com.example.FinalProject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private EmailService emailService;
    @Override
    public void notifyUserAboutNewVacancy(String userEmail, SocialTask socialTask) {
        String subject = "Новая вакансия: " + socialTask.getTitle();
        String text = "Была создана новая вакансия.\n\n" +
                "Название: " + socialTask.getTitle() + "\n" +
                "Описание: " + socialTask.getDescription() + "\n\n" +
                "Подробности можно посмотреть на нашем сайте!";
        emailService.sendEmail(userEmail, subject, text);
    }
}
