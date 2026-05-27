package com.testai.controller.advice;

import com.testai.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений REST-контроллеров.
 * <p>
 * Перехватывает исключения, возникающие в слое контроллеров,
 * и преобразует их в единообразные HTTP-ответы с понятными
 * сообщениями об ошибках.
 * </p>
 *
 * <h3>Обрабатываемые исключения:</h3>
 * <ul>
 *   <li>{@link ResourceNotFoundException} → 404 Not Found</li>
 *   <li>{@link MethodArgumentNotValidException} → 400 Bad Request (с детализацией полей)</li>
 *   <li>{@link Exception} (общее) → 500 Internal Server Error</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link ResourceNotFoundException}.
     * <p>
     * Возвращает HTTP-ответ со статусом 404 Not Found и сообщением об ошибке.
     * </p>
     *
     * @param ex исключение, содержащее сообщение о ненайденном ресурсе
     * @return ответ с картой полей: timestamp, status, error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Обрабатывает исключение {@link MethodArgumentNotValidException}.
     * <p>
     * Возвращает HTTP-ответ со статусом 400 Bad Request и списком ошибок
     * валидации по каждому полю.
     * </p>
     *
     * @param ex исключение, содержащее результаты валидации
     * @return ответ с картой полей: timestamp, status, errors (поле → сообщение)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Обрабатывает все неперехваченные исключения (резервный обработчик).
     * <p>
     * Возвращает HTTP-ответ со статусом 500 Internal Server Error
     * и общим сообщением, не раскрывающим детали реализации.
     * </p>
     *
     * @param ex неперехваченное исключение
     * @return ответ с картой полей: timestamp, status, error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    /**
     * Вспомогательный метод для построения единообразного ответа с ошибкой.
     *
     * @param status  HTTP-статус ошибки
     * @param message текст сообщения об ошибке
     * @return ResponseEntity с картой полей: timestamp, status, error
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return ResponseEntity.status(status).body(body);
    }

}
