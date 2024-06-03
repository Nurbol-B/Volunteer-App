package com.example.FinalProject.service;

import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.SocialTaskDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {
    List<OrganizationDto> getAll();
    OrganizationDto findById(Long id);
    OrganizationDto createOrganization(OrganizationDto organizationDto);
    String deleteById(Long id);
    OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto);
    List<SocialTaskDto> listAllTasks(Long organizationId);
}
