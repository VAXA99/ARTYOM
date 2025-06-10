package com.dronov_graduation_project.Service.Email;

/**
 * Сервис для отправки простых текстовых email сообщений
 */
public interface EmailService {

    /**
     * Отправляет email с простым текстом
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param text    содержание письма
     */
    void send(String to, String subject, String text);
}
