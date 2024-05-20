package com.example.FinalProject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusTask {
    COMPLETED("Выполнена"),
    CANCELED("Отменена"),
    IN_PROGRESS("В процессе")
    ;
    String DESCRIPTION;
}
