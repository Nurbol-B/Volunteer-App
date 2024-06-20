package com.example.FinalProject.dto;

import com.example.FinalProject.enums.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialTaskDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal bonusForExecution;
    private LocalDate deadline;
    private StatusTask statusTask;
    private Long organizationId;
    private Long userId;
    private Date removeDate;
    private List<TaskReportDto> taskReports;



}
