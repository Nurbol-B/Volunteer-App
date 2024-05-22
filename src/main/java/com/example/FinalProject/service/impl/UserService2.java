package com.example.FinalProject.service.impl;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.entity.User2;
import org.springframework.http.ResponseEntity;

public interface UserService2 {

    ResponseEntity<?> saveUser2(User2 user2);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
