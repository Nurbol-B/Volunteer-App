package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.UserDto;
import com.example.FinalProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
}
