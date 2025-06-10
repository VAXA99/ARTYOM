package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationResponse;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для обработки ответов на заявления
 */
public interface ApplicationResponseService {
    List<ApplicationResponse> getAll();
    Optional<ApplicationResponse> getById(Long id);
    ApplicationResponse create(ApplicationResponse response);
    ApplicationResponse update(ApplicationResponse response);
    void delete(Long id);
}
