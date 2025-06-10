package com.dronov_graduation_project.Model.Core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplicationStatusHistoryId.class)
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Id
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ApplicationStatus status;

    @Column(name = "change_date", nullable = false)
    private LocalDate changeDate;
}

