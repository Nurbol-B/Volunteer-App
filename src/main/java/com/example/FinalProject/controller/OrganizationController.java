package com.example.FinalProject.controller;

import com.example.FinalProject.dto.OrganizationDto;
import com.example.FinalProject.dto.SocialTaskDto;
import com.example.FinalProject.enums.StatusTask;
import com.example.FinalProject.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/all")
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        List<OrganizationDto> organizationDtos = organizationService.getAll();
        return new ResponseEntity<>(organizationDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getById(@PathVariable Long id) {
        OrganizationDto organizationDto =organizationService.findById(id);
        if (organizationDto != null) {
            return new ResponseEntity<>(organizationDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto createOrganization = organizationService.createOrganization(organizationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrganization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationDto> updateOrganization(@PathVariable Long organizationId, @RequestBody OrganizationDto organizationDto) {
        OrganizationDto updatedOrganization = organizationService.updateOrganization(organizationId, organizationDto);
        if (updatedOrganization != null) {
            return ResponseEntity.ok(updatedOrganization);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{organizationId}/tasks")
    public ResponseEntity<List<SocialTaskDto>> listAllTasks(@PathVariable Long organizationId) {
        List<SocialTaskDto> tasks = organizationService.listAllTasks(organizationId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/{organizationId}/tasks/status")
    public ResponseEntity<List<SocialTaskDto>> getTasksByStatus(@PathVariable Long organizationId,
                                                                @RequestParam StatusTask status) {
        return ResponseEntity.ok(organizationService.getTasksByStatus(organizationId, status));
    }

}
