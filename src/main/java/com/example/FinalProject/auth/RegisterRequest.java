package com.example.FinalProject.auth;

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
    @NotNull(message = "Failed")
    private String username;
    @NotNull(message = "Failed")
    private String bio;
    private BigDecimal balance;
    @NotNull(message = "Email не может быть пустым")
    private String email;
    @Size(min = 6,message = "Пароль не может быть меньше 6 символов")
    private String password;
}
