package com.dronov_graduation_project.Service.User;

import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Model.User.PasswordResetToken;
import com.dronov_graduation_project.Repository.User.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Реализация сервиса для работы с токенами сброса пароля сотрудников
 */
@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    /**
     * Создаёт новый токен сброса пароля для сотрудника.
     * Удаляет старый активный токен, если он есть.
     */
    @Override
    public PasswordResetToken createToken(Employee employee) {
        tokenRepository.deleteByEmployee(employee); // удаляем предыдущий токен

        String rawToken = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plus(15, ChronoUnit.MINUTES);

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(rawToken);
        token.setEmployee(employee);
        token.setExpiryDate(expiryDate);

        return tokenRepository.save(token);
    }

    /**
     * Проверяет, существует ли токен и не истёк ли срок его действия.
     * Если невалиден — выбрасывает исключение.
     */
    @Override
    public PasswordResetToken validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(Instant.now()))
                .orElseThrow(() -> new RuntimeException("Токен недействителен или истёк"));
    }

    /**
     * Удаляет токен по значению после использования.
     */
    @Override
    public void removeToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
