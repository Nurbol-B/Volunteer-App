package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.OrganizationMapper;
import com.example.FinalProject.mapper.SocialTaskMapper;
import com.example.FinalProject.repository.OrganizationRepository;
import com.example.FinalProject.repository.SocialTaskRepository;
import com.example.FinalProject.service.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final SocialTaskMapper socialTaskMapper;
    private final SocialTaskRepository socialTaskRepository;


    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = organizationRepository.findAllByRemoveDateIsNull();
        return organizationMapper.toDtoList(organizations);
    }

    @Override
    public OrganizationDto findById(Long id) {
        Organization organization = organizationRepository.findByIdAndRemoveDateIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("Организация с id " + id + " не найден"));
        return organizationMapper.toDto(organization);
    }

    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        Organization organization = organizationRepository.save(organizationMapper.toEntity(organizationDto));
        return organizationMapper.toDto(organization);
    }

    @Override
    public String deleteById(Long id) {
        Optional<Organization> optionalOrganization = organizationRepository.findByIdAndRemoveDateIsNull(id);
        if (optionalOrganization.isPresent()) {
            Organization organization = optionalOrganization.get();
            organization.setRemoveDate(new Date(System.currentTimeMillis()));
            organizationRepository.save(organization);
            return "Deleted";
        } else throw new NullPointerException(String.format("Организация с id %s не найдена", id));
    }

    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Организация с id " + organizationId + " не найдено"));

        organization.setName(organizationDto.getName());
        organization.setContact(organizationDto.getContact());

        Organization updatedOrganization = organizationRepository.save(organization);
        return organizationMapper.toDto(updatedOrganization);

    }

    @Override
    public List<SocialTaskDto> listAllTasks(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Организация с id " + organizationId + " не найдена"));

        return organization.getSocialTasks().stream()
                .map(socialTaskMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<SocialTaskDto> getTasksByStatus(Long organizationId, StatusTask status) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Организация с id " + organizationId + " не найдена"));
        return organization.getSocialTasks().stream()
                .filter(task -> task.getStatusTask() == status)
                .map(socialTaskMapper::toDto)
                .collect(Collectors.toList());
    }
}
