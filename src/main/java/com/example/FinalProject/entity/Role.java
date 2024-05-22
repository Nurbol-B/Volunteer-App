package com.example.FinalProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("Пользователь"),
    ADMIN("Администратор"),
    MODERATOR("Модератор");
    String DESCRIPTION;

}
