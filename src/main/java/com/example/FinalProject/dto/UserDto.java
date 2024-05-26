package com.example.FinalProject.dto;

import com.example.FinalProject.enums.Role;
import com.example.FinalProject.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String bio;
    private BigDecimal balance;
    private Role role;
    private UserStatus status;
    private Date removeDate;

}
