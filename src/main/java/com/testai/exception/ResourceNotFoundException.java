package com.testai.exception;

/**
 * Исключение, выбрасываемое при попытке обращения к несуществующему ресурсу.
 * <p>
 * Возникает в сервисном слое, когда товар с указанным ID не найден в базе данных.
 * Обрабатывается глобальным обработчиком исключений и преобразуется
 * в HTTP-ответ со статусом 404 Not Found.
 * </p>
 *
 * @see com.testai.controller.advice.GlobalExceptionHandler
 * @see com.testai.service.ProductService
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Создаёт исключение с сообщением об ошибке.
     *
     * @param message описание причины ошибки (например,
     *                "Product not found with id: 99")
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
