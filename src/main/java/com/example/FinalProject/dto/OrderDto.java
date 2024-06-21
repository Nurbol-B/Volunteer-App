package com.example.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
    private Long id;
    private Long userId;
    private String pickupLocation;
    private String uniqueCode;
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;

}
