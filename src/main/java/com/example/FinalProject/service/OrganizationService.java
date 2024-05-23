package com.example.FinalProject.service;

import com.example.FinalProject.dto.OrganizationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationService {
    List<OrganizationDto> getAll();
    OrganizationDto findById(Long id);
    OrganizationDto createOrganization(OrganizationDto organizationDto);
    void deleteById(Long id);
    OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto);
    void findTasksByOrganization(Long organizationId);

}
