package com.example.FinalProject.entity;

import com.example.FinalProject.dto.SocialTaskDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3,max = 70)
    private String name;
    private String contact;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<SocialTask> socialTasks;
}
