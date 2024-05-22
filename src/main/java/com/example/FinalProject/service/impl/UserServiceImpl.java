package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.Role;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.UserMapper;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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


    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
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
                .orElseThrow(()->new NotFoundException("Пользователь с именем " + username + "не найден"));
        return user.getBalance();
    }

    @Override
    public UserDto changeRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Пользователь не найден с " + id));
        user.setRole(role);
        User updateUserRole = userRepository.save(user);
        return userMapper.toDto(updateUserRole);
    }
}
