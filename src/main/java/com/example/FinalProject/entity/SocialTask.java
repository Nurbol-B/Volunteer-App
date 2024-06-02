package com.example.FinalProject.entity;

import com.example.FinalProject.enums.StatusTask;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Size(min = 5, max = 500)
    private String description;
    private BigDecimal bonusForExecution;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private StatusTask statusTask;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

//    @Column(name = "organization_id")
//    private Long organizationId;

}

