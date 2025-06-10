package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Repository.Core.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;

    // ctor
    public ApplicationServiceImpl(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Application> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Application> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Application> getByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public Application create(Application application) {
        return repository.save(application);
    }

    @Override
    public Application update(Application application) {
        return repository.save(application);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
