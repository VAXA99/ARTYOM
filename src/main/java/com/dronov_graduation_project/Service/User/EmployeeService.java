package com.dronov_graduation_project.Service.User;

import com.dronov_graduation_project.Model.User.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сотрудниками
 */
public interface EmployeeService {

    /**
     * Получить список всех сотрудников
     * @return список сотрудников
     */
    List<Employee> getAll();

    /**
     * Найти сотрудника по идентификатору
     * @param id уникальный идентификатор сотрудника
     * @return сотрудник в Optional или пустой Optional, если не найден
     */
    Optional<Employee> getById(Long id);

    /**
     * Создать нового сотрудника
     * @param employee объект сотрудника для создания
     * @return созданный сотрудник с заполненным ID
     */
    Employee create(Employee employee);

    /**
     * Обновить данные существующего сотрудника
     * @param employee объект сотрудника с обновлёнными данными
     * @return обновлённый сотрудник
     */
    Employee update(Employee employee);

    /**
     * Удалить сотрудника по идентификатору
     * @param id уникальный идентификатор сотрудника для удаления
     */
    void delete(Long id);

    /**
     * Найти сотрудника по email
     * @param email email сотрудника
     * @return сотрудник в Optional или пустой Optional, если не найден
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Найти сотрудника по username
     * @param username имя пользователя сотрудника
     * @return сотрудник в Optional или пустой Optional, если не найден
     */
    Optional<Employee> findByUsername(String username);
}
