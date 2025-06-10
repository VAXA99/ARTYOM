package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationResponse;
import com.dronov_graduation_project.Repository.Core.ApplicationResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для обработки ответов на заявления
 */
@Service
public class ApplicationResponseServiceImpl implements ApplicationResponseService {

    private final ApplicationResponseRepository repository;

    public ApplicationResponseServiceImpl(ApplicationResponseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationResponse> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ApplicationResponse> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ApplicationResponse create(ApplicationResponse response) {
        return repository.save(response);
    }

    @Override
    public ApplicationResponse update(ApplicationResponse response) {
        return repository.save(response);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
