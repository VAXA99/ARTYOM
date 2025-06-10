package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationSignature;
import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Repository.Core.ApplicationSignatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с подписями заявлений
 */
@Service
public class ApplicationSignatureServiceImpl implements ApplicationSignatureService {

    private final ApplicationSignatureRepository repository;

    public ApplicationSignatureServiceImpl(ApplicationSignatureRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ApplicationSignature> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ApplicationSignature> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ApplicationSignature> getBySigningEmployeeAndRole(Employee employee, String roleName) {
        return repository.findBySigningEmployeeAndSigningEmployeeRoleName(employee, roleName);
    }

    @Override
    public List<ApplicationSignature> getByApplication(Application application) {
        return repository.findByApplication(application);
    }

    @Override
    public ApplicationSignature create(ApplicationSignature signature) {
        return repository.save(signature);
    }

    @Override
    public ApplicationSignature update(ApplicationSignature signature) {
        return repository.save(signature);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
