package com.dronov_graduation_project.Controller.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationSignature;
import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Service.Core.ApplicationSignatureService;
import com.dronov_graduation_project.Service.Core.ApplicationService;
import com.dronov_graduation_project.Service.User.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/core/application-signatures")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ApplicationSignatureController {

    private final ApplicationSignatureService applicationSignatureService;
    private final ApplicationService applicationService;
    private final EmployeeService employeeService;  // сервис для сотрудников

    @GetMapping("/")
    public ResponseEntity<List<ApplicationSignature>> getAllSignatures() {
        List<ApplicationSignature> signatures = applicationSignatureService.getAll();
        return ResponseEntity.ok(signatures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationSignature> getSignatureById(@PathVariable Long id) {
        Optional<ApplicationSignature> signatureOpt = applicationSignatureService.getById(id);
        return signatureOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<ApplicationSignature>> getSignaturesByApplicationId(@PathVariable Long applicationId) {
        Optional<Application> applicationOpt = applicationService.getById(applicationId);
        if (applicationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ApplicationSignature> signatures = applicationSignatureService.getByApplication(applicationOpt.get());
        return ResponseEntity.ok(signatures);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ApplicationSignature>> getSignaturesByEmployeeWithRole(@PathVariable Long employeeId) {
        Optional<Employee> employeeOpt = employeeService.getById(employeeId);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Employee employee = employeeOpt.get();
        if (!"ROLE_USER".equals(employee.getRole().getName())) {
            return ResponseEntity.badRequest().body(null);
        }

        List<ApplicationSignature> signatures = applicationSignatureService
                .getBySigningEmployeeAndRole(employee, "ROLE_USER");
        return ResponseEntity.ok(signatures);
    }

    @PostMapping("/")
    public ResponseEntity<ApplicationSignature> createSignature(@RequestBody ApplicationSignature signature) {
        ApplicationSignature created = applicationSignatureService.create(signature);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationSignature> updateSignature(@PathVariable Long id,
                                                                @RequestBody ApplicationSignature signature) {
        return applicationSignatureService.getById(id)
                .map(existing -> {
                    signature.setId(id);
                    ApplicationSignature updated = applicationSignatureService.update(signature);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        if (applicationSignatureService.getById(id).isPresent()) {
            applicationSignatureService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
