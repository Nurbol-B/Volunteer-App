package com.example.FinalProject.entity;

import com.example.FinalProject.dto.SocialTaskDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.FetchType.EAGER;

@Data
@Table
@Entity

public class Organization extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3,max = 70)
    private String name;
    private String contact;
    private String address;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL,fetch = EAGER)
    private List<SocialTask> socialTasks;
    private LocalDateTime removeDate;

}
