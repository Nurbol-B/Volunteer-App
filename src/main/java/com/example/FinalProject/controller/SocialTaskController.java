package com.example.FinalProject.controller;

import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.service.SocialTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/social-tasks")
@RequiredArgsConstructor
public class SocialTaskController {
    private final SocialTaskService socialTaskService;
    @GetMapping("/all")
    public ResponseEntity<List<SocialTaskDto>> getAllSocialTask() {
        List<SocialTaskDto> socialTaskDtos = socialTaskService.getAll();
        return new ResponseEntity<>(socialTaskDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialTaskDto> getById(@PathVariable Long id) {
        SocialTaskDto socialTaskDto = socialTaskService.findById(id);
        if (socialTaskDto != null) {
            return new ResponseEntity<>(socialTaskDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<SocialTaskDto> createSocialTask(@RequestBody SocialTaskDto socialTaskDto) {
        SocialTaskDto createSocialTask = socialTaskService.createSocialTask(socialTaskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSocialTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialTask(@PathVariable Long id) {
        socialTaskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/status")
    public ResponseEntity<Void> changeTaskStatus(@RequestParam Long taskId,
                                                 @RequestParam StatusTask statusTask){
        socialTaskService.changeTaskStatus(taskId, statusTask);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/organizationTask/{organizationId}")
    public ResponseEntity<SocialTaskDto> getTaskByOrganizationId(@PathVariable Long organizationId){
    socialTaskService.getOrganizationTasks(organizationId);
    return ResponseEntity.ok().build();
    }
    @PutMapping("/assign/{taskId}/user/{userId}")
    public ResponseEntity<SocialTaskDto> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        SocialTaskDto task = socialTaskService.assignTaskToUser(taskId, userId);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/complete/{taskId}")
    public ResponseEntity<SocialTaskDto> completeTask(@PathVariable Long taskId) {
        SocialTaskDto task = socialTaskService.completeTask(taskId);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SocialTaskDto>> getTasksByUser(@PathVariable Long userId) {
        List<SocialTaskDto> tasks = socialTaskService.getTasksByUser(userId);
        return ResponseEntity.ok(tasks);
    }
}
