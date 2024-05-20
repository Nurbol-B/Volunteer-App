package com.example.FinalProject.controller;

import com.example.FinalProject.dto.UserDto;
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
}
