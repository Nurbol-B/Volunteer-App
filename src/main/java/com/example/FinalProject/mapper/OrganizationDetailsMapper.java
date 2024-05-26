package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.OrganizationDetailsDto;
import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationDetailsMapper {
    OrganizationDetailsDto toDto(Organization organization);
}
