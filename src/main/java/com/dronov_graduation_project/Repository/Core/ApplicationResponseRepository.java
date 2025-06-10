package com.dronov_graduation_project.Repository.Core;

import com.dronov_graduation_project.Model.Core.ApplicationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationResponseRepository extends JpaRepository<ApplicationResponse, Long> {
}

