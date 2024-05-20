package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.OrganizationMapper;
import com.example.FinalProject.mapper.SocialTaskMapper;
import com.example.FinalProject.repository.OrganizationRepository;
import com.example.FinalProject.service.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final SocialTaskMapper socialTaskMapper;


    @Override
    public List<OrganizationDto> getAll() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizationMapper.toDtoList(organizations);
    }

    @Override
    public OrganizationDto findById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id " + id));
        return organizationMapper.toDto(organization);
    }

    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        Organization organization = organizationRepository.save(organizationMapper.toEntity(organizationDto));
        return organizationMapper.toDto(organization);
    }

    @Override
    public void deleteById(Long id) {
        organizationRepository.deleteById(id);
    }
    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(()->new NotFoundException("Организация с айди " + organizationId +" не найдено"));

        organization.setName(organizationDto.getName());
        organization.setContact(organizationDto.getContact());

        Organization updatedOrganization = organizationRepository.save(organization);
            return organizationMapper.toDto(updatedOrganization);

    }
    @Override
    public List<SocialTaskDto> findTasksByOrganization(Long organizationId) {
        // Найти организацию по идентификатору
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("Organization with id " + organizationId + " not found"));

        // Получить список задач, опубликованных этой организацией
        return organization.getSocialTasks().stream()
                .map(socialTaskMapper::toDto)
                .collect(Collectors.toList());    }
}
