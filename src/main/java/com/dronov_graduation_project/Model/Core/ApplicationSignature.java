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
@Table(name = "application_signatures")
public class ApplicationSignature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "signing_employee_id", nullable = false)
    private Employee signingEmployee;

    @Column(name = "signature_date", nullable = false)
    private LocalDate signatureDate;

    @Column(name = "signature", nullable = false)
    private String signature;
}

