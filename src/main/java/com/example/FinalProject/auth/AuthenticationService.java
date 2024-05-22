package com.example.FinalProject.auth;


import com.example.FinalProject.secutityConfig.JwtService;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.Role;
import com.example.FinalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
//    private final JavaMailSender emailSender;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .balance(registerRequest.getBalance())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
//        sendRegistrationEmail(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

//    private void sendRegistrationEmail(String recipientEmail){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("nurbolbakasov1@gmail.com");
//        message.setTo(recipientEmail);
//        message.setSubject("Успешная регистрация");
//        message.setText("Вы успешно зарегистировались в нашем сервисе Clothes Shop");
//        emailSender.send(message);
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
