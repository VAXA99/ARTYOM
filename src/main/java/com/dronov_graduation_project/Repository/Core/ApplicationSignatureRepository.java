package com.dronov_graduation_project.Repository.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationSignature;
import com.dronov_graduation_project.Model.User.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationSignatureRepository extends JpaRepository<ApplicationSignature, Long> {
    List<ApplicationSignature> findByApplication(Application application);

    // Метод для поиска подписей по сотруднику с ролью ROLE_USER
    List<ApplicationSignature> findBySigningEmployeeAndSigningEmployeeRoleName(Employee employee, String roleName);
}

