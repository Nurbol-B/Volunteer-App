package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface UserDetailsMapper {
    UserDetailsDto toDto(User user);
    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(@MappingTarget User user, UserDetailsDto userDetailsDto);
}
