package com.example.FinalProject;

import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.Role;
import com.example.FinalProject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}
	@Bean
	CommandLineRunner run(UserService userService) {
		return arg -> {
			userService.createUser(new UserDto(null, "nurba", "nurbolbakasov1@gmail.com", "qwerty", "Volunteer", null, Role.ADMIN));
		};
	}
}

