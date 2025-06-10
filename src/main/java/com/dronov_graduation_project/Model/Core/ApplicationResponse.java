package com.dronov_graduation_project.Model.Core;

import com.dronov_graduation_project.Model.User.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_responses")
public class ApplicationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "responding_employee_id", nullable = false)
    private Employee respondingEmployee;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(name = "response_date", nullable = false)
    private LocalDate responseDate;

    private String comment;

    private Boolean rejected;
}

