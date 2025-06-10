package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationStatus;
import com.dronov_graduation_project.Repository.Core.ApplicationStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы со статусами заявлений
 */
@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    private final ApplicationStatusRepository repository;

    public ApplicationStatusServiceImpl(ApplicationStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationStatus> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ApplicationStatus> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ApplicationStatus create(ApplicationStatus status) {
        return repository.save(status);
    }

    @Override
    public ApplicationStatus update(ApplicationStatus status) {
        return repository.save(status);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
