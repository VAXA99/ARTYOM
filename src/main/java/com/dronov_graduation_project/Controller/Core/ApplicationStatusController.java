package com.dronov_graduation_project.Controller.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationStatus;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistory;
import com.dronov_graduation_project.Service.Core.ApplicationService;
import com.dronov_graduation_project.Service.Core.ApplicationStatusHistoryService;
import com.dronov_graduation_project.Service.Core.ApplicationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/core/application-statuses")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ApplicationStatusController {

    private final ApplicationStatusHistoryService statusHistoryService;
    private final ApplicationService applicationService;
    private final ApplicationStatusService applicationStatusService;

    @GetMapping("/")
    public ResponseEntity<List<ApplicationStatus>> getAllStatuses() {
        List<ApplicationStatus> statuses = applicationStatusService.getAll();
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/{id}/status-history")
    public ResponseEntity<List<ApplicationStatusHistory>> getStatusHistory(@PathVariable Long id) {
        Optional<Application> applicationOpt = applicationService.getById(id);
        if (applicationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ApplicationStatusHistory> statusHistory = statusHistoryService.getStatusHistoryForApplication(applicationOpt.get());
        return ResponseEntity.ok(statusHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatus> getStatusById(@PathVariable Long id) {
        return applicationStatusService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ApplicationStatusHistory> getCurrentStatus(@PathVariable Long id) {
        Optional<Application> applicationOpt = applicationService.getById(id);
        if (applicationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<ApplicationStatusHistory> statusOpt = statusHistoryService.getLatestStatusForApplication(applicationOpt.get());
        return statusOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ApplicationStatus> createStatus(@RequestBody ApplicationStatus status) {
        ApplicationStatus created = applicationStatusService.create(status);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatus> updateStatus(@PathVariable Long id, @RequestBody ApplicationStatus status) {
        return applicationStatusService.getById(id)
                .map(existing -> {
                    status.setId(id);
                    ApplicationStatus updated = applicationStatusService.update(status);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        if (applicationStatusService.getById(id).isPresent()) {
            applicationStatusService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
