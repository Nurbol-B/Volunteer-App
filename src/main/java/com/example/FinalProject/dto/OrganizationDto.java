package com.example.FinalProject.dto;

import com.example.FinalProject.entity.SocialTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    private Long id;
    private String name;
    private String contact;
    private List<SocialTaskDto> socialTasks;
}