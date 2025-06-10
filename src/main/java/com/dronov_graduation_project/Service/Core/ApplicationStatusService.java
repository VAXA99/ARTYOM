package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationStatus;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со статусами заявлений
 */
public interface ApplicationStatusService {
    List<ApplicationStatus> getAll();
    Optional<ApplicationStatus> getById(Long id);
    ApplicationStatus create(ApplicationStatus status);
    ApplicationStatus update(ApplicationStatus status);
    void delete(Long id);
}
