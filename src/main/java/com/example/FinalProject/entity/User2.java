package com.example.FinalProject.entity;

import jakarta.persistence.*;
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
    private String userName;

    private String userEmail;

    private String userPassword;

    private boolean isEnabled;
}
