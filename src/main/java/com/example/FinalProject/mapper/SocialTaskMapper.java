package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.dto.TaskReportDto;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.entity.TaskReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SocialTaskMapper {

        SocialTaskMapper INSTANCE = Mappers.getMapper(SocialTaskMapper.class);

//        @Mapping(source = "assignedUser", target = "assignedUser", qualifiedByName = "toDto")
        SocialTaskDto toDto(SocialTask socialTask);

//        @Mapping(source = "assignedUser", target = "assignedUser", qualifiedByName = "toEntity")
        SocialTask fromDto(SocialTaskDto socialTaskDto);

//        @Mapping(source = "assignedUser", target = "assignedUser", qualifiedByName = "updateFromDto")
        void updateFromDto(SocialTaskDto dto, @MappingTarget SocialTask entity);

        List<SocialTaskDto> toDtoList(List<SocialTask> allByRemoveDateIsNull);

        SocialTask toEntity(SocialTaskDto socialTaskDto);
}
