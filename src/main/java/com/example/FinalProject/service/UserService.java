package com.example.FinalProject.service;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface UserService {
    List<UserDto> getAll();
    UserDto findById(Long id);
    UserDto createUser(UserDto user);
    void deleteById(Long id);
    BigDecimal getBalanceByUsername(String userName);
    UserDto changeRole(Long id, Role role);
    void sendConfirmCode(User user);
    ResponseEntity<?> confirmEmail(Long code);


}