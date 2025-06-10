package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistory;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistoryId;
import com.dronov_graduation_project.Repository.Core.ApplicationStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с историей статусов заявлений
 */
@Service
public class ApplicationStatusHistoryServiceImpl implements ApplicationStatusHistoryService {

    private final ApplicationStatusHistoryRepository repository;

    public ApplicationStatusHistoryServiceImpl(ApplicationStatusHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationStatusHistory> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ApplicationStatusHistory> getStatusHistoryForApplication(Application application) {
        return repository.findByApplicationOrderByChangeDateAsc(application);
    }

    @Override
    public Optional<ApplicationStatusHistory> getById(ApplicationStatusHistoryId id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ApplicationStatusHistory> getLatestStatusForApplication(Application application) {
        return repository.findTopByApplicationOrderByChangeDateDesc(application);
    }

    @Override
    public ApplicationStatusHistory create(ApplicationStatusHistory history) {
        return repository.save(history);
    }

    @Override
    public ApplicationStatusHistory update(ApplicationStatusHistory history) {
        return repository.save(history);
    }

    @Override
    public void delete(ApplicationStatusHistoryId id) {
        repository.deleteById(id);
    }
}
