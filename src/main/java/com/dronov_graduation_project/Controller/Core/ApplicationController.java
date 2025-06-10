package com.dronov_graduation_project.Controller.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Service.Core.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/core/applications")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAll();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Application>> getApplicationsByEmployeeId(@PathVariable Long employeeId) {
        List<Application> applications = applicationService.getByEmployeeId(employeeId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application created = applicationService.create(application);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application application) {
        return applicationService.getById(id)
                .map(existing -> {
                    application.setId(id);
                    Application updated = applicationService.update(application);
                    return ResponseEntity.ok(updated);
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
