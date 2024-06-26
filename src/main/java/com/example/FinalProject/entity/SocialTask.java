package com.example.FinalProject.entity;

import com.example.FinalProject.enums.StatusTask;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table

public class SocialTask extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Size(min = 5, max = 500)
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
    private LocalDateTime removeDate;

    @OneToMany(mappedBy = "socialTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskReport> taskReports = new ArrayList<>();

}

