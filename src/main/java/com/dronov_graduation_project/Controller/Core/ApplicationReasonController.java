package com.dronov_graduation_project.Controller.Core;

import com.dronov_graduation_project.Model.Core.ApplicationReason;
import com.dronov_graduation_project.Service.Core.ApplicationReasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/core/application-reasons")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ApplicationReasonController {

    private final ApplicationReasonService applicationReasonService;

    /**
     * Получить список всех причин заявлений
     */
    @GetMapping("/")
    public ResponseEntity<List<ApplicationReason>> getAllReasons() {
        List<ApplicationReason> reasons = applicationReasonService.getAll();
        return ResponseEntity.ok(reasons);
    }

    /**
     * Получить причину по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationReason> getReasonById(@PathVariable Long id) {
        return applicationReasonService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
