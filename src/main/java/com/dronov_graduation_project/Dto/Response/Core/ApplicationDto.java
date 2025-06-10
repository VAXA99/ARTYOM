package com.dronov_graduation_project.Dto.Response.Core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApplicationDto {

    private Long id;

    private EmployeeDto employee;

    private LocalDate startDate;

    private LocalDate endDate;

    private ReasonDto reason;

    private List<StatusHistoryDto> statusHistory;

    // Если нужно, можно добавить подписи и ответы, иначе убрать

    @Data
    @NoArgsConstructor
    public static class EmployeeDto {
        private Long id;
        private String surname;
        private String name;
        private String middleName;
        private String email;
        private String username;
        private RoleDto role;
    }

    @Data
    @NoArgsConstructor
    public static class RoleDto {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    public static class ReasonDto {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    public static class StatusHistoryDto {
        private Long id;
        private StatusDto status;
        private String changeDate;  // Или LocalDateTime, смотря что используете
    }

    @Data
    @NoArgsConstructor
    public static class StatusDto {
        private Long id;
        private String name;
    }
}
