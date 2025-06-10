package com.dronov_graduation_project.Repository.User;

import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Model.User.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByEmployee(Employee employee);
}

