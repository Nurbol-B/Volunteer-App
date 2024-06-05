package com.example.FinalProject.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(max = 255, message = "Имя пользователя не может превышать 255 символов")
    private String username;
    @Size(max = 255, message = "Информация о себе не может превышать 255 символов")
    private String bio;
    @Email(message = "Эл. почта не валиден")
    private String email;
    @Size(min = 6 ,message = "Пароль должен содержать не более 6 символов")
    @Size(max =255, message = "Пароль не может превышать 255 символов")
    private String password;
}
