package com.example.FinalProject.service.impl;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.CodeConfirm;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.CodeStatus;
import com.example.FinalProject.enums.Role;
import com.example.FinalProject.enums.UserStatus;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.UserMapper;
import com.example.FinalProject.repository.CodeConfirmRepository;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CodeConfirmRepository codeConfirmRepository;
    private final JavaMailSender mailSender;


    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден с " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public BigDecimal getBalanceByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с именем " + username + "не найден"));
        return user.getBalance();
    }

    @Override
    public UserDto changeRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден с " + id));
        user.setRole(role);
        User updateUserRole = userRepository.save(user);
        return userMapper.toDto(updateUserRole);
    }

    @Override
    public void sendConfirmCode(User user) {
        CodeConfirm codeConfirm = new CodeConfirm();
        codeConfirm.setCode(generateVerificationCode());
        codeConfirm.setStatus(CodeStatus.PENDING);
        codeConfirm.setUser(user);
        codeConfirmRepository.save(codeConfirm);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Успешная регистрация!");
        mailMessage.setText("Чтобы подтвердить свою учетную запись, используйте следующий код: " + codeConfirm.getCode());
        mailSender.send(mailMessage);
    }

    @Override
    public ResponseEntity<?> confirmEmail(Long code) {
        CodeConfirm codeConfirm = codeConfirmRepository.findByCode(code);

        if (codeConfirm != null && codeConfirm.getStatus() == CodeStatus.PENDING) {
            User user = codeConfirm.getUser();
            user.setUserStatus(UserStatus.CONFIRM);
            userRepository.save(user);

            codeConfirm.setStatus(CodeStatus.CONFIRMED);
            codeConfirmRepository.save(codeConfirm);

            return ResponseEntity.ok("Электронная почта успешно подтверждена!");
        }
        return ResponseEntity.badRequest().body("Ошибка: не удалось подтвердить адрес электронной почты.");
    }

    private Long generateVerificationCode() {
        return (long) (Math.random() * 1000000);
    }
}
