package com.dronov_graduation_project.Repository.Core;

import com.dronov_graduation_project.Model.Core.Application;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistory;
import com.dronov_graduation_project.Model.Core.ApplicationStatusHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, ApplicationStatusHistoryId> {

    @Query("SELECT ash FROM ApplicationStatusHistory ash WHERE ash.application = :application ORDER BY ash.changeDate ASC")
    List<ApplicationStatusHistory> findByApplicationOrderByChangeDateAsc(Application application);

    @Query("SELECT ash FROM ApplicationStatusHistory ash WHERE ash.application = :application ORDER BY ash.changeDate DESC")
    Optional<ApplicationStatusHistory> findTopByApplicationOrderByChangeDateDesc(Application application);
}

