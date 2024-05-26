package com.example.FinalProject.auth;


import com.example.FinalProject.enums.UserStatus;
import com.example.FinalProject.secutityConfig.JwtService;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.Role;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
//    private final JavaMailSender emailSender;

@Transactional
public AuthenticationResponse register(RegisterRequest registerRequest) {
    User user = User.builder()
            .username(registerRequest.getUsername())
            .balance(registerRequest.getBalance())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(Role.USER)
            .userStatus(UserStatus.NEW)
            .build();
    user = userRepository.save(user);

    userService.sendConfirmCode(user);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
}

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByEmailAndRemoveDateIsNull(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с почтой " + authenticationRequest.getEmail() + " не найден" ));

        if (user.getUserStatus() != UserStatus.CONFIRM) {
            throw new IllegalStateException("Пользователь не подтвердил свой адрес электронной почты");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );
        user = userRepository.findByEmailAndRemoveDateIsNull(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

