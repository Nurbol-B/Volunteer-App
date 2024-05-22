package com.example.FinalProject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusTask {
    COMPLETED("Выполнена"),
    IN_PROGRESS("В процессе"),
    PENDING("В ожидании"),
    CANCELED("Отменена")
    ;
    final String DESCRIPTION;
}
