package com.example.FinalProject.entity;

import com.example.FinalProject.dto.SocialTaskDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import java.util.*;

import static jakarta.persistence.FetchType.EAGER;

@Data
@Table
@Entity

public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contact;
    private String address;
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL,fetch = EAGER)
    private List<SocialTask> socialTasks;
    private Date removeDate;

}
