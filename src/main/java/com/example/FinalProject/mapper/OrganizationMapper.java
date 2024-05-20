package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.entity.Organization;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toEntity(OrganizationDto organizationDto);

    OrganizationDto toDto(Organization organization);
    List<OrganizationDto> toDtoList(List<Organization> organizations);
}
