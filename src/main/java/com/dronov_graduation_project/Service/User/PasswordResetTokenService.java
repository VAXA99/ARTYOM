package com.dronov_graduation_project.Service.User;

import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Model.User.PasswordResetToken;

/**
 * Сервис для работы с токенами сброса пароля сотрудников (Employee)
 */
public interface PasswordResetTokenService {

    /**
     * Создаёт и сохраняет новый токен сброса пароля для сотрудника.
     * Если у сотрудника уже есть активный токен, он будет заменён.
     *
     * @param employee сотрудник, для которого создаётся токен
     * @return созданный объект PasswordResetToken
     */
    PasswordResetToken createToken(Employee employee);

    /**
     * Проверяет валидность токена сброса пароля:
     * существует ли он и не истёк ли срок действия.
     *
     * @param token строка токена для проверки
     * @return найденный валидный PasswordResetToken
     * @throws RuntimeException если токен невалиден или истёк
     */
    PasswordResetToken validateToken(String token);

    /**
     * Удаляет токен сброса пароля после использования или по необходимости.
     *
     * @param token строка токена для удаления
     */
    void removeToken(String token);
}
