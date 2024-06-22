package com.example.FinalProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private LocalDateTime removeDate;
        private int stockQuantity;

}
