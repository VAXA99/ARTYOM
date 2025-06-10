package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с заявлениями (Application)
 */
public interface ApplicationService {

    /**
     * Получить список всех заявлений
     */
    List<Application> getAll();

    /**
     * Получить заявление по ID
     */
    Optional<Application> getById(Long id);

    /**
     * Получение по ID сотрудника
     */
    List<Application> getByEmployeeId(Long employeeId);

    /**
     * Создать новое заявление
     */
    Application create(Application application);

    /**
     * Обновить существующее заявление
     */
    Application update(Application application);

    /**
     * Удалить заявление по ID
     */
    void delete(Long id);
}
