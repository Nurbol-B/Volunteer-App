package com.example.FinalProject.enums;

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
