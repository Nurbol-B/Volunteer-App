package com.example.FinalProject.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserStatus {
    NEW("Новый"),
    ACTIVE("Подтвержденный"),
    CONFIRM("Подтвержденный"),
    BLOCKED("Заблокирован")
    ;

    String DESCRIPTION;
}
