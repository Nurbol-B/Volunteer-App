package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.mapper.SocialTaskMapper;
import com.example.FinalProject.repository.OrganizationRepository;
import com.example.FinalProject.repository.SocialTaskRepository;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.SocialTaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SocialTaskServiceImpl implements SocialTaskService {
    private final SocialTaskRepository socialTaskRepository;
    private final SocialTaskMapper socialTaskMapper;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    @Override
    public List<SocialTaskDto> getAll() {
        return socialTaskMapper.toDtoList(socialTaskRepository.findAll());
    }

    @Override
    public SocialTaskDto findById(Long id) {
        SocialTask socialTask = socialTaskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача с id " + id + " не найден"));
        return socialTaskMapper.toDto(socialTask);
    }

    @Override
    public SocialTaskDto createSocialTask(SocialTaskDto socialTaskDto) {
        Long organizationId = socialTaskDto.getOrganizationId();

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Организация с id " + organizationId + " не найдена"));

        SocialTask socialTask = socialTaskMapper.toEntity(socialTaskDto);
        socialTask.setOrganization(organization);

        SocialTask savedSocialTask = socialTaskRepository.save(socialTask);

        return socialTaskMapper.toDto(savedSocialTask);
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
    public void getOrganizationTasks(Long organizationId) {
        List<SocialTask> tasks = socialTaskRepository.findByOrganizationId(organizationId);
        tasks.stream().map(socialTaskMapper::toDto).collect(Collectors.toList());
    }
    public SocialTaskDto assignTaskToUser(Long taskId, Long userId) {
        Optional<SocialTask> taskOpt = socialTaskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            SocialTask task = taskOpt.get();
            task.setAssignedUser(userRepository.findById(userId).orElse(null));
            task.setStatusTask(StatusTask.IN_PROGRESS);
            return socialTaskMapper.toDto(socialTaskRepository.save(task));
        }
        return null;

    }

    public SocialTaskDto completeTask(Long taskId) {
        Optional<SocialTask> taskOpt = socialTaskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            SocialTask task = taskOpt.get();
            if (task.getStatusTask() != StatusTask.COMPLETED) {
                User assignedUser = task.getAssignedUser();
                if (assignedUser != null) {
                    BigDecimal bonus = task.getBonusForExecution();
                    if (bonus != null && bonus.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal currentBalance = assignedUser.getBalance() != null ? assignedUser.getBalance() : BigDecimal.ZERO;
                        assignedUser.setBalance(currentBalance.add(bonus));
                        task.setBonusForExecution(BigDecimal.ZERO);
                        userRepository.save(assignedUser);
                    }
                }
                task.setStatusTask(StatusTask.COMPLETED);
                return socialTaskMapper.toDto(socialTaskRepository.save(task));
            }
        }
        return null;
    }

    public List<SocialTaskDto> getTasksByUser(Long userId) {
        return socialTaskMapper.toDtoList(socialTaskRepository.findByAssignedUserId(userId));
    }
}
