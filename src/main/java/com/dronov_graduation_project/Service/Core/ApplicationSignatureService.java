package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationSignature;
import com.dronov_graduation_project.Model.User.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с подписями заявлений
 */
public interface ApplicationSignatureService {
    List<ApplicationSignature> getAll();
    Optional<ApplicationSignature> getById(Long id);
    List<ApplicationSignature> getBySigningEmployeeAndRole(Employee employee, String roleName);
    List<ApplicationSignature> getByApplication(Application application);
    ApplicationSignature create(ApplicationSignature signature);
    ApplicationSignature update(ApplicationSignature signature);
    void delete(Long id);
}
