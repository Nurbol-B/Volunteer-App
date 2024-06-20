package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.TaskReportDto;
import com.example.FinalProject.entity.TaskReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface TaskReportMapper {

    TaskReportMapper INSTANCE = Mappers.getMapper(TaskReportMapper.class);

//    @Mapping(source = "socialTask.id", target = "socialTaskId")
    TaskReportDto toDto(TaskReport taskReport);

//    @Mapping(source = "socialTaskId", target = "socialTask")
    TaskReport fromDto(TaskReportDto taskReportDto);
}