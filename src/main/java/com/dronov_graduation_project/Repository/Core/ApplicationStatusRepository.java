package com.dronov_graduation_project.Repository.Core;

import com.dronov_graduation_project.Model.Core.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
}

