package com.dronov_graduation_project.Controller.User;

import com.dronov_graduation_project.Dto.Response.User.EmployeeResponse;
import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Service.User.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/employees")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Получить данные текущего аутентифицированного сотрудника
     */
    @GetMapping("/me")
    public ResponseEntity<EmployeeResponse> getCurrentEmployee(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Employee> employeeOpt = employeeService.findByUsername(userDetails.getUsername());
        return employeeOpt.map(emp -> {
            EmployeeResponse dto = new EmployeeResponse(
                    emp.getId(),
                    emp.getSurname(),
                    emp.getName(),
                    emp.getMiddleName(),
                    emp.getEmail(),
                    emp.getUsername(),
                    emp.getRole() != null ? emp.getRole().getName() : null
            );
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Получить ФИО сотрудника по ID (можно использовать для автозаполнения)
     */
    @GetMapping("/{id}/fullname")
    public ResponseEntity<String> getFullName(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeService.getById(id);
        if (employeeOpt.isPresent()) {
            Employee e = employeeOpt.get();
            String fullName = e.getSurname() + " " + e.getName() + " " + e.getMiddleName();
            return ResponseEntity.ok(fullName);
        }
        return ResponseEntity.notFound().build();
    }

    // Добавь другие методы по необходимости (например, список сотрудников и т.п.)
}
