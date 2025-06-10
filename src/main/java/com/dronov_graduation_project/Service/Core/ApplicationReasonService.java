package com.dronov_graduation_project.Service.Core;

import com.dronov_graduation_project.Model.Core.ApplicationReason;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с причинами заявлений
 */
public interface ApplicationReasonService {

    /**
     * Получить список всех причин заявлений
     * @return список всех причин
     */
    List<ApplicationReason> getAll();

    /**
     * Получить причину по ID
     * @param id идентификатор причины
     * @return Optional с причиной или пустой, если не найдена
     */
    Optional<ApplicationReason> getById(Long id);

    /**
     * Создать новую причину заявления
     * @param reason объект причины
     * @return созданная причина
     */
    ApplicationReason create(ApplicationReason reason);

    /**
     * Обновить существующую причину
     * @param reason обновлённый объект причины
     * @return обновлённая причина
     */
    ApplicationReason update(ApplicationReason reason);

    /**
     * Удалить причину по ID
     * @param id идентификатор причины
     */
    void delete(Long id);
}
