package com.example.FinalProject.entity;

import com.example.FinalProject.enums.StatusTask;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table

public class SocialTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigDecimal bonusForExecution;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private StatusTask statusTask;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;
}

