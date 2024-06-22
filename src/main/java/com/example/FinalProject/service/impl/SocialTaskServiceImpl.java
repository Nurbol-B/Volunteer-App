package com.example.FinalProject.service.impl;

import com.example.FinalProject.dto.OrganizationDetailsDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.dto.TaskReportDto;
import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.entity.Organization;
import com.example.FinalProject.entity.SocialTask;
import com.example.FinalProject.entity.TaskReport;
import com.example.FinalProject.entity.User;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.exception.NotFoundException;
import com.example.FinalProject.exception.TaskNotInProgressException;
import com.example.FinalProject.mapper.OrganizationDetailsMapper;
import com.example.FinalProject.mapper.SocialTaskMapper;
import com.example.FinalProject.mapper.TaskReportMapper;
import com.example.FinalProject.mapper.UserDetailsMapper;
import com.example.FinalProject.repository.OrganizationRepository;
import com.example.FinalProject.repository.SocialTaskRepository;
import com.example.FinalProject.repository.TaskReportRepository;
import com.example.FinalProject.repository.UserRepository;
import com.example.FinalProject.service.BonusHistoryService;
import com.example.FinalProject.service.SocialTaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SocialTaskServiceImpl implements SocialTaskService {
    private final SocialTaskRepository socialTaskRepository;
    private final SocialTaskMapper socialTaskMapper;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final OrganizationDetailsMapper organizationDetailsMapper;
    private final BonusHistoryService bonusHistoryService;
    private final TaskReportRepository taskReportRepository;

    @Override
    public List<SocialTaskDto> getAll() {
        return socialTaskMapper.toDtoList(socialTaskRepository.findAllByRemoveDateIsNull());
    }

    @Override
    public SocialTaskDto findById(Long id) {
        SocialTask socialTask = socialTaskRepository.findByIdAndRemoveDateIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("Задание с id " + id + " не найдено!"));
        return socialTaskMapper.toDto(socialTask);
    }

    @Override
    public SocialTaskDto createSocialTask(SocialTaskDto socialTaskDto) {
        Long organizationId = socialTaskDto.getOrganizationId();

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Организация с id " + organizationId + " не найдена!"));
        if (socialTaskDto.getTaskReports() == null) {
            socialTaskDto.setTaskReports(new ArrayList<>());
        }
        SocialTask socialTask = socialTaskMapper.toEntity(socialTaskDto);
        socialTask.setOrganization(organization);
        socialTask.setStatusTask(StatusTask.PENDING);

        SocialTask savedSocialTask = socialTaskRepository.save(socialTask);

        return socialTaskMapper.toDto(savedSocialTask);
    }

    @Override
    public String deleteById(Long id) {
        Optional<SocialTask> optionalSocialTask = socialTaskRepository.findByIdAndRemoveDateIsNull(id);
        if (optionalSocialTask.isPresent()) {
            SocialTask socialTask = optionalSocialTask.get();
            socialTask.setRemoveDate(LocalDateTime.now());
            socialTaskRepository.save(socialTask);
            return "Deleted";
        } else throw new NullPointerException(String.format("Задание с id %s не найдено!", id));
    }

    @Override
    public void changeTaskStatus(Long taskId, StatusTask statusTask) {
        SocialTask socialTask = socialTaskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Задание с id  " + taskId + " не найдено!"));
        socialTask.setStatusTask(statusTask);
        socialTaskRepository.save(socialTask);
    }

    @Override
    public void getOrganizationTasks(Long organizationId) {
        List<SocialTask> tasks = socialTaskRepository.findByOrganizationId(organizationId);
        tasks.stream().map(socialTaskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public SocialTaskDto assignTaskToUser(Long taskId, Long userId) {
        Optional<SocialTask> taskOpt = Optional.ofNullable(socialTaskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задание с id   " + taskId + " не найдено!")));
        if (taskOpt.isPresent()) {
            SocialTask task = taskOpt.get();
            task.setAssignedUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + userId + " не найден!")));
            task.setStatusTask(StatusTask.IN_PROGRESS);
            return socialTaskMapper.toDto(socialTaskRepository.save(task));
        }
        return null;

    }

    public SocialTaskDto completeTask(Long taskId, MultipartFile reportPhoto, String reportDescription) throws IOException {
        Optional<SocialTask> taskOpt = socialTaskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            SocialTask task = taskOpt.get();
            if (task.getStatusTask() == StatusTask.IN_PROGRESS) {
                User assignedUser = task.getAssignedUser();
                if (assignedUser != null) {
                    BigDecimal bonus = task.getBonusForExecution();
                    if (bonus != null && bonus.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal currentBalance = assignedUser.getBalance() != null ? assignedUser.getBalance() : BigDecimal.ZERO;
                        BigDecimal newBalance = currentBalance.add(bonus);
                        assignedUser.setBalance(newBalance);
                        task.setBonusForExecution(BigDecimal.ZERO);
                        bonusHistoryService.addBonusHistory(assignedUser.getId(), bonus, currentBalance, newBalance, "Выполнил задание: " + task.getTitle());
                        userRepository.save(assignedUser);
                    }
                }

                TaskReport taskReport = new TaskReport();
                try {
                    byte[] photoBytes = reportPhoto.getBytes();
                    String base64EncodedPhoto = Base64.getEncoder().encodeToString(photoBytes);
                    taskReport.setPhoto(base64EncodedPhoto);
                } catch (IOException e) {
                }

                taskReport.setDescription(reportDescription);
                taskReport.setSocialTask(task);
                taskReportRepository.save(taskReport);
                task.getTaskReports().add(taskReport);
                task.setStatusTask(StatusTask.COMPLETED);
                socialTaskRepository.save(task);

                return socialTaskMapper.toDto(task);
            } else {
                throw new TaskNotInProgressException("Задача должна быть в статусе 'IN_PROGRESS', чтобы быть выполненной.");
            }
        } else {
            throw new NoSuchElementException("Задача с id " + taskId + " не найдена!");
        }
    }

    @Override
    public SocialTaskDto cancelSocialTask(Long taskId) {
        Optional<SocialTask> taskOpt = socialTaskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            SocialTask task = taskOpt.get();
            if (task.getStatusTask() == StatusTask.IN_PROGRESS) {
                task.setStatusTask(StatusTask.CANCELED);
                return socialTaskMapper.toDto(socialTaskRepository.save(task));
            } else {
                throw new TaskNotInProgressException("Задача должна быть в статусе 'IN_PROGRESS', чтобы отменить его.");
            }
        } else {
            throw new EntityNotFoundException("Задача с id " + taskId + " не найдена!");
        }
    }

    @Override
    public SocialTaskDto unAssignUser(Long taskId, Long userId) {
        Optional<SocialTask> taskOpt = socialTaskRepository.findById(taskId);
        if (!taskOpt.isPresent()) {
            throw new EntityNotFoundException("Задание с id " + taskId + " не найдено!");
        }
        SocialTask task = taskOpt.get();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + userId + " не найден!"));
        if (task.getAssignedUser() != null && task.getAssignedUser().getId().equals(user.getId())) {
            task.setAssignedUser(null);
            task.setStatusTask(StatusTask.PENDING);
            bonusHistoryService.addBonusHistory(userId, BigDecimal.ZERO.negate(), task.getAssignedUser().getBalance(), task.getAssignedUser().getBalance(), "Отменено назначение на задачу: " + taskId);
            return socialTaskMapper.toDto(socialTaskRepository.save(task));
        } else {
            throw new IllegalStateException("Задача не назначена пользователю с id " + userId);
        }
    }

    @Override
    public UserDetailsDto getAssignedUserDetails(Long taskId) {
        SocialTask socialTask = socialTaskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задача социальной сети с id " + taskId + " не найдена"));

        User assignedUser = socialTask.getAssignedUser();
        if (assignedUser == null) {
            throw new EntityNotFoundException("Назначенный пользователь для задачи социальной сети с id " + taskId + " не найден");
        }
        return userDetailsMapper.toDto(assignedUser);
    }
    @Override
    public OrganizationDetailsDto getOrganizationDetails(Long taskId) {
        SocialTask socialTask = socialTaskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задача социальной сети с id " + taskId + " не найдена!"));

        Organization organization = socialTask.getOrganization();
        if (organization == null) {
            throw new EntityNotFoundException("Организация для задачи социальной сети с id " + taskId + " не найдена!");
        }
        return organizationDetailsMapper.toDto(organization);
    }
    @Override
    public List<SocialTaskDto> listAssignedTasks(Long userId) {
        List<SocialTask> tasks = socialTaskRepository.findByAssignedUserIdAndRemoveDateIsNull(userId);
        return tasks.stream()
                .map(socialTaskMapper::toDto)
                .collect(Collectors.toList());
    }
}
