package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.SocialTaskMapper;
import com.example.FinalProject.repository.SocialTaskRepository;
import com.example.FinalProject.service.SocialTaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SocialTaskServiceImpl implements SocialTaskService {
    private final SocialTaskRepository socialTaskRepository;
    private final SocialTaskMapper socialTaskMapper;

    @Override
    public List<SocialTaskDto> getAll() {
        return socialTaskMapper.toDtoList(socialTaskRepository.findAll());
    }

    @Override
    public SocialTaskDto findById(Long id) {
        SocialTask socialTask = socialTaskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Social task not found with id " + id));
        return socialTaskMapper.toDto(socialTask);
    }

    @Override
    public SocialTaskDto createSocialTask(SocialTaskDto socialTaskDto) {
        Long organizationId = socialTaskDto.getOrganizationId();
        socialTaskDto.setOrganizationId(organizationId);
        return socialTaskMapper.toDto(socialTaskRepository.save(socialTaskMapper.toEntity(socialTaskDto)));
    }

    @Override
    public void deleteById(Long id) {
        socialTaskRepository.deleteById(id);
    }

    @Override
    public void changeTaskStatus(Long taskId, StatusTask statusTask) {
        SocialTask socialTask = socialTaskRepository.findById(taskId)
                .orElseThrow(()->new NotFoundException("Задание с идентификатором  "+ taskId + " не найдено"));
        socialTask.setStatusTask(statusTask);
        socialTaskRepository.save(socialTask);
    }
    @Override
    public List<SocialTaskDto> getOrganizationTasks(Long organizationId) {
        List<SocialTask> tasks = socialTaskRepository.findByOrganizationId(organizationId);
        return tasks.stream().map(socialTaskMapper::toDto).collect(Collectors.toList());
    }

}
