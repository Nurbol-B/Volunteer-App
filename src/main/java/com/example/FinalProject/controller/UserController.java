package com.example.FinalProject.controller;

import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.enums.Role;
import com.example.FinalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAll();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/balance/{username}")
    public ResponseEntity<BigDecimal> getBalanceByUsername(@PathVariable String username){
        BigDecimal balance = userService.getBalanceByUsername(username);
        return ResponseEntity.ok(balance);
    }
    @PutMapping("/changeRole/{id}")
    public ResponseEntity<UserDto> changeUserRole(@PathVariable Long id, @RequestBody Role role) {
        if (id == null || role == null) {
//            logger.error("Invalid id or role: id={}, role={}", id, role);
            return ResponseEntity.badRequest().build();
        }
        try {
            UserDto updatedUserRole = userService.changeRole(id,role);
            if (updatedUserRole != null) {
//                logger.info("Successfully changed role of user with id {}", id);
                return ResponseEntity.ok(updatedUserRole);
            } else {
//                logger.warn("User with id {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
//            logger.error("Invalid role value: {}", role, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
//            logger.error("Error changing role of user with id {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/updateProfile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody UserDetailsDto updatedUserDto) {
        try {
            userService.updateUser(userId,updatedUserDto);
            return ResponseEntity.ok("Профиль пользователя успешно обновлен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось обновить профиль пользователя: " + e.getMessage());
        }
    }
    @GetMapping("/balance/greater-than")
    public ResponseEntity<List<UserDto>> getUsersWithBalanceGreaterThan(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userService.getUsersWithBalanceGreaterThan(amount));
    }
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferBalance(@RequestParam Long fromUserId, @RequestParam Long toUserId,
                                                @RequestParam BigDecimal amount) {
        userService.transferBalance(fromUserId, toUserId, amount);
        return ResponseEntity.ok().build();
    }
}
