package com.example.FinalProject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
public class TaskReport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String photo;
    private String description;
    @ManyToOne
    @JoinColumn(name = "social_task_id")
    private SocialTask socialTask;
    private LocalDateTime removeDate;
}