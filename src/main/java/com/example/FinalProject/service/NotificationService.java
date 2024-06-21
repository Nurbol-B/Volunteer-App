package com.example.FinalProject.service;

import com.example.FinalProject.entity.SocialTask;

public interface NotificationService {
    public void notifyUserAboutNewVacancy(String userEmail, SocialTask socialTask);
}
