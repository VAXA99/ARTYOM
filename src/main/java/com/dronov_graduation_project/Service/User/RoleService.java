package com.dronov_graduation_project.Service.User;

import com.dronov_graduation_project.Model.User.Role;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ролями
 */
public interface RoleService {

    /**
     * Получить список всех ролей
     * @return список ролей
     */
    List<Role> getAll();

    /**
     * Найти роль по идентификатору
     * @param id уникальный идентификатор роли
     * @return роль в Optional или пустой Optional, если не найдена
     */
    Optional<Role> getById(Long id);

    /**
     * Создать новую роль
     * @param role объект роли для создания
     * @return созданная роль
     */
    Role create(Role role);

    /**
     * Обновить существующую роль
     * @param role объект роли с обновлёнными данными
     * @return обновлённая роль
     */
    Role update(Role role);

    /**
     * Удалить роль по идентификатору
     * @param id уникальный идентификатор роли для удаления
     */
    void delete(Long id);

    /**
     * Найти роль по имени
     * @param name название роли
     * @return роль в Optional или пустой Optional, если не найдена
     */
    Optional<Role> findByName(String name);
}
