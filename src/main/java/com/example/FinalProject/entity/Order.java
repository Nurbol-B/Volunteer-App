package com.example.FinalProject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pickupLocation;
    private LocalDateTime removeDate;
    private BigDecimal totalAmount;
    private String uniqueCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
