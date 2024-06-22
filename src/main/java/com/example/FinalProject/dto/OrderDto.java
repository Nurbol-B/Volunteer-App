package com.example.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
    private Long id;
    private String pickupLocation;
    private BigDecimal totalAmount;
    private String uniqueCode;
    private Long userId;
    private LocalDateTime removeDate;
}
