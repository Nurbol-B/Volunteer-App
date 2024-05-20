package com.example.FinalProject.entity;

import com.example.FinalProject.dto.SocialTaskDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table
@Entity

public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contact;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<SocialTask> socialTasks;
}
