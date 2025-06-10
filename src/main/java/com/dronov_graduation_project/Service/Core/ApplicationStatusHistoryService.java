package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistory;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistoryId;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с историей статусов заявлений
 */
public interface ApplicationStatusHistoryService {
    List<ApplicationStatusHistory> getAll();
    List<ApplicationStatusHistory> getStatusHistoryForApplication(Application application);
    Optional<ApplicationStatusHistory> getById(ApplicationStatusHistoryId id);
    Optional<ApplicationStatusHistory> getLatestStatusForApplication(Application application);
    ApplicationStatusHistory create(ApplicationStatusHistory history);
    ApplicationStatusHistory update(ApplicationStatusHistory history);
    void delete(ApplicationStatusHistoryId id);
}
