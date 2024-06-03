package com.example.FinalProject.service;

import com.example.FinalProject.dto.OrganizationDetailsDto;
import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.enums.StatusTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocialTaskService {
    List<SocialTaskDto> getAll();
    SocialTaskDto findById(Long id);
    SocialTaskDto createSocialTask(SocialTaskDto socialTaskDto);
    String deleteById(Long id);
    void changeTaskStatus(Long taskId, StatusTask statusTask);
    void getOrganizationTasks(Long organizationId);
    SocialTaskDto assignTaskToUser(Long taskId, Long userId);
    SocialTaskDto completeTask(Long taskId);
    SocialTaskDto cancelSocialTask(Long taskId);
    SocialTaskDto unAssignUser(Long taskId, Long userId);
    UserDetailsDto getAssignedUserDetails(Long userId);
    OrganizationDetailsDto getOrganizationDetails(Long taskId);
    List<SocialTaskDto> listAssignedTasks(Long userId);
}
