package com.example.FinalProject.auth;

import com.example.FinalProject.exception.EmailNotConfirmedException;
import com.example.FinalProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка валидации: " + result.getAllErrors());
        } else {
            authenticationService.register(registerRequest);
            return ResponseEntity.ok("Регистрация прошла успешно. Подтвердите электронную почту по коду, отправленному на ваш адрес электронной почты.");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
            return ResponseEntity.ok().body(response);
        } catch (EmailNotConfirmedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Электронная почта не подтверждена. Пожалуйста, подтвердите вашу электронную почту.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации: " + e.getMessage());
        }
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam Long code) {
        return userService.confirmEmail(code);
    }
}
