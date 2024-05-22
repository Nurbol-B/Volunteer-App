package com.example.FinalProject.service.impl;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.entity.ConfirmationToken;
import com.example.FinalProject.entity.User2;
import com.example.FinalProject.repository.ConfirmationTokenRepository;
import com.example.FinalProject.repository.UserRepository2;
import com.example.FinalProject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl2 implements UserService2 {

    @Autowired
    private UserRepository2 userRepository2;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    private User2 user2;

    @Override
    public ResponseEntity<?> saveUser2(User2 user2) {

        if (userRepository2.existsByUserEmail(user2.getUserEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        userRepository2.save(user2);

        ConfirmationToken confirmationToken = new ConfirmationToken(user2);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user2.getUserEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User2 user2 = userRepository2.findByUserEmailIgnoreCase(token.getUserEntity().getUserEmail());
            user2.setEnabled(true);
            userRepository2.save(user2);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }
}
