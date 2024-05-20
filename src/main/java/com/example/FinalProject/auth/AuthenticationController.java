package com.example.FinalProject.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка в валидации: " + result.getAllErrors());
        } else {
            authenticationService.register(registerRequest);
            return ResponseEntity.ok("Registered Successully");
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }


}
