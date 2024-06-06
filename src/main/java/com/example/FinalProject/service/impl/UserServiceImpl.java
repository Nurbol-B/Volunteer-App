package com.example.FinalProject.service.impl;

import com.example.FinalProject.auth.RegisterRequest;
import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.CodeConfirm;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.CodeStatus;
import com.example.FinalProject.enums.Role;
import com.example.FinalProject.enums.UserStatus;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.exception.UserNotFoundException;
import com.example.FinalProject.mapper.UserDetailsMapper;
import com.example.FinalProject.mapper.UserMapper;
import com.example.FinalProject.repository.CodeConfirmRepository;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CodeConfirmRepository codeConfirmRepository;
    private final JavaMailSender mailSender;
    private final UserDetailsMapper userDetailsMapper;


    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAllByRemoveDateIsNull();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findByIdAndRemoveDateIsNull(id)
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
    public String deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndRemoveDateIsNull(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRemoveDate(new Date(System.currentTimeMillis()));
            userRepository.save(user);
            return "Deleted";
        } else throw new NullPointerException(String.format("Пользователь с id %s не найден", id));
    }

    @Override
    public UserDto updateUser(Long userId, UserDetailsDto updatedUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найдено"));
        userDetailsMapper.updateUserFromDto(user, updatedUserDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void blockUser(String username, Long id) {
        try {
            User user = userRepository.findByUsernameAndId(username,id)
                    .orElseThrow(() -> new NotFoundException("Пользователь с именем " + username + "не найден"));
            user.setIsBlocked(true);
            user.setUserStatus(UserStatus.BLOCKED);
            user.setBlockedAt(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw new NotFoundException("ользователь с именем " + username + "не найден");
        }
    }

    @Override
    public void unlockUser(Long id,String username) {
        User user = userRepository.findByUsernameAndRemoveDateIsNull(username)
                .orElseThrow(() -> new UserNotFoundException("User not found or already unlocked: " + username));

        user.setBlocked(false);
        userRepository.save(user);
    }

    @Override
    public boolean isBlocked(String username) {
        return false;
    }

    @Override
    public BigDecimal getBalanceByUsername(String username) {
        User user = userRepository.findByUsernameAndRemoveDateIsNull(username)
                .orElseThrow(()->new NotFoundException("Пользователь с именем " + username + "не найден"));
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
