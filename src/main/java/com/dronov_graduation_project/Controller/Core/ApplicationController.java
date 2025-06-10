package com.dronov_graduation_project.Controller.Core;

import com.dronov_graduation_project.Dto.Response.Core.ApplicationDto;
import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Service.Core.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/core/applications")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ApplicationController {

    private final ApplicationService applicationService;

    private ApplicationDto mapToDto(Application app) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(app.getId());

        ApplicationDto.EmployeeDto empDto = new ApplicationDto.EmployeeDto();
        empDto.setId(app.getEmployee().getId());
        empDto.setSurname(app.getEmployee().getSurname());
        empDto.setName(app.getEmployee().getName());
        empDto.setMiddleName(app.getEmployee().getMiddleName());
        empDto.setEmail(app.getEmployee().getEmail());
        empDto.setUsername(app.getEmployee().getUsername());

        ApplicationDto.RoleDto roleDto = new ApplicationDto.RoleDto();
        roleDto.setId(app.getEmployee().getRole().getId());
        roleDto.setName(app.getEmployee().getRole().getName());
        empDto.setRole(roleDto);

        dto.setEmployee(empDto);

        ApplicationDto.ReasonDto reasonDto = new ApplicationDto.ReasonDto();
        reasonDto.setId(app.getReason().getId());
        reasonDto.setName(app.getReason().getName());
        dto.setReason(reasonDto);

        dto.setStartDate(app.getStartDate());
        dto.setEndDate(app.getEndDate());

        dto.setStatusHistory(app.getStatusHistory().stream().map(sh -> {
            ApplicationDto.StatusHistoryDto shDto = new ApplicationDto.StatusHistoryDto();
            shDto.setId(sh.getStatus().getId());

            ApplicationDto.StatusDto statusDto = new ApplicationDto.StatusDto();
            statusDto.setId(sh.getStatus().getId());
            statusDto.setName(sh.getStatus().getName());
            shDto.setStatus(statusDto);

            shDto.setChangeDate(sh.getChangeDate().toString()); // форматируйте по необходимости
            return shDto;
        }).collect(Collectors.toList()));

        return dto;
    }

    @GetMapping("/")
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        List<Application> applications = applicationService.getAll();
        List<ApplicationDto> dtos = applications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        return applicationService.getById(id)
                .map(this::mapToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByEmployeeId(@PathVariable Long employeeId) {
        List<Application> applications = applicationService.getByEmployeeId(employeeId);
        List<ApplicationDto> dtos = applications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody Application application) {
        Application created = applicationService.create(application);
        return ResponseEntity.ok(mapToDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDto> updateApplication(@PathVariable Long id, @RequestBody Application application) {
        return applicationService.getById(id)
                .map(existing -> {
                    application.setId(id);
                    Application updated = applicationService.update(application);
                    return ResponseEntity.ok(mapToDto(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (applicationService.getById(id).isPresent()) {
            applicationService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
