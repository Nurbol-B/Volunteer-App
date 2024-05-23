package com.example.FinalProject.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CodeStatus {
    PENDING("В ожидании"),
    CONFIRMED("Подтвержден")
    ;
    String DESCRIPTION;
}

