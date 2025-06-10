package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationReason;
import com.dronov_graduation_project.Repository.Core.ApplicationReasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с причинами заявлений
 */
@Service
public class ApplicationReasonServiceImpl implements ApplicationReasonService {

    private final ApplicationReasonRepository repository;

    public ApplicationReasonServiceImpl(ApplicationReasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationReason> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ApplicationReason> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ApplicationReason create(ApplicationReason reason) {
        return repository.save(reason);
    }

    @Override
    public ApplicationReason update(ApplicationReason reason) {
        return repository.save(reason);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
