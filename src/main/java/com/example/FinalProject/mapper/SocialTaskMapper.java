package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.SocialTask;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocialTaskMapper {
    SocialTask toEntity(SocialTaskDto socialTaskDto);

    SocialTaskDto toDto(SocialTask socialTask);
    List<SocialTaskDto> toDtoList(List<SocialTask> socialTasks);
}
