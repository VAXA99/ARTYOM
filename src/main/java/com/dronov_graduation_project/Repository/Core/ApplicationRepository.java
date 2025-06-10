package com.dronov_graduation_project.Repository.Core;

import com.dronov_graduation_project.Model.Core.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByEmployeeId(Long employeeId);
}

