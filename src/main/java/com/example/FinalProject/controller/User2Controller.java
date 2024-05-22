package com.example.FinalProject.controller;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.entity.User2;
import com.example.FinalProject.service.impl.UserService2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class User2Controller {

    private UserService2 userService2;

    private RegisterRequest registerRequest;

    @PostMapping("/register2")
    public ResponseEntity<?> registerUser(@RequestBody User2 user2) {
        return userService2.saveUser2(user2);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService2.confirmEmail(confirmationToken);
    }

}