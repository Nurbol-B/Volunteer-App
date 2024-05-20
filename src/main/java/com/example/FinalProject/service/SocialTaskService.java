package com.example.FinalProject.service;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.enums.StatusTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocialTaskService {
    List<SocialTaskDto> getAll();
    SocialTaskDto findById(Long id);
    SocialTaskDto createSocialTask(SocialTaskDto socialTaskDto);
    void deleteById(Long id);
    void changeTaskStatus(Long taskId, StatusTask statusTask);
    public List<SocialTaskDto> getOrganizationTasks(Long organizationId);

    }
