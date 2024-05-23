package com.example.FinalProject.entity;

import com.example.FinalProject.enums.CodeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Data
public class CodeConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long code;
    @Enumerated(value = EnumType.STRING)
    private CodeStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
