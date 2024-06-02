package com.example.FinalProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "username")
    @Size(min = 3, max = 20)
    private String userName;

    @Pattern(regexp = "^([a-zA-Z0-9\\\\-\\\\.\\\\_]+)'+'(\\\\@)([a-zA-Z0-9\\\\-\\\\.]+)'+'(\\\\.)([a-zA-Z]{2,4})$")
    private String userEmail;

    @Size(max = 50)
    private String userPassword;

    private boolean isEnabled;
}
